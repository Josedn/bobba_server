package io.bobba.poc.core.gameclients;

import io.bobba.poc.net.Connection;
import io.bobba.poc.net.IConnectionHandler;

import java.util.HashMap;
import java.util.Map;

public class GameClientManager implements IConnectionHandler {
    private Map<Integer, GameClient> clients;
    private GameClientMessageHandler messageHandler;

    public GameClientManager() {
        this.clients = new HashMap<>();
        this.messageHandler = new GameClientMessageHandler();
    }

    @Override
    public void handleNewConnection(Connection newConnection) {

    }

    @Override
    public void handleDisconnect(Connection connection) {

    }

    @Override
    public void handleMessage(Connection connection, String message) {

    }
}
