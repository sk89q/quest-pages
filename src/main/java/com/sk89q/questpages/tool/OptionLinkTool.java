package com.sk89q.questpages.tool;

import com.sk89q.questpages.ui.component.BookPanel;
import com.sk89q.questpages.model.Quest;

import java.awt.*;
import java.awt.event.MouseEvent;

public class OptionLinkTool extends SelectionTool {

    @Override
    public MouseContext createMouseContext(BookPanel panel, MouseEvent e) {
        Quest clickedQuest = panel.getQuestAt(e.getX(), e.getY());

        if (clickedQuest != null) {
            return new OptionLinkContext(panel, clickedQuest);
        } else {
            return super.createMouseContext(panel, e);
        }
    }

    @Override
    public Cursor getDefaultHoverCursor() {
        return Cursor.getDefaultCursor();
    }

}
