package io.bobba.poc.communication.incoming.rooms;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import io.bobba.poc.BobbaEnvironment;
import io.bobba.poc.communication.incoming.IIncomingEvent;
import io.bobba.poc.communication.protocol.ClientMessage;
import io.bobba.poc.core.gameclients.GameClient;
import io.bobba.poc.core.rooms.items.RoomItem;
import io.bobba.poc.core.rooms.users.RoomUser;

public class RequestFurniPickUp implements IIncomingEvent {
    @Override
    public void handle(GameClient client, ClientMessage request) {
        int itemId = request.popInt();
        
        RoomUser user = client.getUser().getCurrentRoomUser();
        RoomItem furni = user.getRoom().getRoomItemManager().getItem(itemId);
        int room_id = user.getRoom().getRoomData().getId();

        if (user != null){
        	user.getRoom().getRoomItemManager().handleItemPickUp(itemId, user);
        }

        try (Connection connection = BobbaEnvironment.getGame().getDatabase().getDataSource().getConnection(); Statement statement = connection.createStatement()) {
                    
            String query = "DELETE FROM room_furnis WHERE room_id = "+room_id+" and id = "+furni.getId();
            if (statement.execute(query)) {
                
            }
        } catch (SQLException e) {
            System.out.println("Erro ao deletar furni na sala: "+e);
        }
    }
}
