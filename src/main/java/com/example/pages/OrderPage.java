package com.example.pages;

import com.example.utils.WaitUtils;
import org.openqa.selenium.By;

public class OrderPage extends BasePage {
    private final By confirmationHeader = By.cssSelector("h2.complete-header");

    public String getConfirmationMessage() {
        return WaitUtils.untilVisible(confirmationHeader).getText().trim();
    }
}