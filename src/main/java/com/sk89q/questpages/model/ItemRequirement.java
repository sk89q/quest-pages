package com.sk89q.questpages.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sk89q.questpages.item.Item;
import com.sk89q.questpages.item.ItemDatabase;
import com.sk89q.questpages.util.swing.SwingHelper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.awt.*;

import static com.sk89q.questpages.util.SharedLocale.tr;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequirement {

    public static final String ITEM_FIELD = "item";
    public static final String FLUID_FIELD = "fluid";
    public static final String REQUIRED_FIELD = "required";
    public static final String PRECISION_FIELD = "precision";

    private ItemStack item;
    private String fluid;
    private boolean required;
    private ItemPrecision precision = ItemPrecision.PRECISE;

    public ItemRequirement(ItemRequirement itemRequirement) {
        this.item = itemRequirement.getItem() != null ? new ItemStack(itemRequirement.getItem()) : null;
        this.fluid = itemRequirement.getFluid();
        this.required = itemRequirement.isRequired();
        this.precision = itemRequirement.getPrecision();
    }

    @JsonIgnore
    public String getFluidLabel(boolean isSelected) {
        // TODO: Fluid amount?
        return tr("itemRequirement.fluid", getFluid());
    }

    @JsonIgnore
    public String getItemLabel(boolean isSelected) {
        Color subtleColor = UIManager.getDefaults().getColor("TextField.inactiveForeground");

        StringBuilder builder = new StringBuilder();
        ItemStack itemStack = getItem();
        Item item = ItemDatabase.INSTANCE.getItemInfo(itemStack.toItemId());
        builder.append(itemStack.getAmount()).append(" \u00D7 ");
        builder.append(SwingHelper.htmlEscape(item != null ? item.getDisplayName() : itemStack.getId()));
        builder.append("<br>");
        if (isSelected) {
            builder.append("<span>");
        } else {
            builder.append("<span style=\"color: #").append(SwingHelper.hexCode(subtleColor)).append("\">");
        }
        builder.append(getPrecision().toString());
        builder.append(", ");
        builder.append(isRequired() ? tr("itemRequirement.required") : tr("itemRequirement.optional"));
        builder.append("</span>");
        return builder.toString();
    }

}
