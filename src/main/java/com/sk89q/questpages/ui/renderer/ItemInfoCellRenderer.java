package com.sk89q.questpages.ui.renderer;

import com.sk89q.questpages.item.ItemId;
import com.sk89q.questpages.item.ItemImageLoader;
import com.sk89q.questpages.item.ItemInfo;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ItemInfoCellRenderer extends DefaultListCellRenderer {

    @SuppressWarnings("unchecked")
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Map.Entry<ItemId, ItemInfo> entry = (Map.Entry<ItemId, ItemInfo>) value;
        ItemId itemId = entry.getKey();
        ItemInfo info = entry.getValue();
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        label.setText(info.getDisplayName());
        label.setToolTipText(info.getDisplayName());
        Image image = ItemImageLoader.INSTANCE.getImage(itemId);
        if (image != null) {
            label.setIcon(new ImageIcon(image));
        }
        return label;
    }

}
