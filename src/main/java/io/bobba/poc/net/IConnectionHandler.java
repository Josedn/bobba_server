package io.bobba.poc.net;

public interface IConnectionHandler {

    void handleNewConnection(ClientConnection newConnection);

    void handleDisconnect(ClientConnection connection);

    void handleMessage(ClientConnection connection, String message);
}
