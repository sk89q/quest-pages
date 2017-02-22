package com.sk89q.questpages.item;

import static com.sk89q.questpages.util.swing.SwingHelper.htmlEscape;

public final class ItemToolTip {

    private ItemToolTip() {
    }

    public static String createToolTipText(Item item) {
        return "<html><strong>" + htmlEscape(item.getDisplayName()) + "</strong> " +
                "(" + item.getWorldSpecificId() + ":" + item.getId().getDamage() + ")<br>" +
                htmlEscape(item.getId().getName());
    }

}
