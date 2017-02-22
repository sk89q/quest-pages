package com.sk89q.questpages.model.task;

import com.sk89q.questpages.QuestPages;
import com.sk89q.questpages.ui.component.task.LocationTaskPanel;
import com.sk89q.questpages.ui.component.task.TaskPanel;
import com.sk89q.questpages.util.swing.SwingHelper;
import com.sk89q.questpages.model.Location;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class LocationTask extends Task {

    private static final ImageIcon ICON = SwingHelper.readImageIcon(QuestPages.class, "task/location.png");

    private List<Location> locations = new ArrayList<>();

    @Override
    public String getTypeName() {
        return "Go To Location";
    }

    @Override
    public String getDisplayDesc(boolean isSelected) {
        return "12, 65, 355";
    }

    @Override
    public ImageIcon getDisplayIcon() {
        return ICON;
    }

    @Override
    public TaskPanel createEditPanel() {
        return new LocationTaskPanel(this);
    }

}
