package com.example.tests;

import com.example.pages.DemoblazeHomePage;
import com.example.pages.DemoblazeProductPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DemoblazeProductTests extends BaseTest {

    @Test(description = "Filter by category: verify a known product is present")
    public void filterByCategoryPhones() {
        DemoblazeHomePage home = new DemoblazeHomePage()
                .open(baseUrl)
                .openLoginModal().waitForOpen().enterUsername(username).enterPassword(password).submitValid();

        home.filterByCategory("Phones");
        DemoblazeProductPage product = home.openProductByName("Samsung galaxy s6");
        Assert.assertEquals(product.getTitle(), "Samsung galaxy s6",
                "Product title should match after navigating from filtered list");
    }

    @Test(description = "Simulated search by name: open and verify product page")
    public void productVisibilityByName() {
        DemoblazeHomePage home = new DemoblazeHomePage()
                .open(baseUrl)
                .openLoginModal().waitForOpen().enterUsername(username).enterPassword(password).submitValid();

        // Ensure the product list shows laptops before selecting MacBook air
        home.filterByCategory("Laptops");
        DemoblazeProductPage product = home.openProductByName("MacBook air");
        Assert.assertEquals(product.getTitle(), "MacBook air",
                "Product page should display the requested item title");
    }
}