package com.sk89q.questpages.ui.component;

import com.sk89q.questpages.QuestPages;
import com.sk89q.questpages.tool.RequirementLinkTool;
import com.sk89q.questpages.tool.Tool;
import com.sk89q.questpages.history.History;
import com.sk89q.questpages.item.ItemImageLoader;
import com.sk89q.questpages.model.Quest;
import com.sk89q.questpages.util.GraphicsUtils;
import com.sk89q.questpages.util.Painters;
import com.sk89q.questpages.util.swing.SwingHelper;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookPanel extends JPanel {

    public static final int SCALING = 2;
    public static final Color REQUIREMENT_COLOR = new Color(0x414141);
    public static final Color OPTION_COLOR = new Color(0x4040DD);
    public static final Stroke LINK_DETAILED_STROKE = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    public static final Stroke LINK_STROKE = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    private static final Dimension PANEL_DIMENSION = new Dimension(340 * SCALING, 234 * SCALING);
    private static final Image BOOK_BACKGROUND = SwingHelper.readImage(QuestPages.class, "book_bg.png");
    private static final Image QUEST_ICON = SwingHelper.readImage(QuestPages.class, "quest_icon.png");
    private static final Image QUEST_ICON_SEL = SwingHelper.readImage(QuestPages.class, "quest_icon_sel.png");
    private static final Image QUEST_ICON_LARGE = SwingHelper.readImage(QuestPages.class, "quest_icon_large.png");
    private static final Image QUEST_ICON_LARGE_SEL = SwingHelper.readImage(QuestPages.class, "quest_icon_large_sel.png");
    private static final int ICON_SIZE = 23;
    private static final int LARGE_ICON_SIZE = 32;

    private final BookMouseListener mouseAdapter = new BookMouseListener(this);

    @Getter private final List<Quest> quests;
    @Getter private final History history;

    @Getter @Setter private Tool tool = new RequirementLinkTool();
    @Getter private boolean detailedView = false;

    @Getter private final Set<Quest> selectedQuests = new HashSet<>();
    @Getter @Setter private Quest hoveredQuest;
    @Getter @Setter private boolean shiftKeyEnabled = false;
    @Getter @Setter private boolean ctrlKeyEnabled = false;

    public BookPanel(List<Quest> quests, History history) {
        this.quests = quests;
        this.history = history;

        setFocusable(true);

        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        addKeyListener(new BookKeyListener(this));
    }

    public void setDetailedView(boolean detailedView) {
        this.detailedView = detailedView;
        repaint();
    }

    public void setSelectedQuest(Quest quest) {
        selectedQuests.clear();
        if (quest != null) {
            selectedQuests.add(quest);
        }
    }

    public void setSelectedQuests(Collection<Quest> quests) {
        this.selectedQuests.clear();
        this.selectedQuests.addAll(quests);
    }

    public void addSelectedQuests(Collection<Quest> quests) {
        this.selectedQuests.addAll(quests);
    }

    public void addSelectedQuest(Quest quest) {
        this.selectedQuests.add(quest);
    }

    public void removeSelectedQuests(Collection<Quest> quests) {
        this.selectedQuests.removeAll(quests);
    }

    public void removeSelectedQuest(Quest quest) {
        this.selectedQuests.remove(quest);
    }

    public void fireSelectedQuestsChange() {
        firePropertyChange("selectedQuests", null, selectedQuests);
    }

    public Quest getQuestAt(int x, int y) {
        x = x / SCALING;
        y = y / SCALING;

        for (int i = quests.size() - 1; i >= 0; i--) {
            Quest quest = quests.get(i);

            int size = quest.isBigIcon() ? LARGE_ICON_SIZE : ICON_SIZE;
            if (x >= quest.getX() && x <= quest.getX() + size && y >= quest.getY() && y <= quest.getY() + size) {
                return quest;
            }
        }

        return null;
    }

    public Set<Quest> getQuestsWithin(int x1, int y1, int x2, int y2) {
        if (x1 > x2) {
            int temp = x2;
            x2 = x1;
            x1 = temp;
        }

        if (y1 > y2) {
            int temp = y2;
            y2 = y1;
            y1 = temp;
        }

        x1 = x1 / SCALING;
        y1 = y1 / SCALING;
        x2 = x2 / SCALING;
        y2 = y2 / SCALING;

        Set<Quest> matches = new HashSet<>();

        for (int i = quests.size() - 1; i >= 0; i--) {
            Quest quest = quests.get(i);

            int size = quest.isBigIcon() ? LARGE_ICON_SIZE : ICON_SIZE;
            if (x1 <= quest.getX() + size && x2 >= quest.getX() && y1 <= quest.getY() + size && y2 >= quest.getY()) {
                matches.add(quest);
            }
        }

        return matches;
    }

    public Image getQuestIcon(Quest quest) {
        return quest.isBigIcon() ? QUEST_ICON_LARGE : QUEST_ICON;
    }

    public Image getSelectedQuestIcon(Quest quest) {
        return quest.isBigIcon() ? QUEST_ICON_LARGE_SEL : QUEST_ICON_SEL;
    }

    @Override
    public Dimension getPreferredSize() {
        return PANEL_DIMENSION;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (BOOK_BACKGROUND != null) {
            g.drawImage(BOOK_BACKGROUND, 0, 0, (int) PANEL_DIMENSION.getWidth(), (int) PANEL_DIMENSION.getHeight(), this);
        }

        mouseAdapter.paintBackground(g);

        // Links
        if (detailedView) {
            g2d.setStroke(LINK_DETAILED_STROKE);
        } else {
            g2d.setStroke(LINK_STROKE);
        }

        // Draw requirements
        g2d.setColor(REQUIREMENT_COLOR);
        for (Quest quest : quests) {
            for (Quest requirement : quest.getRequirements()) {
                int x1 = (requirement.getX() + getQuestIcon(requirement).getWidth(this) / 2) * SCALING;
                int y1 = (requirement.getY() + getQuestIcon(requirement).getHeight(this) / 2) * SCALING;
                int x2 = (quest.getX() + getQuestIcon(quest).getWidth(this) / 2) * SCALING;
                int y2 = (quest.getY() + getQuestIcon(quest).getHeight(this) / 2) * SCALING;

                g.drawLine(x1, y1, x2, y2);
                if (detailedView) {
                    Painters.drawArrow(g2d, x1, y1, x2, y2, 9, 7, false, 0.6);
                }
            }
        }

        // Draw options
        g2d.setColor(OPTION_COLOR);
        for (Quest quest : quests) {
            for (Quest option : quest.getOptions()) {
                int x1 = (option.getX() + getQuestIcon(option).getWidth(this) / 2) * SCALING;
                int y1 = (option.getY() + getQuestIcon(option).getHeight(this) / 2) * SCALING;
                int x2 = (quest.getX() + getQuestIcon(quest).getWidth(this) / 2) * SCALING;
                int y2 = (quest.getY() + getQuestIcon(quest).getHeight(this) / 2) * SCALING;

                g.drawLine(x1, y1, x2, y2);
                if (detailedView) {
                    Painters.drawArrow(g2d, x1, y1, x2, y2, 9, 7, false, 0.6);
                }
            }
        }

        for (Quest quest : quests) {
            Image questIcon = getSelectedQuests().contains(quest) ? getSelectedQuestIcon(quest) : getQuestIcon(quest);

            GraphicsUtils.drawScaledImage(
                    g,
                    questIcon,
                    quest.getX() * SCALING,
                    quest.getY() * SCALING,
                    SCALING,
                    this);

            Image itemImage = ItemImageLoader.INSTANCE.getImage(quest.getIcon());
            if (itemImage != null) {
                g.drawImage(
                        itemImage,
                        (quest.getX() + questIcon.getWidth(this) / 2 - 8) * SCALING,
                        (quest.getY() + questIcon.getHeight(this) / 2 - 8) * SCALING,
                        16 * SCALING,
                        16 * SCALING,
                        this);
            }
        }

        mouseAdapter.paintForeground(g);
    }

}
