package io.bobba.poc.communication.outgoing.rooms;

import io.bobba.poc.communication.protocol.ServerMessage;
import io.bobba.poc.communication.protocol.ServerOpCodes;
import io.bobba.poc.core.rooms.users.RoomUser;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SerializeRoomUserStatus extends ServerMessage {

    public SerializeRoomUserStatus(List<RoomUser> users) {
        super(ServerOpCodes.PLAYER_STATUS);
        appendInt(users.size());
        for (RoomUser user : users) {
            appendInt(user.getVirtualId());
            appendInt(user.getX());
            appendInt(user.getY());
            appendFloat(user.getZ());
            appendInt(user.getRot());
            Map<String, String> statusses = user.getStattuses();
            appendInt(statusses.size());
            for (String key : statusses.keySet()) {
                appendString(key);
                appendString(statusses.get(key));
            }
        }
    }

    public SerializeRoomUserStatus(RoomUser user) {
        this(Arrays.asList(user));
    }
}
