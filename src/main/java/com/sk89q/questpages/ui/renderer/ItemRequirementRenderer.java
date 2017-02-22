package com.sk89q.questpages.ui.renderer;

import com.sk89q.questpages.model.ItemRequirement;

import javax.swing.*;
import java.awt.*;

public class ItemRequirementRenderer extends ItemStackCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        ItemRequirement itemRequirement = (ItemRequirement) value;
        JLabel label = (JLabel) super.getListCellRendererComponent(list, itemRequirement.getItem(), index, isSelected, cellHasFocus);
        if (itemRequirement.getFluid() != null) {
            label.setText("<html>" + itemRequirement.getFluidLabel(isSelected));
        } else {
            label.setText("<html>" + itemRequirement.getItemLabel(isSelected));
        }
        return label;
    }

}
