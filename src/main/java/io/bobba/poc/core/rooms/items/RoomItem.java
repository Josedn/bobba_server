package io.bobba.poc.core.rooms.items;

import io.bobba.poc.communication.outgoing.rooms.FurniStateComposer;
import io.bobba.poc.communication.protocol.ServerMessage;
import io.bobba.poc.core.items.BaseItem;
import io.bobba.poc.core.rooms.Room;
import io.bobba.poc.core.rooms.users.RoomUser;
import io.bobba.poc.core.rooms.gamemap.GameMap;
import io.bobba.poc.core.rooms.items.interactors.InteractorGenericSwitch;
import io.bobba.poc.core.rooms.items.interactors.RoomItemInteractor;
import io.bobba.poc.misc.TextHandling;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class RoomItem {
    private int id;
    private int x, y, rot;
    private double z;
    private int state;
    private Room room;

    private boolean needsUpdate;
    private BaseItem baseItem;

    private RoomItemInteractor interactor;
    private List<Point> coords;

    public RoomItem(int id, int x, int y, double z, int rot, int state, Room room, BaseItem baseItem) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.rot = rot;
        this.z = z;
        this.state = state;
        this.room = room;
        this.needsUpdate = false;
        this.baseItem = baseItem;
        this.interactor = new InteractorGenericSwitch(this);
        updateCoords();
    }

    public RoomItemInteractor getInteractor() {
        return interactor;
    }

    private void updateCoords() {
        coords = new ArrayList<>();
        coords = GameMap.getAffectedTiles(baseItem.getX(), baseItem.getY(), x, y, rot);
        coords.add(new Point(x, y));
    }

    public List<Point> getCoords() {
        if (needsUpdate || coords == null) {
            updateCoords();
        }
        return coords;
    }

    public int getState() {
        return state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getRot() {
        return rot;
    }

    public void setRot(int rot) {
        this.rot = rot;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public boolean isNeedsUpdate() {
        return needsUpdate;
    }

    public void setNeedsUpdate(boolean needsUpdate) {
        this.needsUpdate = needsUpdate;
    }

    public BaseItem getBaseItem() {
        return baseItem;
    }

    public void setBaseItem(BaseItem baseItem) {
        this.baseItem = baseItem;
    }

    public void updateState() {
        this.needsUpdate = true;
        ServerMessage updateMessage = new FurniStateComposer(id, state);
        room.sendMessage(updateMessage);
    }

    public double getTotalHeight() {
        return baseItem.getZ() + z;
    }

    public void onUserWalk(RoomUser user) {
        if (getBaseItem().isSeat()) {
            user.addStatus("sit", TextHandling.getFloatString(getBaseItem().getZ()));
            user.setZ(getZ());
            user.setRot(getRot());
            user.setNeedsUpdate(true);
        }
    }
}
