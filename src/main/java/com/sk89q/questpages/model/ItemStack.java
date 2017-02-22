package com.sk89q.questpages.model;

import com.sk89q.questpages.item.ItemId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemStack {

    private String id = "minecraft:stone";
    private int damage;
    private int amount = 1;
    private String nbt;

    public ItemStack(ItemStack itemStack) {
        this.id = itemStack.getId();
        this.damage = itemStack.getDamage();
        this.amount = itemStack.getAmount();
        this.nbt = itemStack.getNbt();
    }

    public ItemId toItemId() {
        return new ItemId(id, damage);
    }

}
