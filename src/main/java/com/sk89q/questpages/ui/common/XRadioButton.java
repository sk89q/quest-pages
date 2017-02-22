package com.sk89q.questpages.ui.common;

import com.sk89q.questpages.util.swing.SwingHelper;

import javax.swing.*;

public class XRadioButton extends JRadioButton {

    {
        SwingHelper.removeOpaqueness(this);
        addChangeListener(e -> firePropertyChange("selected", !isSelected(), isSelected()));
    }

    public XRadioButton() {
    }

    public XRadioButton(Icon icon) {
        super(icon);
    }

    public XRadioButton(Action a) {
        super(a);
    }

    public XRadioButton(Icon icon, boolean selected) {
        super(icon, selected);
    }

    public XRadioButton(String text) {
        super(text);
    }

    public XRadioButton(String text, boolean selected) {
        super(text, selected);
    }

    public XRadioButton(String text, Icon icon) {
        super(text, icon);
    }

    public XRadioButton(String text, Icon icon, boolean selected) {
        super(text, icon, selected);
    }

}
