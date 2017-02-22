package com.sk89q.questpages.tool;

import com.sk89q.questpages.ui.component.BookPanel;
import com.sk89q.questpages.model.Quest;
import com.sk89q.questpages.util.Painters;

import java.awt.*;
import java.awt.event.MouseEvent;

import static com.sk89q.questpages.ui.component.BookPanel.SCALING;

public abstract class LinkCreationContext extends SelectionContext implements MouseContext {

    private final Stroke TARGET_STROKE = new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

    protected final BookPanel panel;
    private final Quest parentQuest;
    private int curX;
    private int curY;

    public LinkCreationContext(BookPanel panel, Quest parentQuest) {
        super(panel);
        this.panel = panel;
        this.parentQuest = parentQuest;
    }

    protected abstract void acceptLink(Quest parentQuest, Quest childQuest);

    protected abstract Color getColor();

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        this.curX = e.getX();
        this.curY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.curX = e.getX();
        this.curY = e.getY();
        panel.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Quest childQuest = panel.getQuestAt(e.getX(), e.getY());
        if (childQuest != null && parentQuest != childQuest) {
            acceptLink(parentQuest, childQuest);
            panel.repaint();
        }
        panel.fireSelectedQuestsChange();
    }

    @Override
    public void paintForeground(Graphics g) {
        super.paintForeground(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(getColor());

        // Draw circle over child quest
        Quest questUnderPointer = panel.getQuestAt(curX, curY);
        if (questUnderPointer != null && questUnderPointer != parentQuest) {
            int x = questUnderPointer.getX() * SCALING;
            int y = questUnderPointer.getY() * SCALING;
            int width = panel.getQuestIcon(questUnderPointer).getWidth(panel) * SCALING;
            int height = panel.getQuestIcon(questUnderPointer).getHeight(panel) * SCALING;
            int length = width;

            g2d.setStroke(TARGET_STROKE);
            g2d.drawOval(
                    x + (width - length) / 2 - 1,
                    y + (height - length) / 2 - 1,
                    length,
                    length);
        }

        // Draw arrow
        g2d.setStroke(BookPanel.LINK_STROKE);
        int x = (parentQuest.getX() + panel.getQuestIcon(parentQuest).getWidth(panel) / 2) * SCALING;
        int y = (parentQuest.getY() + panel.getQuestIcon(parentQuest).getHeight(panel) / 2) * SCALING;
        Painters.drawArrow(g2d, x, y, curX, curY, 15, 9, true, 1);
    }

    @Override
    public Cursor getHoverCursor() {
        return Cursor.getDefaultCursor();
    }

}
