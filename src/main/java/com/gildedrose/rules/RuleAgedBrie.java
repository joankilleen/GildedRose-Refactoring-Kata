package com.gildedrose.rules;

import com.gildedrose.Item;

public class RuleAgedBrie implements IRule<Item> {

	@Override
	public Item update(final Item item) {
		// - Quality for Aged Brie increases
		// - If the new sellIn is negative, quality increases by 2
		int newSellIn = item.sellIn - 1;
		Item itemUpdated = item;
		if (item.quality < 50) {
			itemUpdated = new Item(item.name, newSellIn, item.quality + 1);
		}
		// If aged brie is past its sellIn, the quality increases twice as fast.
		if (newSellIn < 0 && itemUpdated.quality < 50) {
			itemUpdated.quality += 1;
		}
		return itemUpdated;
	}

}
