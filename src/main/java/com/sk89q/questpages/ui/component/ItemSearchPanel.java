package com.sk89q.questpages.ui.component;

import com.sk89q.questpages.QuestPages;
import com.sk89q.questpages.util.swing.SwingHelper;
import com.sk89q.questpages.ui.renderer.ItemCellRenderer;
import com.sk89q.questpages.item.Item;
import com.sk89q.questpages.item.ItemComparator;
import com.sk89q.questpages.item.ItemDatabase;
import com.sk89q.questpages.util.swing.ForwardingListModel;
import com.sk89q.questpages.util.swing.InstantTooltipListener;
import lombok.Getter;
import lombok.extern.java.Log;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;

@Log
public class ItemSearchPanel extends JPanel {

    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor(new ThreadFactory() {
                public Thread newThread(Runnable r) {
                    Thread t = Executors.defaultThreadFactory().newThread(r);
                    t.setDaemon(true);
                    return t;
                }
            });
    private Future<?> searchTask;

    @Getter private final JTextField queryText = new JTextField();
    @Getter private final JList<Item> itemList = new JList<>();
    private final JButton clearButton = new JButton(SwingHelper.readImageIcon(QuestPages.class, "clear_filter.png"));

    public ItemSearchPanel() {
        setLayout(new MigLayout("fill, insets dialog", "[][fill,grow][]", "[][fill,grow]"));

        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemList.setVisibleRowCount(-1);
        itemList.setFixedCellWidth(32); // These MUST be set otherwise all cells will be rendered immediately!
        itemList.setFixedCellHeight(32);
        itemList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        itemList.setCellRenderer(new ItemCellRenderer());

        add(new JLabel("Search Items:"));
        add(queryText, "growx");
        add(clearButton, "wrap");
        add(SwingHelper.wrapScrollPane(itemList), "grow, w 380, h 200, span");

        search("");

        itemList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                firePropertyChange("selectedItem", null, itemList.getSelectedValue());
            }
        });

        itemList.addMouseListener(new InstantTooltipListener());

        queryText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                search(queryText.getText());
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                queryText.setText("");
                search("");
                queryText.requestFocus();
            }
        });
    }

    public void selectFirstItem() {
        itemList.ensureIndexIsVisible(0);
        itemList.setSelectedIndex(0);
    }

    public Item getSelectedItem() {
        return itemList.getSelectedValue();
    }

    private void setResults(List<Item> results) {
        itemList.setModel(new ForwardingListModel<>(results));
    }

    private void search(String query) {
        if (searchTask != null) {
            searchTask.cancel(true);
        }
        searchTask = EXECUTOR.submit(new SearchTask(query));
    }

    public class SearchTask implements Runnable {
        private final String query;

        public SearchTask(String query) {
            this.query = query;
        }

        @Override
        public void run() {
            final List<Item> results = new ArrayList<>();

            if (query.isEmpty()) {
                results.addAll(ItemDatabase.INSTANCE.getItems());
            } else {
                String testQuery = query.toLowerCase();
                for (Item entry : ItemDatabase.INSTANCE.getItems()) {
                    if (Thread.interrupted()) break;
                    if (entry.getDisplayName().toLowerCase().contains(testQuery) ||
                            entry.getId().getName().toLowerCase().contains(testQuery)) {
                        results.add(entry);
                    }
                }
            }

            Collections.sort(results, ItemComparator.INSTANCE);

            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        setResults(results);
                    }
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (InvocationTargetException e) {
                log.log(Level.WARNING, "Failed to update item search list", e);
            }

        }
    }

}
