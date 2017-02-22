package com.sk89q.questpages.model.task;

import com.sk89q.questpages.item.ItemDatabase;
import com.sk89q.questpages.model.ItemRequirement;
import com.sk89q.questpages.ui.component.task.ItemTaskPanel;
import com.sk89q.questpages.ui.component.task.TaskPanel;
import com.sk89q.questpages.util.swing.SwingHelper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.sk89q.questpages.util.SharedLocale.tr;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class ItemTask extends Task {

    private List<ItemRequirement> items = new ArrayList<>();

    @Override
    public String getDisplayDesc(boolean isSelected) {
        ItemDatabase itemDatabase = ItemDatabase.INSTANCE;
        Color subtleColor = UIManager.getDefaults().getColor("TextField.inactiveForeground");

        StringBuilder builder = new StringBuilder();
        if (!getItems().isEmpty()) {
            builder.append("<ul style=\"margin: 0; padding: 0 0 0 12px; list-style-type: square\">");
            for (ItemRequirement requirement : getItems()) {
                builder.append("<li>");
                if (requirement.getFluid() != null) {
                    builder.append(requirement.getFluidLabel(isSelected));
                } else if (requirement.getItem() != null) {
                    builder.append(requirement.getItemLabel(isSelected));
                }
                builder.append("</li>");
            }
            builder.append("</ul>");
        } else {
            if (isSelected) {
                builder.append("<span>");
            } else {
                builder.append("<span style=\"color: #").append(SwingHelper.hexCode(subtleColor)).append("\">");
            }
            builder.append(SwingHelper.htmlEscape(tr("taskList.noItems")));
            builder.append("</span>");
        }
        return builder.toString();
    }

    @Override
    public TaskPanel createEditPanel() {
        return new ItemTaskPanel(this);
    }

}
