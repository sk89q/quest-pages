package com.sk89q.questpages.util;

import java.awt.*;
import java.awt.image.ImageObserver;

public final class GraphicsUtils {

    private GraphicsUtils() {
    }

    public static void drawScaledImage(Graphics g, Image image, int x, int y, int scale, ImageObserver observer) {
        g.drawImage(image, x, y, image.getWidth(observer) * scale, image.getHeight(observer) * scale, observer);
    }

}
