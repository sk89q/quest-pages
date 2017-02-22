package com.sk89q.questpages.util.swing;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InstantTooltipListener extends MouseAdapter {

    private final int defaultInitialDelay = ToolTipManager.sharedInstance().getInitialDelay();

    public void mouseEntered(MouseEvent event) {
        ToolTipManager.sharedInstance().setInitialDelay(0);
    }

    public void mouseExited(MouseEvent event) {
        ToolTipManager.sharedInstance().setInitialDelay(defaultInitialDelay);
    }

}
