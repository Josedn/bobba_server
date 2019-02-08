package io.bobba.poc.core.gameclients;

import io.bobba.poc.net.Connection;

public class GameClient {
    private Connection connection;
    private GameClientMessageHandler messageHandler;

    public GameClient(int id, Connection connection) {
        this.connection = connection;
    }
}
