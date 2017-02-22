package com.sk89q.questpages.ui.component;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class BookKeyListener extends KeyAdapter {

    private static final int CTRL_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

    private final BookPanel panel;

    public BookKeyListener(BookPanel panel) {
        this.panel = panel;
    }

    private void updateMasks(KeyEvent e) {
        if ((e.getModifiers() & CTRL_MASK) == CTRL_MASK) {
            panel.setCtrlKeyEnabled(true);
        } else {
            panel.setCtrlKeyEnabled(false);
        }

        if ((e.getModifiers() & KeyEvent.SHIFT_MASK) == KeyEvent.SHIFT_MASK) {
            panel.setShiftKeyEnabled(true);
        } else {
            panel.setShiftKeyEnabled(false);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        updateMasks(e);
        if (panel.isCtrlKeyEnabled() && e.getKeyCode() == KeyEvent.VK_A) {
            panel.setSelectedQuests(panel.getQuests());
            panel.repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        updateMasks(e);
    }

}
