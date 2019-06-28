package io.bobba.poc.core.users;

import io.bobba.poc.communication.outgoing.LoginOkComposer;
import io.bobba.poc.core.gameclients.GameClient;
import io.bobba.poc.core.rooms.Room;
import io.bobba.poc.core.rooms.users.RoomUser;
import io.bobba.poc.misc.logging.LogLevel;
import io.bobba.poc.misc.logging.Logging;

public class User {
	private int id;
	private String username;
	private String look;
	private String motto;
	private GameClient client;
	private boolean disconnected;
	private Room currentRoom;

	public Room getCurrentRoom() {
		return currentRoom;
	}

	public void setCurrentRoom(Room currentRoom) {
		this.currentRoom = currentRoom;
	}

	public int getId() {
		return id;
	}

	public GameClient getClient() {
		return client;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLook() {
		return look;
	}

	public void setLook(String look) {
		this.look = look;
		if (this.getCurrentRoomUser() != null) {
			this.getCurrentRoomUser().getRoom().getRoomUserManager().serializeUser(this.getCurrentRoomUser());
		}
		client.sendMessage(new LoginOkComposer(getId(), getUsername(), getLook(), getMotto()));
	}

	public String getMotto() {
		return motto;
	}

	public void setMotto(String motto) {
		this.motto = motto;
	}

	public User(int id, String username, String motto, String look, GameClient client) {
		this.id = id;
		this.username = username;
		this.motto = motto;
		this.look = look;
		this.client = client;
		this.disconnected = false;
	}

	public RoomUser getCurrentRoomUser() {
		if (currentRoom != null) {
			return currentRoom.getRoomUserManager().getUser(id);
		}
		return null;
	}

	public void onDisconnect() {
		if (disconnected)
			return;
		Logging.getInstance().writeLine(username + " has logged out", LogLevel.Verbose, this.getClass());
		if (currentRoom != null) {
			currentRoom.getRoomUserManager().removeUserFromRoom(client);
		}
	}
}
