package io.bobba.poc.core.users.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.bobba.poc.communication.outgoing.users.InventoryItemRemoveComposer;
import io.bobba.poc.communication.outgoing.users.SerializeInventoryItemsComposer;
import io.bobba.poc.core.items.BaseItem;
import io.bobba.poc.core.users.User;

public class Inventory {
	private User user;
	private Map<Integer, UserItem> items;

	public Inventory(User user) {
		this.user = user;
		this.items = new HashMap<>();
	}

	public void addItem(int itemId, BaseItem baseItem, int state) {
		if (getItem(itemId) == null) {
			UserItem newItem = new UserItem(itemId, baseItem, state);
			items.put(itemId, newItem);
			this.getUser().getClient().sendMessage(new SerializeInventoryItemsComposer(newItem));
		}
	}

	public void removeItem(int itemId) {
		if (getItem(itemId) != null) {
			this.items.remove(itemId);
			
			this.getUser().getClient().sendMessage(new InventoryItemRemoveComposer(itemId));
		}
	}

	public UserItem getItem(int itemId) {
		return this.items.getOrDefault(itemId, null);
	}

	public User getUser() {
		return this.user;
	}
	
	public void serialize() {
		this.getUser().getClient().sendMessage(new SerializeInventoryItemsComposer(new ArrayList<UserItem>(this.items.values())));
	}
}
