package io.bobba.poc.communication.incoming.navigator;

import io.bobba.poc.BobbaEnvironment;
import io.bobba.poc.communication.incoming.IIncomingEvent;
import io.bobba.poc.communication.protocol.ClientMessage;
import io.bobba.poc.core.gameclients.GameClient;
import io.bobba.poc.core.users.User;
//OpenFlat
public class RequestNavigatorGoToRoom implements IIncomingEvent {
    @Override
    public void handle(GameClient client, ClientMessage request) {
    	User user = client.getUser();
        if (user != null){
        	int roomId = request.popInt();
        	String password = request.popString();
        	BobbaEnvironment.getGame().getRoomManager().prepareRoomForUser(user, roomId, password);
        }
    }
}
