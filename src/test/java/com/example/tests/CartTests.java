package com.example.tests;

import com.example.pages.CartPage;
import com.example.pages.InventoryPage;
import com.example.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CartTests extends BaseTest {

    @DataProvider(name = "productPairs", parallel = true)
    public Object[][] productPairs() {
        return new Object[][]{
                {"Sauce Labs Backpack", "Sauce Labs Bike Light"},
                {"Sauce Labs Bolt T-Shirt", "Sauce Labs Onesie"}
        };
    }

    @Test(description = "Add and remove items in cart, verify counts", dataProvider = "productPairs")
    public void addRemoveItems(String product1, String product2) {
        InventoryPage inventory = new LoginPage()
                .open(baseUrl)
                .enterUsername(username)
                .enterPassword(password)
                .submitValid();

        inventory.addToCart(product1)
                .addToCart(product2);

        Assert.assertEquals(inventory.getCartBadgeCount(), 2, "Expected cart badge count to be 2");

        CartPage cart = inventory.openCart();
        Assert.assertEquals(cart.getItemCount(), 2, "Cart should have 2 items");

        cart.removeItem(product1);
        Assert.assertEquals(cart.getItemCount(), 1, "Cart should have 1 item after removal");
    }
}