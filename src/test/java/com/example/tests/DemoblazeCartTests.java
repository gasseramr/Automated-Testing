package com.example.tests;

import com.example.pages.DemoblazeHomePage;
import com.example.pages.DemoblazeProductPage;
import com.example.pages.DemoblazeCartPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DemoblazeCartTests extends BaseTest {

    @Test(description = "Add and remove items in cart, verify counts")
    public void addRemoveItems() {
        DemoblazeHomePage home = new DemoblazeHomePage()
                .open(baseUrl)
                .openLoginModal().waitForOpen().enterUsername(username).enterPassword(password).submitValid();

        // Add two products
        DemoblazeProductPage p1 = home.openProductByName("Samsung galaxy s6");
        p1.addToCartAndAcceptAlert();
        DemoblazeHomePage homeAfterP1 = new DemoblazeHomePage().open(baseUrl);

        // Navigate to laptops to ensure MacBook air is present
        homeAfterP1.filterByCategory("Laptops");
        DemoblazeProductPage p2 = homeAfterP1.openProductByName("MacBook air");
        p2.addToCartAndAcceptAlert();

        DemoblazeCartPage cart = new DemoblazeHomePage().open(baseUrl).openCart().waitForLoaded();
        int countAfterAdd = cart.getItemCount();
        Assert.assertTrue(countAfterAdd >= 2, "Cart should have at least 2 items, found: " + countAfterAdd);

        // Remove one item and verify decrement
        cart.removeItemByName("Samsung galaxy s6");
        int countAfterRemove = cart.getItemCount();
        Assert.assertTrue(countAfterRemove == countAfterAdd - 1,
                "Cart count should decrement by 1 after removal");
    }
}