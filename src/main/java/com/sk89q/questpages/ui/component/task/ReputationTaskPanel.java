package com.sk89q.questpages.ui.component.task;

import com.sk89q.questpages.ui.component.ModifiableListPanel;
import com.sk89q.questpages.model.ReputationTarget;
import com.sk89q.questpages.model.task.ReputationTask;
import com.sk89q.questpages.model.task.Task;
import com.sk89q.questpages.util.swing.SwingHelper;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ReputationTaskPanel extends TaskPanel {

    private final ReputationTask task;
    private final ModifiableListPanel<ReputationTarget> requirementsList = new ModifiableListPanel<>();

    public ReputationTaskPanel(ReputationTask task) {
        this.task = task;

        SwingHelper.removeOpaqueness(this);
        setLayout(new MigLayout("insets 0, fill"));

        add(new JLabel("Reputations:"), "span");
        add(requirementsList, "w 200, h 200, grow, span");

        List<ReputationTarget> newList = new ArrayList<>();
        for (ReputationTarget entry : task.getReputations()) {
            newList.add(new ReputationTarget(entry));
        }

        requirementsList.setModel(newList);
    }

    @Override
    public Task getChangedTask() {
        task.setReputations(requirementsList.getEntries());
        return task;
    }

}
