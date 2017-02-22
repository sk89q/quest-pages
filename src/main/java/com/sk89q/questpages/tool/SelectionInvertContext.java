package com.sk89q.questpages.tool;

import com.sk89q.questpages.ui.component.BookPanel;
import com.sk89q.questpages.model.Quest;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

public class SelectionInvertContext extends SelectionContext implements MouseContext {

    private final BookPanel panel;
    private final Set<Quest> priorSelectedQuests = new HashSet<>();

    public SelectionInvertContext(BookPanel panel) {
        super(panel);
        this.panel = panel;
    }

    private void invertSelection() {
        for (Quest quest : priorSelectedQuests) {
            if (panel.getSelectedQuests().contains(quest)) {
                panel.removeSelectedQuest(quest);
            } else {
                panel.addSelectedQuest(quest);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        priorSelectedQuests.addAll(panel.getSelectedQuests());
        setSelection(e.getX(), e.getY(), e.getX(), e.getY());
        invertSelection();
        panel.repaint();
        panel.fireSelectedQuestsChange();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        setSelectionPoint2(e.getX(), e.getY());
        invertSelection();
        panel.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        panel.repaint();
        panel.fireSelectedQuestsChange();
    }

    @Override
    public Cursor getHoverCursor() {
        return Cursor.getDefaultCursor();
    }


}
