package com.gildedrose;

import static org.junit.Assert.assertTrue;

import java.text.MessageFormat;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class GildedRoseTest {

	private GildedRose app = null;

	@Before
	public void setUp() {
		app = new GildedRose(TexttestFixture.getItems());
	}

	@Test
	public void testQualitySellInDecrement() {
		// arrange
		Item item3 = app.getItems().get(2);
		int qualityBeforeUpdate = item3.quality;
		int sellInBeforeUpdate = item3.sellIn;
		// act
		app.updateQuality();
		// assert
		assertTrue(item3.quality == qualityBeforeUpdate - 1);
		assertTrue(item3.sellIn == sellInBeforeUpdate - 1);

	}

	@Test
	public void testSulfurasQualityDoesntChange() {
		List<Item> sulfuras = app.searchItems("Sulfuras");
		assertTrue(sulfuras.size() == 2);
		int qualityBeforeUpdate = sulfuras.get(0).quality;
		System.out.println(MessageFormat.format("Quality before update: {0}", qualityBeforeUpdate));

		// Update quality for 5 days
		for (int i = 5; i > 0; i--) {
			app.updateQuality();
		}
		int qualityAfterUpdate = app.searchItems("Sulfuras").get(0).quality;
		assertTrue(qualityAfterUpdate == qualityBeforeUpdate);

	}
}
