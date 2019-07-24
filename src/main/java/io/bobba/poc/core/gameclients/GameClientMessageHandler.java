package io.bobba.poc.core.gameclients;

import io.bobba.poc.communication.incoming.IIncomingEvent;
import io.bobba.poc.communication.incoming.catalogue.RequestCatalogueIndex;
import io.bobba.poc.communication.incoming.catalogue.RequestCataloguePage;
import io.bobba.poc.communication.incoming.catalogue.RequestCataloguePurchase;
import io.bobba.poc.communication.incoming.messenger.RequestMessengerAcceptFriend;
import io.bobba.poc.communication.incoming.messenger.RequestMessengerAddFriend;
import io.bobba.poc.communication.incoming.messenger.RequestMessengerDenyFriend;
import io.bobba.poc.communication.incoming.messenger.RequestMessengerFollowFriend;
import io.bobba.poc.communication.incoming.messenger.RequestMessengerLoadFriends;
import io.bobba.poc.communication.incoming.messenger.RequestMessengerRemoveFriend;
import io.bobba.poc.communication.incoming.messenger.RequestMessengerSearchFriend;
import io.bobba.poc.communication.incoming.messenger.RequestMessengerSendMessage;
import io.bobba.poc.communication.incoming.navigator.RequestNavigatorCreateRoom;
import io.bobba.poc.communication.incoming.navigator.RequestNavigatorGoToRoom;
import io.bobba.poc.communication.incoming.navigator.RequestNavigatorLeaveRoom;
import io.bobba.poc.communication.incoming.navigator.RequestNavigatorMakeFavourite;
import io.bobba.poc.communication.incoming.navigator.RequestNavigatorOwnRooms;
import io.bobba.poc.communication.incoming.navigator.RequestNavigatorPopularRooms;
import io.bobba.poc.communication.incoming.navigator.RequestNavigatorRemoveFavourite;
import io.bobba.poc.communication.incoming.navigator.RequestNavigatorSearchRooms;
import io.bobba.poc.communication.incoming.roomdata.RequestHeightMap;
import io.bobba.poc.communication.incoming.roomdata.RequestRoomData;
import io.bobba.poc.communication.incoming.rooms.RequestChangeLooks;
import io.bobba.poc.communication.incoming.rooms.RequestChangeMotto;
import io.bobba.poc.communication.incoming.rooms.RequestChat;
import io.bobba.poc.communication.incoming.rooms.RequestFurniInteract;
import io.bobba.poc.communication.incoming.rooms.RequestFurniMovement;
import io.bobba.poc.communication.incoming.rooms.RequestFurniPickUp;
import io.bobba.poc.communication.incoming.rooms.RequestFurniPlace;
import io.bobba.poc.communication.incoming.rooms.RequestLookAt;
import io.bobba.poc.communication.incoming.rooms.RequestMovement;
import io.bobba.poc.communication.incoming.rooms.RequestWave;
import io.bobba.poc.communication.incoming.users.Login;
import io.bobba.poc.communication.incoming.users.RequestGetInventory;
import io.bobba.poc.communication.protocol.ClientMessage;
import io.bobba.poc.communication.protocol.ClientOpCodes;
import io.bobba.poc.misc.logging.LogLevel;
import io.bobba.poc.misc.logging.Logging;

public class GameClientMessageHandler {
	private IIncomingEvent[] requestHandlers;
	private final static int HIGHEST_MESSAGE_ID = 50;

	public GameClientMessageHandler() {
		this.requestHandlers = new IIncomingEvent[HIGHEST_MESSAGE_ID];
		this.registerRequests();
	}

	public boolean handleMessage(GameClient client, ClientMessage message) {
		if (message.getId() < 0 || message.getId() > HIGHEST_MESSAGE_ID) {
			Logging.getInstance().writeLine("MessageId out of protocol request.", LogLevel.Debug, this.getClass());
			return false;
		}

		if (requestHandlers[message.getId()] == null) {
			Logging.getInstance().writeLine("No handler for id: " + message.getId(), LogLevel.Debug, this.getClass());
			return false;
		}
		Logging.getInstance().writeLine("Handled by: " + requestHandlers[message.getId()].getClass().getSimpleName(),
				LogLevel.Debug, this.getClass());
		try {
			requestHandlers[message.getId()].handle(client, message);
		} catch (Exception e) {
			Logging.getInstance().logError(
					"Error handling " + requestHandlers[message.getId()].getClass().getSimpleName() + ". ", e,
					this.getClass());
		}
		return true;
	}

	private void registerRequests() {
		requestHandlers[ClientOpCodes.REQUEST_MAP] = new RequestHeightMap();
		requestHandlers[ClientOpCodes.LOGIN] = new Login();
		requestHandlers[ClientOpCodes.REQUEST_MOVEMENT] = new RequestMovement();
		requestHandlers[ClientOpCodes.REQUEST_CHAT] = new RequestChat();
		requestHandlers[ClientOpCodes.REQUEST_LOOK_AT] = new RequestLookAt();
		requestHandlers[ClientOpCodes.REQUEST_WAVE] = new RequestWave();
		requestHandlers[ClientOpCodes.REQUEST_ROOM_DATA] = new RequestRoomData();
		requestHandlers[ClientOpCodes.REQUEST_ITEM_INTERACT] = new RequestFurniInteract();
		requestHandlers[ClientOpCodes.REQUEST_ITEM_MOVE] = new RequestFurniMovement();
		requestHandlers[ClientOpCodes.REQUEST_ITEM_PICK_UP] = new RequestFurniPickUp();
		requestHandlers[ClientOpCodes.REQUEST_CHANGE_LOOKS] = new RequestChangeLooks();
		requestHandlers[ClientOpCodes.REQUEST_CHANGE_MOTTO] = new RequestChangeMotto();
		requestHandlers[ClientOpCodes.REQUEST_INVENTORY_ITEMS] = new RequestGetInventory();
		requestHandlers[ClientOpCodes.REQUEST_ITEM_PLACE] = new RequestFurniPlace();
		requestHandlers[ClientOpCodes.REQUEST_CATALOGUE_INDEX] = new RequestCatalogueIndex();
		requestHandlers[ClientOpCodes.REQUEST_CATALOGUE_PAGE] = new RequestCataloguePage();
		requestHandlers[ClientOpCodes.REQUEST_CATALOGUE_PURCHASE] = new RequestCataloguePurchase();
		requestHandlers[ClientOpCodes.REQUEST_NAVIGATOR_LEAVE_ROOM] = new RequestNavigatorLeaveRoom();
		requestHandlers[ClientOpCodes.REQUEST_NAVIGATOR_MAKE_FAVOURITE] = new RequestNavigatorMakeFavourite();
		requestHandlers[ClientOpCodes.REQUEST_NAVIGATOR_REMOVE_FAVOURITE] = new RequestNavigatorRemoveFavourite();
		requestHandlers[ClientOpCodes.REQUEST_NAVIGATOR_OWN_ROOMS] = new RequestNavigatorOwnRooms();
		requestHandlers[ClientOpCodes.REQUEST_NAVIGATOR_POPULAR_ROOMS] = new RequestNavigatorPopularRooms();
		requestHandlers[ClientOpCodes.REQUEST_NAVIGATOR_SEARCH_ROOMS] = new RequestNavigatorSearchRooms();
		requestHandlers[ClientOpCodes.REQUEST_NAVIGATOR_GO_TO_ROOM] = new RequestNavigatorGoToRoom();
		requestHandlers[ClientOpCodes.REQUEST_NAVIGATOR_CREATE_ROOM] = new RequestNavigatorCreateRoom();
		requestHandlers[ClientOpCodes.REQUEST_MESSENGER_ACCEPT_FRIEND] = new RequestMessengerAcceptFriend();
		requestHandlers[ClientOpCodes.REQUEST_MESSENGER_ADD_FRIEND] = new RequestMessengerAddFriend();
		requestHandlers[ClientOpCodes.REQUEST_MESSENGER_DENY_FRIEND] = new RequestMessengerDenyFriend();
		requestHandlers[ClientOpCodes.REQUEST_MESSENGER_FOLLOW_FRIEND] = new RequestMessengerFollowFriend();
		requestHandlers[ClientOpCodes.REQUEST_MESSENGER_LOAD_FRIENDS] = new RequestMessengerLoadFriends();
		requestHandlers[ClientOpCodes.REQUEST_MESSENGER_REMOVE_FRIEND] = new RequestMessengerRemoveFriend();
		requestHandlers[ClientOpCodes.REQUEST_MESSENGER_SEARCH_FRIEND] = new RequestMessengerSearchFriend();
		requestHandlers[ClientOpCodes.REQUEST_MESSENGER_SEND_MESSAGE] = new RequestMessengerSendMessage();
	}
}
