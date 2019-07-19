package io.bobba.poc.core.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseItemManager {
	public static int baseItemId = 0;

	private Map<Integer, BaseItem> items;

	public BaseItemManager() {
		this.items = new HashMap<>();
	}

	public void initialize() {
		this.addRoomItem(baseItemId++, 13, 1, 1, 1.0, "shelves_norja", 1, false, false, false, Arrays.asList(0, 2));
		this.addRoomItem(baseItemId++, 267, 2, 1, 1.0, "club_sofa", 1, false, false, true, Arrays.asList(0, 2, 4, 6));
		this.addRoomItem(baseItemId++, 3188, 2, 2, 0.01, "lt_patch", 3, false, true, false, Arrays.asList(0, 2, 4, 6));
		this.addRoomItem(baseItemId++, 3177, 2, 1, 1.05, "lt_stone2", 3, true, false, false, Arrays.asList(0, 2));
		this.addRoomItem(baseItemId++, 3172, 2, 1, 0.01, "lt_gate", 2, false, true, false, Arrays.asList(0, 2, 4, 6));
		this.addRoomItem(baseItemId++, 1575, 1, 1, 0, "scifidoor*4", 2, false, true, false, Arrays.asList(2, 4));
		this.addRoomItem(baseItemId++, 4733, 1, 1, 0, "hween12_scarecrow", 2, false, false, false,
				Arrays.asList(0, 2, 4, 6));
		this.addRoomItem(baseItemId++, 1632, 1, 1, 0, "rare_icecream*2", 2, false, false, false, Arrays.asList(2, 4));
		this.addRoomItem(baseItemId++, 1621, 1, 1, 0, "rare_dragonlamp*5", 2, false, false, false, Arrays.asList(2, 4));
		this.addRoomItem(baseItemId++, 4729, 1, 1, 1.0, "hween12_cart", 2, false, false, true,
				Arrays.asList(0, 2, 4, 6));
		this.addRoomItem(baseItemId++, 55, 1, 1, 1.0, "small_chair_armas", 1, false, false, true,
				Arrays.asList(0, 2, 4, 6));
		this.addRoomItem(baseItemId++, 4731, 1, 1, 0.25, "hween12_track", 3, true, true, false,
				Arrays.asList(0, 2, 4, 6));
		this.addRoomItem(baseItemId++, 4736, 1, 1, 0.25, "hween12_track_crl", 3, true, true, false,
				Arrays.asList(0, 2, 4, 6));
		this.addRoomItem(baseItemId++, 4739, 1, 1, 0.25, "hween12_track_crr", 3, true, true, false,
				Arrays.asList(0, 2, 4, 6));
		this.addRoomItem(baseItemId++, 3189, 1, 1, 0.4, "LT_skull", 1, false, false, false, Arrays.asList(0, 2, 4, 6));
		this.addRoomItem(baseItemId++, 4740, 1, 1, 0.01, "hween12_moon", 4, false, true, false, Arrays.asList(2, 4));

		this.addRoomItem(baseItemId++, 5735, 2, 2, 0, "stories_shakespeare_tree", 2, false, false, false,
				Arrays.asList(0, 2));
		this.addRoomItem(baseItemId++, 4655, 3, 1, 0, "anc_artifact3", 1, false, false, false, Arrays.asList(2, 4));
		this.addRoomItem(baseItemId++, 4651, 1, 1, 0, "anc_waterfall", 1, false, true, false, Arrays.asList(2, 4));
		this.addRoomItem(baseItemId++, 4650, 2, 2, 0, "anc_talltree", 1, false, false, false, Arrays.asList(2, 4));
		this.addRoomItem(baseItemId++, 4653, 1, 1, 0, "anc_comfy_tree", 1, false, false, false,
				Arrays.asList(0, 2, 4, 6));

		this.addWallItem(baseItemId++, 4121, "lt_jngl_wall", 3);
		this.addWallItem(baseItemId++, 4462, "anc_sunset_wall", 2);

		this.addRoomItem(baseItemId++, 1505, 1, 1, 0, "doorD", 3, false, true, false, Arrays.asList(2, 4));
		this.addRoomItem(baseItemId++, 179, 1, 1, 1.0, "duck", 1, false, false, false, Arrays.asList(0, 2, 4, 6));

		this.addRoomItem(baseItemId++, 1565, 1, 1, 0, "scifirocket*3", 2, false, false, false, Arrays.asList(0));

		this.addWallItem(baseItemId++, 4258, "flag_columbia", 1);

		this.addWallItem(baseItemId++, 4003, "hc_wall_lamp", 2);
	}

	public BaseItem addRoomItem(int id, int baseId, int x, int y, double z, String itemName, int states,
			boolean stackable, boolean walkable, boolean seat, List<Integer> directions) {
		if (!items.containsKey(id)) {
			items.put(id, new BaseItem(id, ItemType.RoomItem, baseId, x, y, z, itemName, states, stackable, walkable,
					seat, directions, InteractionType.DEFAULT));
		}
		return items.get(id);
	}

	public BaseItem addWallItem(int id, int baseId, String itemName, int states) {
		if (!items.containsKey(id)) {
			items.put(id, new BaseItem(id, ItemType.WallItem, baseId, 0, 0, 0, itemName, states, false, false, false,
					Arrays.asList(2, 4), InteractionType.DEFAULT));
		}
		return items.get(id);
	}

	public BaseItem getItem(int id) {
		return items.getOrDefault(id, null);
	}

	public List<BaseItem> getItems() {
		return new ArrayList<BaseItem>(items.values());
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
