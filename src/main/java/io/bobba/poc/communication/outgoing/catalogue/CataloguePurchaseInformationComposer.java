package io.bobba.poc.communication.outgoing.catalogue;

import io.bobba.poc.communication.protocol.ServerMessage;
import io.bobba.poc.communication.protocol.ServerOpCodes;
import io.bobba.poc.core.catalogue.CatalogueItem;
import io.bobba.poc.core.items.ItemType;

public class CataloguePurchaseInformationComposer extends ServerMessage {

	public CataloguePurchaseInformationComposer(CatalogueItem item) {
		super(ServerOpCodes.CATALOGUE_PURCHASE_INFO);
		appendInt(item.getId());
		appendString(item.getBaseItem().getItemName());
		appendInt(item.getCost());
		appendString(item.getBaseItem().getType() == ItemType.RoomItem ? "F" : "W");
		appendInt(item.getBaseItem().getBaseId());
	}

}
