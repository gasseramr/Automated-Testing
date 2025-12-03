package com.example.tests;

import com.example.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DemoblazeCheckoutTests extends BaseTest {

    @Test(description = "Checkout flow and verify order confirmation")
    public void checkoutFlow() {
        DemoblazeHomePage home = new DemoblazeHomePage()
                .open(baseUrl)
                .openLoginModal().waitForOpen().enterUsername(username).enterPassword(password).submitValid();

        DemoblazeProductPage product = home.openProductByName("Samsung galaxy s6");
        product.addToCartAndAcceptAlert();

        DemoblazeCartPage cart = new DemoblazeHomePage().open(baseUrl).openCart().waitForLoaded();
        Assert.assertTrue(cart.getItemCount() >= 1, "Expected at least one item in cart before checkout");

        DemoblazeCheckoutModal checkout = cart.openPlaceOrder().waitForOpen();
        checkout.fillForm("Gasser", "Egypt", "Cairo", "4111111111111111", "12", "2025")
                .submitPurchase();

        String title = checkout.getConfirmationTitle();
        String details = checkout.getConfirmationDetails();
        Assert.assertTrue(title.contains("Thank you for your purchase"),
                "Confirmation title should indicate successful purchase");
        Assert.assertTrue(details.contains("Amount"),
                "Confirmation details should include amount and order info");

        checkout.confirmOk();
    }
}