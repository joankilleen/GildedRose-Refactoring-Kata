package com.gildedrose.rules;

import com.gildedrose.Item;

public class RuleDefault implements IRule<Item> {

	@Override
	public Item update(final Item item) {
		// - Once the sell by date has passed, Quality degrades twice as fast
		// - The Quality of an item is never negative
		if (item.quality > 0) {
			return new Item(item.name, item.sellIn - 1, item.sellIn > 0 ? item.quality - 1 : item.quality - 2);
		}
		return item;
	}

}
