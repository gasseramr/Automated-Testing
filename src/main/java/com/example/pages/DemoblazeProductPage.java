package com.example.pages;

import com.example.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;

public class DemoblazeProductPage extends BasePage {
    private final By productTitle = By.cssSelector(".name");
    private final By addToCart = By.xpath("//a[text()='Add to cart']");

    public String getTitle() {
        return WaitUtils.untilVisible(productTitle).getText().trim();
    }

    public DemoblazeProductPage addToCartAndAcceptAlert() {
        WaitUtils.untilClickable(addToCart).click();
        Alert alert = WaitUtils.untilAlertPresent();
        alert.accept();
        return this;
    }

    public DemoblazeCartPage openCartFromNavbar() {
        return new DemoblazeHomePage().openCart();
    }
}