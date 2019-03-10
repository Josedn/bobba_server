package io.bobba.poc.communication.incoming.generic;

import io.bobba.poc.BobbaEnvironment;
import io.bobba.poc.communication.incoming.IIncomingEvent;
import io.bobba.poc.communication.protocol.ClientMessage;
import io.bobba.poc.core.gameclients.GameClient;

public class Login implements IIncomingEvent {

    @Override
    public void handle(GameClient client, ClientMessage request) {
        String username = request.popString();
        System.out.println("USERNAME: " + username);
        String look = request.popString();
        BobbaEnvironment.getInstance().getGame().getAuthenticator().tryLogin(client, username, look);
    }
}
