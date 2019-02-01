package io.bobba.poc.net;

import java.util.HashMap;
import java.util.Map;

public class ConnectionManager {
    private Map<Integer, Connection> connections;
    private ConnectionListener listener;

    public ConnectionManager(int port) {
        this.connections = new HashMap<>();

    }
}
