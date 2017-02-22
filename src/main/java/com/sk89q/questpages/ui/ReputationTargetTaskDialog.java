package com.sk89q.questpages.ui;

import com.sk89q.questpages.ui.common.XCheckBox;
import com.sk89q.questpages.util.swing.SwingHelper;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class ReputationTargetTaskDialog extends JDialog {

    private final JSpinner reputationSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000000, 1));
    private final JSpinner lowerSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000000, 1));
    private final JSpinner upperSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000000, 1));
    private final XCheckBox invertedReputationCheck = new XCheckBox("Invert the reputation criteria");

    public ReputationTargetTaskDialog() {
        SwingHelper.removeOpaqueness(this);

        setLayout(new MigLayout("insets 0"));

        add(new JLabel("Reputation ID:"));
        add(reputationSpinner, "span");

        add(new JLabel("Min. Reputation:"));
        add(lowerSpinner, "span");

        add(new JLabel("Max. Reputation:"));
        add(upperSpinner, "span");

        add(invertedReputationCheck, "span");
    }

}
