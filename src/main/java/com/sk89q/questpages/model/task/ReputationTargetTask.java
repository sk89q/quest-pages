package com.sk89q.questpages.model.task;

import com.sk89q.questpages.QuestPages;
import com.sk89q.questpages.ui.component.task.ReputationTaskPanel;
import com.sk89q.questpages.ui.component.task.TaskPanel;
import com.sk89q.questpages.util.swing.SwingHelper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.swing.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReputationTargetTask extends ReputationTask {

    private static final ImageIcon ICON = SwingHelper.readImageIcon(QuestPages.class, "task/reputation.png");

    @Override
    public String getTypeName() {
        return "Reach Reputation";
    }

    @Override
    public String getDisplayDesc(boolean isSelected) {
        return "40 reputation";
    }

    @Override
    public ImageIcon getDisplayIcon() {
        return ICON;
    }

    @Override
    public TaskPanel createEditPanel() {
        return new ReputationTaskPanel(this);
    }

}
