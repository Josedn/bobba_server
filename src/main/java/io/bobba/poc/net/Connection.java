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



}
