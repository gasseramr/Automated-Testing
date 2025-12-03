package com.example.tests;

import com.example.pages.CartPage;
import com.example.pages.CheckoutPage;
import com.example.pages.InventoryPage;
import com.example.pages.LoginPage;
import com.example.pages.OrderPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutTests extends BaseTest {

    @Test(description = "Complete checkout flow and verify order confirmation")
    public void checkoutFlow() {
        InventoryPage inventory = new LoginPage()
                .open(baseUrl)
                .enterUsername(username)
                .enterPassword(password)
                .submitValid();

        inventory.addToCart("Sauce Labs Backpack");

        CartPage cart = inventory.openCart();
        CheckoutPage checkout = cart.checkout();
        checkout.fillCustomerInfo("John", "Doe", "12345")
                .continueToOverview();

        OrderPage order = checkout.finishOrder();
        Assert.assertEquals(order.getConfirmationMessage(), "Thank you for your order!",
                "Order confirmation message should match");
    }
}