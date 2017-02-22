package com.sk89q.questpages.util.swing;

import com.sk89q.questpages.item.Item;
import com.sk89q.questpages.item.ItemDatabase;
import com.sk89q.questpages.item.ItemImageLoader;
import com.sk89q.questpages.item.ItemToolTip;
import com.sk89q.questpages.model.ItemStack;
import com.sk89q.questpages.ui.ItemStackDialog;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.JXLabel;

import javax.swing.*;
import java.awt.*;

public class ItemStackField extends JPanel {

    private final JXLabel itemLabel = new JXLabel("(none)");
    private final JButton changeButton = new JButton("Change...");
    @Getter private ItemStack itemStack = new ItemStack();

    public ItemStackField() {
        SwingHelper.removeOpaqueness(this);

        setLayout(new MigLayout("insets 0, fill"));

        itemLabel.setLineWrap(true);

        JScrollPane scrollPane = new JScrollPane(itemLabel);
        SwingHelper.removeOpaqueness(scrollPane);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, "grow, span");
        add(changeButton);

        setItemStack(itemStack);

        changeButton.addActionListener(e -> setItemStack(ItemStackDialog.showItemDialog(SwingUtilities.getWindowAncestor(ItemStackField.this), itemStack)));
    }

    public void setItemStack(ItemStack itemStack) {
        ItemStack previousItemStack = this.itemStack;
        this.itemStack = itemStack;

        if (itemStack != null) {
            Item item = ItemDatabase.INSTANCE.getItemInfo(itemStack.toItemId());
            Image icon = ItemImageLoader.INSTANCE.getImage(itemStack);
            itemLabel.setIcon(icon != null ? new ImageIcon(icon) : null);
            if (item != null) {
                itemLabel.setText("<html><strong>" + SwingHelper.htmlEscape(item.getDisplayName()) +
                        "</strong><br>" + SwingHelper.htmlEscape(item.getId().getName()));
                itemLabel.setToolTipText(ItemToolTip.createToolTipText(item));
            } else {
                itemLabel.setText(itemStack.getId());
                itemLabel.setToolTipText(null);
            }
        } else {
            itemLabel.setText("(None)");
            itemLabel.setToolTipText(null);
        }

        firePropertyChange("itemStack", previousItemStack, itemStack);
    }

}
