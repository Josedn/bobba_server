package io.bobba.poc.net.codec;

import io.bobba.poc.net.ConnectionManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

public class WebSocketHandler extends ChannelInboundHandlerAdapter {

	ConnectionManager manager;

	public WebSocketHandler(ConnectionManager manager) {
		this.manager = manager;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		manager.stopConnection(ctx.channel());
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		if (msg instanceof WebSocketFrame) {
			// System.out.println("This is a WebSocket frame");
			// System.out.println("Client Channel : " + ctx.channel());
			if (msg instanceof BinaryWebSocketFrame) {
				// System.out.println("BinaryWebSocketFrame Received : ");
				// System.out.println(((BinaryWebSocketFrame) msg).content());
			} else if (msg instanceof TextWebSocketFrame) {
				manager.handleMessage(ctx.channel(), ((TextWebSocketFrame) msg).text());

				// System.out.println("TextWebSocketFrame Received : ");
				// ctx.channel().writeAndFlush(new TextWebSocketFrame("3"));
				// System.out.println(((TextWebSocketFrame) msg).text());
			} else if (msg instanceof PingWebSocketFrame) {
				// System.out.println("PingWebSocketFrame Received : ");
				// System.out.println(((PingWebSocketFrame) msg).content());
			} else if (msg instanceof PongWebSocketFrame) {
				// System.out.println("PongWebSocketFrame Received : ");
				// System.out.println(((PongWebSocketFrame) msg).content());
			} else if (msg instanceof CloseWebSocketFrame) {

				manager.stopConnection(ctx.channel());

				// System.out.println("CloseWebSocketFrame Received : ");
				// System.out.println("ReasonText :" + ((CloseWebSocketFrame)
				// msg).reasonText());
				// System.out.println("StatusCode : " + ((CloseWebSocketFrame)
				// msg).statusCode());
			} else {
				// System.out.println("Unsupported WebSocketFrame");
				// System.out.println(msg.getClass().getCanonicalName());
				manager.stopConnection(ctx.channel());
			}
		}
	}
}