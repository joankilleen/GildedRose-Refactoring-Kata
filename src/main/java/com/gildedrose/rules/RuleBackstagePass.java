package com.gildedrose.rules;

import com.gildedrose.Item;

public class RuleBackstagePass implements IRule<Item> {

	@Override
	public Item update(final Item item) {
		// "Backstage passes", like aged brie, increases in Quality as its SellIn value
		// approaches;
		// Quality increases by 2 when there are 10 days or less and by 3 when there are
		// 5 days or less but
		// Quality drops to 0 after the concert

		if (item.quality >= 50 && item.sellIn > 0) {
			return item;
		}
		if (item.sellIn > 10) {
			return new Item(item.name, item.sellIn - 1, item.quality + 1);
		}
		if (item.sellIn > 5) {
			return new Item(item.name, item.sellIn - 1, item.quality + 2);
		}
		if (item.sellIn > 0) {
			return new Item(item.name, item.sellIn - 1, item.quality + 3);
		} else {
			return new Item(item.name, item.sellIn - 1, 0);
		}
	}

}
