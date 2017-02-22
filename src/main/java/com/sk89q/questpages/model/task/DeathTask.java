package com.sk89q.questpages.model.task;

import com.sk89q.questpages.QuestPages;
import com.sk89q.questpages.ui.component.task.DeathTaskPanel;
import com.sk89q.questpages.ui.component.task.TaskPanel;
import com.sk89q.questpages.util.swing.SwingHelper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.swing.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeathTask extends Task {

    private static final ImageIcon ICON = SwingHelper.readImageIcon(QuestPages.class, "task/death.png");

    private int deaths;

    @Override
    public String getTypeName() {
        return "Accumulate Deaths";
    }

    @Override
    public String getDisplayDesc(boolean isSelected) {
        return "4 deaths";
    }

    @Override
    public ImageIcon getDisplayIcon() {
        return ICON;
    }

    @Override
    public TaskPanel createEditPanel() {
        return new DeathTaskPanel(this);
    }


}
