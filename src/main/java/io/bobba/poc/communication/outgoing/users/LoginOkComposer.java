package io.bobba.poc.communication.outgoing.users;

import io.bobba.poc.communication.protocol.ServerMessage;
import io.bobba.poc.communication.protocol.ServerOpCodes;

public class LoginOkComposer extends ServerMessage {
    public LoginOkComposer(int id, String name, String look, String motto) {
        super(ServerOpCodes.LOGIN_OK);
        
        appendInt(id);
        appendString(name);
        appendString(look);
        appendString(motto);
    }
}
