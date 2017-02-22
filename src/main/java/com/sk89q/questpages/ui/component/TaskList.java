package com.sk89q.questpages.ui.component;

import com.sk89q.questpages.ui.TaskDialog;
import com.sk89q.questpages.ui.renderer.TaskCellRenderer;
import com.sk89q.questpages.history.Change;
import com.sk89q.questpages.history.CombinedChange;
import com.sk89q.questpages.history.History;
import com.sk89q.questpages.history.IndexedListChangeProxy;
import com.sk89q.questpages.model.task.Task;
import com.sk89q.questpages.model.task.Tasks;
import com.sk89q.questpages.util.swing.ListItemTransferHandler.ResortHandler;
import com.sk89q.questpages.util.swing.SwingHelper;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.sk89q.questpages.util.SharedLocale.tr;

public class TaskList extends ModifiableListPanel<Task> {

    private final JPopupMenu addPopupMenu = createAddPopupMenu();
    private final History history;

    public TaskList(History history) {
        super(true);

        this.history = history;

        SwingHelper.removeOpaqueness(this);

        getList().setCellRenderer(new TaskCellRenderer());
    }

    private JPopupMenu createAddPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();

        for (Map.Entry<String, Class<? extends Task>> entry : Tasks.getTaskTypes().entrySet()) {
            JMenuItem menuItem = new JMenuItem(entry.getKey());
            menuItem.addActionListener(event -> {
                showAddDialog(entry.getValue());
            });
            popupMenu.add(menuItem);
        }

        return popupMenu;
    }

    private void showAddDialog(Class<? extends Task> taskClass) {
        try {
            Task task = taskClass.newInstance();
            Task returnValue = TaskDialog.showTaskDialog(SwingUtilities.getWindowAncestor(this), task);
            if (returnValue != null) {
                List<Change> changes = new ArrayList<>();
                IndexedListChangeProxy<Task> proxy = IndexedListChangeProxy.createProxy(changes, getEntries());
                proxy.add(returnValue);
                history.commit(new CombinedChange("Add " + returnValue.getName(), changes));
                getModel().fireContentsChanged();
            }
        } catch (InstantiationException | IllegalAccessException e) {
            SwingHelper.showErrorDialog(this, tr("errors.genericError"), tr("errorTitle"), e);
        }
    }

    @Override
    protected void add() {
        JButton addButton = getAddButton();
        addPopupMenu.show(addButton, addButton.getX(), addButton.getY() + addButton.getHeight());
    }

    @Override
    protected void edit(Task entry) {
        int index = getEntries().indexOf(entry);
        Task returnValue = TaskDialog.showTaskDialog(SwingUtilities.getWindowAncestor(this), entry);
        if (returnValue != null) {
            List<Change> changes = new ArrayList<>();
            IndexedListChangeProxy<Task> proxy = IndexedListChangeProxy.createProxy(changes, getEntries());
            proxy.remove(entry);
            proxy.add(index, returnValue);
            history.commit(new CombinedChange("Change Task " + entry.getName(), changes));
            getModel().fireContentsChanged();
        }
    }

    @Override
    protected void remove(Task entry) {
        List<Change> changes = new ArrayList<>();
        IndexedListChangeProxy<Task> proxy = IndexedListChangeProxy.createProxy(changes, getEntries());
        proxy.remove(entry);
        history.commit(new CombinedChange("Delete Task " + entry.getName(), changes));
        getModel().fireContentsChanged();
    }

    @Override
    public ResortHandler createResortHandler() {
        List<Change> changes = new ArrayList<>();
        IndexedListChangeProxy<Task> proxy = IndexedListChangeProxy.createProxy(changes, getEntries());

        return new ResortHandler() {
            @Override
            public void addElement(int index, Object value) {
                proxy.add(index, (Task) value);
            }

            @Override
            public void removeElement(int index) {
                proxy.remove(index);
                history.commit(new CombinedChange("Resort Tasks", changes));
                getModel().fireContentsChanged();
            }
        };
    }

}
