package io.bobba.poc.core.catalogue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.bobba.poc.BobbaEnvironment;
import io.bobba.poc.communication.outgoing.catalogue.CatalogueIndexComposer;
import io.bobba.poc.communication.outgoing.catalogue.CataloguePageComposer;
import io.bobba.poc.communication.outgoing.catalogue.CataloguePurchaseErrorComposer;
import io.bobba.poc.communication.outgoing.catalogue.CataloguePurchaseInformationComposer;
import io.bobba.poc.core.items.BaseItem;
import io.bobba.poc.core.users.User;

public class Catalogue {
	private Map<Integer, CataloguePage> pages;
	private static int itemIdGenerator = 0;

	public Catalogue() {
		this.pages = new LinkedHashMap<>();
	}
	
	public void initialize() {
		BaseItem club_sofa = BobbaEnvironment.getInstance().getGame().getItemManager().findItem("club_sofa");
		BaseItem shelves_norja = BobbaEnvironment.getInstance().getGame().getItemManager().findItem("shelves_norja");
		BaseItem duck = BobbaEnvironment.getInstance().getGame().getItemManager().findItem("duck");
		BaseItem flag_columbia = BobbaEnvironment.getInstance().getGame().getItemManager().findItem("flag_columbia");
		BaseItem scifirocket3 = BobbaEnvironment.getInstance().getGame().getItemManager().findItem("scifirocket*3");
		BaseItem small_chair_armas = BobbaEnvironment.getInstance().getGame().getItemManager().findItem("small_chair_armas");
		
		pages.put(1, new CataloguePage(1, -1, "Catálogo", true, true, 0, "c8684e", 1, "frontpage",
				"catalog_frontpage_headline_shop_ES", "fatherhabbo_300x187_girl", "Introducing Bobba Catalogue",
				"omg it works", "Why is it in spanish?",
				"I just took the catalogue from my old hispanic hotel, sorry bout that. :')",
				new ArrayList<>()));
		
		pages.put(80, new CataloguePage(80, -1, "Wired", false, true, 0, "aaaaaa", 80, "default", "", "", "", "", "", "", new ArrayList<>()));
		
		List<CatalogueItem> dummy = new ArrayList<>();
		dummy.add(new CatalogueItem(45, club_sofa, "club_sofa", 3, 1));
		dummy.add(new CatalogueItem(46, shelves_norja, "shelves_norja", 3, 1));
		
		pages.put(81, new CataloguePage(81, 80, "Causantes", true, true, 0, "aaaaaa", 81, "default",
				"catalog_wired_header2_es", "ctlg_pic_wired_triggers",
				"Los Causantes permiten definir qué se necesita que pase para que tenga lugar un Efecto. Para programar un Causante, colócalo en una Sala, haz doble clic en él y ponlo en marcha. Necesitarás apilar un Efecto sobre un Causante.",
				"¡Haz click en cada objeto para ver cómo funciona!", "", "", dummy));
		
		List<CatalogueItem> dummy2 = new ArrayList<>();
		dummy2.add(new CatalogueItem(47, duck, "duck", 5, 1));
		
		pages.put(82, new CataloguePage(82, 80, "Efectos", true, true, 0, "aaaaaa", 82, "default",
				"catalog_wired_header3_es", "ctlg_pic_wired_effects",
				"Los Efectos permiten definir qué se necesita que pase para que tenga lugar un Efecto. Para programar un Causante, colócalo en una Sala, haz doble clic en él y ponlo en marcha. Necesitarás apilar un Efecto sobre un Causante.",
				"¡Haz click en cada objeto para ver cómo funciona!", "", "", dummy2));
		
		
		pages.put(83, new CataloguePage(83, -1, "Tienda", false, true, 0, "8ebb4a", 2, "default", "", "", "", "", "", "", Arrays.asList()));
		
		List<CatalogueItem> dummy3 = new ArrayList<>();
		dummy3.add(new CatalogueItem(47, duck, "duck", 5, 1));
		dummy3.add(new CatalogueItem(48, flag_columbia, "flag_columbia", 5, 1));
		dummy3.add(new CatalogueItem(49, scifirocket3, "scifirocket3", 5, 1));
		dummy3.add(new CatalogueItem(50, small_chair_armas, "small_chair_armas", 5, 1));
		
		pages.put(84, new CataloguePage(84, 83, "Extras", true, true, 0, "aaaaaa", 11, "default",
				"catalog_extra_headline1_de", "catalog_extra_teaser1",
				"Los accesorios son indispensables para dar personalidad. ¡Diferénciate!",
				"¡Me encanta!", "", "", dummy3));
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

	public void handlePurchase(User user, int pageId, int itemId) {
		CataloguePage page = getPage(pageId);
		if (page != null && page.isEnabled() && page.isVisible() && page.getMinRank() <= user.getRank()) {
			CatalogueItem item = page.getItem(itemId);
			if (item != null) {
				if (user.getCredits() < item.getCost()) {
					user.getClient().sendMessage(new CataloguePurchaseErrorComposer());
				} else {
					user.getClient().sendMessage(new CataloguePurchaseInformationComposer(item));
					user.setCredits(user.getCredits() - item.getCost());
					deliverItem(user, item.getBaseItem(), item.getAmount());
				}
			}
		}
	}
	
	public static int generateItemId() {
		return itemIdGenerator++;
	}
	
	private void deliverItem(User user, BaseItem item, int amount) {
		for (int i = 0; i < amount; i++) {
			switch (item.getInteractionType()) {
			default:
				user.getInventory().addItem(generateItemId(), item, 0);
			}
		}
	}
}
