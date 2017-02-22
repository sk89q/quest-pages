package com.sk89q.questpages.ui;

import com.sk89q.questpages.ui.common.XTextArea;
import com.sk89q.questpages.ui.common.XTextField;
import com.sk89q.questpages.ui.component.ItemSearchPanel;
import com.sk89q.questpages.item.Item;
import com.sk89q.questpages.model.ItemStack;
import com.sk89q.questpages.util.swing.NoNewLineFilter;
import com.sk89q.questpages.util.swing.SwingHelper;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ItemStackDialog extends JDialog {

    private static ItemSearchPanel ITEM_SEARCH_PANEL;

    private final XTextField nameText = new XTextField();
    private final JSpinner damageSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 65535, 1));
    private final JSpinner amountSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 64, 1));
    private final XTextArea nbtText = new XTextArea();
    @Getter private final ItemSearchPanel itemSearchPanel;

    private final JButton okButton = new JButton("OK");
    private final JButton cancelButton = new JButton("Cancel");

    @Getter private int value = JOptionPane.DEFAULT_OPTION;

    public ItemStackDialog(Window owner, ItemSearchPanel itemSearchPanel) {
        super(owner, "Choose Item", ModalityType.DOCUMENT_MODAL);

        this.itemSearchPanel = itemSearchPanel;

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(owner);

        registerListeners();

        getRootPane().setDefaultButton(okButton);
        SwingHelper.setEscapeButton(getRootPane(), cancelButton);
        SwingHelper.focusLater(this, getItemSearchPanel().getQueryText());
    }

    private void initComponents() {
        JSplitPane container = new JSplitPane();
        container.setDividerLocation(0.5);

        JPanel form = new JPanel();
        form.setLayout(new MigLayout("insets dialog", "[][grow]"));

        form.add(new JLabel("Item Name:"));
        form.add(nameText, "growx, w 200, span");

        form.add(new JLabel("Metadata:"));
        form.add(damageSpinner, "span");

        form.add(new JLabel("Amount:"));
        form.add(amountSpinner, "span");

        form.add(new JLabel("NBT Data:"));
        form.add(SwingHelper.wrapScrollPane(nbtText), "w 200, h 150, growx, span");
        nbtText.setFont(nameText.getFont());
        nbtText.setLineWrap(true);
        nbtText.setWrapStyleWord(true);
        ((AbstractDocument) nbtText.getDocument()).setDocumentFilter(new NoNewLineFilter());

        form.add(okButton, "gaptop unrel, tag ok, span, split 2, sizegroup bttn");
        form.add(cancelButton, "tag cancel, sizegroup bttn");

        container.setLeftComponent(form);
        container.setRightComponent(itemSearchPanel);

        add(container, BorderLayout.CENTER);
    }

    private void registerListeners() {
        itemSearchPanel.addPropertyChangeListener("selectedItem", evt -> {
            Item item = (Item) evt.getNewValue();
            if (item != null) {
                setItem(item);
            }
        });

        itemSearchPanel.getQueryText().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                itemSearchPanel.selectFirstItem();
                Item item = itemSearchPanel.getSelectedItem();
                if (item != null) {
                    setItem(item);
                    itemSearchPanel.getItemList().requestFocus();
                    itemSearchPanel.selectFirstItem(); // Previous line causing reset of selection
                }
            }
        });

        itemSearchPanel.getItemList().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    value = JOptionPane.OK_OPTION;
                    setVisible(false);
                }
            }
        });

        okButton.addActionListener(e -> {
            value = JOptionPane.OK_OPTION;
            setVisible(false);
        });

        cancelButton.addActionListener(e -> {
            value = JOptionPane.CANCEL_OPTION;
            setVisible(false);
        });
    }

    private void setItem(Item item) {
        nameText.setText(item.getId().getName());
        damageSpinner.setValue(item.getId().getDamage());
    }

    private void copyFrom(ItemStack itemStack) {
        nameText.setText(String.valueOf(itemStack.getId()));
        damageSpinner.setValue(itemStack.getDamage());
        amountSpinner.setValue(itemStack.getAmount());
        nbtText.setText(itemStack.getNbt() != null ? itemStack.getNbt() : "");
    }

    private void copyTo(ItemStack itemStack) {
        itemStack.setId(nameText.getText().trim());
        itemStack.setDamage((int) damageSpinner.getValue());
        itemStack.setAmount((int) amountSpinner.getValue());
        itemStack.setNbt(nbtText.getText().trim().isEmpty() ? null : nbtText.getText());
    }

    public static ItemStack showItemDialog(Window parent, ItemStack itemStack) {
        if (ITEM_SEARCH_PANEL == null) {
            ITEM_SEARCH_PANEL = new ItemSearchPanel();
        }

        ITEM_SEARCH_PANEL.getQueryText().selectAll();

        ItemStackDialog dialog = new ItemStackDialog(parent, ITEM_SEARCH_PANEL);
        if (itemStack != null) {
            dialog.copyFrom(itemStack);
        }
        dialog.setVisible(true);
        switch (dialog.getValue()) {
            case JOptionPane.OK_OPTION:
                if (!dialog.nameText.getText().isEmpty()) {
                    ItemStack returnStack = new ItemStack();
                    dialog.copyTo(returnStack);
                    return returnStack;
                } else {
                    return null;
                }
            default:
                return itemStack;
        }
    }


}
