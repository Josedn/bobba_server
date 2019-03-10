package io.bobba.poc.communication.incoming;

import io.bobba.poc.communication.protocol.ClientMessage;
import io.bobba.poc.core.gameclients.GameClient;

public interface IIncomingEvent {
    void handle(GameClient client, ClientMessage request);
}
