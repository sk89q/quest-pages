package com.sk89q.questpages.tool;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public interface MouseContext extends MouseListener, MouseMotionListener {

    void paintBackground(Graphics g);

    void paintForeground(Graphics g);

    Cursor getHoverCursor();

}
