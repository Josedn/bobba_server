package io.bobba.poc.core.navigator;

import java.util.ArrayList;
import java.util.List;

import io.bobba.poc.BobbaEnvironment;
import io.bobba.poc.communication.outgoing.navigator.NavigatorRoomListComposer;
import io.bobba.poc.core.rooms.Room;
import io.bobba.poc.core.rooms.roomdata.RoomData;
import io.bobba.poc.core.users.User;

public class Navigator {
	public Navigator() {

	}

	public void handleGetPopularRooms(User user) {
		List<Room> rooms = BobbaEnvironment.getGame().getRoomManager().getLoadedRooms();
		List<RoomData> data = new ArrayList<>();

		for (Room room : rooms) {
			data.add(room.getRoomData());
		}

		user.getClient().sendMessage(new NavigatorRoomListComposer(data));
	}

	public void handleGetOwnRooms(User user) {
		List<Room> rooms = BobbaEnvironment.getGame().getRoomManager().getLoadedRooms();
		List<RoomData> data = new ArrayList<>();

		for (Room room : rooms) {
			if (room.getRoomData().getOwner().toLowerCase().equals(user.getUsername().toLowerCase())) {
				data.add(room.getRoomData());
			}
		}

		user.getClient().sendMessage(new NavigatorRoomListComposer(data));
	}

	public void handleSearchRooms(User user, String search) {
		List<Room> rooms = BobbaEnvironment.getGame().getRoomManager().getLoadedRooms();
		List<RoomData> data = new ArrayList<>();

		for (Room room : rooms) {
			if (room.getRoomData().getName().toLowerCase().contains(search.toLowerCase())) {
				data.add(room.getRoomData());
			}
		}

		user.getClient().sendMessage(new NavigatorRoomListComposer(data));
	}
}
