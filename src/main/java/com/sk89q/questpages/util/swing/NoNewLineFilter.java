package com.sk89q.questpages.util.swing;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class NoNewLineFilter extends DocumentFilter {

    public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
        fb.insertString(offset, text.replaceAll("[\\n\\r]", ""), attr);
    }

    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attr) throws BadLocationException {
        fb.replace(offset, length, text.replaceAll("[\\n\\r]", ""), attr);
    }

}
