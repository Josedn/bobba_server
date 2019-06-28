package io.bobba.poc.net.codec;

import io.bobba.poc.net.ConnectionManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;

public class HttpServerHandler extends ChannelInboundHandlerAdapter {

	WebSocketServerHandshaker handshaker;
	ConnectionManager manager;
	
	public HttpServerHandler(ConnectionManager manager) {
		this.manager = manager;
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		manager.stopConnection(ctx.channel());
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		if (msg instanceof HttpRequest) {

			HttpRequest httpRequest = (HttpRequest) msg;
			HttpHeaders headers = httpRequest.headers();

			if ("Upgrade".equalsIgnoreCase(headers.get(HttpHeaderNames.CONNECTION))
					&& "WebSocket".equalsIgnoreCase(headers.get(HttpHeaderNames.UPGRADE))) {

				// Adding new handler to the existing pipeline to handle WebSocket Messages
				ctx.pipeline().replace(this, "websocketHandler", new WebSocketHandler(manager));
				
				// Do the Handshake to upgrade connection from HTTP to WebSocket protocol
				handleHandshake(ctx, httpRequest);
				
				this.manager.startNewConnection(ctx.channel());
				//System.out.println("Handshake is done");
			}
		}
	}

	/* Do the handshaking for WebSocket request */
	protected void handleHandshake(ChannelHandlerContext ctx, HttpRequest req) {
		WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(getWebSocketURL(req), null,
				true);
		handshaker = wsFactory.newHandshaker(req);
		if (handshaker == null) {
			WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
		} else {
			handshaker.handshake(ctx.channel(), req);
		}
	}

	protected String getWebSocketURL(HttpRequest req) {
		//System.out.println("Req URI : " + req.uri());
		String url = "ws://" + req.headers().get("Host") + req.uri();
		//System.out.println("Constructed URL : " + url);
		return url;
	}
}