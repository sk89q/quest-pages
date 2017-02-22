package com.sk89q.questpages.util;

import java.awt.*;
import java.awt.geom.AffineTransform;

public final class Painters {

    private Painters() {
    }

    public static void drawArrow(Graphics2D g, int x1, int y1, int x2, int y2, int w, int h, boolean drawLine, double factor) {
        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) (Math.sqrt(dx * dx + dy * dy) * factor);
        AffineTransform previousTransform = g.getTransform();
        AffineTransform transform = AffineTransform.getTranslateInstance(x1, y1);
        transform.concatenate(AffineTransform.getRotateInstance(angle));
        g.transform(transform);
        if (drawLine && len > h) {
            g.drawLine(0, 0, len - h, 0);
        }
        g.fillPolygon(new int[]{len, len - w, len - w, len}, new int[] { 0, -h, h, 0 }, 4);
        g.setTransform(previousTransform);
    }

}
