package io.bobba.poc.core;

import java.time.Duration;
import java.time.Instant;

import io.bobba.poc.BobbaEnvironment;
import io.bobba.poc.core.catalogue.Catalogue;
import io.bobba.poc.core.gameclients.GameClientManager;
import io.bobba.poc.core.items.BaseItemManager;
import io.bobba.poc.core.navigator.Navigator;
import io.bobba.poc.core.rooms.RoomManager;
import io.bobba.poc.core.users.Authenticator;
import io.bobba.poc.misc.SSLHelper;
import io.bobba.poc.misc.logging.Logging;
import io.bobba.poc.net.ConnectionManager;

public class Game {
	private ConnectionManager connectionManager;
	private GameClientManager gameClientManager;
	private Authenticator authenticator;
	private BaseItemManager itemManager;
	private Catalogue catalogue;
	private Navigator navigator;
	private RoomManager roomManager;

	private final int DELTA_TIME = 500;
	public static int baseItemId = 0;

	/*private void addFurniture() {
		BaseItem shelves_norja = itemManager.addRoomItem(baseItemId++, 13, 1, 1, 1.0, "shelves_norja", 1, false, false,
				false, Arrays.asList(0, 2));
		BaseItem club_sofa = itemManager.addRoomItem(baseItemId++, 267, 2, 1, 1.0, "club_sofa", 1, false, false, true,
				Arrays.asList(0, 2, 4, 6));
		BaseItem lt_patch = itemManager.addRoomItem(baseItemId++, 3188, 2, 2, 0.01, "lt_patch", 3, false, true, false,
				Arrays.asList(0, 2, 4, 6));
		BaseItem lt_stone2 = itemManager.addRoomItem(baseItemId++, 3177, 2, 1, 1.05, "lt_stone2", 3, true, false, false,
				Arrays.asList(0, 2));
		BaseItem lt_gate = itemManager.addRoomItem(baseItemId++, 3172, 2, 1, 0.01, "lt_gate", 2, false, true, false,
				Arrays.asList(0, 2, 4, 6));
		BaseItem scifidoor_4 = itemManager.addRoomItem(baseItemId++, 1575, 1, 1, 0, "scifidoor*4", 2, false, true,
				false, Arrays.asList(2, 4));
		BaseItem hween12_scarecrow = itemManager.addRoomItem(baseItemId++, 4733, 1, 1, 0, "hween12_scarecrow", 2, false,
				false, false, Arrays.asList(0, 2, 4, 6));
		BaseItem rare_icecream_2 = itemManager.addRoomItem(baseItemId++, 1632, 1, 1, 0, "rare_icecream*2", 2, false,
				false, false, Arrays.asList(2, 4));
		BaseItem rare_dragon_5 = itemManager.addRoomItem(baseItemId++, 1621, 1, 1, 0, "rare_dragonlamp*5", 2, false,
				false, false, Arrays.asList(2, 4));
		BaseItem hween12_cart = itemManager.addRoomItem(baseItemId++, 4729, 1, 1, 1.0, "hween12_cart", 2, false, false,
				true, Arrays.asList(0, 2, 4, 6));
		BaseItem small_chair_armas = itemManager.addRoomItem(baseItemId++, 55, 1, 1, 1.0, "small_chair_armas", 1, false,
				false, true, Arrays.asList(0, 2, 4, 6));
		BaseItem hween12_track = itemManager.addRoomItem(baseItemId++, 4731, 1, 1, 0.25, "hween12_track", 3, true, true,
				false, Arrays.asList(0, 2, 4, 6));
		BaseItem hween12_track_crl = itemManager.addRoomItem(baseItemId++, 4736, 1, 1, 0.25, "hween12_track_crl", 3,
				true, true, false, Arrays.asList(0, 2, 4, 6));
		BaseItem hween12_track_crr = itemManager.addRoomItem(baseItemId++, 4739, 1, 1, 0.25, "hween12_track_crr", 3,
				true, true, false, Arrays.asList(0, 2, 4, 6));
		BaseItem LT_skull = itemManager.addRoomItem(baseItemId++, 3189, 1, 1, 0.4, "LT_skull", 1, false, false, false,
				Arrays.asList(0, 2, 4, 6));
		BaseItem hween12_moon = itemManager.addRoomItem(baseItemId++, 4740, 1, 1, 0.01, "hween12_moon", 4, false, true,
				false, Arrays.asList(2, 4));

		BaseItem stories_shakespeare_tree = itemManager.addRoomItem(baseItemId++, 5735, 2, 2, 0,
				"stories_shakespeare_tree", 2, false, false, false, Arrays.asList(0, 2));
		BaseItem anc_artifact3 = itemManager.addRoomItem(baseItemId++, 4655, 3, 1, 0, "anc_artifact3", 1, false, false,
				false, Arrays.asList(2, 4));
		BaseItem anc_waterfall = itemManager.addRoomItem(baseItemId++, 4651, 1, 1, 0, "anc_waterfall", 1, false, true,
				false, Arrays.asList(2, 4));
		BaseItem anc_talltree = itemManager.addRoomItem(baseItemId++, 4650, 2, 2, 0, "anc_talltree", 1, false, false,
				false, Arrays.asList(2, 4));
		BaseItem anc_comfy_tree = itemManager.addRoomItem(baseItemId++, 4653, 1, 1, 0, "anc_comfy_tree", 1, false,
				false, false, Arrays.asList(0, 2, 4, 6));

		BaseItem lt_jngl_wall = itemManager.addWallItem(baseItemId++, 4121, "lt_jngl_wall", 3);
		BaseItem anc_sunset_wall = itemManager.addWallItem(baseItemId++, 4462, "anc_sunset_wall", 2);

		BaseItem doorD = itemManager.addRoomItem(baseItemId++, 1505, 1, 1, 0, "doorD", 3, false, true, false,
				Arrays.asList(2, 4));
		BaseItem duck = itemManager.addRoomItem(baseItemId++, 179, 1, 1, 1.0, "duck", 1, false, false, false,
				Arrays.asList(0, 2, 4, 6));

		BaseItem scifirocket3 = itemManager.addRoomItem(baseItemId++, 1565, 1, 1, 0, "scifirocket*3", 2, false, false,
				false, Arrays.asList(0));

		BaseItem flag_columbia = itemManager.addWallItem(baseItemId++, 4258, "flag_columbia", 1);

		BaseItem hc_wall_lamp = itemManager.addWallItem(baseItemId++, 4003, "hc_wall_lamp", 2);

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
		
		
		this.catalogue.pages.put(1, new CataloguePage(1, -1, "Catálogo", true, true, 0, "c8684e", 1, "frontpage",
				"catalog_frontpage_headline_shop_ES", "fatherhabbo_300x187_girl", "Introducing Bobba Catalogue",
				"omg it works", "Why is it in spanish?",
				"I just took the catalogue from my old hispanic hotel, sorry bout that. :')",
				new ArrayList<>()));
		
		this.catalogue.pages.put(80, new CataloguePage(80, -1, "Wired", false, true, 0, "aaaaaa", 80, "default", "", "", "", "", "", "", new ArrayList<>()));
		
		List<CatalogueItem> dummy = new ArrayList<>();
		dummy.add(new CatalogueItem(45, club_sofa, "club_sofa", 3, 1));
		dummy.add(new CatalogueItem(46, shelves_norja, "shelves_norja", 3, 1));
		
		this.catalogue.pages.put(81, new CataloguePage(81, 80, "Causantes", true, true, 0, "aaaaaa", 81, "default",
				"catalog_wired_header2_es", "ctlg_pic_wired_triggers",
				"Los Causantes permiten definir qué se necesita que pase para que tenga lugar un Efecto. Para programar un Causante, colócalo en una Sala, haz doble clic en él y ponlo en marcha. Necesitarás apilar un Efecto sobre un Causante.",
				"¡Haz click en cada objeto para ver cómo funciona!", "", "", dummy));
		
		List<CatalogueItem> dummy2 = new ArrayList<>();
		dummy2.add(new CatalogueItem(47, duck, "duck", 5, 1));
		
		this.catalogue.pages.put(82, new CataloguePage(82, 80, "Efectos", true, true, 0, "aaaaaa", 82, "default",
				"catalog_wired_header3_es", "ctlg_pic_wired_effects",
				"Los Efectos permiten definir qué se necesita que pase para que tenga lugar un Efecto. Para programar un Causante, colócalo en una Sala, haz doble clic en él y ponlo en marcha. Necesitarás apilar un Efecto sobre un Causante.",
				"¡Haz click en cada objeto para ver cómo funciona!", "", "", dummy2));
	}*/

	public Game(int port) throws Exception {
		this.gameClientManager = new GameClientManager();
		this.authenticator = new Authenticator();
		this.itemManager = new BaseItemManager();
		this.catalogue = new Catalogue();
		this.roomManager = new RoomManager();

		if (BobbaEnvironment.getConfigManager().getSslEnabled().toLowerCase().equals("true")) {
			this.connectionManager = new ConnectionManager(port, this.gameClientManager, SSLHelper.loadSslContext());
		} else {
			this.connectionManager = new ConnectionManager(port, this.gameClientManager);
		}

		Thread roomThread = new Thread(new Runnable() {
			@Override
			public void run() {
				gameThread();
			}
		});
		roomThread.start();
	}

	private void gameThread() {
		Instant starts, ends;
		while (true) {
			try {
				starts = Instant.now();
				onCycle();
				ends = Instant.now();

				long sleepTime = DELTA_TIME - Duration.between(starts, ends).toMillis();
				if (sleepTime < 0) {
					sleepTime = 0;
				}

				Thread.sleep(sleepTime);

			} catch (Exception e) {
				Logging.getInstance().logError("Game thread error", e, this.getClass());
			}
		}
	}

	public void onCycle() {
		this.roomManager.onCycle();
	}

	public ConnectionManager getConnectionManager() {
		return connectionManager;
	}

	public GameClientManager getGameClientManager() {
		return gameClientManager;
	}

	public Authenticator getAuthenticator() {
		return authenticator;
	}

	public BaseItemManager getItemManager() {
		return itemManager;
	}
	
	public Catalogue getCatalogue() {
		return catalogue;
	}

	public Navigator getNavigator() {
		return navigator;
	}
	
	public RoomManager getRoomManager() {
		return roomManager;
	}
}
