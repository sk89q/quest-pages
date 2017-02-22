package com.sk89q.questpages.tool;

import com.sk89q.questpages.ui.component.BookPanel;
import com.sk89q.questpages.history.Change;
import com.sk89q.questpages.history.CollectionChangeProxy;
import com.sk89q.questpages.history.CombinedChange;
import com.sk89q.questpages.model.Quest;

import java.awt.*;
import java.util.*;
import java.util.List;

public class OptionLinkContext extends LinkCreationContext {

    public OptionLinkContext(BookPanel panel, Quest childQuest) {
        super(panel, childQuest);
    }

    @Override
    protected Color getColor() {
        return BookPanel.OPTION_COLOR;
    }

    @Override
    protected void acceptLink(Quest parentQuest, Quest childQuest) {
        List<Change> changes = new ArrayList<>();
        CollectionChangeProxy<Quest> parentRequirements = new CollectionChangeProxy<>(changes, parentQuest.getRequirements());
        CollectionChangeProxy<Quest> parentOptions = new CollectionChangeProxy<>(changes, parentQuest.getOptions());
        CollectionChangeProxy<Quest> childRequirements = new CollectionChangeProxy<>(changes, childQuest.getRequirements());
        CollectionChangeProxy<Quest> childOptions = new CollectionChangeProxy<>(changes, childQuest.getOptions());

        parentOptions.remove(childQuest);
        if (childQuest.getRequirements().contains(parentQuest)) {
            childOptions.remove(parentQuest);
        } else {
            childOptions.add(parentQuest);
            childRequirements.remove(parentQuest);
            parentRequirements.remove(childQuest);
        }

        panel.getHistory().commit(new CombinedChange("Change Options", changes));
    }

}
