package com.sk89q.questpages.model;

import lombok.Data;

@Data
public class Repeatability {

    private RepeatType type = RepeatType.NONE;
    private int days;
    private int hours;

}
