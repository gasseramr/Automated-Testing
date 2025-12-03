package com.example.pages;

import com.example.utils.WaitUtils;
import org.openqa.selenium.By;

public class CheckoutPage extends BasePage {
    // Step One
    private final By firstName = By.id("first-name");
    private final By lastName = By.id("last-name");
    private final By postalCode = By.id("postal-code");
    private final By continueButton = By.id("continue");

    // Step Two
    private final By finishButton = By.id("finish");

    public CheckoutPage fillCustomerInfo(String f, String l, String p) {
        type(firstName, f);
        type(lastName, l);
        type(postalCode, p);
        return this;
    }

    public CheckoutPage continueToOverview() {
        WaitUtils.untilClickable(continueButton).click();
        return this;
    }

    public OrderPage finishOrder() {
        WaitUtils.untilClickable(finishButton).click();
        return new OrderPage();
    }
}