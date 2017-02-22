package com.sk89q.questpages.tool;

import com.sk89q.questpages.ui.component.BookPanel;
import com.sk89q.questpages.model.Quest;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Set;

public class SelectionContext extends MouseAdapter implements MouseContext {

    private static final Stroke SELECTION_STROKE_BG = new BasicStroke(1);
    private static final Stroke SELECTION_STROKE = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 4, new float[] { 4 }, 0);

    private final BookPanel panel;
    private boolean movedMouseAfterPress = false;
    private int selectionX1;
    private int selectionY1;
    private int selectionX2;
    private int selectionY2;

    public SelectionContext(BookPanel panel) {
        this.panel = panel;
    }

    public void setSelection(int x1, int y1, int x2, int y2) {
        this.selectionX1 = x1;
        this.selectionY1 = y1;
        this.selectionX2 = x2;
        this.selectionY2 = y2;
        panel.setSelectedQuests(getQuestsWithinSelection());
    }

    public void setSelectionPoint2(int x2, int y2) {
        this.selectionX2 = x2;
        this.selectionY2 = y2;
        panel.setSelectedQuests(getQuestsWithinSelection());
    }

    public Set<Quest> getQuestsWithinSelection() {
        return panel.getQuestsWithin(selectionX1, selectionY1, selectionX2, selectionY2);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        setSelection(e.getX(), e.getY(), e.getX(), e.getY());
        panel.fireSelectedQuestsChange();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        movedMouseAfterPress = true;

        setSelectionPoint2(e.getX(), e.getY());
        panel.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!movedMouseAfterPress) {
            panel.setSelectedQuest(null);
        }
        panel.fireSelectedQuestsChange();
    }

    @Override
    public void paintBackground(Graphics g) {
    }

    @Override
    public void paintForeground(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        int x = Math.min(selectionX1, selectionX2);
        int y = Math.min(selectionY1, selectionY2);
        int w = Math.max(selectionX1, selectionX2) - x;
        int h = Math.max(selectionY1, selectionY2) - y;
        g2d.setColor(Color.WHITE);
        g2d.setStroke(SELECTION_STROKE_BG);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(SELECTION_STROKE);
        g.drawRect(x, y, w, h);
    }

    @Override
    public Cursor getHoverCursor() {
        return Cursor.getDefaultCursor();
    }

}
