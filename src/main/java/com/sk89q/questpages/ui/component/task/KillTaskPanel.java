package com.sk89q.questpages.ui.component.task;

import com.sk89q.questpages.ui.component.ModifiableListPanel;
import com.sk89q.questpages.model.Mob;
import com.sk89q.questpages.model.task.KillTask;
import com.sk89q.questpages.model.task.Task;
import com.sk89q.questpages.util.swing.SwingHelper;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class KillTaskPanel extends TaskPanel {

    private final ModifiableListPanel<Mob> mobList = new ModifiableListPanel<>();
    private final KillTask task;

    public KillTaskPanel(KillTask task) {
        this.task = task;

        SwingHelper.removeOpaqueness(this);
        setLayout(new MigLayout("insets 0, fill"));

        add(new JLabel("Mobs to Slay:"), "span");
        add(mobList, "w 200, h 200, grow, span");

        List<Mob> newList = new ArrayList<>();
        for (Mob entry : task.getMobs()) {
            newList.add(new Mob(entry));
        }

        mobList.setModel(newList);
    }

    @Override
    public Task getChangedTask() {
        task.setMobs(mobList.getEntries());
        return task;
    }

}
