package io.bobba.poc.communication.outgoing.navigator;

import io.bobba.poc.communication.protocol.ServerMessage;
import io.bobba.poc.communication.protocol.ServerOpCodes;

public class NavigatorLeaveRoomComposer extends ServerMessage {
	public NavigatorLeaveRoomComposer() {
		super(ServerOpCodes.NAVIGATOR_LEAVE_ROOM);
	}
}
