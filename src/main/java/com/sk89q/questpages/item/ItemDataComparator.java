package com.sk89q.questpages.item;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

public class ItemDataComparator implements Comparator<Map.Entry<ItemId, ItemInfo>> {

    public static final ItemDataComparator INSTANCE = new ItemDataComparator();

    private ItemDataComparator() {
    }

    @Override
    public int compare(Entry<ItemId, ItemInfo> o1, Entry<ItemId, ItemInfo> o2) {
        String name1 = o1.getKey().getName();
        String name2 = o2.getKey().getName();

        boolean mc1 = name1.startsWith("minecraft:");
        boolean mc2 = name2.startsWith("minecraft:");

        if (mc1 && !mc2) {
            return -1;
        } else if (mc2 && !mc1) {
            return 1;
        } else {
            int id1 = o1.getValue().getId();
            int id2 = o2.getValue().getId();
            if (id1 < id2) {
                return -1;
            } else if (id1 > id2) {
                return 1;
            } else {
                return 0;
            }
        }
    }

}
