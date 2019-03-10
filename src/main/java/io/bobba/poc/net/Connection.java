package io.bobba.poc.net;

import org.java_websocket.WebSocket;

import java.util.Date;

public class Connection {
    private int id;
    private Date created;

    private WebSocket socket;

    public Connection(int id, WebSocket socket) {
        this.id = id;
        this.socket = socket;
        this.created = new Date();
    }

    public int getId() {
        return id;
    }

    public boolean isOpen() {
        return this.socket.isOpen();
    }

    public Date getCreated() {
        return created;
    }

    public WebSocket getSocket() {
        return socket;
    }

    public void send(String data) {
        this.socket.send(data);
    }

    public void close() {
        if (this.socket.isOpen()) {
            this.socket.close();
        }
    }
}
