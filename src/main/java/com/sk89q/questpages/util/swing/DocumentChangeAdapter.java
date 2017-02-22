package com.sk89q.questpages.util.swing;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public abstract  class DocumentChangeAdapter implements DocumentListener {

    protected abstract void change(DocumentEvent e);

    @Override
    public void insertUpdate(DocumentEvent e) {
        change(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        change(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        change(e);
    }

}
