package io.bobba.poc.communication.outgoing.messenger;

import io.bobba.poc.communication.protocol.ServerMessage;
import io.bobba.poc.communication.protocol.ServerOpCodes;
import io.bobba.poc.core.users.User;

public class MessengerUpdateFriendComposer extends ServerMessage {
	public MessengerUpdateFriendComposer(User user) {
		super(ServerOpCodes.MESSENGER_UPDATE_FRIEND);
		appendInt(user.getId());
		appendString(user.getUsername());
		appendString(user.getLook());
		appendString(user.getMotto());
		appendBoolean(user.isConnected());
	}
}
