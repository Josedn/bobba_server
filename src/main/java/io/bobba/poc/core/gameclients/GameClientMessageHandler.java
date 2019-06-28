package io.bobba.poc.core.gameclients;

import io.bobba.poc.communication.incoming.IIncomingEvent;
import io.bobba.poc.communication.incoming.generic.Login;
import io.bobba.poc.communication.incoming.rooms.*;
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
    }
}
