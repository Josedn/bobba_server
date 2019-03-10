package io.bobba.poc.communication.incoming.rooms;

import io.bobba.poc.communication.incoming.IIncomingEvent;
import io.bobba.poc.communication.protocol.ClientMessage;
import io.bobba.poc.core.gameclients.GameClient;
import io.bobba.poc.core.rooms.users.RoomUser;

public class RequestChat implements IIncomingEvent {

    @Override
    public void handle(GameClient client, ClientMessage request) {
        String chat = request.popString();

        RoomUser user = client.getUser().getCurrentRoomUser();
        if (user != null) {
            user.chat(chat);
        }
    }
}
