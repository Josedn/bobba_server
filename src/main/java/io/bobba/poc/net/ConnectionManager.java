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
    private Channel listener;
    private ServerBootstrap server;

    public ConnectionManager(int port, IConnectionHandler connectionHandler) {
    	
        this.connectionHandler = connectionHandler;
        this.connections = new HashMap<>();
        this.totalConnectionCount = 0;
    	
    	// Configure the server.
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup(2);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG, 1024);
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new HTTPInitializer(this));

            this.listener = b.bind(port).sync().channel();
            
            if (this.listener.isOpen()) {
				this.server = b;
				// Bind and start to accept incoming connections.
			}
            
            this.listener.closeFuture().sync();
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
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
/*
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
    
    */
}
