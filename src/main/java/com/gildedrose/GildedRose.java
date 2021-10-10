package com.gildedrose;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;

public class GildedRose {
	Item[] items = new Item[1];

	public GildedRose(Item[] items) {
		Validate.notNull(items);
		this.items = items;
	}

	public GildedRose(List<Item> listItems) {
		Validate.notNull(listItems);
		int i = 0;
		items = new Item[listItems.size()];
		for (Item listItem : listItems) {
			items[i++] = listItem;

		}
	}

	public List<Item> getItems() {
		List<Item> list = Arrays.asList(items);
		Validate.notNull(list);
		return list.stream().filter(i -> i != null).collect(Collectors.toList());

	}

	public List<Item> searchItems(String searchText) {
		List<Item> itemsFound = getItems().stream().filter(i -> i.name.contains(searchText))
				.collect(Collectors.toList());
		return itemsFound;
	}

	public void updateQuality() {
		for (int i = 0; i < items.length; i++) {
			if (!items[i].name.equals("Aged Brie")
					&& !items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
				if (items[i].quality > 0) {
					if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
						items[i].quality = items[i].quality - 1;
					}
				}
			} else {
				if (items[i].quality < 50) {
					items[i].quality = items[i].quality + 1;

					if (items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
						if (items[i].sellIn < 11) {
							if (items[i].quality < 50) {
								items[i].quality = items[i].quality + 1;
							}
						}

						if (items[i].sellIn < 6) {
							if (items[i].quality < 50) {
								items[i].quality = items[i].quality + 1;
							}
						}
					}
				}
			}

			if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
				items[i].sellIn = items[i].sellIn - 1;
			}

			if (items[i].sellIn < 0) {
				if (!items[i].name.equals("Aged Brie")) {
					if (!items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
						if (items[i].quality > 0) {
							if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
								items[i].quality = items[i].quality - 1;
							}
						}
					} else {
						items[i].quality = items[i].quality - items[i].quality;
					}
				} else {
					if (items[i].quality < 50) {
						items[i].quality = items[i].quality + 1;
					}
				}
			}
		}
	}
}