package io.bobba.poc.core.gameclients;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import io.bobba.poc.misc.logging.LogLevel;
import io.bobba.poc.misc.logging.Logging;
import io.bobba.poc.net.ClientConnection;
import io.bobba.poc.net.IConnectionHandler;

public class GameClientManager implements IConnectionHandler {
    private ConcurrentMap<Integer, GameClient> clients;
    private GameClientMessageHandler messageHandler;

    public GameClientManager() {
        this.clients = new ConcurrentHashMap<>();
        this.messageHandler = new GameClientMessageHandler();
    }

    public GameClientMessageHandler getSharedMessageHandler() {
        return this.messageHandler;
    }

    @Override
    public void handleNewConnection(ClientConnection newConnection) {
        if (!this.clients.containsKey(newConnection.getId())) {
            this.clients.put(newConnection.getId(), new GameClient(newConnection.getId(), newConnection));
            Logging.getInstance().writeLine("New gameclient created (" + newConnection.getId() + ")", LogLevel.Debug, this.getClass());
        }
    }

    @Override
    public void handleDisconnect(ClientConnection connection) {
        GameClient disconnected = this.clients.remove(connection.getId());
        if (disconnected != null) {
			disconnected.stop();
            Logging.getInstance().writeLine("Gameclient dropped (" + connection.getId() + ")", LogLevel.Debug, this.getClass());
        }
    }

    @Override
    public void handleMessage(ClientConnection connection, String message) {
        if (this.clients.containsKey(connection.getId())) {
            //Logging.getInstance().writeLine("Message incoming", LogLevel.Debug, this.getClass());
            this.clients.get(connection.getId()).handleMessage(message);
        }
    }
}
