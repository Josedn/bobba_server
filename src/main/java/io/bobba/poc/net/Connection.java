package io.bobba.poc.net;

import java.util.Date;

import io.netty.channel.Channel;

public class Connection {
    private int id;
    private Date created;
    private Channel channel;

    //private WebSocket socket;

    public Connection(int id, Channel channel) {
        this.id = id;
        this.channel = channel;
        //this.socket = socket;
        this.created = new Date();
    }

    public int getId() {
        return id;
    }

    public boolean isOpen() {
    	return false;
        //return this.socket.isOpen();
    }

    public Date getCreated() {
        return created;
    }

    /*public WebSocket getSocket() {
        return socket;
    }*/

    public void send(String data) {
        //this.socket.send(data);
    }

    public void close() {
        /*if (this.socket.isOpen()) {
            this.socket.close();
        }*/
    }
}
