package com.sk89q.questpages.ui.component;

import com.sk89q.questpages.QuestPages;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class WelcomePanel extends JPanel {

    private final JButton helpButton = new JButton("Documentation");
    private final JButton patreonButton = new JButton("Patreon");
    private final JButton serverButton = new JButton("sk89q's Server");

    public WelcomePanel() {
        setLayout(new MigLayout("fill, insets 0", "", "[grow 3][fill,grow 20][grow 3][][grow 3]"));

        add(new JPanel(), "span");
        add(new TipPanel(QuestPages.class, "tips.txt"), "span, alignx center");
        add(new JPanel(), "span");
        add(helpButton, "span, alignx center");
        //add(patreonButton, "span, alignx center");
        //add(serverButton, "span, alignx center");
        add(new JPanel());
    }
}
