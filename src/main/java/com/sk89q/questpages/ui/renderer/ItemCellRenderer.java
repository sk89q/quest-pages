package com.sk89q.questpages.ui.renderer;

import com.sk89q.questpages.item.Item;
import com.sk89q.questpages.item.ItemImageLoader;
import com.sk89q.questpages.item.ItemToolTip;

import javax.swing.*;
import java.awt.*;

public class ItemCellRenderer extends DefaultListCellRenderer {

    @SuppressWarnings("unchecked")
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Item item = (Item) value;
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        label.setText(item.getDisplayName());
        label.setToolTipText(ItemToolTip.createToolTipText(item));
        Image image = ItemImageLoader.INSTANCE.getImage(item.getId());
        if (image != null) {
            label.setIcon(new ImageIcon(image));
        }
        label.add(new JLabel("test"));
        return label;
    }

}
