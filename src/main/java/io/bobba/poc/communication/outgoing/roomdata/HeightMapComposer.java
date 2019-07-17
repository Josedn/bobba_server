package io.bobba.poc.communication.outgoing.roomdata;

import io.bobba.poc.communication.protocol.ServerMessage;
import io.bobba.poc.communication.protocol.ServerOpCodes;
import io.bobba.poc.core.rooms.gamemap.RoomModel;

public class HeightMapComposer extends ServerMessage {
    public HeightMapComposer(RoomModel model) {
        super(ServerOpCodes.ROOM_DATA_HEIGHTMAP);
        appendInt(model.maxX);
        appendInt(model.maxY);
        appendInt(model.doorX);
        appendInt(model.doorY);

        for (int i = 0; i < model.maxX; i++)
        {
            for (int j = 0; j < model.maxY; j++)
            {
                appendInt(model.map[i][j]);
            }
        }
    }
}
