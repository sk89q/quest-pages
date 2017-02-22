package com.sk89q.questpages.ui.component;

import com.sk89q.questpages.ui.common.XList;
import com.sk89q.questpages.util.swing.ForwardingListModel;
import com.sk89q.questpages.util.swing.Icons;
import com.sk89q.questpages.util.swing.ListItemTransferHandler;
import com.sk89q.questpages.util.swing.ListItemTransferHandler.ResortHandler;
import com.sk89q.questpages.util.swing.ListItemTransferHandler.ResortListener;
import com.sk89q.questpages.util.swing.SwingHelper;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import static com.sk89q.questpages.util.SharedLocale.tr;

public class ModifiableListPanel<E> extends JPanel implements ResortListener {

    @Getter private final XList<E> list = new XList<>();
    @Getter private final JButton addButton = new JButton(Icons.ADD_ICON);
    @Getter private final JButton editButton = new JButton(Icons.EDIT_ICON);
    @Getter private final JButton removeButton = new JButton(Icons.DELETE_ICON);
    @Getter private ForwardingListModel<E> model;

    public ModifiableListPanel() {
        this(false);
    }

    public ModifiableListPanel(boolean wideList) {
        SwingHelper.removeOpaqueness(this);

        if (wideList) {
            setLayout(new MigLayout("insets 0, fill", "[][]push[]", "[shrink][grow]"));
        } else {
            setLayout(new MigLayout("insets 0, fill", "[fill,grow][]"));
        }

        list.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setTransferHandler(new ListItemTransferHandler(this));
        list.setDropMode(DropMode.INSERT);
        list.setDragEnabled(true);

        if (wideList) {
            addButton.setText(tr("button.add"));

            add(addButton);
            add(editButton);
            add(removeButton, "wrap");
            add(SwingHelper.wrapScrollPane(list), "grow, span");
        } else {
            add(SwingHelper.wrapScrollPane(list), "grow");
            add(addButton, "flowy, aligny top, split 3, sg");
            add(editButton, "sg");
            add(removeButton, "sg");
        }

        addButton.addActionListener(e -> {
            add();
        });

        editButton.addActionListener(e -> {
            editSelected();
        });

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editSelected();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    createPopupMenu().show(list, e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    createPopupMenu().show(list, e.getX(), e.getY());
                }
            }
        });

        removeButton.addActionListener(e -> {
            removeSelected();
        });
    }

    private JPopupMenu createPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItem;

        E selected = list.getSelectedValue();

        if (selected != null) {
            menuItem = new JMenuItem(tr("listPanel.edit"));
            menuItem.addActionListener(e -> edit(selected));
            popupMenu.add(menuItem);

            menuItem = new JMenuItem(tr("listPanel.remove"));
            menuItem.addActionListener(e -> remove(selected));
            popupMenu.add(menuItem);

            popupMenu.addSeparator();
        }

        menuItem = new JMenuItem(tr("listPanel.add"));
        menuItem.addActionListener(e -> add());
        popupMenu.add(menuItem);

        return popupMenu;
    }

    public void setModel(List<E> entries) {
        this.model = new ForwardingListModel<>(entries);
        list.setModel(this.model);
    }

    public List<E> getEntries() {
        return model.getEntries();
    }

    private void editSelected() {
        E selected = list.getSelectedValue();
        if (selected != null) {
            edit(selected);
        }
    }

    private void removeSelected() {
        E selected = list.getSelectedValue();
        if (selected != null) {
            remove(selected);
        }
    }

    protected void add() {
    }

    protected void edit(E entry) {
    }

    protected void remove(E entry) {
        getEntries().remove(entry);
        getModel().fireContentsChanged();
    }

    @Override
    public ResortHandler createResortHandler() {
        return new ListResortHandler();
    }

    private class ListResortHandler implements ResortHandler {
        @Override
        public void addElement(int index, Object value) {
            model.addElement(index, value);
        }

        @Override
        public void removeElement(int index) {
            model.removeElement(index);
        }
    }

}
