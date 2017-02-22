package com.sk89q.questpages.tool;

import com.sk89q.questpages.ui.component.BookPanel;

import java.awt.event.MouseEvent;

public class PointerContext extends SelectionContext {

    public PointerContext(BookPanel panel) {
        super(panel);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

}
