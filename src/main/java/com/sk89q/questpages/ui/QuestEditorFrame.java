package com.sk89q.questpages.ui;

import com.sk89q.questpages.QuestPages;
import com.sk89q.questpages.history.Change;
import com.sk89q.questpages.history.History;
import com.sk89q.questpages.history.HistoryListener;
import com.sk89q.questpages.history.LinearHistory;
import com.sk89q.questpages.model.Quest;
import com.sk89q.questpages.model.QuestLine;
import com.sk89q.questpages.ui.component.BookPanel;
import com.sk89q.questpages.ui.component.QuestPanel;
import com.sk89q.questpages.ui.component.WelcomePanel;
import com.sk89q.questpages.tool.OptionLinkTool;
import com.sk89q.questpages.tool.PointerTool;
import com.sk89q.questpages.tool.RequirementLinkTool;
import com.sk89q.questpages.tool.SelectionTool;
import com.sk89q.questpages.tool.Tool;
import com.sk89q.questpages.util.swing.SwingHelper;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Set;

public class QuestEditorFrame extends JFrame {

    private final History history = new LinearHistory();
    private final QuestLine questLine;
    @Getter private Tool tool = new SelectionTool();
    @Getter private boolean detailedView = false;

    private final JMenuBar menuBar = new JMenuBar();
    private final JMenuItem newMenuItem = new JMenuItem("New");
    private final JMenuItem openMenuItem = new JMenuItem("Open...");
    private final JMenuItem saveMenuItem = new JMenuItem("Save");
    private final JMenuItem saveAsMenuItem = new JMenuItem("Save As...");
    private final JMenuItem quitMenuItem = new JMenuItem("Quit");
    private final JMenuItem undoMenuItem = new JMenuItem("Undo");
    private final JMenuItem redoMenuItem = new JMenuItem("Redo");
    private final JMenuItem detailsMenuItem = new JCheckBoxMenuItem("Detailed View");
    private final JMenuItem docsMenuItem = new JMenuItem("Documentation");
    private final JMenuItem patreonMenuItem = new JMenuItem("Patreon");
    private final JMenuItem aboutMenuItem = new JMenuItem("About");

    private final JToolBar toolbar = new JToolBar("Toolbar");
    private final JButton newButton = new JButton(SwingHelper.readImageIcon(QuestPages.class, "new.png"));
    private final JButton openButton = new JButton(SwingHelper.readImageIcon(QuestPages.class, "open.png"));
    private final JButton saveButton = new JButton(SwingHelper.readImageIcon(QuestPages.class, "save.png"));
    private final JButton undoButton = new JButton(SwingHelper.readImageIcon(QuestPages.class, "undo.png"));
    private final JButton redoButton = new JButton(SwingHelper.readImageIcon(QuestPages.class, "redo.png"));
    private final JToggleButton createModeButton = new JToggleButton(SwingHelper.readImageIcon(QuestPages.class, "create.png"));
    private final JToggleButton pointerModeButton = new JToggleButton(SwingHelper.readImageIcon(QuestPages.class, "pointer.png"));
    private final JToggleButton repositionModeButton = new JToggleButton(SwingHelper.readImageIcon(QuestPages.class, "move.png"));
    private final JToggleButton requirementModeButton = new JToggleButton(SwingHelper.readImageIcon(QuestPages.class, "link.png"));
    private final JToggleButton optionModeButton = new JToggleButton(SwingHelper.readImageIcon(QuestPages.class, "link_option.png"));
    private final JToggleButton detailsButton = new JToggleButton(SwingHelper.readImageIcon(QuestPages.class, "details.png"));
    private final JToggleButton warningsButton = new JToggleButton(SwingHelper.readImageIcon(QuestPages.class, "warnings.png"));
    private final JButton helpButton = new JButton(SwingHelper.readImageIcon(QuestPages.class, "help.png"));

    private final BookPanel bookPanel;
    private final CardLayout sidebarCardLayout = new CardLayout();
    private final JPanel sidebarPanel = new JPanel(sidebarCardLayout);
    private final WelcomePanel welcomePanel = new WelcomePanel();
    private final QuestPanel questPanel = new QuestPanel(history);

    public QuestEditorFrame(QuestLine questLine) throws HeadlessException {
        super("Quest Pages [Starting Out]");

        this.questLine = questLine;

        bookPanel = new BookPanel(questLine.getQuests(), history);
        configureMenuBar(menuBar);
        configureToolbar(toolbar);
        setJMenuBar(menuBar);
        initComponents();
        registerListeners();
        SwingHelper.setIconImage(this, QuestPages.class, "icon.png");
        pack();
        setResizable(false);
        setLocationRelativeTo(null);

        setTool(new PointerTool());
        updateHistory();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("insets panel", "[][grow]", "[grow]"));

        panel.add(bookPanel);

        sidebarPanel.add(welcomePanel);
        sidebarPanel.add(questPanel);

        panel.add(sidebarPanel, "w 250, gapleft unrel, grow");

        add(panel, BorderLayout.CENTER);
    }

    private void registerListeners() {
        bookPanel.addPropertyChangeListener("selectedQuests", evt -> {
            @SuppressWarnings("unchecked")
            Set<Quest> selectedQuests = (Set<Quest>) evt.getNewValue();
            if (selectedQuests.size() == 1) {
                sidebarCardLayout.last(sidebarPanel);
                questPanel.setQuest(selectedQuests.iterator().next());
            } else {
                sidebarCardLayout.first(sidebarPanel);
            }
        });

        bookPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Set<Quest> selected = bookPanel.getSelectedQuests();
                    if (selected.size() == 1) {
                        // Edit first task
                        questPanel.getTabbedPane().setSelectedIndex(2);
                        questPanel.getTaskList().getList().setSelectedIndex(0);
                        questPanel.getTaskList().getEditButton().doClick();
                    }
                }
            }
        });

        questPanel.addPropertyChangeListener("quest", evt -> bookPanel.repaint());

        pointerModeButton.addActionListener(e -> setTool(new PointerTool()));

        repositionModeButton.addActionListener(e -> setTool(new SelectionTool()));

        requirementModeButton.addActionListener(e -> setTool(new RequirementLinkTool()));

        optionModeButton.addActionListener(e -> setTool(new OptionLinkTool()));

        detailsButton.addActionListener(e -> setDetailedView(!detailedView));

        detailsMenuItem.addActionListener(e -> setDetailedView(!detailedView));

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                bookPanel.requestFocus();
            }
        });

        undoMenuItem.addActionListener(e -> history.undo());

        redoMenuItem.addActionListener(e -> history.redo());

        undoButton.addActionListener(e -> history.undo());

        redoButton.addActionListener(e -> history.redo());

        history.addHistoryListener(new HistoryListener() {
            @Override
            public void undone() {
                updateHistory();
                questPanel.copyFrom();
            }

            @Override
            public void redone() {
                updateHistory();
                questPanel.copyFrom();
            }

            @Override
            public void added() {
                updateHistory();
            }
        });
    }

    private void configureMenuBar(JMenuBar menuBar) {
        menuBar.setBorder(BorderFactory.createEmptyBorder());

        undoMenuItem.setAccelerator(KeyStroke.getKeyStroke('Z', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        redoMenuItem.setAccelerator(KeyStroke.getKeyStroke('Y', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));

        Insets menuInset = new Insets(2, 2, 2, 2);

        JMenu menu;

        menu = new JMenu("File");
        menu.setMargin(menuInset);
        menu.setMnemonic('f');
        menuBar.add(menu);
        menu.add(newMenuItem);
        menu.add(openMenuItem);
        menu.add(saveMenuItem);
        menu.add(saveAsMenuItem);
        menu.addSeparator();
        menu.add(quitMenuItem);

        menu = new JMenu("Edit");
        menu.setMargin(menuInset);
        menu.setMnemonic('e');
        menuBar.add(menu);
        menu.add(undoMenuItem);
        menu.add(redoMenuItem);

        menu = new JMenu("View");
        menu.setMargin(menuInset);
        menu.setMnemonic('v');
        menuBar.add(menu);
        menu.add(detailsMenuItem);

        menu = new JMenu("Help");
        menu.setMargin(menuInset);
        menu.setMnemonic('h');
        menuBar.add(menu);
        menu.add(docsMenuItem);
        menu.addSeparator();
        menu.add(patreonMenuItem);
        menu.add(aboutMenuItem);
    }

    private void configureToolbar(JToolBar toolBar) {
        toolbar.setOrientation(SwingConstants.VERTICAL);

        toolBar.setFloatable(false);
        toolbar.add(createModeButton);
        toolbar.add(pointerModeButton);
        toolbar.add(repositionModeButton);
        toolbar.add(requirementModeButton);
        toolbar.add(optionModeButton);
        toolbar.addSeparator();
        toolbar.add(detailsButton);
        toolbar.add(warningsButton);

        add(toolbar, BorderLayout.WEST);
    }

    private void setTool(Tool tool) {
        this.tool = tool;
        Class<?> toolClass = tool.getClass();
        createModeButton.setSelected(false);
        pointerModeButton.setSelected(toolClass == PointerTool.class);
        repositionModeButton.setSelected(toolClass == SelectionTool.class);
        requirementModeButton.setSelected(toolClass == RequirementLinkTool.class);
        optionModeButton.setSelected(toolClass == OptionLinkTool.class);
        bookPanel.setTool(tool);
    }

    private void setDetailedView(boolean detailedView) {
        this.detailedView = detailedView;
        detailsButton.setSelected(detailedView);
        detailsMenuItem.setSelected(detailedView);
        bookPanel.setDetailedView(detailedView);
    }

    private void updateHistory() {
        Change nextUndo = history.getNextUndo();
        Change nextRedo = history.getNextRedo();
        String undoText = nextUndo != null ? "Undo '" + nextUndo.getName() + "'" : "Undo";
        String redoText = nextRedo != null ? "Redo '" + nextRedo.getName() + "'" : "Redo";

        undoMenuItem.setEnabled(nextUndo != null);
        undoButton.setEnabled(nextUndo != null);
        redoMenuItem.setEnabled(nextRedo != null);
        redoButton.setEnabled(nextRedo != null);

        undoMenuItem.setText(undoText);
        undoButton.setToolTipText(undoText);
        redoMenuItem.setText(redoText);
        redoButton.setToolTipText(redoText);

        bookPanel.repaint();
    }

}
