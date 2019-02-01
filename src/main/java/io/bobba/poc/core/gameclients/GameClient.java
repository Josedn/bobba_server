package io.bobba.poc.core.gameclients;

import io.bobba.poc.net.Connection;

public class GameClient {
    private int id;
    private Connection connection;

    public GameClient(int id, Connection connection) {
        this.id = id;
        this.connection = connection;
    }
}
