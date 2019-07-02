package io.bobba.poc.communication.outgoing.rooms;

import io.bobba.poc.communication.protocol.ServerMessage;
import io.bobba.poc.communication.protocol.ServerOpCodes;

public class PlayerRemoveComposer extends ServerMessage {
    public PlayerRemoveComposer(int userId) {
        super(ServerOpCodes.PLAYER_REMOVE);
        appendInt(userId);
    }
}
