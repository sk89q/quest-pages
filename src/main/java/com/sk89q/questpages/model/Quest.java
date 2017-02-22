package com.sk89q.questpages.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sk89q.questpages.model.task.Task;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "@id")
@Data
public class Quest {

    private String name = "";
    private String description = "";
    private int x;
    private int y;
    private ItemStack icon = new ItemStack();
    private boolean bigIcon;
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Quest> requirements = new HashSet<>();
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Quest> options = new HashSet<>();
    @JsonProperty("repeat")
    private Repeatability repeatability = new Repeatability();
    @JsonProperty("trigger")
    private TriggerType visibility = TriggerType.NONE;
    @JsonProperty("triggerTask")
    private int visibilityTaskCount;
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("parentRequirement")
    private Integer requirementCount;
    private List<Task> tasks = new ArrayList<>();
    @JsonProperty("reward")
    private List<ItemStack> rewards = new ArrayList<>();
    @JsonProperty("rewardChoice")
    private List<ItemStack> choiceRewards = new ArrayList<>();
    private List<ReputationReward> reputationRewards = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        return o == this;
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }
}
