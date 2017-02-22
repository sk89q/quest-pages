package com.sk89q.questpages.model.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sk89q.questpages.ui.component.task.TaskPanel;
import lombok.Data;

import javax.swing.*;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @Type(value = DetectItemTask.class, name = "DETECT"),
        @Type(value = CraftTask.class, name = "CRAFT"),
        @Type(value = ConsumeTask.class, name = "CONSUME"),
        @Type(value = ConsumeQDSTask.class, name = "CONSUME_QDS"),
        @Type(value = LocationTask.class, name = "LOCATION"),
        @Type(value = ReputationTargetTask.class, name = "REPUTATION"),
        @Type(value = DeathTask.class, name = "DEATH"),
        @Type(value = KillTask.class, name = "KILL"),
        @Type(value = ReputationKillTask.class, name = "REPUTATION_KILL")
})
@Data
public abstract class Task {

    @JsonProperty("description")
    private String name;
    @JsonProperty("longDescription")
    private String description;

    @JsonIgnore
    public abstract String getTypeName();

    public String getDisplayName() {
        return getName();
    }

    @JsonIgnore
    public abstract String getDisplayDesc(boolean isSelected);

    @JsonIgnore
    public abstract ImageIcon getDisplayIcon();

    public abstract TaskPanel createEditPanel();

}
