package io.bobba.poc.core.catalogue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

	private void loadFromDb() throws SQLException {
		try (Connection connection = BobbaEnvironment.getGame().getDatabase().getDataSource().getConnection();
				Statement statement = connection.createStatement()) {
			if (statement.execute("SELECT * FROM catalog_pages")) {
				try (ResultSet set = statement.getResultSet()) {
					while (set.next()) {
						int pageId = set.getInt("id");
						int parentId = set.getInt("parent_id");
						String caption = set.getString("caption");
						int iconColor = set.getInt("icon_color");
						int iconImage = set.getInt("icon_image");
						boolean visible = set.getString("visible").equals("1");
						boolean enabled = set.getString("enabled").equals("1");
						int minRank = set.getInt("min_rank");
						String layout = set.getString("page_layout");
						String headline = set.getString("page_headline");
						String teaser = set.getString("page_teaser");
						String text1 = set.getString("page_text1");
						String text2 = set.getString("page_text2");
						String text3 = set.getString("page_text_details");
						String text4 = set.getString("page_text_teaser");
						
						List<CatalogueItem> dummy = new ArrayList<>();

						try (Connection connection2 = BobbaEnvironment.getGame().getDatabase().getDataSource()
								.getConnection(); Statement statement2 = connection.createStatement()) {
							if (statement2.execute("SELECT * FROM catalog_items WHERE page_id = " + pageId)) {
								try (ResultSet set2 = statement2.getResultSet()) {
									while (set2.next()) {
										int catalogItemId = set2.getInt("id");
										String catalogName = set2.getString("catalog_name");
										int baseId = Integer.parseInt(set2.getString("item_ids"));
										int cost = set2.getInt("cost_credits");
										
										BaseItem base = BobbaEnvironment.getGame().getItemManager().getItem(baseId);
										if (base != null) {
											dummy.add(new CatalogueItem(catalogItemId, base, catalogName, cost, 1));	
										} else {
											//System.out.println("null base: " + catalogName);
										}
									}
								}
							}
						}


					pages.put(pageId, new CataloguePage(pageId, parentId, caption, visible, enabled, minRank, iconColor,
							iconImage, layout, headline, teaser, text1, text2, text3, text4, dummy));
				}
			}
		}
	}catch(

	SQLException e)
	{
		throw e;
	}
	}

	public void initialize() throws SQLException {
		loadFromDb();
	}

	private void initializeDummy() {
		BaseItem club_sofa = BobbaEnvironment.getGame().getItemManager().findItem("club_sofa");
		BaseItem shelves_norja = BobbaEnvironment.getGame().getItemManager().findItem("shelves_norja");
		BaseItem duck = BobbaEnvironment.getGame().getItemManager().findItem("duck");
		BaseItem flag_columbia = BobbaEnvironment.getGame().getItemManager().findItem("flag_columbia");
		BaseItem scifirocket3 = BobbaEnvironment.getGame().getItemManager().findItem("scifirocket*3");
		BaseItem small_chair_armas = BobbaEnvironment.getGame().getItemManager().findItem("small_chair_armas");

		pages.put(1, new CataloguePage(1, -1, "Catálogo", true, true, 0, 5, 1, "frontpage",
				"catalog_frontpage_headline_shop_ES", "fatherhabbo_300x187_girl", "Introducing Bobba Catalogue",
				"omg it works", "Why is it in spanish?",
				"I just took the catalogue from my old hispanic hotel, sorry bout that. :')", new ArrayList<>()));

		pages.put(80, new CataloguePage(80, -1, "Wired", false, true, 0, 5, 80, "default", "", "", "", "", "",
				"", new ArrayList<>()));

		List<CatalogueItem> dummy = new ArrayList<>();
		dummy.add(new CatalogueItem(45, club_sofa, "club_sofa", 3, 1));
		dummy.add(new CatalogueItem(46, shelves_norja, "shelves_norja", 3, 1));

		pages.put(81, new CataloguePage(81, 80, "Causantes", true, true, 0, 5, 81, "default",
				"catalog_wired_header2_es", "ctlg_pic_wired_triggers",
				"Los Causantes permiten definir qué se necesita que pase para que tenga lugar un Efecto. Para programar un Causante, colócalo en una Sala, haz doble clic en él y ponlo en marcha. Necesitarás apilar un Efecto sobre un Causante.",
				"¡Haz click en cada objeto para ver cómo funciona!", "", "", dummy));

		List<CatalogueItem> dummy2 = new ArrayList<>();
		dummy2.add(new CatalogueItem(47, duck, "duck", 5, 1));

		pages.put(82, new CataloguePage(82, 80, "Efectos", true, true, 0, 5, 82, "default",
				"catalog_wired_header3_es", "ctlg_pic_wired_effects",
				"Los Efectos permiten definir qué se necesita que pase para que tenga lugar un Efecto. Para programar un Causante, colócalo en una Sala, haz doble clic en él y ponlo en marcha. Necesitarás apilar un Efecto sobre un Causante.",
				"¡Haz click en cada objeto para ver cómo funciona!", "", "", dummy2));

		pages.put(83, new CataloguePage(83, -1, "Tienda", false, true, 0, 5, 2, "default", "", "", "", "", "",
				"", Arrays.asList()));

		List<CatalogueItem> dummy3 = new ArrayList<>();
		dummy3.add(new CatalogueItem(47, duck, "duck", 5, 1));
		dummy3.add(new CatalogueItem(48, flag_columbia, "flag_columbia", 5, 1));
		dummy3.add(new CatalogueItem(49, scifirocket3, "scifirocket3", 5, 1));
		dummy3.add(new CatalogueItem(50, small_chair_armas, "small_chair_armas", 5, 1));

		pages.put(84,
				new CataloguePage(84, 83, "Extras", true, true, 0, 5, 11, "default",
						"catalog_extra_headline1_de", "catalog_extra_teaser1",
						"Los accesorios son indispensables para dar personalidad. ¡Diferénciate!", "¡Me encanta!", "",
						"", dummy3));
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
