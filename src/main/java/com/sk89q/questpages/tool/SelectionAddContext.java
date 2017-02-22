package com.sk89q.questpages.tool;

import com.sk89q.questpages.ui.component.BookPanel;
import com.sk89q.questpages.model.Quest;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

public class SelectionAddContext extends SelectionContext implements MouseContext {

    private final BookPanel panel;
    private final Set<Quest> priorSelectedQuests = new HashSet<>();

    public SelectionAddContext(BookPanel panel) {
        super(panel);
        this.panel = panel;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        priorSelectedQuests.addAll(panel.getSelectedQuests());
        setSelection(e.getX(), e.getY(), e.getX(), e.getY());
        panel.addSelectedQuests(priorSelectedQuests);
        panel.repaint();
        panel.fireSelectedQuestsChange();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        setSelectionPoint2(e.getX(), e.getY());
        panel.addSelectedQuests(priorSelectedQuests);
        panel.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        setSelectionPoint2(e.getX(), e.getY());
        panel.addSelectedQuests(priorSelectedQuests);
        panel.repaint();
        panel.fireSelectedQuestsChange();
    }

    @Override
    public Cursor getHoverCursor() {
        return Cursor.getDefaultCursor();
    }

}
