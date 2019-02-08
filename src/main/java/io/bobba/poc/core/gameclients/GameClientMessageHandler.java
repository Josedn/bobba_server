package io.bobba.poc.core.gameclients;

import io.bobba.poc.communication.incoming.IIncomingEvent;
import io.bobba.poc.communication.incoming.generic.Login;
import io.bobba.poc.communication.protocol.ClientMessage;
import io.bobba.poc.communication.protocol.ClientOpCodes;
import io.bobba.poc.misc.logging.LogLevel;
import io.bobba.poc.misc.logging.Logging;

public class GameClientMessageHandler {
    private IIncomingEvent[] requestHandlers;
    private final static int HIGHEST_MESSAGE_ID = 20;

    public GameClientMessageHandler() {
        this.requestHandlers = new IIncomingEvent[HIGHEST_MESSAGE_ID];
        this.registerRequests();
    }
    public boolean handleMessage(GameClient session, ClientMessage message)
    {
        if (message.getId() < 0 || message.getId() > HIGHEST_MESSAGE_ID)
        {
            Logging.getInstance().writeLine("MessageId out of protocol request.", LogLevel.Debug);
            return false;
        }

        if (requestHandlers[message.getId()] == null)
        {
            Logging.getInstance().writeLine("No handler for id: " + message.getId(), LogLevel.Debug);
            return false;
        }
        Logging.getInstance().writeLine("Handled by: " + requestHandlers[message.getId()].getClass().getName(), LogLevel.Debug);
        requestHandlers[message.getId()].handle(session, message);
        return true;
    }
    private void registerRequests() {
        requestHandlers[ClientOpCodes.LOGIN] = new Login();
    }
}
