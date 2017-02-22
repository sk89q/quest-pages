package com.sk89q.questpages.ui.common;

import com.sk89q.questpages.util.swing.TextFieldPopupMenu;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;

public class XTextArea extends JTextArea {

    {
        setFont(UIManager.getDefaults().getFont("TextField.font"));
        setLineWrap(true);
        setWrapStyleWord(true);
        setComponentPopupMenu(TextFieldPopupMenu.INSTANCE);

        // Allow TAB to traverse
        setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
    }

    public XTextArea() {
    }

    public XTextArea(String text) {
        super(text);
    }

    public XTextArea(int rows, int columns) {
        super(rows, columns);
    }

    public XTextArea(String text, int rows, int columns) {
        super(text, rows, columns);
    }

    public XTextArea(Document doc) {
        super(doc);
    }

    public XTextArea(Document doc, String text, int rows, int columns) {
        super(doc, text, rows, columns);
    }

}
