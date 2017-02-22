package com.sk89q.questpages.ui.common;

import com.sk89q.questpages.util.swing.TextFieldPopupMenu;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class XTextField extends JTextField {

    {
        setComponentPopupMenu(TextFieldPopupMenu.INSTANCE);

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                selectAll();
            }
        });
    }

    public XTextField() {
    }

    public XTextField(String text) {
        super(text);
    }

    public XTextField(int columns) {
        super(columns);
    }

    public XTextField(String text, int columns) {
        super(text, columns);
    }

    public XTextField(Document doc, String text, int columns) {
        super(doc, text, columns);
    }

}
