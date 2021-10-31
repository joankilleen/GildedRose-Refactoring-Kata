package com.gildedrose;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class GildedRoseTest {

	private GildedRose app = null;

	@Before
	public void setUp() {
		app = new GildedRose(new TexttestFixture().getItems());
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

//Doesn't work yet
	@Ignore
	@Test
	public void testQualityNeverNegative() {
		// arange
		Item itemManaCake = app.searchItems("Conjured Mana Cake").get(0);
		assertTrue(itemManaCake != null);
		int qualityBeforeUpdates = itemManaCake.quality;

		// act - updateQuality more times than necessary to get quality to 0
		for (int i = qualityBeforeUpdates + 5; i >= 0; i++) {
			app.updateQuality();
		}

		// assert quality has not decreased below 0
		assertTrue(app.searchItems("Conjured Mana Cake").get(0).quality == 0);

	}

	@Test
	public void testAgedBrieQualityIncreases() {
		// arrange
		Item itemAgedBrie = app.searchItems("Aged Brie").get(0);
		assertTrue(itemAgedBrie != null);
		int qualityBeforeUpdates = itemAgedBrie.quality;

		// act
		for (int i = 0; i <= 5; i++) {
			app.updateQuality();
		}

		// assert quality of aged brie has increased
		int qualiyAfterUpdates = app.searchItems("Aged Brie").get(0).quality;
		assertTrue(qualiyAfterUpdates == qualityBeforeUpdates + 10);
	}

	@Test
	public void testQualityNeverMoreThan50() {

		// arrange
		Item itemAgedBrie = app.searchItems("Aged Brie").get(0);
		assertTrue(itemAgedBrie != null);
		int qualityBeforeUpdates = itemAgedBrie.quality;
		assertTrue(qualityBeforeUpdates == 0);

		// act
		// Update 100 times and verify quaility does not exceed 50
		//
		for (int i = 0; i <= 100; i++) {
			app.updateQuality();
		}

		// assert quality of aged brie is 50
		int qualiyAfterUpdates = app.searchItems("Aged Brie").get(0).quality;
		assertTrue(qualiyAfterUpdates == 50);

	}

	@Test
	public void testSulfurasQualityAlways80AndSellInDoesntChange() {
		// arrange
		List<Item> itemSulfuras = app.searchItems("Sulfuras");
		assert itemSulfuras.size() == 2;
		assert itemSulfuras.get(0).quality == 80;
		int sellInBefore = itemSulfuras.get(0).sellIn;

		// act
		// Update 10 times and verify that quality does not change
		for (int i = 0; i <= 10; i++) {
			app.updateQuality();
		}

		int qualiyAfterUpdates = app.searchItems("Sulfuras").get(0).quality;
		assertTrue(qualiyAfterUpdates == 80);
		assertEquals(sellInBefore, app.searchItems("Sulfuras").get(0).sellIn);
	}

	@Test
	public void testBackstagePassQualityIncreases() {

	}
}
