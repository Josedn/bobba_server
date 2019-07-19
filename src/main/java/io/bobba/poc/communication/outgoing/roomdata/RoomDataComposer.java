package io.bobba.poc.communication.outgoing.roomdata;

import io.bobba.poc.communication.outgoing.navigator.NavigatorRoomListComposer;
import io.bobba.poc.communication.protocol.ServerMessage;
import io.bobba.poc.communication.protocol.ServerOpCodes;
import io.bobba.poc.core.rooms.roomdata.RoomData;

public class RoomDataComposer extends ServerMessage {
	public RoomDataComposer(RoomData roomData) {
		super(ServerOpCodes.ROOM_DATA);
		NavigatorRoomListComposer.serializeRoomData(roomData, this);
	}
}
