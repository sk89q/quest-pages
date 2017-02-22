package com.sk89q.questpages.ui.component.task;

import com.sk89q.questpages.model.task.ReputationKillTask;
import com.sk89q.questpages.model.task.Task;

import javax.swing.*;

public class ReputationKillTaskPanel extends ReputationTaskPanel {

    private final ReputationKillTask task;

    private final JSpinner killsSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));

    public ReputationKillTaskPanel(ReputationKillTask task) {
        super(task);
        this.task = task;

        add(new JLabel("Required Kills:"));
        add(killsSpinner, "gaptop unrel, span");

        killsSpinner.setValue(task.getKills());
    }

    @Override
    public Task getChangedTask() {
        ReputationKillTask task = (ReputationKillTask) super.getChangedTask();
        task.setKills((Integer) killsSpinner.getValue());
        return task;
    }

}
