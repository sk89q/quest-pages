package com.sk89q.questpages.item;

import lombok.Value;

@Value
public class Item {

    private final ItemId id;
    private final int worldSpecificId;
    private final String displayName;

}
