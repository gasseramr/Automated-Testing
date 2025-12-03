package com.example.pages;

import com.example.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DemoblazeHomePage extends BasePage {
    private final By loginLink = By.id("login2");
    private final By cartLink = By.id("cartur");
    private final By userWelcome = By.id("nameofuser");
    private final By productsContainer = By.id("tbodyid");

    public DemoblazeHomePage open(String baseUrl) {
        driver.get(baseUrl);
        WaitUtils.untilVisible(productsContainer);
        return this;
    }

    public DemoblazeLoginModal openLoginModal() {
        WaitUtils.untilClickable(loginLink).click();
        return new DemoblazeLoginModal();
    }

    public boolean isWelcomeUser(String username) {
        WebElement el = WaitUtils.untilVisible(userWelcome);
        return el.getText().trim().contains(username);
    }

    public DemoblazeHomePage filterByCategory(String category) {
        By categoryLink = By.xpath("//a[contains(@class,'list-group-item') and normalize-space(text())='" + category + "']");
        WaitUtils.untilClickable(categoryLink).click();
        // Ensure products list refreshed: wait until at least one product link is visible
        By anyProductLink = By.xpath("//div[@id='tbodyid']//a[@class='hrefch']");
        WaitUtils.untilVisible(anyProductLink);
        return this;
    }

    public DemoblazeProductPage openProductByName(String name) {
        By productLink = By.xpath("//div[@id='tbodyid']//a[@class='hrefch' and normalize-space(text())='" + name + "']");
        WaitUtils.untilClickable(productLink).click();
        return new DemoblazeProductPage();
    }

    public DemoblazeCartPage openCart() {
        WaitUtils.untilClickable(cartLink).click();
        return new DemoblazeCartPage();
    }
}