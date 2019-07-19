package io.bobba.poc.core.rooms.users;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.bobba.poc.communication.outgoing.navigator.NavigatorLeaveRoomComposer;
import io.bobba.poc.communication.outgoing.rooms.PlayerRemoveComposer;
import io.bobba.poc.communication.outgoing.rooms.SerializeFloorItemComposer;
import io.bobba.poc.communication.outgoing.rooms.SerializeRoomUserComposer;
import io.bobba.poc.communication.outgoing.rooms.SerializeRoomUserStatus;
import io.bobba.poc.communication.outgoing.rooms.SerializeWallItemComposer;
import io.bobba.poc.core.rooms.Room;
import io.bobba.poc.core.rooms.gamemap.GameMap;
import io.bobba.poc.core.rooms.items.RoomItem;
import io.bobba.poc.core.users.User;
import io.bobba.poc.misc.TextHandling;

public class RoomUserManager {

	private Map<Integer, RoomUser> users;
	private Room room;

	public RoomUserManager(Room room) {
		this.room = room;
		this.users = new HashMap<>();
	}

	public List<RoomUser> getUsers() {
		return new ArrayList<>(users.values());
	}

	public RoomUser getUser(String name) {
		for (RoomUser user : getUsers()) {
			if (user.getUser().getUsername().toLowerCase().equals(name.toLowerCase())) {
				return user;
			}
		}
		return null;
	}

	public RoomUser getUser(int id) {
		return users.getOrDefault(id, null);
	}

	public void addUserToRoom(User user) {
		if (user.getClient() != null) {
			RoomUser roomUser = new RoomUser(user.getId(), room.getGameMap().getRoomModel().getDoorX(),
					room.getGameMap().getRoomModel().getDoorY(), room.getGameMap().getRoomModel().getDoorZ(),
					room.getGameMap().getRoomModel().getDoorRot(), room, user);
			user.setCurrentRoom(room);

			room.sendMessage(new SerializeRoomUserComposer(roomUser));
			users.put(roomUser.getVirtualId(), roomUser);
			user.getClient().sendMessage(new SerializeFloorItemComposer(room.getRoomItemManager().getFloorItems()));
			user.getClient().sendMessage(new SerializeWallItemComposer(room.getRoomItemManager().getWallItems()));
			List<RoomUser> users = getUsers();
			user.getClient().sendMessage(new SerializeRoomUserComposer(users));
			user.getClient().sendMessage(new SerializeRoomUserStatus(users));

			updateRoomData();
		}
	}

	private void updateRoomData() {
		room.getRoomData().setUserCount(users.size());
	}

	public void serializeUser(RoomUser user) {
		room.sendMessage(new SerializeRoomUserComposer(user));
		room.sendMessage(new SerializeRoomUserStatus(user));
	}

	public void removeUserFromRoom(User user) {
		RoomUser roomUser = getUser(user.getId());
		if (roomUser != null) {
			room.getGameMap().removeUserFromMap(roomUser);
			users.remove(user.getId());
			user.getClient().sendMessage(new NavigatorLeaveRoomComposer());
			room.sendMessage(new PlayerRemoveComposer(user.getId()));
			user.setCurrentRoom(null);
			
			updateRoomData();
		}
	}

	private void broadcastStatusUpdates() {
		List<RoomUser> users = getUsers();
		List<RoomUser> usersToUpdate = new ArrayList<>();

		for (RoomUser user : users) {
			if (user.isNeedsUpdate()) {
				user.setNeedsUpdate(false);
				usersToUpdate.add(user);
			}
		}
		if (usersToUpdate.size() > 0) {
			room.sendMessage(new SerializeRoomUserStatus(usersToUpdate));
		}
	}

	public void onCycle() {
		List<RoomUser> currentUsers = getUsers();
		for (RoomUser user : currentUsers) {
			if (user.isWalking()) {
				handleWalkingUser(user);
			} else {
				user.removeStatus("mv");
			}
		}

		broadcastStatusUpdates();
	}

	private void handleWalkingUser(RoomUser user) {
		if (user.getX() != user.getNextX() || user.getY() != user.getNextY()) {
			if (room.getGameMap().canWalkTo(user.getNextX(), user.getNextY())) {
				room.getGameMap().updateUserMovement(new Point(user.getX(), user.getY()),
						new Point(user.getNextX(), user.getNextY()), user);
				user.setX(user.getNextX());
				user.setY(user.getNextY());
				user.setZ(user.getNextZ());
			} else {
				user.setWalking(false);
				user.removeStatus("mv");
			}
			updateUserStatus(user);
		}
		Point nextStep = room.getGameMap().getUserNextStep(user.getX(), user.getY(), user.getTargetX(),
				user.getTargetY());
		if (nextStep.getX() == user.getX() && nextStep.getY() == user.getY()) { // No path found or already on goal
			user.setWalking(false);
			user.removeStatus("mv");
		} else if (room.getGameMap().canWalkTo(nextStep.x, nextStep.y)) {
			user.setNextX(nextStep.x);
			user.setNextY(nextStep.y);
			double nextZ = room.getGameMap().sqAbsoluteHeight(nextStep);
			user.setNextZ(nextZ);
			user.addStatus("mv", nextStep.x + "," + nextStep.y + "," + TextHandling.getFloatString(nextZ));
			int newRot = GameMap.calculateRotation(user.getX(), user.getY(), user.getNextX(), user.getNextY());
			user.setRot(newRot);
		} else { // Walking canceled
			user.setWalking(false);
			user.removeStatus("mv");
			updateUserStatus(user);
		}
		user.setNeedsUpdate(true);
	}

	public void updateUserStatusses() {
		for (RoomUser user : getUsers()) {
			updateUserStatus(user);
		}
	}

	public void updateUserStatus(RoomUser user) {
		if (user.getStatus("lay") != null || user.getStatus("sit") != null) {
			user.removeStatus("lay");
			user.removeStatus("sit");
			user.setNeedsUpdate(true);
		}
		List<RoomItem> itemsOnSquare = room.getGameMap()
				.getCoordinatedHeighestItems(new Point(user.getX(), user.getY()));
		for (RoomItem item : itemsOnSquare) {
			item.onUserWalk(user);
		}
	}
}
