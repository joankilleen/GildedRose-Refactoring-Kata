package com.gildedrose.rules;

import com.gildedrose.Item;

public interface IRule<I extends Item> {
	public I update(I item);

}
