package com.sk89q.questpages.tool;

import com.sk89q.questpages.ui.component.BookPanel;
import com.sk89q.questpages.history.Change;
import com.sk89q.questpages.history.CollectionChangeProxy;
import com.sk89q.questpages.history.CombinedChange;
import com.sk89q.questpages.model.Quest;

import java.awt.*;
import java.util.*;
import java.util.List;

public class RequirementLinkContext extends LinkCreationContext {

    public RequirementLinkContext(BookPanel panel, Quest childQuest) {
        super(panel, childQuest);
    }

    @Override
    protected Color getColor() {
        return BookPanel.REQUIREMENT_COLOR;
    }

    @Override
    protected void acceptLink(Quest parentQuest, Quest childQuest) {
        List<Change> changes = new ArrayList<>();
        CollectionChangeProxy<Quest> parentRequirements = new CollectionChangeProxy<>(changes, parentQuest.getRequirements());
        CollectionChangeProxy<Quest> parentOptions = new CollectionChangeProxy<>(changes, parentQuest.getOptions());
        CollectionChangeProxy<Quest> childRequirements = new CollectionChangeProxy<>(changes, childQuest.getRequirements());
        CollectionChangeProxy<Quest> childOptions = new CollectionChangeProxy<>(changes, childQuest.getOptions());

        parentRequirements.remove(childQuest);
        if (childQuest.getRequirements().contains(parentQuest)) {
            childRequirements.remove(parentQuest);
        } else {
            childRequirements.add(parentQuest);
            childOptions.remove(parentQuest);
            parentOptions.remove(childQuest);
        }

        panel.getHistory().commit(new CombinedChange("Change Requirements", changes));
    }

}
