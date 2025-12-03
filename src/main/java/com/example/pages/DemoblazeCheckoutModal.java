package com.example.pages;

import com.example.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DemoblazeCheckoutModal extends BasePage {
    private final By modal = By.id("orderModal");
    private final By nameInput = By.id("name");
    private final By countryInput = By.id("country");
    private final By cityInput = By.id("city");
    private final By cardInput = By.id("card");
    private final By monthInput = By.id("month");
    private final By yearInput = By.id("year");
    private final By purchaseButton = By.xpath("//button[text()='Purchase']");

    // Confirmation sweetalert
    private final By confirmationTitle = By.cssSelector(".sweet-alert.visible h2");
    private final By confirmationDetails = By.cssSelector(".sweet-alert.visible .lead");
    private final By confirmationOk = By.cssSelector(".sweet-alert.visible button.confirm");

    public DemoblazeCheckoutModal waitForOpen() {
        WaitUtils.untilVisible(modal);
        return this;
    }

    public DemoblazeCheckoutModal fillForm(String name, String country, String city,
                                           String card, String month, String year) {
        WaitUtils.untilVisible(nameInput).sendKeys(name);
        WaitUtils.untilVisible(countryInput).sendKeys(country);
        WaitUtils.untilVisible(cityInput).sendKeys(city);
        WaitUtils.untilVisible(cardInput).sendKeys(card);
        WaitUtils.untilVisible(monthInput).sendKeys(month);
        WaitUtils.untilVisible(yearInput).sendKeys(year);
        return this;
    }

    public DemoblazeCheckoutModal submitPurchase() {
        WaitUtils.untilClickable(purchaseButton).click();
        WaitUtils.untilVisible(confirmationTitle);
        return this;
    }

    public String getConfirmationTitle() {
        return WaitUtils.untilVisible(confirmationTitle).getText().trim();
    }

    public String getConfirmationDetails() {
        WebElement details = WaitUtils.untilVisible(confirmationDetails);
        return details.getText().trim();
    }

    public DemoblazeHomePage confirmOk() {
        WaitUtils.untilClickable(confirmationOk).click();
        return new DemoblazeHomePage();
    }
}