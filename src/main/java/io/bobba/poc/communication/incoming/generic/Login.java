package io.bobba.poc.communication.incoming.generic;

import io.bobba.poc.communication.incoming.IIncomingEvent;
import io.bobba.poc.communication.protocol.ClientMessage;
import io.bobba.poc.core.gameclients.GameClient;

public class Login implements IIncomingEvent {

    @Override
    public void handle(GameClient gameClient, ClientMessage request) {
        String username = request.popString();
        String look = request.popString();
        //TODO: do login
    }
}
