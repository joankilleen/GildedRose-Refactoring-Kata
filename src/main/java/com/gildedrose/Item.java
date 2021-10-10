package com.gildedrose;

import java.text.MessageFormat;

public class Item {

	public String name;

	public int sellIn;

	public int quality;

	public Item(String name, int sellIn, int quality) {
		this.name = name;
		this.sellIn = sellIn;
		this.quality = quality;
	}

	@Override
	public String toString() {
		return MessageFormat.format("name: {0}, sellIn: {1}, quality: {2}", this.name, this.sellIn, this.quality);
	}
}
