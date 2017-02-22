package com.sk89q.questpages.ui.component;

import com.sk89q.questpages.model.Quest;
import com.sk89q.questpages.tool.MouseContext;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

class BookMouseListener implements MouseListener, MouseMotionListener {

    private final BookPanel panel;
    private MouseContext context = null;

    public BookMouseListener(BookPanel panel) {
        this.panel = panel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (context != null) {
            context.mouseClicked(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        panel.requestFocusInWindow();

        context = panel.getTool().createMouseContext(panel, e);

        if (context != null) {
            context.mousePressed(e);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (context != null) {
            context.mouseDragged(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (context != null) {
            context.mouseMoved(e);
        }

        Quest questUnderPointer = panel.getQuestAt(e.getX(), e.getY());

        if (questUnderPointer != panel.getHoveredQuest()) {
            panel.setHoveredQuest(questUnderPointer);
            if (questUnderPointer == null) {
                panel.setCursor(Cursor.getDefaultCursor());
            } else {
                panel.setCursor(context != null ? context.getHoverCursor() : panel.getTool().getDefaultHoverCursor());
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (context != null) {
            context.mouseReleased(e);
            context = null;
        }

        panel.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (context != null) {
            context.mouseEntered(e);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (context != null) {
            context.mouseExited(e);
        }
    }

    public void paintBackground(Graphics g) {
        if (context != null) {
            context.paintBackground(g);
        }
    }

    public void paintForeground(Graphics g) {
        if (context != null) {
            context.paintForeground(g);
        }
    }

}
