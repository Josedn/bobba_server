package io.bobba.poc.core.gameclients;

import io.bobba.poc.communication.incoming.IIncomingEvent;
import io.bobba.poc.communication.incoming.catalogue.RequestCatalogueIndex;
import io.bobba.poc.communication.incoming.catalogue.RequestCataloguePage;
import io.bobba.poc.communication.incoming.catalogue.RequestCataloguePurchase;
import io.bobba.poc.communication.incoming.rooms.RequestChangeLooks;
import io.bobba.poc.communication.incoming.rooms.RequestChangeMotto;
import io.bobba.poc.communication.incoming.rooms.RequestChat;
import io.bobba.poc.communication.incoming.rooms.RequestFurniInteract;
import io.bobba.poc.communication.incoming.rooms.RequestFurniMovement;
import io.bobba.poc.communication.incoming.rooms.RequestFurniPickUp;
import io.bobba.poc.communication.incoming.rooms.RequestFurniPlace;
import io.bobba.poc.communication.incoming.rooms.RequestLookAt;
import io.bobba.poc.communication.incoming.rooms.RequestMap;
import io.bobba.poc.communication.incoming.rooms.RequestMovement;
import io.bobba.poc.communication.incoming.rooms.RequestRoomData;
import io.bobba.poc.communication.incoming.rooms.RequestWave;
import io.bobba.poc.communication.incoming.users.Login;
import io.bobba.poc.communication.incoming.users.RequestGetInventory;
import io.bobba.poc.communication.protocol.ClientMessage;
import io.bobba.poc.communication.protocol.ClientOpCodes;
import io.bobba.poc.misc.logging.LogLevel;
import io.bobba.poc.misc.logging.Logging;

public class GameClientMessageHandler {
    private IIncomingEvent[] requestHandlers;
    private final static int HIGHEST_MESSAGE_ID = 30;

    public GameClientMessageHandler() {
        this.requestHandlers = new IIncomingEvent[HIGHEST_MESSAGE_ID];
        this.registerRequests();
    }
    public boolean handleMessage(GameClient client, ClientMessage message)
    {
        if (message.getId() < 0 || message.getId() > HIGHEST_MESSAGE_ID)
        {
            Logging.getInstance().writeLine("MessageId out of protocol request.", LogLevel.Debug, this.getClass());
            return false;
        }

        if (requestHandlers[message.getId()] == null)
        {
            Logging.getInstance().writeLine("No handler for id: " + message.getId(), LogLevel.Debug, this.getClass());
            return false;
        }
        Logging.getInstance().writeLine("Handled by: " + requestHandlers[message.getId()].getClass().getName(), LogLevel.Debug, this.getClass());
        try {
        	requestHandlers[message.getId()].handle(client, message);	
        } catch (Exception e) {
        	Logging.getInstance().writeLine("Error handling " +requestHandlers[message.getId()].getClass().getSimpleName() + ". " + e.toString(), LogLevel.Warning, this.getClass());
        }
        return true;
    }
    private void registerRequests() {
        requestHandlers[ClientOpCodes.REQUEST_MAP] = new RequestMap();
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
    }
}
