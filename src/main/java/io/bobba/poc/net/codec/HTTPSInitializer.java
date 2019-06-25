package io.bobba.poc.net.codec;

import io.bobba.poc.net.ConnectionManager;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslHandler;

public class HTTPSInitializer extends ChannelInitializer<SocketChannel> {

	private ConnectionManager manager;
	private SslHandler sslHandler;

	public HTTPSInitializer(ConnectionManager manager, SslHandler sslHandler) {
		this.manager = manager;
		this.sslHandler = sslHandler;
	}

	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		ChannelPipeline pipeline = socketChannel.pipeline();
		pipeline.addLast(this.sslHandler);
		pipeline.addLast("httpServerCodec", new HttpServerCodec());
		pipeline.addLast("httpHandler", new HttpServerHandler(manager));
	}
}