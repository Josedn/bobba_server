package io.bobba.poc.core.rooms.items;

import io.bobba.poc.communication.outgoing.FurniRemoveComposer;
import io.bobba.poc.communication.outgoing.SerializeFloorItemComposer;
import io.bobba.poc.communication.outgoing.SerializeWallItemComposer;
import io.bobba.poc.core.items.BaseItem;
import io.bobba.poc.core.rooms.Room;
import io.bobba.poc.core.rooms.users.RoomUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomItemManager {
    private Map<Integer, RoomItem> floorItems;
    private Map<Integer, WallItem> wallItems;
    private Room room;

    public RoomItemManager(Room room) {
        this.room = room;
        floorItems = new HashMap<>();
        wallItems = new HashMap<>();
    }

    public RoomItem getItem(int id) {
        if (floorItems.containsKey(id))
            return floorItems.get(id);
        if (wallItems.containsKey(id))
            return wallItems.get(id);
        return null;
    }

    public List<RoomItem> getFloorItems() {
        return new ArrayList<>(floorItems.values());
    }

    public List<WallItem> getWallItems() {
        return new ArrayList<>(wallItems.values());
    }

    public void addFloorItemToRoom(int id, int x, int y, double z, int rot, int state, BaseItem baseItem) {
        if (getItem(id) == null) {
            floorItems.put(id, new RoomItem(id, x, y, z, rot, state, room, baseItem));
            room.getGameMap().addItemToMap(floorItems.get(id));
            room.sendMessage(new SerializeFloorItemComposer(floorItems.get(id)));
            room.updateUserStatusses();
        }
    }

    public void removeItem(int id) {
        RoomItem item = getItem(id);
        if (item != null) {
            if (item instanceof WallItem) {
                wallItems.remove(item);
            } else {
                floorItems.remove(item);
                room.getGameMap().removeItemFromMap(item);
                room.updateUserStatusses();
            }
            room.sendMessage(new FurniRemoveComposer(id));
        }
    }

    public void removeAllFurniture() {
        List<Integer> items = new ArrayList<>();
        items.addAll(floorItems.keySet());
        items.addAll(wallItems.keySet());
        for (int itemId : items) {
            removeItem(itemId);
        }
    }

    public void addWallItemToRoom(int id, int x, int y, int rot, int state, BaseItem baseItem) {
        if (getItem(id) == null) {
            wallItems.put(id, new WallItem(id, x, y, rot, state, room, baseItem));
            room.sendMessage(new SerializeWallItemComposer(wallItems.get(id)));
        }
    }

    public void furniInteract(RoomUser user, int itemId) {
        RoomItem item = getItem(itemId);
        if (item != null) {
            item.getInteractor().onTrigger(user, true);
        }
    }
}
