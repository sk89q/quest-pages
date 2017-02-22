package com.sk89q.questpages.tool;

import com.sk89q.questpages.ui.component.BookPanel;

import java.awt.*;
import java.awt.event.MouseEvent;

public interface Tool {

    MouseContext createMouseContext(BookPanel panel, MouseEvent e);

    Cursor getDefaultHoverCursor();

}
