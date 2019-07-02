package io.bobba.poc.communication.outgoing.rooms;

import io.bobba.poc.communication.protocol.ServerMessage;
import io.bobba.poc.communication.protocol.ServerOpCodes;

public class FurniStateComposer extends ServerMessage {
    public FurniStateComposer(int furniId, int state) {
        super(ServerOpCodes.ITEM_STATE);
        appendInt(furniId);
        appendInt(state);
    }
}
