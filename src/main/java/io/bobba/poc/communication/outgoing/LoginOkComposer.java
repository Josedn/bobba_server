package io.bobba.poc.communication.outgoing;

import io.bobba.poc.communication.protocol.ServerMessage;
import io.bobba.poc.communication.protocol.ServerOpCodes;

public class LoginOkComposer extends ServerMessage {
    public LoginOkComposer() {
        super(ServerOpCodes.LOGIN_OK);
    }
}
