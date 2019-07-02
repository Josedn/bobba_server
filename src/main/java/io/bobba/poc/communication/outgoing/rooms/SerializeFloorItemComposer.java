package io.bobba.poc.communication.outgoing.rooms;

import io.bobba.poc.communication.protocol.ServerMessage;
import io.bobba.poc.communication.protocol.ServerOpCodes;
import io.bobba.poc.core.rooms.items.RoomItem;

import java.util.Arrays;
import java.util.List;

public class SerializeFloorItemComposer extends ServerMessage {

    public SerializeFloorItemComposer(List<RoomItem> items) {
        super(ServerOpCodes.ROOM_ITEM_DATA);
        appendInt(items.size());
        for (RoomItem item : items) {
            appendInt(item.getId());
            appendInt(item.getX());
            appendInt(item.getY());
            appendFloat(item.getZ());
            appendInt(item.getRot());
            appendInt(item.getBaseItem().getBaseId());
            appendInt(item.getState());
        }
    }

    public SerializeFloorItemComposer(RoomItem item) {
        this(Arrays.asList(item));
    }

}
