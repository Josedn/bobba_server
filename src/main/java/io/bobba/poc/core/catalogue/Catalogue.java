package io.bobba.poc.core.catalogue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
											dummy.add(new CatalogueItem(catalogItemId, pageId, base, catalogName, cost, 1));	
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

	public CatalogueItem findItem(String itemName) {
		for (CataloguePage page : new ArrayList<>(pages.values())) {
			for (CatalogueItem item : page.getItems()) {
				if (item.getName().toLowerCase().equals(itemName.toLowerCase())) {
					return item;
				}
			}
		}
		return null;
	}
}
