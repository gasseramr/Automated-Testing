package com.example.tests;

import com.example.pages.DemoblazeHomePage;
import com.example.pages.DemoblazeLoginModal;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DemoblazeLoginTests extends BaseTest {

    @Test(description = "Valid login should show welcome user")
    public void validLogin() {
        DemoblazeHomePage home = new DemoblazeHomePage()
                .open(baseUrl);
        home.openLoginModal()
                .waitForOpen()
                .enterUsername(username)
                .enterPassword(password)
                .submitValid();

        Assert.assertTrue(home.isWelcomeUser(username),
                "Expected welcome banner to contain username: " + username);
    }

    @Test(description = "Invalid login should show error alert")
    public void invalidLogin() {
        DemoblazeHomePage home = new DemoblazeHomePage()
                .open(baseUrl);
        String alertText = home.openLoginModal()
                .waitForOpen()
                .enterUsername(username)
                .enterPassword("wrongPass")
                .submitInvalidExpectAlert();

        Assert.assertTrue(alertText.toLowerCase().contains("wrong"),
                "Expected an alert indicating wrong credentials, got: " + alertText);
    }
}