package io.bobba.poc.communication.outgoing.users;

import io.bobba.poc.communication.protocol.ServerMessage;
import io.bobba.poc.communication.protocol.ServerOpCodes;

public class UpdateCreditsBalanceComposer extends ServerMessage {
	public UpdateCreditsBalanceComposer(int amount) {
		super(ServerOpCodes.CREDITS_BALANCE);
		appendInt(amount);
	}
}
