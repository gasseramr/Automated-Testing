package com.example.tests;

import com.example.pages.InventoryPage;
import com.example.pages.LoginPage;
import com.example.utils.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    @Test(description = "Valid login should navigate to inventory page")
    public void validLogin() {
        InventoryPage inventory = new LoginPage()
                .open(baseUrl)
                .enterUsername(username)
                .enterPassword(password)
                .submitValid();

        Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("inventory.html"),
                "Expected to be on inventory page after valid login");
    }

    @Test(description = "Invalid login should show error message")
    public void invalidLogin() {
        LoginPage login = new LoginPage()
                .open(baseUrl)
                .enterUsername("invalid_user")
                .enterPassword("wrong_password")
                .submitInvalid();

        String error = login.getErrorText();
        Assert.assertTrue(error.toLowerCase().contains("epic sadface") || error.toLowerCase().contains("invalid"),
                "Expected error message for invalid login, got: " + error);
    }
}