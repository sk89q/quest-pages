package com.sk89q.questpages.ui.renderer;

import com.sk89q.questpages.item.ItemDatabase;
import com.sk89q.questpages.item.ItemImageLoader;
import com.sk89q.questpages.item.Item;
import com.sk89q.questpages.model.ItemStack;

import javax.swing.*;
import java.awt.*;

public class ItemStackCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        ItemStack itemStack = (ItemStack) value;
        Item info = ItemDatabase.INSTANCE.getItemInfo(itemStack.toItemId());
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        label.setBorder(BorderFactory.createCompoundBorder(label.getBorder(), BorderFactory.createEmptyBorder(4, 4, 4, 4)));
        label.setText(itemStack.getAmount() + "x " + (info != null ? info.getDisplayName() : itemStack.getId()));
        Image image = ItemImageLoader.INSTANCE.getImage(itemStack);
        if (image != null) {
            label.setIcon(new ImageIcon(image));
        }
        return label;
    }

}
