package io.bobba.poc.communication.outgoing.navigator;

import java.util.List;

import io.bobba.poc.communication.protocol.ServerMessage;
import io.bobba.poc.communication.protocol.ServerOpCodes;
import io.bobba.poc.core.rooms.roomdata.RoomData;

public class NavigatorRoomListComposer extends ServerMessage {
	public NavigatorRoomListComposer(List<RoomData> rooms) {
		super(ServerOpCodes.NAVIGATOR_ROOM_LIST);

		appendInt(rooms.size());
		for (RoomData room : rooms) {
			serializeRoomData(room, this);
		}
	}

	public static void serializeRoomData(RoomData room, ServerMessage message) {
		message.appendInt(room.getId());
		message.appendString(room.getName());
		message.appendString(room.getOwner());
		message.appendString(room.getDescription());
		switch (room.getLockType()) {
		case Password:
			message.appendInt(2);
			break;
		case Locked:
			message.appendInt(1);
			break;
		default:
			message.appendInt(0);
		}
		message.appendInt(room.getUserCount());
		message.appendInt(room.getCapacity());
	}
}
