package com.sk89q.questpages.util.swing;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.sk89q.questpages.util.SharedLocale.tr;

public class TextFieldPopupMenu extends JPopupMenu implements ActionListener {

    public static final TextFieldPopupMenu INSTANCE = new TextFieldPopupMenu();

    private final JMenuItem cutItem;
    private final JMenuItem copyItem;
    private final JMenuItem pasteItem;
    private final JMenuItem deleteItem;
    private final JMenuItem selectAllItem;

    private TextFieldPopupMenu() {
        cutItem = addMenuItem(new JMenuItem(tr("context.cut"), 'T'));
        copyItem = addMenuItem(new JMenuItem(tr("context.copy"), 'C'));
        pasteItem = addMenuItem(new JMenuItem(tr("context.paste"), 'P'));
        deleteItem = addMenuItem(new JMenuItem(tr("context.delete"), 'D'));
        addSeparator();
        selectAllItem = addMenuItem(new JMenuItem(tr("context.selectAll"), 'A'));
    }

    private JMenuItem addMenuItem(JMenuItem item) {
        item.addActionListener(this);
        return add(item);
    }

    @Override
    public void show(Component invoker, int x, int y) {
        JTextComponent textComponent = (JTextComponent) invoker;
        boolean editable = textComponent.isEditable() && textComponent.isEnabled();
        cutItem.setVisible(editable);
        pasteItem.setVisible(editable);
        deleteItem.setVisible(editable);
        super.show(invoker, x, y);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTextComponent textComponent = (JTextComponent) getInvoker();
        textComponent.requestFocus();

        boolean haveSelection =
                textComponent.getSelectionStart() != textComponent.getSelectionEnd();

        if (e.getSource() == cutItem) {
            if (!haveSelection) textComponent.selectAll();
            textComponent.cut();
        } else if (e.getSource() == copyItem) {
            if (!haveSelection) textComponent.selectAll();
            textComponent.copy();
        } else if (e.getSource() == pasteItem) {
            textComponent.paste();
        } else if (e.getSource() == deleteItem) {
            if (!haveSelection) textComponent.selectAll();
            textComponent.replaceSelection("");
        } else if (e.getSource() == selectAllItem) {
            textComponent.selectAll();
        }
    }
}
