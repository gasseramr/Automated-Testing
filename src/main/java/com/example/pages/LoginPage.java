package com.example.pages;

import com.example.utils.WaitUtils;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {
    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("h3[data-test='error']");

    public LoginPage open(String baseUrl) {
        driver.get(baseUrl);
        return this;
    }

    public LoginPage enterUsername(String username) {
        type(usernameInput, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        type(passwordInput, password);
        return this;
    }

    public InventoryPage submitValid() {
        click(loginButton);
        // Ensure navigation and that inventory page UI is ready before returning
        WaitUtils.untilUrlContains("inventory.html");
        // Wait for at least one inventory item to be visible
        WaitUtils.untilVisible(By.cssSelector(".inventory_item"));
        return new InventoryPage();
    }

    public LoginPage submitInvalid() {
        click(loginButton);
        return this;
    }

    public String getErrorText() {
        return WaitUtils.untilVisible(errorMessage).getText();
    }
}