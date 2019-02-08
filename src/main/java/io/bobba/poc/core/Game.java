package io.bobba.poc.core;

import io.bobba.poc.BobbaEnvironment;
import io.bobba.poc.core.gameclients.GameClientManager;
import io.bobba.poc.net.ConnectionManager;

public class Game {
    private ConnectionManager connectionManager;
    private GameClientManager gameClientManager;

    public Game() {
        this.gameClientManager = new GameClientManager();
        this.connectionManager = new ConnectionManager(BobbaEnvironment.PORT, this.gameClientManager);
    }

}
