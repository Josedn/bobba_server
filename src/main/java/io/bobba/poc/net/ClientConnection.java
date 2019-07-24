package io.bobba.poc.net;

import java.util.Date;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class ClientConnection {
	private int id;
	private Date created;
	private Channel channel;

	public ClientConnection(int id, Channel channel) {
		this.id = id;
		this.channel = channel;
		this.created = new Date();
	}

	public int getId() {
		return id;
	}

	public boolean isOpen() {
		return this.channel.isOpen();
	}

	public Date getCreated() {
		return created;
	}

	public void send(String data) {
		this.channel.writeAndFlush(new TextWebSocketFrame(data));
	}

	public void close() {
		if (this.channel.isOpen()) {
			this.channel.close();
		}
	}
}
