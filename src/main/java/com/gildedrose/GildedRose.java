package com.gildedrose;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.activation.UnsupportedDataTypeException;

import org.apache.commons.lang3.Validate;

import com.gildedrose.rules.IRule;
import com.gildedrose.rules.RuleAgedBrie;
import com.gildedrose.rules.RuleDefault;
import com.gildedrose.rules.RuleSulfuras;
import com.google.common.collect.ImmutableMap;

//TODO - enums or some kind of structure for the different types
//make formatting work
//better initialisation of the static types

public class GildedRose {
	private Item[] items = new Item[1];
	// @formatter:off
	private ImmutableMap<String, IRule<Item>> rules = ImmutableMap.<String, IRule<Item>>builder()
			.put("Elixir of the Mongoose", new RuleDefault())
			.put("+5 Dexterity Vest", new RuleDefault())
			.put("Aged Brie", new RuleAgedBrie())
			.put("Sulfuras, Hand of Ragnaros", new RuleSulfuras())
			.build();

	// @formatter:on
	public GildedRose(Item[] items) {
		Validate.notNull(items);
		this.items = new Item[items.length];
		for (int i = 0; i < items.length; i++) {
			Item next = items[i];
			this.items[i] = new Item(next.name, next.sellIn, next.quality);
		}
	}

	public GildedRose(List<Item> listItems) {
		Validate.notNull(listItems);
		int i = 0;
		items = new Item[listItems.size()];
		for (Item listItem : listItems) {
			Item itemCopy = new Item(listItem.name, listItem.sellIn, listItem.quality);
			items[i++] = itemCopy;

		}
	}

	public List<Item> getItems() {
		List<Item> list = Arrays.asList(items);
		Validate.notNull(list);
		return list.stream().filter(i -> i != null).collect(Collectors.toList());

	}

	public List<Item> searchItems(String searchText, Integer sellInParam, Integer qualityParam) {
		List<Item> itemsFound = getItems().stream().filter(i -> i.name.contains(searchText))
				.collect(Collectors.toList());

		if (sellInParam != null) {
			itemsFound = itemsFound.stream().filter(i -> sellInParam == i.sellIn).collect(Collectors.toList());
		}
		if (qualityParam != null) {
			itemsFound.stream().filter(i -> qualityParam == i.quality).collect(Collectors.toList());
		}
		return itemsFound;
	}

	public List<Item> searchItems(String searchText) {
		return searchItems(searchText, null, null);

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

	public void update(Item item) throws UnsupportedDataTypeException {

		IRule<Item> rule = rules.get(item.name);
		if (rule == null) {
			throw new UnsupportedDataTypeException(MessageFormat.format("Rule not implemented for {0}", item.name));
		} else {
			Item updated = rule.update(item);
			item.quality = updated.quality;
			item.sellIn = updated.sellIn;
		}

	}
}