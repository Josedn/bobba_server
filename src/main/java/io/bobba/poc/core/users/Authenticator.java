package io.bobba.poc.core.users;

import io.bobba.poc.communication.outgoing.LoginOkComposer;
import io.bobba.poc.core.gameclients.GameClient;
import io.bobba.poc.misc.logging.LogLevel;
import io.bobba.poc.misc.logging.Logging;

public class Authenticator {
    private int nextId;

    public Authenticator() {
        nextId = 1;
    }

    public void tryLogin(GameClient client, String username, String look) {
        if (client.getUser() == null) {
            client.setUser(new User(nextId++, username, look, client));

            Logging.getInstance().writeLine(client.getUser().getUsername() + " (" + client.getUser().getId() + ") has logged in!", LogLevel.Verbose, this.getClass());

            client.sendMessage(new LoginOkComposer());
        } else {
            Logging.getInstance().writeLine("Client already logged!", LogLevel.Warning, this.getClass());
            client.stop();
        }
    }
}
