package com.sk89q.questpages.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReputationTarget {

    private int reputation;
    private int lower;
    private int upper;
    private boolean inverted;

    public ReputationTarget(ReputationTarget requirement) {
        setReputation(requirement.getReputation());
        setLower(requirement.getLower());
        setUpper(requirement.getUpper());
        setInverted(requirement.isInverted());
    }

}
