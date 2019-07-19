package io.bobba.poc.net.codec;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import io.bobba.poc.net.ConnectionManager;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslHandler;

public class HTTPSInitializer extends ChannelInitializer<SocketChannel> {

	private ConnectionManager manager;
	private SSLContext sslContext;

	public HTTPSInitializer(ConnectionManager manager, SSLContext sslContext) {
		this.manager = manager;
		this.sslContext = sslContext;
	}
	
	private SslHandler generateSslHandler() {
		SSLEngine sslEngine = sslContext.createSSLEngine();
		sslEngine.setUseClientMode(false);
		sslEngine.setNeedClientAuth(false);
		return new SslHandler(sslEngine);
	}

	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {

		ChannelPipeline pipeline = socketChannel.pipeline();
		pipeline.addLast(generateSslHandler());
		pipeline.addLast("httpServerCodec", new HttpServerCodec());
		pipeline.addLast("httpHandler", new HttpServerHandler(manager));
	}
}