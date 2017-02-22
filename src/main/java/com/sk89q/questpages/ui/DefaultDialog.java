package com.sk89q.questpages.ui;

import com.sk89q.questpages.util.swing.SwingHelper;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

import static com.sk89q.questpages.util.SharedLocale.tr;

public class DefaultDialog extends JDialog {

    private final JButton okButton = new JButton(tr("button.ok"));
    private final JButton cancelButton = new JButton(tr("button.cancel"));

    public DefaultDialog(Window owner, String title, JComponent panel) {
        super(owner, title);

        setLayout(new MigLayout("insets dialog, fill", "", "[]push[]"));
        add(panel, "grow, span");
        add(okButton, "gaptop unrel, tag ok, span, split 2, sizegroup bttn");
        add(cancelButton, "tag cancel, sizegroup bttn");

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(owner);

        getRootPane().setDefaultButton(okButton);
        SwingHelper.setEscapeButton(getRootPane(), cancelButton);
    }
}
