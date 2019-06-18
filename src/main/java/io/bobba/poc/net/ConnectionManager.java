package io.bobba.poc.net;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import io.bobba.poc.misc.logging.LogLevel;
import io.bobba.poc.misc.logging.Logging;

public class ConnectionManager extends WebSocketServer {
    private int totalConnectionCount;
    private Map<WebSocket, Connection> connections;
    private IConnectionHandler connectionHandler;

    public ConnectionManager(int port, IConnectionHandler connectionHandler) {
        super(new InetSocketAddress(port));
        this.connectionHandler = connectionHandler;
        this.connections = new HashMap<>();
        this.totalConnectionCount = 0;
    }

    private int generateConnectionId() {
        return this.totalConnectionCount++;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        Connection newConnection = new Connection(generateConnectionId(), conn);
        this.connections.put(conn, newConnection);
        this.connectionHandler.handleNewConnection(newConnection);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        this.connectionHandler.handleDisconnect(this.connections.get(conn));
        this.connections.remove(conn);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        this.connectionHandler.handleMessage(this.connections.get(conn), message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        Logging.getInstance().logError("Error with socket", ex, this.getClass());
    }

    @Override
    public void onStart() {
        Logging.getInstance().writeLine("Server listening on " + this.getPort() + "...", LogLevel.Debug, this.getClass());
    }
}
