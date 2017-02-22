package com.sk89q.questpages.ui.component;

import com.sk89q.questpages.ui.renderer.ItemStackCellRenderer;
import com.sk89q.questpages.model.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemStackList extends ModifiableListPanel<ItemStack> {

    public ItemStackList() {
        getList().setCellRenderer(new ItemStackCellRenderer());

        // TODO: Remove
        List<ItemStack> stacks = new ArrayList<>();
        stacks.add(new ItemStack("minecraft:stone", 0, 1, ""));
        stacks.add(new ItemStack("minecraft:obsidian", 0, 1, ""));
        setModel(stacks);
    }

}
