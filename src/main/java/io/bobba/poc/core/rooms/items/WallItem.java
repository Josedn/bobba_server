package io.bobba.poc.core.rooms.items;

import io.bobba.poc.core.items.BaseItem;
import io.bobba.poc.core.rooms.Room;

public class WallItem extends RoomItem {
    public WallItem(int id, int x, int y, int rot, int state, Room room, BaseItem baseItem) {
        super(id, x, y, 0, rot, state, room, baseItem);
    }
}
