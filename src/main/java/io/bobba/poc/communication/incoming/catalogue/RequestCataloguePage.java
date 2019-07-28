package io.bobba.poc.communication.incoming.catalogue;

import io.bobba.poc.BobbaEnvironment;
import io.bobba.poc.communication.incoming.IIncomingEvent;
import io.bobba.poc.communication.protocol.ClientMessage;
import io.bobba.poc.core.gameclients.GameClient;
import io.bobba.poc.core.users.User;

public class RequestCataloguePage implements IIncomingEvent {

    @Override
    public void handle(GameClient client, ClientMessage request) {
    	User user = client.getUser();
        if (user != null){
        	BobbaEnvironment.getGame().getCatalogue().serializePage(user, request.popInt());
        }
    }
}
