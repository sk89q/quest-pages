package com.sk89q.questpages.model.task;

import com.sk89q.questpages.QuestPages;
import com.sk89q.questpages.util.swing.SwingHelper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.swing.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class CraftTask extends ItemTask {

    private static final ImageIcon ICON = SwingHelper.readImageIcon(QuestPages.class, "task/craft.png");

    @Override
    public String getTypeName() {
        return "Craft";
    }

    @Override
    public ImageIcon getDisplayIcon() {
        return ICON;
    }

}
