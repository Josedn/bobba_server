package io.bobba.poc.communication.outgoing.messenger;

import java.util.List;

import io.bobba.poc.communication.protocol.ServerMessage;
import io.bobba.poc.communication.protocol.ServerOpCodes;
import io.bobba.poc.core.users.User;

public class MessengerRequestsComposer extends ServerMessage {
	public MessengerRequestsComposer(List<User> friends) {
		super(ServerOpCodes.MESSENGER_REQUESTS);
		appendInt(friends.size());
		for (User user: friends) {
			appendInt(user.getId());
			appendString(user.getUsername());
			appendString(user.getLook());
			appendString(user.getMotto());
			appendBoolean(true); // Is online?
		}
	}
}
