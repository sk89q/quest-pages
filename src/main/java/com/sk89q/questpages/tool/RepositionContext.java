package com.sk89q.questpages.tool;

import com.sk89q.questpages.ui.component.BookPanel;
import com.sk89q.questpages.history.Change;
import com.sk89q.questpages.history.CombinedChange;
import com.sk89q.questpages.model.Quest;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

import static com.sk89q.questpages.ui.component.BookPanel.SCALING;

public class RepositionContext extends MouseAdapter implements MouseContext {

    private final BookPanel panel;

    private boolean movedMouseAfterPress = false;
    private int dragStartX;
    private int dragStartY;
    private List<PositionChange> changes = new ArrayList<>();

    public RepositionContext(BookPanel panel) {
        this.panel = panel;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Quest clickedQuest = panel.getQuestAt(e.getX(), e.getY());

        // Clear selection if the user clicked on a quest not already in the selection
        if (!panel.getSelectedQuests().contains(clickedQuest)) {
            panel.setSelectedQuest(clickedQuest);
        }

        for (Quest quest : panel.getSelectedQuests()) {
            changes.add(new PositionChange(quest));
        }

        dragStartX = e.getX();
        dragStartY = e.getY();

        // Move to stop of stack
        panel.getQuests().remove(clickedQuest);
        panel.getQuests().add(clickedQuest);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        movedMouseAfterPress = true;

        int xOffset = (e.getX() - dragStartX) / SCALING;
        int yOffset = (e.getY() - dragStartY) / SCALING;

        for (PositionChange change : changes) {
            change.quest.setX(change.oldX + xOffset);
            change.quest.setY(change.oldY + yOffset);
            change.newX = change.quest.getX();
            change.newY = change.quest.getY();
        }

        panel.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!movedMouseAfterPress) {
            Quest questUnderPointer = panel.getQuestAt(e.getX(), e.getY());
            if (questUnderPointer != null) {
                panel.setSelectedQuest(questUnderPointer);
            } else {
                panel.setSelectedQuest(null);
            }
        } else {
            panel.getHistory().commit(new CombinedChange("Move Quests", changes));
        }
        panel.fireSelectedQuestsChange();
    }

    @Override
    public void paintBackground(Graphics g) {

    }

    @Override
    public void paintForeground(Graphics g) {

    }

    @Override
    public Cursor getHoverCursor() {
        return Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
    }

    private static class PositionChange implements Change {
        private final Quest quest;
        private final int oldX;
        private final int oldY;
        private int newX;
        private int newY;

        private PositionChange(Quest quest) {
            this.quest = quest;
            oldX = quest.getX();
            oldY = quest.getY();
            newX = quest.getX();
            newY = quest.getY();
        }

        @Override
        public String getName() {
            return "";
        }

        @Override
        public boolean containsChange() {
            return oldX != newY && oldY != newY;
        }

        @Override
        public void undo() {
            quest.setX(oldX);
            quest.setY(oldY);
        }

        @Override
        public void redo() {
            quest.setX(newX);
            quest.setY(newY);
        }
    }

}
