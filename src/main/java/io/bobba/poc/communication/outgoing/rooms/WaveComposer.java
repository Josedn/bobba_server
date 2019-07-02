package io.bobba.poc.communication.outgoing.rooms;

import io.bobba.poc.communication.protocol.ServerMessage;
import io.bobba.poc.communication.protocol.ServerOpCodes;

public class WaveComposer extends ServerMessage {
	public WaveComposer(int userId) {
		super(ServerOpCodes.PLAYER_WAVE);
		appendInt(userId);
	}
}
