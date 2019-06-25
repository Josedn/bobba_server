package io.bobba.poc.net;

import java.util.HashMap;
import java.util.Map;

import io.bobba.poc.net.codec.HTTPInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class ConnectionManager {
	private int totalConnectionCount;
	private Map<Channel, Connection> connections;
	public IConnectionHandler connectionHandler;
	private ServerBootstrap server;
	EventLoopGroup bossGroup;
	EventLoopGroup workerGroup;

	public ConnectionManager(int port, IConnectionHandler connectionHandler) throws InterruptedException {

		this.connectionHandler = connectionHandler;
		this.connections = new HashMap<>();
		this.totalConnectionCount = 0;

		// Configure the server.
		this.bossGroup = new NioEventLoopGroup();
		this.workerGroup = new NioEventLoopGroup();

		this.server = new ServerBootstrap();
		this.server.option(ChannelOption.SO_BACKLOG, 1024);
		this.server.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
				.childHandler(new HTTPInitializer(this));

		this.server.bind(port).sync().channel();
	}

	public void dispose() {
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
	}

	public void startNewConnection(Channel channel) {
		Connection connection = new Connection(generateConnectionId(), channel);
		this.connections.put(channel, connection);
		this.connectionHandler.handleNewConnection(connection);
	}

	public void stopConnection(Channel channel) {
		this.connectionHandler.handleDisconnect(this.connections.get(channel));
		this.connections.remove(channel);
	}

	public void handleMessage(Channel channel, String message) {
		this.connectionHandler.handleMessage(this.connections.get(channel), message);
	}

	private int generateConnectionId() {
		return this.totalConnectionCount++;
	}
}
