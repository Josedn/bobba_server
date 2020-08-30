package io.bobba.poc.communication.incoming.rooms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import io.bobba.poc.BobbaEnvironment;

import io.bobba.poc.communication.incoming.IIncomingEvent;
import io.bobba.poc.communication.protocol.ClientMessage;
import io.bobba.poc.core.gameclients.GameClient;
import io.bobba.poc.core.items.BaseItem;
import io.bobba.poc.core.rooms.users.RoomUser;
import io.bobba.poc.core.rooms.items.RoomItem;
import io.bobba.poc.core.rooms.roomdata.RoomData;

public class RequestFurniPlace implements IIncomingEvent {
    @Override
    public void handle(GameClient client, ClientMessage request) {
        int itemId = request.popInt();
        int x = request.popInt();
        int y = request.popInt();
        int rot = request.popInt();

        RoomUser user = client.getUser().getCurrentRoomUser();
        if (user != null){
        	user.getRoom().getRoomItemManager().handleItemPlacement(itemId, x, y, rot, user);
        }
        
        RoomItem furni = user.getRoom().getRoomItemManager().getItem(itemId);
        
        int furni_base_id = furni.getBaseItem().getBaseId();
        int room_id = user.getRoom().getRoomData().getId();
        
        try (Connection connection = BobbaEnvironment.getGame().getDatabase().getDataSource().getConnection(); Statement statement = connection.createStatement()) {
                    
            String query = "INSERT INTO room_furnis (room_id, item_id, x, y, rot) VALUES ("+room_id+", "+furni_base_id+", "+x+", "+y+", "+rot+")";
            if (statement.execute(query)) {
                
            }
        } catch (SQLException e) {
            System.out.println("Erro ao salvar furni na sala: "+e);
        }
    }
}
