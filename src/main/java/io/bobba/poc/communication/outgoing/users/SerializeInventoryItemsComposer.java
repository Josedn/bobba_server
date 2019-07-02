package io.bobba.poc.communication.outgoing.users;

import java.util.Arrays;
import java.util.List;

import io.bobba.poc.communication.protocol.ServerMessage;
import io.bobba.poc.communication.protocol.ServerOpCodes;
import io.bobba.poc.core.items.ItemType;
import io.bobba.poc.core.users.inventory.UserItem;

public class SerializeInventoryItemsComposer extends ServerMessage {
    public SerializeInventoryItemsComposer(List<UserItem> items) {
        super(ServerOpCodes.INVENTORY_ITEMS);
        appendInt(items.size());
        for (UserItem item : items) {
        	appendInt(item.getId());
        	appendString(item.getBaseItem().getType() == ItemType.RoomItem ? "F" : "W");
        	appendInt(item.getBaseItem().getBaseId());
        	appendInt(item.getState());
        	appendBoolean(item.getBaseItem().isInventoryStackable());
        }
    }

	public SerializeInventoryItemsComposer(UserItem item) {
    	this(Arrays.asList(item));
    }
}
