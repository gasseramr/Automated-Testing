package com.example.pages;

import com.example.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage {
    private final By cartItems = By.cssSelector(".cart_item");
    private final By checkoutButton = By.id("checkout");

    public int getItemCount() {
        return driver.findElements(cartItems).size();
    }

    public CartPage removeItem(String productName) {
        int before = driver.findElements(cartItems).size();
        List<WebElement> items = driver.findElements(cartItems);
        for (WebElement item : items) {
            String name = item.findElement(By.cssSelector(".inventory_item_name")).getText().trim();
            if (name.equalsIgnoreCase(productName)) {
                item.findElement(By.xpath(".//button[contains(., 'Remove')]")).click();
                int seconds = Integer.parseInt(com.example.utils.ConfigReader.get("explicitWait"));
                new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(seconds))
                        .until(d -> d.findElements(cartItems).size() == Math.max(0, before - 1));
                break;
            }
        }
        return this;
    }

    public CheckoutPage checkout() {
        WaitUtils.untilClickable(checkoutButton).click();
        return new CheckoutPage();
    }
}