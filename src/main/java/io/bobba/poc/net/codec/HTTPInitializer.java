package io.bobba.poc.net.codec;

import io.bobba.poc.net.ConnectionManager;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class HTTPInitializer extends ChannelInitializer<SocketChannel> {

	private ConnectionManager manager;

	public HTTPInitializer(ConnectionManager manager) {
		this.manager = manager;
	}

	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		ChannelPipeline pipeline = socketChannel.pipeline();
		pipeline.addLast("httpServerCodec", new HttpServerCodec());
		pipeline.addLast("httpHandler", new HttpServerHandler(manager));
	}
}