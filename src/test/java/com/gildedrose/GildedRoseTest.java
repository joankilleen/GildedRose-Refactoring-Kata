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
	public void foo() {
		System.out.println("Test can be called");
	}

	@Test
	public void testSulfuras() {

		assert app != null;
		assert app.getItems() != null;
		List<Item> sulfuras = app.searchItems("Sulfuras");
		assertTrue(sulfuras.size() == 2);

	}
}
