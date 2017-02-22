package com.sk89q.questpages.ui.component.task;

import com.sk89q.questpages.ui.component.ModifiableListPanel;
import com.sk89q.questpages.model.Location;
import com.sk89q.questpages.model.task.LocationTask;
import com.sk89q.questpages.model.task.Task;
import com.sk89q.questpages.util.swing.SwingHelper;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class LocationTaskPanel extends TaskPanel {

    private final LocationTask task;
    private final ModifiableListPanel<Location> locationList = new ModifiableListPanel<>();

    public LocationTaskPanel(LocationTask task) {
        this.task = task;

        SwingHelper.removeOpaqueness(this);
        setLayout(new MigLayout("insets 0, fill"));

        add(new JLabel("Locations to Visit:"), "span");
        add(locationList, "w 200, h 200, grow, span");

        List<Location> newList = new ArrayList<>();
        for (Location entry : task.getLocations()) {
            newList.add(new Location(entry));
        }

        locationList.setModel(newList);
    }

    @Override
    public Task getChangedTask() {
        task.setLocations(locationList.getEntries());
        return task;
    }

}
