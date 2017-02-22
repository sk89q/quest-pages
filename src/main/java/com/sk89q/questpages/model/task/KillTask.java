package com.sk89q.questpages.model.task;

import com.sk89q.questpages.QuestPages;
import com.sk89q.questpages.ui.component.task.KillTaskPanel;
import com.sk89q.questpages.ui.component.task.TaskPanel;
import com.sk89q.questpages.util.swing.SwingHelper;
import com.sk89q.questpages.model.Mob;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class KillTask extends Task {

    private static final ImageIcon ICON = SwingHelper.readImageIcon(QuestPages.class, "task/kill.png");

    private List<Mob> mobs = new ArrayList<>();

    @Override
    public String getTypeName() {
        return "Kill Mob";
    }

    @Override
    public String getDisplayDesc(boolean isSelected) {
        return "20x Creepers";
    }

    @Override
    public ImageIcon getDisplayIcon() {
        return ICON;
    }

    @Override
    public TaskPanel createEditPanel() {
        return new KillTaskPanel(this);
    }

}
