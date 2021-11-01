package com.gildedrose.rules;

import com.gildedrose.Item;

public class RuleConjured implements IRule<Item> {

	@Override
	public Item update(final Item item) {
		// -"Conjured" items degrade in Quality twice as fast as normal items
		if (item.quality > 0) {
			return new Item(item.name, item.sellIn - 1, item.sellIn > 0 ? item.quality - 2 : item.quality - 4);
		}
		return item;
	}

}
