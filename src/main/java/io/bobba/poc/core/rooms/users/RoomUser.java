package io.bobba.poc.core.rooms.users;

import io.bobba.poc.communication.outgoing.rooms.ChatComposer;
import io.bobba.poc.communication.outgoing.rooms.WaveComposer;
import io.bobba.poc.core.rooms.Room;
import io.bobba.poc.core.rooms.gamemap.GameMap;
import io.bobba.poc.core.rooms.gamemap.SqState;
import io.bobba.poc.core.users.User;
import io.bobba.poc.misc.logging.LogLevel;
import io.bobba.poc.misc.logging.Logging;

import java.util.HashMap;
import java.util.Map;

public class RoomUser {

    private int virtualId;
    private int x;
    private int y;
    private double z;
    private int rot;
    private int targetX;
    private int targetY;
    private int nextX;
    private int nextY;
    private double nextZ;
    private boolean walking;
    private boolean needsUpdate;
    private SqState currentSqState;

    private Map<String, String> stattuses;

    private Room room;
    private User user;

    public int getVirtualId() {
        return virtualId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public int getRot() {
        return rot;
    }

    public void setRot(int rot) {
        this.rot = rot;
    }

    public int getTargetX() {
        return targetX;
    }

    public void setTargetX(int targetX) {
        this.targetX = targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    public void setTargetY(int targetY) {
        this.targetY = targetY;
    }

    public int getNextX() {
        return nextX;
    }

    public void setNextX(int nextX) {
        this.nextX = nextX;
    }

    public int getNextY() {
        return nextY;
    }

    public void setNextY(int nextY) {
        this.nextY = nextY;
    }

    public double getNextZ() {
        return nextZ;
    }

    public void setNextZ(double nextZ) {
        this.nextZ = nextZ;
    }

    public boolean isWalking() {
        return walking;
    }

    public void setWalking(boolean walking) {
        this.walking = walking;
    }

    public boolean isNeedsUpdate() {
        return needsUpdate;
    }

    public void setNeedsUpdate(boolean needsUpdate) {
        this.needsUpdate = needsUpdate;
    }

    public Room getRoom() {
        return room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SqState getCurrentSqState() {
        return currentSqState;
    }

    public void setCurrentSqState(SqState currentSqState) {
        this.currentSqState = currentSqState;
    }

    public RoomUser(int virtualId, int x, int y, double z, int rot, Room room, User user) {
        this.virtualId = virtualId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.rot = rot;
        this.room = room;
        this.user = user;

        this.targetX = x;
        this.targetY = y;
        this.nextX = x;
        this.nextY = y;
        this.nextZ = z;

        this.walking = false;

        this.stattuses = new HashMap<>();
        this.currentSqState = SqState.WalkableLast;
    }

    public Map<String, String> getStattuses() {
        return new HashMap<>(stattuses);
    }

    public String getStatus(String key) {
        return this.stattuses.getOrDefault(key, null);
    }

    public void addStatus(String key, String value) {
        this.stattuses.put(key, value);
    }

    public boolean hasStatus(String key) {
        return this.stattuses.containsKey(key);
    }

    public void removeStatus(String key) {
        if (this.stattuses.containsKey(key)) {
            this.stattuses.remove(key);
        }
    }

    public void chat(String message) {
        if ((message.startsWith(":") || message.startsWith("/")) && SimpleChatCommandHandler.parse(this, message.substring(1))) {
            return;
        }
        if (message.toLowerCase().contains("o/"))
            wave();
        room.sendMessage(new ChatComposer(virtualId, message));
    }

    public void furniInteract(int itemId) {
        if (room != null) {
            room.getRoomItemManager().furniInteract(this, itemId);
        }
    }

    public void lookAt(int userId) {
        RoomUser otherUser = room.getRoomUserManager().getUser(userId);
        if (otherUser != null && otherUser != this && getStatus("sit") == null) {
            setRot(GameMap.calculateRotation(x, y, otherUser.getX(), otherUser.getY()));
            setNeedsUpdate(true);
        }
    }

    public void wave() {
        room.sendMessage(new WaveComposer(virtualId));
    }

    public void moveTo(int x, int y) {
        Logging.getInstance().writeLine(getUser().getUsername() + " wants to move to " + x + ", " + y, LogLevel.Debug, this.getClass());
        if (room.getGameMap().canWalkTo(x, y)) {
            this.setTargetX(x);
            this.setTargetY(y);
            this.setWalking(true);
        }
    }
}
