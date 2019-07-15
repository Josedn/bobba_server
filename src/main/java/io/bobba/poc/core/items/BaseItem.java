package io.bobba.poc.core.items;

import java.util.List;

public class BaseItem {
    private int id;
    private ItemType type;
    private int baseId;
    private int x;
    private int y;
    private double z;
    private String itemName;
    private int states;
    private boolean stackable;
    private boolean walkable;
    private boolean seat;
    private boolean inventoryStackable;
    private InteractionType interactionType;
    private List<Integer> directions;

    public BaseItem(int id, ItemType type, int baseId, int x, int y, double z, String itemName, int states, boolean stackable, boolean walkable, boolean seat, List<Integer> directions, InteractionType interactionType) {
        this.id = id;
        this.type = type;
        this.baseId = baseId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.itemName = itemName;
        this.states = states;
        this.stackable = stackable;
        this.walkable = walkable;
        this.seat = seat;
        this.directions = directions;
        this.interactionType = interactionType;
        this.inventoryStackable = true;
    }

    public int getId() {
        return id;
    }

    public ItemType getType() {
        return type;
    }

    public int getBaseId() {
        return baseId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public String getItemName() {
        return itemName;
    }

    public int getStates() {
        return states;
    }

    public boolean isStackable() {
        return stackable;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public boolean isSeat() {
        return seat;
    }
    
    public InteractionType getInteractionType() {
    	return interactionType;
    }

    public List<Integer> getDirections() {
        return directions;
    }

	public boolean isInventoryStackable() {
		return inventoryStackable;
	}
}
