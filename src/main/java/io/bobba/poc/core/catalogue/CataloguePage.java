package io.bobba.poc.core.catalogue;

import java.util.List;

public class CataloguePage {
	private int id;
	private int parentId;
	private String caption;
	private boolean visible;
	private boolean enabled;
	private int minRank;
	private int iconColor;
	private int iconId;
	private String layout;
	private String imageHeadline;
	private String imageTeaser;
	private String textHeader;
	private String textDetails;
	private String textMisc;
	private String textMisc2;
	
	private List<CatalogueItem> items;

	public int getId() {
		return id;
	}

	public int getParentId() {
		return parentId;
	}

	public String getCaption() {
		return caption;
	}

	public boolean isVisible() {
		return visible;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public int getMinRank() {
		return minRank;
	}

	public int getIconColor() {
		return iconColor;
	}

	public int getIconId() {
		return iconId;
	}

	public String getLayout() {
		return layout;
	}

	public String getImageHeadline() {
		return imageHeadline;
	}

	public String getImageTeaser() {
		return imageTeaser;
	}

	public String getTextHeader() {
		return textHeader;
	}

	public String getTextDetails() {
		return textDetails;
	}

	public String getTextMisc() {
		return textMisc;
	}

	public String getTextMisc2() {
		return textMisc2;
	}

	public List<CatalogueItem> getItems() {
		return items;
	}

	public CataloguePage(int id, int parentId, String caption, boolean visible, boolean enabled, int minRank,
			int iconColor, int iconId, String layout, String imageHeadline, String imageTeaser, String textHeader,
			String textDetails, String textMisc, String textMisc2, List<CatalogueItem> items) {
		this.id = id;
		this.parentId = parentId;
		this.caption = caption;
		this.visible = visible;
		this.enabled = enabled;
		this.minRank = minRank;
		this.iconColor = iconColor;
		this.iconId = iconId;
		this.layout = layout;
		this.imageHeadline = imageHeadline;
		this.imageTeaser = imageTeaser;
		this.textHeader = textHeader;
		this.textDetails = textDetails;
		this.textMisc = textMisc;
		this.textMisc2 = textMisc2;
		this.items = items;
	}

	public CatalogueItem getItem(int itemId) {
		for (CatalogueItem item : items) {
			if (item.getId() == itemId) {
				return item;
			}
		}
		return null;
	}
	
	
}
