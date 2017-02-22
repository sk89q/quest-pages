package com.sk89q.questpages.util.swing;

import com.sk89q.questpages.QuestPages;

import javax.swing.*;

public final class Icons {

    public static final ImageIcon ADD_ICON = SwingHelper.readImageIcon(QuestPages.class, "add.png");
    public static final ImageIcon EDIT_ICON = SwingHelper.readImageIcon(QuestPages.class, "edit.png");
    public static final ImageIcon DELETE_ICON = SwingHelper.readImageIcon(QuestPages.class, "delete.png");
    public static final ImageIcon HELP_ICON = SwingHelper.readImageIcon(QuestPages.class, "help.png");

    private Icons() {
    }

}
