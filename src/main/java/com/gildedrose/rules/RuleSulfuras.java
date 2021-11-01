package com.gildedrose.rules;

import com.gildedrose.Item;

public class RuleSulfuras implements IRule<Item> {

	@Override
	public Item update(final Item item) {
		// Sulfauras - quality and sellIn do not change
		return item;
	}

}
