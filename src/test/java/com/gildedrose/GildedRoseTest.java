package com.gildedrose;

import static org.junit.Assert.assertTrue;

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
	public void testSellInPassed() {

		// arrange
		List<Item> items = app.searchItems("Dexterity");
		Item dexterityVest = items.get(0);
		assertTrue(dexterityVest != null);
		int sellInBefore = dexterityVest.sellIn;

		// act
		// Update to day before last
		for (int i = sellInBefore; i > 0; i--) {
			app.updateQuality();
		}

		// Read Quality now and check that at the next update it has been degraded by 2.
		int quality = app.searchItems("Dexterity").get(0).quality;

		// act - SellIn down to zero - quality must decrease by 2
		app.updateQuality();

		// assert
		int qualityAfterSellInZero = app.searchItems("Dexterity").get(0).quality;
		assertTrue(qualityAfterSellInZero == quality - 2);
		assertTrue(app.searchItems("Dexterity").get(0).sellIn == -1);

	}

	@Test
	public void testSulfurasQualityDoesntChange() {
		List<Item> sulfuras = app.searchItems("Sulfuras");
		assertTrue(sulfuras.size() == 2);
		int qualityBeforeUpdate = sulfuras.get(0).quality;

		// Update quality for 5 days
		for (int i = 5; i > 0; i--) {
			app.updateQuality();
		}
		int qualityAfterUpdate = app.searchItems("Sulfuras").get(0).quality;
		assertTrue(qualityAfterUpdate == qualityBeforeUpdate);

	}
}
