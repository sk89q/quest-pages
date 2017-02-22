package com.sk89q.questpages.ui.common;

import com.sk89q.questpages.util.swing.SwingHelper;

import javax.swing.*;

public class XCheckBox extends JCheckBox {

    {
        SwingHelper.removeOpaqueness(this);
        addChangeListener(e -> firePropertyChange("selected", !isSelected(), isSelected()));
    }

    public XCheckBox() {
    }

    public XCheckBox(Icon icon) {
        super(icon);
    }

    public XCheckBox(Icon icon, boolean selected) {
        super(icon, selected);
    }

    public XCheckBox(String text) {
        super(text);
    }

    public XCheckBox(Action a) {
        super(a);
    }

    public XCheckBox(String text, boolean selected) {
        super(text, selected);
    }

    public XCheckBox(String text, Icon icon) {
        super(text, icon);
    }

    public XCheckBox(String text, Icon icon, boolean selected) {
        super(text, icon, selected);
    }

}
