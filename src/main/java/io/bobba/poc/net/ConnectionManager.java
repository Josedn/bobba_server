package io.bobba.poc.net;

import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;

import io.bobba.poc.misc.logging.LogLevel;
import io.bobba.poc.misc.logging.Logging;
import io.bobba.poc.net.codec.HTTPInitializer;
import io.bobba.poc.net.codec.HTTPSInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ConnectionManager {
	private int totalConnectionCount;
	private Map<Channel, ClientConnection> connections;
	public IConnectionHandler connectionHandler;
	private ServerBootstrap server;
	EventLoopGroup bossGroup;
	EventLoopGroup workerGroup;

	public ConnectionManager(int port, IConnectionHandler connectionHandler, SSLContext sslContext)
			throws InterruptedException {
		this.connectionHandler = connectionHandler;
		this.connections = new HashMap<>();
		this.totalConnectionCount = 0;

		// Configure the server.
		this.bossGroup = new NioEventLoopGroup();
		this.workerGroup = new NioEventLoopGroup();

		this.server = new ServerBootstrap();
		this.server.option(ChannelOption.SO_BACKLOG, 1024);

		ChannelHandler channelHandler = null;

		if (sslContext != null) {
			channelHandler = new HTTPSInitializer(this, sslContext);
		} else {
			channelHandler = new HTTPInitializer(this);
		}

		this.server.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(channelHandler);

		this.server.bind(port).sync().channel();
		if (sslContext != null) {
			Logging.getInstance().writeLine("Server listening securely on " + port + "...", LogLevel.Verbose,
					this.getClass());
		} else {
			Logging.getInstance().writeLine("Server listening on " + port + "...", LogLevel.Verbose, this.getClass());
		}
	}

	public ConnectionManager(int port, IConnectionHandler connectionHandler) throws InterruptedException {
		this(port, connectionHandler, null);
	}

	public void dispose() {
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
	}

	public void startNewConnection(Channel channel) {
		ClientConnection connection = new ClientConnection(generateConnectionId(), channel);
		this.connections.put(channel, connection);
		this.connectionHandler.handleNewConnection(connection);
	}

	public void stopConnection(Channel channel) {
		ClientConnection connection = this.connections.get(channel);
		if (connection != null) {
			this.connectionHandler.handleDisconnect(connection);
			connection.close();
			this.connections.remove(channel);
		}
	}

	public void handleMessage(Channel channel, String message) {
		ClientConnection connection = this.connections.get(channel);
		if (connection != null) {
			this.connectionHandler.handleMessage(connection, message);
		}
	}

	private int generateConnectionId() {
		return this.totalConnectionCount++;
	}
}
