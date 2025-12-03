package com.example.pages;

import com.example.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Alert;

public class DemoblazeLoginModal extends BasePage {
    private final By modal = By.id("logInModal");
    private final By usernameInput = By.id("loginusername");
    private final By passwordInput = By.id("loginpassword");
    private final By loginButton = By.xpath("//div[@id='logInModal']//button[text()='Log in']");

    public DemoblazeLoginModal waitForOpen() {
        WaitUtils.untilVisible(modal);
        return this;
    }

    public DemoblazeLoginModal enterUsername(String username) {
        WaitUtils.untilVisible(usernameInput).sendKeys(username);
        return this;
    }

    public DemoblazeLoginModal enterPassword(String password) {
        WaitUtils.untilVisible(passwordInput).sendKeys(password);
        return this;
    }

    public DemoblazeHomePage submitValid() {
        WaitUtils.untilClickable(loginButton).click();
        // Successful login closes modal and shows Welcome user text
        WaitUtils.untilVisible(By.id("nameofuser"));
        return new DemoblazeHomePage();
    }

    public String submitInvalidExpectAlert() {
        WaitUtils.untilClickable(loginButton).click();
        Alert alert = WaitUtils.untilAlertPresent();
        String text = alert.getText();
        alert.accept();
        return text;
    }
}