package io.bobba.poc.net;

public interface IConnectionHandler {

    void handleNewConnection(Connection newConnection);

    void handleDisconnect(Connection connection);

    void handleMessage(Connection connection, String message);
}
