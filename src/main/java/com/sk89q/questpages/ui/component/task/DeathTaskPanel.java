package com.sk89q.questpages.ui.component.task;

import com.sk89q.questpages.model.task.DeathTask;
import com.sk89q.questpages.model.task.Task;
import com.sk89q.questpages.util.swing.SwingHelper;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class DeathTaskPanel extends TaskPanel {

    private final JSpinner deathSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
    private final DeathTask task;

    public DeathTaskPanel(DeathTask task) {
        this.task = task;

        SwingHelper.removeOpaqueness(this);

        setLayout(new MigLayout("insets 0"));

        add(new JLabel("Number of Deaths:"));
        add(deathSpinner, "span");

        deathSpinner.setValue(task.getDeaths());
    }

    @Override
    public Task getChangedTask() {
        task.setDeaths(task.getDeaths());
        return task;
    }

}
