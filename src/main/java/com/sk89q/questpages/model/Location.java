package com.sk89q.questpages.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    private String name;
    private ItemStack icon;
    private int x;
    private int y;
    private int z;
    private int dim;
    private int radius;
    private LocationVisibility location = LocationVisibility.FULL;

    public Location(Location location) {
        setName(location.getName());
        setIcon(location.getIcon() != null ? new ItemStack(location.getIcon()) : null);
        setX(location.getX());
        setY(location.getY());
        setZ(location.getZ());
        setDim(location.getDim());
        setRadius(location.getRadius());
        setLocation(location.getLocation());
    }

}
