package io.bobba.poc.core.rooms;

import java.util.List;

import io.bobba.poc.communication.protocol.ServerMessage;
import io.bobba.poc.core.rooms.gamemap.GameMap;
import io.bobba.poc.core.rooms.gamemap.RoomModel;
import io.bobba.poc.core.rooms.items.RoomItemManager;
import io.bobba.poc.core.rooms.roomdata.RoomData;
import io.bobba.poc.core.rooms.users.RoomUser;
import io.bobba.poc.core.rooms.users.RoomUserManager;

public class Room {
	private RoomData roomData;
	private RoomUserManager roomUserManager;
	private RoomItemManager roomItemManager;
	private GameMap gameMap;

	public RoomUserManager getRoomUserManager() {
		return roomUserManager;
	}

	public RoomItemManager getRoomItemManager() {
		return roomItemManager;
	}

	public GameMap getGameMap() {
		return gameMap;
	}

	public RoomData getRoomData() {
		return roomData;
	}

	public Room(RoomData roomData, RoomModel roomModel) {
		this.roomData = roomData;
		this.roomItemManager = new RoomItemManager(this);
		this.roomUserManager = new RoomUserManager(this);
		this.gameMap = new GameMap(this, roomModel);
	}

	public void sendMessage(ServerMessage message) {
		List<RoomUser> playersToSend = roomUserManager.getUsers();
		for (RoomUser user : playersToSend) {
			if (user != null) {
				user.getUser().getClient().sendMessage(message);
			}
		}
	}

	public void onCycle() {
		roomItemManager.onCycle();
		roomUserManager.onCycle();
	}
}
