package io.bobba.poc.core.rooms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.bobba.poc.BobbaEnvironment;
import io.bobba.poc.communication.outgoing.roomdata.HeightMapComposer;
import io.bobba.poc.communication.outgoing.roomdata.RoomDataComposer;
import io.bobba.poc.communication.outgoing.roomdata.RoomModelInfoComposer;
import io.bobba.poc.core.catalogue.Catalogue;
import io.bobba.poc.core.items.BaseItem;
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
	
	public void initialize() {
		this.loadModels();
		this.createDummyRoom();
	}
	
	private void loadModels() {
		String model_a = "xxxxxxxxxxxx\r\n" + 
				"xxxx00000000\r\n" + 
				"xxxx00000000\r\n" + 
				"xxxx00000000\r\n" + 
				"xxxx00000000\r\n" + 
				"xxxx00000000\r\n" + 
				"xxxx00000000\r\n" + 
				"xxxx00000000\r\n" + 
				"xxxx00000000\r\n" + 
				"xxxx00000000\r\n" + 
				"xxxx00000000\r\n" + 
				"xxxx00000000\r\n" + 
				"xxxx00000000\r\n" + 
				"xxxx00000000\r\n" + 
				"xxxxxxxxxxxx\r\n" + 
				"xxxxxxxxxxxx";
		
		String model_b = "xxxxxxxxxxxx\r\n" + 
				"xxxxx0000000\r\n" + 
				"xxxxx0000000\r\n" + 
				"xxxxx0000000\r\n" + 
				"xxxxx0000000\r\n" + 
				"x00000000000\r\n" + 
				"x00000000000\r\n" + 
				"x00000000000\r\n" + 
				"x00000000000\r\n" + 
				"x00000000000\r\n" + 
				"x00000000000\r\n" + 
				"xxxxxxxxxxxx\r\n" + 
				"xxxxxxxxxxxx\r\n" + 
				"xxxxxxxxxxxx\r\n" + 
				"xxxxxxxxxxxx\r\n" + 
				"xxxxxxxxxxxx";
		
		String model_c = "xxxxxxxxxxxx\r\n" + 
				"xxxxxxxxxxxx\r\n" + 
				"xxxxxxxxxxxx\r\n" + 
				"xxxxxxxxxxxx\r\n" + 
				"xxxxxxxxxxxx\r\n" + 
				"xxxxx000000x\r\n" + 
				"xxxxx000000x\r\n" + 
				"xxxxx000000x\r\n" + 
				"xxxxx000000x\r\n" + 
				"xxxxx000000x\r\n" + 
				"xxxxx000000x\r\n" + 
				"xxxxxxxxxxxx\r\n" + 
				"xxxxxxxxxxxx\r\n" + 
				"xxxxxxxxxxxx\r\n" + 
				"xxxxxxxxxxxx\r\n" + 
				"xxxxxxxxxxxx";
		
		
		String model_d = "xxxxxxxxxxxx\r\n" + 
				"xxxxx000000x\r\n" + 
				"xxxxx000000x\r\n" + 
				"xxxxx000000x\r\n" + 
				"xxxxx000000x\r\n" + 
				"xxxxx000000x\r\n" + 
				"xxxxx000000x\r\n" + 
				"xxxxx000000x\r\n" + 
				"xxxxx000000x\r\n" + 
				"xxxxx000000x\r\n" + 
				"xxxxx000000x\r\n" + 
				"xxxxx000000x\r\n" + 
				"xxxxx000000x\r\n" + 
				"xxxxx000000x\r\n" + 
				"xxxxx000000x\r\n" + 
				"xxxxxxxxxxxx";
		
		String model_e = "xxxxxxxxxxxx\r\n" + 
				"xxxxxxxxxxxx\r\n" + 
				"xxxxxxxxxxxx\r\n" + 
				"xx0000000000\r\n" + 
				"xx0000000000\r\n" + 
				"xx0000000000\r\n" + 
				"xx0000000000\r\n" + 
				"xx0000000000\r\n" + 
				"xx0000000000\r\n" + 
				"xx0000000000\r\n" + 
				"xx0000000000\r\n" + 
				"xxxxxxxxxxxx\r\n" + 
				"xxxxxxxxxxxx\r\n" + 
				"xxxxxxxxxxxx\r\n" + 
				"xxxxxxxxxxxx\r\n" + 
				"xxxxxxxxxxxx";
		
		String model_f = "xxxxxxxxxxxx\r\n" + 
				"xxxxxxx0000x\r\n" + 
				"xxxxxxx0000x\r\n" + 
				"xxx00000000x\r\n" + 
				"xxx00000000x\r\n" + 
				"xxx00000000x\r\n" + 
				"xxx00000000x\r\n" + 
				"x0000000000x\r\n" + 
				"x0000000000x\r\n" + 
				"x0000000000x\r\n" + 
				"x0000000000x\r\n" + 
				"xxxxxxxxxxxx\r\n" + 
				"xxxxxxxxxxxx\r\n" + 
				"xxxxxxxxxxxx\r\n" + 
				"xxxxxxxxxxxx\r\n" + 
				"xxxxxxxxxxxx";
		String model_g = "xxxxxxxxxxxx\r\n" + 
				"xxxxxxxxxxxx\r\n" + 
				"xxxxxxx00000\r\n" + 
				"xxxxxxx00000\r\n" + 
				"xxxxxxx00000\r\n" + 
				"xx1111000000\r\n" + 
				"xx1111000000\r\n" + 
				"xx1111000000\r\n" + 
				"xx1111000000\r\n" + 
				"xx1111000000\r\n" + 
				"xxxxxxx00000\r\n" + 
				"xxxxxxx00000\r\n" + 
				"xxxxxxx00000\r\n" + 
				"xxxxxxxxxxxx\r\n" + 
				"xxxxxxxxxxxx\r\n" + 
				"xxxxxxxxxxxx\r\n" + 
				"xxxxxxxxxxxx";
		String model_h = "xxxxxxxxxxxx\r\n" + 
				"xxxxxxxxxxxx\r\n" + 
				"xxxxx111111x\r\n" + 
				"xxxxx111111x\r\n" + 
				"xxxxx111111x\r\n" + 
				"xxxxx111111x\r\n" + 
				"xxxxx111111x\r\n" + 
				"xxxxx000000x\r\n" + 
				"xxxxx000000x\r\n" + 
				"xxx00000000x\r\n" + 
				"xxx00000000x\r\n" + 
				"xxx00000000x\r\n" + 
				"xxx00000000x\r\n" + 
				"xxxxxxxxxxxx\r\n" + 
				"xxxxxxxxxxxx\r\n" + 
				"xxxxxxxxxxxx";
		
		String model_j = "xxxxxxxxxxxxxxxxxxxxx\r\n" + 
				"xxxxxxxxxxx0000000000\r\n" + 
				"xxxxxxxxxxx0000000000\r\n" + 
				"xxxxxxxxxxx0000000000\r\n" + 
				"xxxxxxxxxxx0000000000\r\n" + 
				"xxxxxxxxxxx0000000000\r\n" + 
				"xxxxxxxxxxx0000000000\r\n" + 
				"x00000000000000000000\r\n" + 
				"x00000000000000000000\r\n" + 
				"x00000000000000000000\r\n" + 
				"x00000000000000000000\r\n" + 
				"x00000000000000000000\r\n" + 
				"x00000000000000000000\r\n" + 
				"x00000000000000000000\r\n" + 
				"x00000000000000000000\r\n" + 
				"x00000000000000000000\r\n" + 
				"x00000000000000000000\r\n" + 
				"x0000000000xxxxxxxxxx\r\n" + 
				"x0000000000xxxxxxxxxx\r\n" + 
				"x0000000000xxxxxxxxxx\r\n" + 
				"x0000000000xxxxxxxxxx\r\n" + 
				"x0000000000xxxxxxxxxx\r\n" + 
				"x0000000000xxxxxxxxxx\r\n" + 
				"xxxxxxxxxxxxxxxxxxxxx";
		
		String model_0 = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\r\n" + 
				"x00000000xx00000000xx00000000xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\r\n" + 
				"x00000000xx00000000xx00000000xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\r\n" + 
				"x00000000xx00000000xx00000000xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx0000\r\n" + 
				"000000000xx00000000xx00000000xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx0000\r\n" + 
				"x00000000xx00000000xx00000000xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx0000\r\n" + 
				"x00000000xx00000000xx00000000xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx0000\r\n" + 
				"x00000000xx00000000xx00000000xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\r\n" + 
				"x00000000xx00000000xx00000000xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\r\n" + 
				"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
		
		String model_m = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxx\r\n" + 
				"xxxxxxxxxxx00000000xxxxxxxxxx\r\n" + 
				"xxxxxxxxxxx00000000xxxxxxxxxx\r\n" + 
				"xxxxxxxxxxx00000000xxxxxxxxxx\r\n" + 
				"xxxxxxxxxxx00000000xxxxxxxxxx\r\n" + 
				"xxxxxxxxxxx00000000xxxxxxxxxx\r\n" + 
				"xxxxxxxxxxx00000000xxxxxxxxxx\r\n" + 
				"xxxxxxxxxxx00000000xxxxxxxxxx\r\n" + 
				"xxxxxxxxxxx00000000xxxxxxxxxx\r\n" + 
				"xxxxxxxxxxx00000000xxxxxxxxxx\r\n" + 
				"xxxxxxxxxxx00000000xxxxxxxxxx\r\n" + 
				"x0000000000000000000000000000\r\n" + 
				"x0000000000000000000000000000\r\n" + 
				"x0000000000000000000000000000\r\n" + 
				"x0000000000000000000000000000\r\n" + 
				"x0000000000000000000000000000\r\n" + 
				"x0000000000000000000000000000\r\n" + 
				"x0000000000000000000000000000\r\n" + 
				"x0000000000000000000000000000\r\n" + 
				"xxxxxxxxxxx00000000xxxxxxxxxx\r\n" + 
				"xxxxxxxxxxx00000000xxxxxxxxxx\r\n" + 
				"xxxxxxxxxxx00000000xxxxxxxxxx\r\n" + 
				"xxxxxxxxxxx00000000xxxxxxxxxx\r\n" + 
				"xxxxxxxxxxx00000000xxxxxxxxxx\r\n" + 
				"xxxxxxxxxxx00000000xxxxxxxxxx\r\n" + 
				"xxxxxxxxxxx00000000xxxxxxxxxx\r\n" + 
				"xxxxxxxxxxx00000000xxxxxxxxxx\r\n" + 
				"xxxxxxxxxxx00000000xxxxxxxxxx\r\n" + 
				"xxxxxxxxxxx00000000xxxxxxxxxx\r\n" + 
				"xxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
		
		String model_n = "xxxxxxxxxxxxxxxxxxxxx\r\n" + 
				"x00000000000000000000\r\n" + 
				"x00000000000000000000\r\n" + 
				"x00000000000000000000\r\n" + 
				"x00000000000000000000\r\n" + 
				"x00000000000000000000\r\n" + 
				"x00000000000000000000\r\n" + 
				"x000000xxxxxxxx000000\r\n" + 
				"x000000x000000x000000\r\n" + 
				"x000000x000000x000000\r\n" + 
				"x000000x000000x000000\r\n" + 
				"x000000x000000x000000\r\n" + 
				"x000000x000000x000000\r\n" + 
				"x000000x000000x000000\r\n" + 
				"x000000xxxxxxxx000000\r\n" + 
				"x00000000000000000000\r\n" + 
				"x00000000000000000000\r\n" + 
				"x00000000000000000000\r\n" + 
				"x00000000000000000000\r\n" + 
				"x00000000000000000000\r\n" + 
				"x00000000000000000000\r\n" + 
				"xxxxxxxxxxxxxxxxxxxxx";
		
		this.models.put("model_a", new RoomModel(3, 5, 0, 2, model_a));
		this.models.put("model_b", new RoomModel(0, 5, 0, 2, model_b));
		this.models.put("model_c", new RoomModel(4, 7, 0, 2, model_c));
		this.models.put("model_d", new RoomModel(4, 7, 0, 2, model_d));
		this.models.put("model_e", new RoomModel(1, 5, 0, 2, model_e));
		this.models.put("model_f", new RoomModel(2, 5, 0, 2, model_f));
		this.models.put("model_g", new RoomModel(1, 7, 1, 2, model_g));
		this.models.put("model_h", new RoomModel(4, 4, 1, 2, model_h));
		this.models.put("model_j", new RoomModel(0, 10, 0, 2, model_j));
		this.models.put("model_0", new RoomModel(0, 4, 0, 2, model_0));
		this.models.put("model_m", new RoomModel(0, 16, 0, 2, model_m));
		this.models.put("model_n", new RoomModel(0, 7, 0, 2, model_n));
	}
	
	private void createDummyRoom() {
		RoomData roomData = new RoomData(roomId++, "The deep forest", "Relevance", "a very cool room", 25, "", "model_h", LockType.Open);
		Room room = new Room(roomData, getModel(roomData.getModelId()));		
		//addFurniture(room);
		this.rooms.put(room.getRoomData().getId(), room);
		
		roomData = new RoomData(roomId++, "dot dot dot", "Gravity", "a cool room", 25, "", "model_g", LockType.Open);
		room = new Room(roomData, getModel(roomData.getModelId()));
		this.rooms.put(room.getRoomData().getId(), room);
	}
	
	public void onCycle() {
		List<Room> cyclingRooms = new ArrayList<>(rooms.values());
		for (Room room : cyclingRooms) {
			room.onCycle();
		}
	}
	
	private void addFurniture(Room room) {	
		BaseItem club_sofa = BobbaEnvironment.getInstance().getGame().getItemManager().findItem("club_sofa");
		BaseItem lt_patch = BobbaEnvironment.getInstance().getGame().getItemManager().findItem("lt_patch");
		BaseItem lt_stone2 = BobbaEnvironment.getInstance().getGame().getItemManager().findItem("lt_stone2");
		BaseItem lt_gate = BobbaEnvironment.getInstance().getGame().getItemManager().findItem("lt_gate");
		BaseItem scifidoor_4 = BobbaEnvironment.getInstance().getGame().getItemManager().findItem("scifidoor*4");
		BaseItem hween12_scarecrow = BobbaEnvironment.getInstance().getGame().getItemManager().findItem("hween12_scarecrow");
		BaseItem rare_icecream_2 = BobbaEnvironment.getInstance().getGame().getItemManager().findItem("rare_icecream*2");
		BaseItem rare_dragon_5 = BobbaEnvironment.getInstance().getGame().getItemManager().findItem("rare_dragonlamp*5");
		BaseItem hween12_cart = BobbaEnvironment.getInstance().getGame().getItemManager().findItem("hween12_cart");
		BaseItem hween12_track = BobbaEnvironment.getInstance().getGame().getItemManager().findItem("hween12_track");
		BaseItem hween12_track_crl = BobbaEnvironment.getInstance().getGame().getItemManager().findItem("hween12_track_crl");
		BaseItem LT_skull = BobbaEnvironment.getInstance().getGame().getItemManager().findItem("LT_skull");
		BaseItem hween12_moon = BobbaEnvironment.getInstance().getGame().getItemManager().findItem("hween12_moon");

		BaseItem stories_shakespeare_tree = BobbaEnvironment.getInstance().getGame().getItemManager().findItem("stories_shakespeare_tree");
		BaseItem anc_artifact3 = BobbaEnvironment.getInstance().getGame().getItemManager().findItem("anc_artifact3");
		BaseItem anc_waterfall = BobbaEnvironment.getInstance().getGame().getItemManager().findItem("anc_waterfall");

		BaseItem lt_jngl_wall = BobbaEnvironment.getInstance().getGame().getItemManager().findItem("lt_jngl_wall");
		BaseItem anc_sunset_wall = BobbaEnvironment.getInstance().getGame().getItemManager().findItem("anc_sunset_wall");

		BaseItem doorD = BobbaEnvironment.getInstance().getGame().getItemManager().findItem("doorD");
		BaseItem duck = BobbaEnvironment.getInstance().getGame().getItemManager().findItem("duck");

		BaseItem scifirocket3 = BobbaEnvironment.getInstance().getGame().getItemManager().findItem("scifirocket*3");

		BaseItem flag_columbia = BobbaEnvironment.getInstance().getGame().getItemManager().findItem("flag_columbia");

		BaseItem hc_wall_lamp = BobbaEnvironment.getInstance().getGame().getItemManager().findItem("hc_wall_lamp");


		double z = 0.0;

		int currentX = 0;

		for (int i = 0; i < 4; i++) {
			int currentY = 0;
			for (int j = 0; j < 6; j++) {
				room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 1 + currentX, currentY, z,
						lt_patch.getDirections().get(0), 1, lt_patch);
				currentY += 2;
			}
			currentX += 2;
		}
		for (int i = 0; i < 4; i++) {
			room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 1 + (i * 2), 11, z, lt_patch.getDirections().get(0),
					1, lt_patch);
		}

		z = lt_patch.getZ();

		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 2, 12, z, scifidoor_4.getDirections().get(0), 1,
				scifidoor_4);

		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 2, 10, z, lt_stone2.getDirections().get(1), 0,
				lt_stone2);

		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 2, 8, z, lt_stone2.getDirections().get(1), 0, lt_stone2);
		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 2, 6, z, lt_stone2.getDirections().get(1), 0, lt_stone2);
		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 2, 4, z, lt_stone2.getDirections().get(1), 0, lt_stone2);

		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 3, 4, z, hween12_scarecrow.getDirections().get(1), 1,
				hween12_scarecrow);
		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 1, 3, z + 1.05, rare_icecream_2.getDirections().get(1),
				0, rare_icecream_2);
		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 8, 12, z, rare_dragon_5.getDirections().get(1), 1,
				rare_dragon_5);

		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 1, 3, z, lt_stone2.getDirections().get(0), 0, lt_stone2);
		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 3, 3, z, lt_stone2.getDirections().get(0), 0, lt_stone2);
		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 5, 3, z, lt_stone2.getDirections().get(0), 0, lt_stone2);
		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 7, 3, z, lt_gate.getDirections().get(0), 0, lt_gate);

		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 3, 10, z, hween12_cart.getDirections().get(1), 0,
				hween12_cart);
		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 3, 8, z, hween12_cart.getDirections().get(1), 0,
				hween12_cart);
		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 3, 6, z, hween12_cart.getDirections().get(1), 0,
				hween12_cart);

		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 5, 4, z, hween12_cart.getDirections().get(2), 0,
				hween12_cart);
		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 7, 4, z, hween12_cart.getDirections().get(2), 0,
				hween12_cart);

		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 8, 7, z, hween12_cart.getDirections().get(3), 0,
				hween12_cart);
		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 8, 9, z, hween12_cart.getDirections().get(3), 0,
				hween12_cart);

		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 6, 12, z, hween12_cart.getDirections().get(0), 0,
				hween12_cart);

		//
		// room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 3, 0, z,
		// club_sofa.getDirections().get(2), 0, club_sofa);

		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 7, 10, z, hween12_track.getDirections().get(0), 0,
				hween12_track);
		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 7, 9, z, hween12_track.getDirections().get(0), 0,
				hween12_track);
		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 7, 8, z, hween12_track.getDirections().get(0), 0,
				hween12_track);
		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 7, 7, z, hween12_track.getDirections().get(0), 0,
				hween12_track);
		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 7, 6, z, hween12_track.getDirections().get(0), 0,
				hween12_track);

		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 4, 10, z, hween12_track.getDirections().get(2), 0,
				hween12_track);
		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 4, 9, z, hween12_track.getDirections().get(2), 0,
				hween12_track);
		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 4, 8, z, hween12_track.getDirections().get(2), 0,
				hween12_track);
		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 4, 7, z, hween12_track.getDirections().get(2), 0,
				hween12_track);
		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 4, 6, z, hween12_track.getDirections().get(2), 0,
				hween12_track);

		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 5, 11, z, hween12_track.getDirections().get(1), 0,
				hween12_track);
		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 6, 11, z, hween12_track.getDirections().get(1), 0,
				hween12_track);

		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 5, 5, z, hween12_track.getDirections().get(3), 0,
				hween12_track);
		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 6, 5, z, hween12_track.getDirections().get(3), 0,
				hween12_track);

		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 7, 5, z, hween12_track_crl.getDirections().get(3), 0,
				hween12_track_crl);
		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 4, 5, z, hween12_track_crl.getDirections().get(2), 0,
				hween12_track_crl);
		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 4, 11, z, hween12_track_crl.getDirections().get(1), 0,
				hween12_track_crl);
		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 7, 11, z, hween12_track_crl.getDirections().get(0), 0,
				hween12_track_crl);

		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 6, 8, z, LT_skull.getDirections().get(2), 0, LT_skull);

		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 5, 3, z, hween12_moon.getDirections().get(0), 0,
				hween12_moon);

		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 3, 0, z, stories_shakespeare_tree.getDirections().get(0),
				0, stories_shakespeare_tree);
		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 7, 0, z, club_sofa.getDirections().get(2), 0, club_sofa);

		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 2, 0, z, anc_artifact3.getDirections().get(0), 0,
				anc_artifact3);

		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 1, 0, z, anc_waterfall.getDirections().get(0), 0,
				anc_waterfall);
		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 1, 1, z, anc_waterfall.getDirections().get(0), 0,
				anc_waterfall);
		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 1, 2, z, anc_waterfall.getDirections().get(0), 0,
				anc_waterfall);

		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 1, 0, z, anc_waterfall.getDirections().get(1), 0,
				anc_waterfall);
		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 2, 0, z, anc_waterfall.getDirections().get(1), 0,
				anc_waterfall);
		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 3, 0, z, anc_waterfall.getDirections().get(1), 0,
				anc_waterfall);

		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 6, 0, z, doorD.getDirections().get(1), 0, doorD);

		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 5, 3, z + lt_stone2.getZ(), duck.getDirections().get(2),
				0, duck);
		room.getRoomItemManager().addFloorItemToRoom(Catalogue.generateItemId(), 6, 10, z, scifirocket3.getDirections().get(0), 0,
				scifirocket3);

		// Wall
		room.getRoomItemManager().addWallItemToRoom(Catalogue.generateItemId(), -220, 115, 2, 1, anc_sunset_wall);
		room.getRoomItemManager().addWallItemToRoom(Catalogue.generateItemId(), -130, 75, 2, 1, anc_sunset_wall);
		room.getRoomItemManager().addWallItemToRoom(Catalogue.generateItemId(), -130 + 90, 75 - 40, 2, 1, anc_sunset_wall);
		room.getRoomItemManager().addWallItemToRoom(Catalogue.generateItemId(), 20, 75 - 40 - 20, 2, 1, anc_sunset_wall);
		room.getRoomItemManager().addWallItemToRoom(Catalogue.generateItemId(), 110, 5, 4, 1, anc_sunset_wall);
		room.getRoomItemManager().addWallItemToRoom(Catalogue.generateItemId(), 195, 40, 4, 1, anc_sunset_wall);
		room.getRoomItemManager().addWallItemToRoom(Catalogue.generateItemId(), 280, 75, 4, 1, anc_sunset_wall);
		room.getRoomItemManager().addWallItemToRoom(Catalogue.generateItemId(), -310, 155, 2, 1, anc_sunset_wall);

		room.getRoomItemManager().addWallItemToRoom(Catalogue.generateItemId(), -130, 75 + 5, 2, 0, lt_jngl_wall);
		room.getRoomItemManager().addWallItemToRoom(Catalogue.generateItemId(), -130 + 90, 75 - 40 + 5, 2, 0, lt_jngl_wall);
		room.getRoomItemManager().addWallItemToRoom(Catalogue.generateItemId(), 20, 75 - 40 - 20 + 5, 2, 0, lt_jngl_wall);
		room.getRoomItemManager().addWallItemToRoom(Catalogue.generateItemId(), 110, 5 + 5, 4, 0, lt_jngl_wall);
		room.getRoomItemManager().addWallItemToRoom(Catalogue.generateItemId(), 195, 40 + 5, 4, 0, lt_jngl_wall);
		room.getRoomItemManager().addWallItemToRoom(Catalogue.generateItemId(), 280, 75 + 5, 4, 0, lt_jngl_wall);
		room.getRoomItemManager().addWallItemToRoom(Catalogue.generateItemId(), -310, 155 + 5, 2, 0, lt_jngl_wall);

		room.getRoomItemManager().addWallItemToRoom(Catalogue.generateItemId(), -220, 115 + 5, 2, 0, lt_jngl_wall);

		room.getRoomItemManager().addWallItemToRoom(Catalogue.generateItemId(), 280, 75 + 5, 4, 0, flag_columbia);

		room.getRoomItemManager().addWallItemToRoom(Catalogue.generateItemId(), 195, 40 + 5, 4, 0, hc_wall_lamp);
	}
	
	public void prepareRoomForUser(User user, int roomId, String password) {
		Room currentRoom = user.getCurrentRoom();
		if (currentRoom != null) {
			currentRoom.getRoomUserManager().removeUserFromRoom(user);
		}
		Room newRoom = this.getLoadedRoom(roomId);
		if (newRoom != null) {
			user.setLoadingRoomId(roomId);
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
			}
		}
	}
}
