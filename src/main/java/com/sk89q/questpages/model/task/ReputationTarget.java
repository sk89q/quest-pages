package com.sk89q.questpages.model.task;

import lombok.Data;

@Data
public class ReputationTarget {

    private int reputation;
    private int lower;
    private int upper;
    private boolean inverted;

}
