package io.bobba.poc.core.rooms.users;

import io.bobba.poc.communication.outgoing.*;
import io.bobba.poc.core.gameclients.GameClient;
import io.bobba.poc.core.rooms.Room;
import io.bobba.poc.core.rooms.gamemap.GameMap;
import io.bobba.poc.core.rooms.items.RoomItem;
import io.bobba.poc.core.rooms.users.RoomUser;
import io.bobba.poc.misc.TextHandling;
import io.bobba.poc.misc.logging.Logging;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public RoomUser getUser(int id) {
        return users.getOrDefault(id, null);
    }

    public void addUserToRoom(GameClient client) {
        RoomUser user = new RoomUser(client.getUser().getId(), room.getGameMap().getRoomModel().doorX, room.getGameMap().getRoomModel().doorY, room.getGameMap().getRoomModel().doorZ, room.getGameMap().getRoomModel().doorRot, room, client.getUser());
        client.getUser().setCurrentRoom(room);

        room.sendMessage(new SerializeRoomUserComposer(user));
        users.put(user.getVirtualId(), user);
        client.sendMessage(new SerializeFloorItemComposer(room.getRoomItemManager().getFloorItems()));
        client.sendMessage(new SerializeWallItemComposer(room.getRoomItemManager().getWallItems()));
        List<RoomUser> users = getUsers();
        client.sendMessage(new SerializeRoomUserComposer(users));
        client.sendMessage(new SerializeRoomUserStatus(users));
    }

    public void removeUserFromRoom(GameClient client) {
        if (client == null)
            return;
        RoomUser user = getUser(client.getUser().getId());
        if (user == null)
            return;
        room.getGameMap().removeUserFromMap(user);
        users.remove(client.getUser().getId());
        room.sendMessage(new PlayerRemoveComposer(client.getUser().getId()));
    }

    private void broadcastStatusUpdates() {
        List<RoomUser> users = getUsers();
        List<RoomUser> usersToUpdate = new ArrayList<>();

        for (RoomUser user : users) {
                if (!user.isNeedsUpdate()) {
                    continue;
                }
                user.setNeedsUpdate(false);
            usersToUpdate.add(user);
        }
        if (usersToUpdate.size() > 0) {
            room.sendMessage(new SerializeRoomUserStatus(usersToUpdate));
        }
    }

    public void onCycle() {
        List<RoomUser> currentUsers = getUsers();
        for (RoomUser user : currentUsers) {
            if (user.isWalking()){
                System.out.println(user.getUser().getUsername() + " is walking");
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
                room.getGameMap().updateUserMovement(new Point(user.getX(), user.getY()), new Point(user.getNextX(), user.getNextY()), user);
                user.setX(user.getNextX());
                user.setY(user.getNextY());
                user.setZ(user.getNextZ());
            } else {
                user.setWalking(false);
                user.removeStatus("mv");
            }
            updateUserStatus(user);
        }
        Point nextStep = room.getGameMap().getUserNextStep(user.getX(), user.getY(), user.getTargetX(), user.getTargetY());
        if (nextStep.getX() == user.getX() && nextStep.getY() == user.getY()) { //No path found or already on goal
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
        } else { //Walking canceled
            user.setWalking(false);
            user.removeStatus("mv");
            updateUserStatus(user);
        }
        user.setNeedsUpdate(true);
    }

    private void updateUserStatus(RoomUser user) {
        if (user.getStatus("lay") != null || user.getStatus("sit") != null) {
            user.removeStatus("lay");
            user.removeStatus("sit");
            user.setNeedsUpdate(true);
        }
        List<RoomItem> itemsOnSquare = room.getGameMap().getCoordinatedHeighestItems(new Point(user.getX(), user.getY()));
        for (RoomItem item : itemsOnSquare) {
            item.onUserWalk(user);
        }
    }
}
