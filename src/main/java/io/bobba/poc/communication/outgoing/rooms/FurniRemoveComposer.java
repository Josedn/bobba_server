package io.bobba.poc.communication.outgoing.rooms;

import io.bobba.poc.communication.protocol.ServerMessage;
import io.bobba.poc.communication.protocol.ServerOpCodes;

public class FurniRemoveComposer extends ServerMessage {
    public FurniRemoveComposer(int furniId)
    {
        super(ServerOpCodes.ITEM_REMOVE);
        appendInt(furniId);
    }
}
