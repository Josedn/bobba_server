package io.bobba.poc.core.rooms.items;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.bobba.poc.communication.outgoing.rooms.FurniRemoveComposer;
import io.bobba.poc.communication.outgoing.rooms.SerializeFloorItemComposer;
import io.bobba.poc.communication.outgoing.rooms.SerializeWallItemComposer;
import io.bobba.poc.core.items.BaseItem;
import io.bobba.poc.core.items.ItemType;
import io.bobba.poc.core.rooms.Room;
import io.bobba.poc.core.rooms.users.RoomUser;
import io.bobba.poc.core.users.inventory.UserItem;

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
			room.getRoomUserManager().updateUserStatusses();
		}
	}

	public void removeItem(int id) {
		RoomItem item = getItem(id);
		if (item != null) {
			if (item instanceof WallItem) {
				wallItems.remove(id);
			} else {
				floorItems.remove(id);
				room.getGameMap().removeItemFromMap(item);
				room.getRoomUserManager().updateUserStatusses();
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

	public void onCycle() {
	}

	public void handleItemMovement(int itemId, int x, int y, int rot, RoomUser user) {
		RoomItem item = getItem(itemId);
		if (item != null) {
			if (item.getBaseItem().getDirections().contains(rot)) {
				this.removeItem(itemId);
				if (item instanceof WallItem) {
					this.addWallItemToRoom(itemId, x, y, rot, item.getState(), item.getBaseItem());
				} else {
					double nextZ = room.getGameMap().sqAbsoluteHeight(new Point(x, y));
					this.addFloorItemToRoom(itemId, x, y, nextZ, rot, item.getState(), item.getBaseItem());
				}
			}
		}
	}

	public void handleItemPickUp(int itemId, RoomUser user) {
		RoomItem item = getItem(itemId);
		if (item != null) {
			this.removeItem(itemId);
			
			user.getUser().getInventory().addItem(itemId, item.getBaseItem(), item.getState());
		}
	}
	
	public void handlePickAll(RoomUser user) {
		for (RoomItem item: new ArrayList<>(floorItems.values())) {
			handleItemPickUp(item.getId(), user);
		}
		for (RoomItem item: new ArrayList<>(wallItems.values())) {
			handleItemPickUp(item.getId(), user);
		}
	}

	public void handleItemPlacement(int itemId, int x, int y, int rot, RoomUser user) {
		UserItem userItem = user.getUser().getInventory().getItem(itemId);
		if (userItem != null) {
			user.getUser().getInventory().removeItem(itemId);
			if (userItem.getBaseItem().getType() == ItemType.WallItem) {
				addWallItemToRoom(itemId, x, y, rot, userItem.getState(), userItem.getBaseItem());
			} else {
				double nextZ = room.getGameMap().sqAbsoluteHeight(new Point(x, y));
				this.addFloorItemToRoom(itemId, x, y, nextZ, rot, userItem.getState(), userItem.getBaseItem());
			}
		}

	}
}
