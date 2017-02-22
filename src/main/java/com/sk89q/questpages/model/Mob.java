package com.sk89q.questpages.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mob {

    private String name = "Creeper";
    private ItemStack icon;
    private int mob;
    private int kills;
    private boolean exact;

    public Mob(Mob mob) {
        this.name = mob.getName();
        this.icon = mob.getIcon() != null ? new ItemStack(mob.getIcon()) : null;
        this.mob = mob.getMob();
        this.kills = mob.getKills();
        this.exact = mob.isExact();
    }

}
