package com.sk89q.questpages.util.swing;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public final class SwingListeners {

    private SwingListeners() {
    }

    public static void addChangeListener(ItemSelectable component, ChangeListener changeListener) {
        component.addItemListener(e -> changeListener.stateChanged(new ChangeEvent(e.getSource())));
    }

    public static void addChangeListener(JSpinner component, ChangeListener changeListener) {
        component.addChangeListener(changeListener);
    }

    public static void addChangeListener(JTextComponent component, ChangeListener changeListener) {
        component.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                changeListener.stateChanged(new ChangeEvent(e.getSource()));
            }
        });
    }

}
