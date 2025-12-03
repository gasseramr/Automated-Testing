package com.example.tests;

import com.example.pages.InventoryPage;
import com.example.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ProductTests extends BaseTest {

    @Test(description = "Verify product is visible (simulated search by name)")
    public void productVisibilityByName() {
        InventoryPage inventory = new LoginPage()
                .open(baseUrl)
                .enterUsername(username)
                .enterPassword(password)
                .submitValid();

        Assert.assertTrue(inventory.isProductVisible("Sauce Labs Backpack"),
                "Expected 'Sauce Labs Backpack' to be visible in inventory list");
    }

    @Test(description = "Filter by price: verify ascending order after 'Price (low to high)'")
    public void filterByPriceLowToHigh() {
        InventoryPage inventory = new LoginPage()
                .open(baseUrl)
                .enterUsername(username)
                .enterPassword(password)
                .submitValid()
                .sortByVisibleText("Price (low to high)");

        List<Double> prices = inventory.getProductPrices();
        for (int i = 1; i < prices.size(); i++) {
            Assert.assertTrue(prices.get(i) >= prices.get(i - 1),
                    "Prices should be ascending after filter; index " + i + " violated");
        }
    }

    @Test(description = "Filter by name: verify descending order after 'Name (Z to A)'")
    public void filterByNameZtoA() {
        InventoryPage inventory = new LoginPage()
                .open(baseUrl)
                .enterUsername(username)
                .enterPassword(password)
                .submitValid()
                .sortByVisibleText("Name (Z to A)");

        List<String> names = inventory.getProductNames();
        for (int i = 1; i < names.size(); i++) {
            Assert.assertTrue(names.get(i - 1).compareToIgnoreCase(names.get(i)) >= 0,
                    "Names should be descending after filter; index " + i + " violated");
        }
    }
}