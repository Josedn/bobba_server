package io.bobba.poc.communication.outgoing.catalogue;

import io.bobba.poc.communication.protocol.ServerMessage;
import io.bobba.poc.communication.protocol.ServerOpCodes;

public class CataloguePurchaseErrorComposer extends ServerMessage {
	public CataloguePurchaseErrorComposer() {
		super(ServerOpCodes.CATALOGUE_PURCHASE_ERROR);
		appendBoolean(true);
	}
}