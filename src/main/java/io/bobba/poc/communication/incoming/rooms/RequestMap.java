package io.bobba.poc.communication.incoming.rooms;

import io.bobba.poc.BobbaEnvironment;
import io.bobba.poc.communication.incoming.IIncomingEvent;
import io.bobba.poc.communication.outgoing.MapComposer;
import io.bobba.poc.communication.protocol.ClientMessage;
import io.bobba.poc.core.gameclients.GameClient;

public class RequestMap implements IIncomingEvent {
    @Override
    public void handle(GameClient client, ClientMessage request) {
        client.sendMessage(new MapComposer(BobbaEnvironment.getInstance().getGame().getRoom().getGameMap().getRoomModel()));
    }
}
