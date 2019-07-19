package io.bobba.poc.communication.outgoing.roomdata;

import io.bobba.poc.communication.protocol.ServerMessage;
import io.bobba.poc.communication.protocol.ServerOpCodes;
import io.bobba.poc.core.rooms.gamemap.RoomModel;
import io.bobba.poc.core.rooms.gamemap.SqState;

public class HeightMapComposer extends ServerMessage {
    public HeightMapComposer(RoomModel model) {
        super(ServerOpCodes.ROOM_DATA_HEIGHTMAP);
        appendInt(model.getMapSizeX());
        appendInt(model.getMapSizeY());
        appendInt(model.getDoorX());
        appendInt(model.getDoorY());

        for (int i = 0; i < model.getMapSizeX(); i++)
        {
            for (int j = 0; j < model.getMapSizeY(); j++)
            {
            	if (model.getSqState()[i][j] != SqState.Closed) {
            		appendInt(model.getSqFloorHeight()[i][j] + 1);
            	} else {
            		appendInt(0);
            	}
                
            }
        }
    }
}
