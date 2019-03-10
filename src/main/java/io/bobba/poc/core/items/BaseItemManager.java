package io.bobba.poc.core.items;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseItemManager {
    private Map<Integer, BaseItem> items;

    public BaseItemManager() {
        this.items = new HashMap<>();
    }

    public BaseItem addRoomItem(int id, int baseId, int x, int y, double z, String itemName, int states, boolean stackable, boolean walkable, boolean seat, List<Integer> directions) {
        if (!items.containsKey(id)) {
            items.put(id, new BaseItem(id, ItemType.RoomItem, baseId, x, y, z, itemName, states, stackable, walkable, seat, directions));
        }
        return items.get(id);
    }

    public BaseItem addWallItem(int id, int baseId, String itemName, int states) {
        if (!items.containsKey(id)) {
            items.put(id, new BaseItem(id, ItemType.RoomItem, baseId, 0, 0, 0, itemName, states, false, false, false, Arrays.asList(2, 4)));
        }
        return items.get(id);
    }

    public BaseItem getItem(int id) {
        return items.getOrDefault(id, null);
    }

    public BaseItem findItem(String itemName) {
        for (BaseItem item : items.values()) {
            if (itemName.toLowerCase().equals(item.getItemName().toLowerCase())) {
                return item;
            }
        }
        return null;
    }
}
