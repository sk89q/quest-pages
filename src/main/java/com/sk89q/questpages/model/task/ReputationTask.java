package com.sk89q.questpages.model.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk89q.questpages.model.ReputationTarget;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class ReputationTask extends Task {

    @JsonProperty("reputation")
    private List<ReputationTarget> reputations = new ArrayList<>();

}
