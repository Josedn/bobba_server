package io.bobba.poc.communication.outgoing.catalogue;

import java.util.List;

import io.bobba.poc.communication.protocol.ServerMessage;
import io.bobba.poc.communication.protocol.ServerOpCodes;
import io.bobba.poc.core.catalogue.CataloguePage;

public class CatalogueIndexComposer extends ServerMessage {

	public CatalogueIndexComposer(List<CataloguePage> pages, int rank) {
		super(ServerOpCodes.CATALOGUE_INDEX);
		appendInt(calculateTreeSize(pages, rank, -1));
		for (CataloguePage mainPage : pages) {
			if (mainPage.getParentId() == -1) {
				serializePage(mainPage);
				appendInt(calculateTreeSize(pages, rank, mainPage.getId()));
				for (CataloguePage secondPage: pages) {
					if (secondPage.getParentId() == mainPage.getId()) {
						serializePage(secondPage);
						appendInt(0);
					}
				}
			}
		}
	}
	
	private void serializePage(CataloguePage page) {
		appendBoolean(page.isVisible());
		appendInt(page.getIconColor());
		appendInt(page.getIconId());
		appendInt(page.getId());
		appendString(page.getCaption());
	}
	
	private int calculateTreeSize(List<CataloguePage> pages, int rank, int treeId) {
		int i = 0;
		for (CataloguePage page : pages) {
			if (page.getMinRank() <= rank && page.getParentId() == treeId) {
				i++;
			}
		}
		return i;
	}

}
