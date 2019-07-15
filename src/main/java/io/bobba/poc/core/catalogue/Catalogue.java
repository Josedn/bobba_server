package io.bobba.poc.core.catalogue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import io.bobba.poc.communication.outgoing.catalogue.CatalogueIndexComposer;
import io.bobba.poc.communication.outgoing.catalogue.CataloguePageComposer;
import io.bobba.poc.core.users.User;

public class Catalogue {
	public Map<Integer, CataloguePage> pages;

	public Catalogue() {
		this.pages = new LinkedHashMap<>();
	}

	public CataloguePage getPage(int pageId) {
		return pages.getOrDefault(pageId, null);
	}

	public void serializeIndex(User user) {
		user.getClient().sendMessage(new CatalogueIndexComposer(new ArrayList<>(pages.values()), user.getRank()));
	}

	public void serializePage(User user, int pageId) {
		CataloguePage page = getPage(pageId);
		if (page != null && page.isEnabled() && page.isVisible() && page.getMinRank() <= user.getRank()) {
			user.getClient().sendMessage(new CataloguePageComposer(page));
		}
	}

	public void handlePurchase(User user, int itemId) {

	}
}
