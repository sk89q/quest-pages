package com.sk89q.questpages.tool;

import com.sk89q.questpages.ui.component.BookPanel;
import com.sk89q.questpages.model.Quest;

import java.awt.*;
import java.awt.event.MouseEvent;

public class SelectionTool implements Tool {

    @Override
    public MouseContext createMouseContext(BookPanel panel, MouseEvent e) {
        Quest clickedQuest = panel.getQuestAt(e.getX(), e.getY());

        if (panel.isShiftKeyEnabled()) {
            return new SelectionAddContext(panel);
        } else if (panel.isCtrlKeyEnabled()) {
            return new SelectionInvertContext(panel);
        } else if (clickedQuest != null) {
            return new RepositionContext(panel);
        } else {
            return new SelectionContext(panel);
        }
    }

    @Override
    public Cursor getDefaultHoverCursor() {
        return Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
    }

}
