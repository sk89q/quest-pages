package com.sk89q.questpages.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
public class QuestLine {

    private String name;
    private String description;
    private List<Quest> quests;

}
