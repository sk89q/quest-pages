package com.sk89q.questpages.item;

import au.com.bytecode.opencsv.CSVReader;
import lombok.extern.java.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Log
public class ItemDatabase {

    public static final ItemDatabase INSTANCE = new ItemDatabase();

    private final Map<ItemId, Item> items = new HashMap<>();

    private ItemDatabase() {
    }

    public void loadItemPanelCsv(File file) throws IOException {
        try (FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                CSVReader reader = new CSVReader(br)) {
            String[] line;
            boolean expectHeader = true;

            while ((line = reader.readNext()) != null) {
                if (expectHeader) {
                    expectHeader = false;
                } else if (line.length >= 5) {
                    try {
                        ItemId id = new ItemId(line[0], Integer.parseInt(line[2]));
                        Item info = new Item(id, Integer.parseInt(line[1]), line[4]);
                        items.put(id, info);
                    } catch (NumberFormatException e) {
                        log.warning("Found a non-number in " + Arrays.toString(line));
                    }
                }
            }
        }
    }

    public Item getItemInfo(ItemId id) {
        return items.get(id);
    }

    public Collection<Item> getItems() {
        return Collections.unmodifiableCollection(items.values());
    }

}
