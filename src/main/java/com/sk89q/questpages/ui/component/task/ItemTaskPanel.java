package com.sk89q.questpages.ui.component.task;

import com.sk89q.questpages.ui.common.XRadioButton;
import com.sk89q.questpages.ui.component.ModifiableListPanel;
import com.sk89q.questpages.model.ItemRequirement;
import com.sk89q.questpages.model.task.ConsumeQDSTask;
import com.sk89q.questpages.model.task.ConsumeTask;
import com.sk89q.questpages.model.task.CraftTask;
import com.sk89q.questpages.model.task.DetectItemTask;
import com.sk89q.questpages.model.task.ItemTask;
import com.sk89q.questpages.model.task.Task;
import com.sk89q.questpages.ui.renderer.ItemRequirementRenderer;
import com.sk89q.questpages.util.swing.SwingHelper;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ItemTaskPanel extends TaskPanel {

    private final XRadioButton detectButton = new XRadioButton("Obtain the following items");
    private final XRadioButton craftButton = new XRadioButton("Craft the following items");
    private final XRadioButton consumeButton = new XRadioButton("Submit (and lose) the following items");
    private final XRadioButton consumeQDSButton = new XRadioButton("Submit (and lose) the following items/fluids with QDS");
    private final ModifiableListPanel<ItemRequirement> itemRequirementList = new ModifiableListPanel<>();

    public ItemTaskPanel(ItemTask task) {
        SwingHelper.removeOpaqueness(this);
        setLayout(new MigLayout("insets 0, gap 0, fill"));

        ButtonGroup group = new ButtonGroup();
        group.add(detectButton);
        group.add(craftButton);
        group.add(consumeButton);
        group.add(consumeQDSButton);

        add(detectButton, "span");
        add(craftButton, "span");
        add(consumeButton, "span");
        add(consumeQDSButton, "span");

        itemRequirementList.getList().setCellRenderer(new ItemRequirementRenderer());
        add(itemRequirementList, "gaptop unrel, w 200, h 200, grow, span");

        if (task instanceof DetectItemTask) {
            detectButton.setSelected(true);
        } else if (task instanceof CraftTask) {
            craftButton.setSelected(true);
        } else if (task instanceof ConsumeTask) {
            consumeButton.setSelected(true);
        } else {
            consumeQDSButton.setSelected(true);
        }

        List<ItemRequirement> newList = new ArrayList<>();
        for (ItemRequirement entry : task.getItems()) {
            newList.add(new ItemRequirement(entry));
        }

        itemRequirementList.setModel(newList);

    }

    @Override
    public Task getChangedTask() {
        ItemTask itemTask;
        if (detectButton.isSelected()) {
            itemTask = new DetectItemTask();
        } else if (craftButton.isSelected()) {
            itemTask = new CraftTask();
        } else if (consumeButton.isBorderPainted()) {
            itemTask = new ConsumeTask();
        } else {
            itemTask = new ConsumeQDSTask();
        }

        itemTask.setItems(itemRequirementList.getEntries());
        return itemTask;
    }

}
