package com.sk89q.questpages.ui.common;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class FormPanel extends JPanel {

    public FormPanel() {
        this("");
    }

    public FormPanel(String constraints) {
        this(constraints, "", "");
    }

    public FormPanel(String constraints, String colConstraints, String rowConstraints) {
        setLayout(new MigLayout(combineConstraints("insets 0, fillx", constraints), colConstraints, rowConstraints));
    }

    public FormPanel component(Component comp) {
        add(comp);
        return this;
    }

    public FormPanel label(JComponent component, String constraints) {
        add(component, combineConstraints("", constraints));
        return this;
    }

    public FormPanel label(JComponent component) {
        return label(component, "");
    }

    public FormPanel field(JComponent component, String constraints) {
        add(component, combineConstraints("span", constraints));
        return this;
    }

    public FormPanel field(JComponent component) {
        return field(component, "");
    }

    public FormPanel spanningField(JComponent component, String constraints) {
        add(component, combineConstraints("growx, span", constraints));
        return this;
    }

    public FormPanel spanningField(JComponent component) {
        return spanningField(component, "");
    }

    public FormPanel buttons(JButton okButton, JButton cancelButton) {
        add(okButton, "gaptop unrel, tag ok, span, split 2, sizegroup bttn");
        add(cancelButton, "tag cancel, sizegroup bttn");
        return this;
    }

    private static String combineConstraints(String a, String b) {
        if (a.isEmpty()) {
            return b;
        } else if (b.isEmpty()) {
            return a;
        } else {
            return a + ", " + b;
        }
    }

}
