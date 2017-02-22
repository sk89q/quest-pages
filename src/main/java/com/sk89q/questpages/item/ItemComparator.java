package com.sk89q.questpages.item;

import java.util.Comparator;

public class ItemComparator implements Comparator<Item> {

    public static final ItemComparator INSTANCE = new ItemComparator();

    private ItemComparator() {
    }

    @Override
    public int compare(Item o1, Item o2) {
        String name1 = o1.getId().getName();
        String name2 = o2.getId().getName();

        boolean mc1 = name1.startsWith("minecraft:");
        boolean mc2 = name2.startsWith("minecraft:");

        if (mc1 && !mc2) {
            return -1;
        } else if (mc2 && !mc1) {
            return 1;
        } else {
            int id1 = o1.getWorldSpecificId();
            int id2 = o2.getWorldSpecificId();
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
