package io.bobba.poc.communication.outgoing.roomdata;

import io.bobba.poc.communication.protocol.ServerMessage;
import io.bobba.poc.communication.protocol.ServerOpCodes;

public class RoomModelInfoComposer  extends ServerMessage {
    public RoomModelInfoComposer(String modelId, int roomId) {
    	super(ServerOpCodes.ROOM_DATA_MODEL_INFO);
    	appendString(modelId);
    	appendInt(roomId);
    }
}
