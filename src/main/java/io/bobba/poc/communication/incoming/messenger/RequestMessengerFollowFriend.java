package io.bobba.poc.communication.incoming.messenger;

import io.bobba.poc.communication.incoming.IIncomingEvent;
import io.bobba.poc.communication.protocol.ClientMessage;
import io.bobba.poc.core.gameclients.GameClient;
import io.bobba.poc.core.users.User;

public class RequestMessengerFollowFriend  implements IIncomingEvent {
    @Override
    public void handle(GameClient client, ClientMessage request) {
    	User user = client.getUser();
        if (user != null){
        	int userId = request.popInt();
        	user.getMessenger().handleFollowFriend(userId);
        }
    }
}