package io.bobba.poc.core.rooms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.bobba.poc.BobbaEnvironment;
import io.bobba.poc.communication.outgoing.roomdata.HeightMapComposer;
import io.bobba.poc.communication.outgoing.roomdata.RoomDataComposer;
import io.bobba.poc.communication.outgoing.roomdata.RoomModelInfoComposer;
import io.bobba.poc.core.rooms.gamemap.RoomModel;
import io.bobba.poc.core.rooms.roomdata.LockType;
import io.bobba.poc.core.rooms.roomdata.RoomData;
import io.bobba.poc.core.users.User;

public class RoomManager {
	private static int roomId = 1;
	private Map<Integer, Room> rooms;
	private Map<String, RoomModel> models;
	
	public RoomManager() {
		this.rooms = new HashMap<>();
		this.models = new HashMap<>();
	}
	
	public RoomModel getModel(String modelId) {
		return models.getOrDefault(modelId, null);
	}
	
	public Room getLoadedRoom(int roomId) {
		return rooms.getOrDefault(roomId, null);
	}
	
	public void initialize() throws SQLException {
		this.loadModelsFromDb();
		this.loadRoomsFromDb();
	}
	
	private void loadModelsFromDb() throws SQLException {	
        try (Connection connection = BobbaEnvironment.getGame().getDatabase().getDataSource().getConnection(); Statement statement = connection.createStatement()) {
            if (statement.execute("SELECT id, door_x, door_y, door_z, door_dir, heightmap FROM room_models")) {
                try (ResultSet set = statement.getResultSet()) {
                    while (set.next()) {                    	
                    	String name = set.getString("id");
        				int doorX = set.getInt("door_x");
        				int doorY = set.getInt("door_y");
        				int doorZ = set.getInt("door_z");
        				int doorDir = set.getInt("door_dir");
        				String heightmap = set.getString("heightmap");

        				models.put(name, new RoomModel(doorX, doorY, doorZ, doorDir, heightmap));
                    }
                }
            }
        } catch (SQLException e) {
            throw e;
        }
	}
	
	private void loadRoomsFromDb() throws SQLException {

    System.out.println("Carregando salas...");
		int loadedRooms = 0;
    try (Connection connection = BobbaEnvironment.getGame().getDatabase().getDataSource().getConnection(); Statement statement = connection.createStatement()) {
        if (statement.execute("SELECT * FROM rooms")) {
            try (ResultSet set = statement.getResultSet()) {
                while (set.next()) {                
    							int roomid = set.getInt("id");

                	String roomName = set.getString("name");
    							String roomOwner = set.getString("owner");
    							String roomDesc = set.getString("description");
    							String roomPass = set.getString("password");
    							String roomModel = set.getString("modelId");
    							String roomLockType = set.getString("lockType");
    							int roomCapacity = set.getInt("capacity");

    							LockType lockStyle = LockType.Open;
    							
    							if(roomLockType == "Locked")
    								lockStyle = LockType.Locked;
    							else if(roomLockType == "Password")
    								lockStyle = LockType.Password;
    							
    							RoomData roomData = new RoomData(roomid, roomName, roomOwner, roomDesc, roomCapacity, roomPass, roomModel, lockStyle);
									Room room = new Room(roomData, getModel(roomData.getModelId()));		
									this.rooms.put(room.getRoomData().getId(), room);
                	
                	if (room != null)
										room.getRoomItemManager().loadRoomsFurnisFromDb();

                	loadedRooms++;
                	roomId++;
                }
            }
        }
    } catch (SQLException e) {
        throw e;
    }

    System.out.println("[LOAD] "+loadedRooms+" sala(s) carregada(s).");
	}
	
	public void onCycle() {
		List<Room> cyclingRooms = new ArrayList<>(rooms.values());
		for (Room room : cyclingRooms) {
			room.onCycle();
		}
	}
	
	public void prepareRoomForUser(User user, int roomId, String password) {
		Room currentRoom = user.getCurrentRoom();
		if (currentRoom != null) {
			currentRoom.getRoomUserManager().removeUserFromRoom(user);
		}
		Room newRoom = null;
		if (roomId == -1 && this.getLoadedRooms().size() > 0) {
			newRoom = this.getLoadedRooms().get(0); //Home room
		} else {
			newRoom = this.getLoadedRoom(roomId);	
		}
		 
		if (newRoom != null) {
			user.setLoadingRoomId(newRoom.getRoomData().getId());
			user.getClient().sendMessage(new RoomModelInfoComposer(newRoom.getRoomData().getModelId(), newRoom.getRoomData().getId()));
		}
	}
	
	public void prepareHeightMapForUser(User user) {
		Room room = this.getLoadedRoom(user.getLoadingRoomId());
		if (room != null) {
			user.getClient().sendMessage(new HeightMapComposer(room.getGameMap().getRoomModel()));
		}
	}

	public void finishRoomLoadingForUser(User user) {
		Room room = this.getLoadedRoom(user.getLoadingRoomId());
		if (room != null) {
			room.getRoomUserManager().addUserToRoom(user);
			user.setLoadingRoomId(0);
			user.getClient().sendMessage(new RoomDataComposer(room.getRoomData()));
		}
	}

	public void handleUserLeaveRoom(User user) {
		Room currentRoom = user.getCurrentRoom();
		if (currentRoom != null) {
			currentRoom.getRoomUserManager().removeUserFromRoom(user);
		}
	}
	
	public List<Room> getLoadedRooms() {
		return new ArrayList<>(rooms.values());
	}

	public void createRoom(User user, String roomName, String modelId) {
		if (roomName.length() > 0) {
			RoomModel model = getModel(modelId);
			if (model != null) {
				RoomData roomData = new RoomData(roomId++, roomName, user.getUsername(), "", 25, "", modelId, LockType.Open);
				Room room = new Room(roomData, model);
				
				this.rooms.put(room.getRoomData().getId(), room);
				prepareRoomForUser(user, room.getRoomData().getId(), "");

				try (Connection connection = BobbaEnvironment.getGame().getDatabase().getDataSource().getConnection(); Statement statement = connection.createStatement()) {
				    
			    String query = "INSERT INTO rooms (name, owner, description, capacity, password, modelId, lockType) VALUES ('"+roomName+"', '"+user.getUsername()+"', '', 25, '', '"+modelId+"', '"+LockType.Open+"')";
			    if (statement.execute(query)) {
			    	try (ResultSet set = statement.getResultSet()) {

			    	}
				  }
			  } catch (SQLException e) {
				  System.out.println("Error creating new room: "+e);
			  }
			}
		}
	}
}
