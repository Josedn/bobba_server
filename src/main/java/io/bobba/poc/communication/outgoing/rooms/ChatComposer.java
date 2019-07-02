package io.bobba.poc.communication.outgoing.rooms;

import io.bobba.poc.communication.protocol.ServerMessage;
import io.bobba.poc.communication.protocol.ServerOpCodes;

public class ChatComposer extends ServerMessage {
	public ChatComposer(int userId, String chat) {
		super(ServerOpCodes.CHAT);
		appendInt(userId);
		appendString(chat);
	}
}
