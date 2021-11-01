package com.gildedrose;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.activation.UnsupportedDataTypeException;

import org.junit.Before;
import org.junit.Test;

public class GildedRoseTest {

	private GildedRose app = null;

	@Before
	public void setUp() {
		app = new GildedRose(new TexttestFixture().getItems());
	}

	@Test
	public void testQualitySellInDecrement() throws UnsupportedDataTypeException {
		// arrange
		Item elixirItem = app.searchItems("Elixir of the Mongoose").get(0);
		int qualityBeforeUpdate = elixirItem.quality;
		int sellInBeforeUpdate = elixirItem.sellIn;
		// act
		app.update(elixirItem);
		// assert
		assertTrue(elixirItem.quality == qualityBeforeUpdate - 1);
		assertTrue(elixirItem.sellIn == sellInBeforeUpdate - 1);
	}

	@Test
	public void testConjuredQualityDecrementsTwiceAsFast() throws UnsupportedDataTypeException {
		// arrange
		Item conjureditem = app.searchItems("Conjure").get(0);
		int qualityBeforeUpdate = conjureditem.quality;
		int sellInBeforeUpdate = conjureditem.sellIn;
		// act
		app.update(conjureditem);
		// assert
		assertTrue(conjureditem.quality == qualityBeforeUpdate - 2);
		assertTrue(conjureditem.sellIn == sellInBeforeUpdate - 1);
	}

	@Test
	public void testSellInPassed() throws UnsupportedDataTypeException {

		// arrange
		List<Item> items = app.searchItems("Dexterity");
		Item dexterityVest = items.get(0);
		assertTrue(dexterityVest != null);
		int sellInBefore = dexterityVest.sellIn;

		// act
		// Update to day before last
		for (int i = sellInBefore; i > 0; i--) {
			app.update(dexterityVest);
		}

		// Read Quality now and check that at the next update it has been degraded by 2.
		int quality = app.searchItems("Dexterity").get(0).quality;

		// act - SellIn down to zero - quality must decrease by 2
		app.update(dexterityVest);

		// assert
		int qualityAfterSellInZero = app.searchItems("Dexterity").get(0).quality;
		assertTrue(qualityAfterSellInZero == quality - 2);
		assertTrue(app.searchItems("Dexterity").get(0).sellIn == -1);

	}

	@Test
	public void testSulfurasQualityDoesntChange() throws UnsupportedDataTypeException {
		List<Item> sulfuras = app.searchItems("Sulfuras");
		assertTrue(sulfuras.size() == 2);
		int qualityBeforeUpdate = sulfuras.get(0).quality;

		// Update quality for 5 days
		for (int i = 5; i > 0; i--) {
			app.update(sulfuras.get(0));
		}
		int qualityAfterUpdate = app.searchItems("Sulfuras").get(0).quality;
		assertTrue(qualityAfterUpdate == qualityBeforeUpdate);
	}

	@Test
	public void testQualityNeverNegative() throws UnsupportedDataTypeException {
		// arange
		Item itemManaCake = app.searchItems("Conjured Mana Cake").get(0);
		assertTrue(itemManaCake != null);
		int qualityBeforeUpdates = itemManaCake.quality;

		// act - updateQuality more times than necessary to get quality to 0
		for (int i = qualityBeforeUpdates + 5; i >= 0; i++) {
			app.update(itemManaCake);
		}

		// assert quality has not decreased below 0
		assertTrue(app.searchItems("Conjured Mana Cake").get(0).quality == 0);

	}

	@Test
	public void testAgedBrieQualityIncreases() throws UnsupportedDataTypeException {
		// arrange
		Item itemAgedBrie = app.searchItems("Aged Brie").get(0);
		assertTrue(itemAgedBrie != null);
		int qualityBeforeUpdates = itemAgedBrie.quality;

		// act
		for (int i = 0; i <= 5; i++) {
			app.update(itemAgedBrie);
		}

		// assert quality of aged brie has increased
		int qualiyAfterUpdates = app.searchItems("Aged Brie").get(0).quality;
		assertTrue(qualiyAfterUpdates == qualityBeforeUpdates + 10);
	}

	@Test
	public void testQualityNeverMoreThan50() throws UnsupportedDataTypeException {

		// arrange
		Item itemAgedBrie = app.searchItems("Aged Brie").get(0);
		assertTrue(itemAgedBrie != null);
		int qualityBeforeUpdates = itemAgedBrie.quality;
		assertTrue(qualityBeforeUpdates == 0);

		// act
		// Update 100 times and verify quaility does not exceed 50
		//
		for (int i = 0; i <= 100; i++) {
			app.update(itemAgedBrie);
		}

		// assert quality of aged brie is 50
		int qualiyAfterUpdates = app.searchItems("Aged Brie").get(0).quality;
		assertTrue(qualiyAfterUpdates == 50);

	}

	@Test
	public void testSulfurasQualityAlways80AndSellInDoesntChange() throws UnsupportedDataTypeException {
		// arrange
		List<Item> itemSulfuras = app.searchItems("Sulfuras");
		assert itemSulfuras.size() == 2;
		assert itemSulfuras.get(0).quality == 80;
		int sellInBefore = itemSulfuras.get(0).sellIn;

		// act
		// Update 10 times and verify that quality does not change
		for (int i = 0; i <= 10; i++) {
			app.update(itemSulfuras.get(0));
		}

		int qualiyAfterUpdates = app.searchItems("Sulfuras").get(0).quality;
		assertTrue(qualiyAfterUpdates == 80);
		assertEquals(sellInBefore, app.searchItems("Sulfuras").get(0).sellIn);
	}

	@Test
	public void testBackstagePassQualityIncreases() throws UnsupportedDataTypeException {

		// arrange
		List<Item> backStagePasses = app.searchItems("TAFKAL80ETC", 15, 20);
		assert backStagePasses.size() == 1;
		Item backStagePass = backStagePasses.get(0);
		int sellIn = backStagePass.sellIn;
		assert sellIn == 15;

		// act: decrease down to 10 days and verify that the quality increase is 1 per
		// day
		for (int i = sellIn; i > 10; i--) {
			app.update(backStagePasses.get(0));
		}
		// assert
		assertTrue(backStagePass.quality == 25);
		sellIn = backStagePass.sellIn;
		assertTrue(sellIn == 10);

		// act: decrease sellIn form 10 down to 6 and verify that the quality increase
		// is 2 per day (10)
		for (int i = sellIn; i > 5; i--) {
			app.update(backStagePass);
		}
		// assert
		assertTrue(backStagePass.quality == 35);
		sellIn = backStagePass.sellIn;
		assertTrue(sellIn == 5);

		// act: decrease sellIn form 5 down to 1 and verify that the quality increase
		// is 3 per day (15)
		for (int i = sellIn; i > 0; i--) {
			app.update(backStagePasses.get(0));
		}
		// assert
		assertTrue(backStagePass.quality == 50);
		sellIn = backStagePass.sellIn;
		assertTrue(sellIn == 0);

		// act: decrease sellIn down past date of concert (0) and verify that quality
		// drops
		// to 0

		for (int i = sellIn; i > -2; i--) {
			app.update(backStagePasses.get(0));
		}

		// assert
		assertTrue(backStagePass.quality == 0);

	}
}
