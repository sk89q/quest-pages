package com.sk89q.questpages.model.task;

import com.sk89q.questpages.QuestPages;
import com.sk89q.questpages.ui.component.task.ReputationKillTaskPanel;
import com.sk89q.questpages.ui.component.task.TaskPanel;
import com.sk89q.questpages.util.swing.SwingHelper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.swing.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReputationKillTask extends ReputationTask {

    private static final ImageIcon ICON = SwingHelper.readImageIcon(QuestPages.class, "task/kill.png");

    private int kills;

    @Override
    public String getTypeName() {
        return "Kill Someone with Rep";
    }

    @Override
    public String getDisplayDesc(boolean isSelected) {
        return "24 reputation";
    }

    @Override
    public ImageIcon getDisplayIcon() {
        return ICON;
    }

    @Override
    public TaskPanel createEditPanel() {
        return new ReputationKillTaskPanel(this);
    }

}
