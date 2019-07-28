package io.bobba.poc.communication.incoming.users;

import io.bobba.poc.BobbaEnvironment;
import io.bobba.poc.communication.incoming.IIncomingEvent;
import io.bobba.poc.communication.protocol.ClientMessage;
import io.bobba.poc.core.gameclients.GameClient;

public class Login implements IIncomingEvent {

    @Override
    public void handle(GameClient client, ClientMessage request) {
        String username = request.popString();
        String look = request.popString();
        BobbaEnvironment.getGame().getUserManager().tryLogin(client, username, look);
    }
}
