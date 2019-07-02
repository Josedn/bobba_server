package io.bobba.poc.communication.outgoing.users;

import io.bobba.poc.communication.protocol.ServerMessage;
import io.bobba.poc.communication.protocol.ServerOpCodes;

public class InventoryItemRemoveComposer extends ServerMessage {
    public InventoryItemRemoveComposer(int itemId) {
        super(ServerOpCodes.INVENTORY_ITEM_REMOVE);
        appendInt(itemId);
    }
}
