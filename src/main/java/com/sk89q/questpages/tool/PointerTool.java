package com.sk89q.questpages.tool;

import com.sk89q.questpages.ui.component.BookPanel;

import java.awt.*;
import java.awt.event.MouseEvent;

public class PointerTool implements Tool {

    @Override
    public MouseContext createMouseContext(BookPanel panel, MouseEvent e) {
        if (panel.isShiftKeyEnabled()) {
            return new SelectionAddContext(panel);
        } else if (panel.isCtrlKeyEnabled()) {
            return new SelectionInvertContext(panel);
        } else {
            return new PointerContext(panel);
        }
    }

    @Override
    public Cursor getDefaultHoverCursor() {
        return Cursor.getDefaultCursor();
    }

}
