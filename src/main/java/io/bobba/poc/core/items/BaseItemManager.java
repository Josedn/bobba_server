package io.bobba.poc.core.items;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.bobba.poc.BobbaEnvironment;

public class BaseItemManager {
	public static int baseItemId = 0;

	private Map<Integer, BaseItem> items;

	public BaseItemManager() {
		this.items = new HashMap<>();
	}

	private void loadFromDb() throws SQLException {
		try (Connection connection = BobbaEnvironment.getGame().getDatabase().getDataSource().getConnection();
				Statement statement = connection.createStatement()) {
			if (statement.execute("SELECT * FROM furniture")) {
				try (ResultSet set = statement.getResultSet()) {
					while (set.next()) {
						int id = set.getInt("id");
						String itemName = set.getString("item_name");
						String type = set.getString("type");
						int x = set.getInt("width");
						int y = set.getInt("length");
						double z = set.getDouble("stack_height");
						boolean canStack = set.getString("can_stack").equals("1");
						boolean canSit = set.getString("can_sit").equals("1");
						boolean canWalk = set.getString("is_walkable").equals("1");
						int spriteId = set.getInt("sprite_id");
						//boolean inventoryStack = set.getString("allow_inventory_stack").equals("1");
						//String interaction = set.getString("interaction_type");
						int states = set.getInt("interaction_modes_count");
						
						if (type.equals("s")) {
							addRoomItem(id, spriteId, x, y, z, itemName, states, canStack, canWalk, canSit, Arrays.asList(0, 2, 4, 6));
						} else if (type.equals("i")) {
							addWallItem(id,  spriteId, itemName, states);
						}
					}
				}
			}
		} catch (SQLException e) {
			throw e;
		}
	}

	public void initialize() throws SQLException {
		loadFromDb();
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
