package com.example.pages;

import com.example.utils.WaitUtils;
import com.example.utils.ConfigReader;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class InventoryPage extends BasePage {
    private final By inventoryItems = By.cssSelector(".inventory_item");
    private final By cartLink = By.cssSelector("a.shopping_cart_link");
    private final By sortSelect = By.cssSelector("select[data-test='product_sort_container']");
    private final By cartBadge = By.cssSelector("span.shopping_cart_badge");

    public InventoryPage sortByVisibleText(String text) {
        WebElement selectEl = WaitUtils.untilVisible(sortSelect);
        try {
            new Select(selectEl).selectByVisibleText(text);
        } catch (Exception e) {
            // Fallback: attempt to click via JS if overlay prevents normal interaction
            try {
                ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", selectEl);
                new Select(selectEl).selectByVisibleText(text);
            } catch (Exception ignored) { }
        }
        return this;
    }

    public InventoryPage addToCart(String productName) {
        int before = getCartBadgeCount();
        List<WebElement> items = driver.findElements(inventoryItems);
        for (WebElement item : items) {
            String name = item.findElement(By.cssSelector(".inventory_item_name")).getText().trim();
            if (name.equalsIgnoreCase(productName)) {
                WebElement addBtn = item.findElement(By.xpath(".//button[contains(., 'Add to cart')]"));
                addBtn.click();
                break;
            }
        }
        // Then wait for cart badge to reflect increment
        waitForCartBadgeToBe(before + 1);
        return this;
    }

    public InventoryPage removeFromCart(String productName) {
        int before = getCartBadgeCount();
        List<WebElement> items = driver.findElements(inventoryItems);
        for (WebElement item : items) {
            String name = item.findElement(By.cssSelector(".inventory_item_name")).getText().trim();
            if (name.equalsIgnoreCase(productName)) {
                WebElement removeBtn = item.findElement(By.xpath(".//button[contains(., 'Remove')]"));
                removeBtn.click();
                break;
            }
        }
        // Synchronize purely on badge change to avoid stale element issues
        waitForCartBadgeToBe(Math.max(0, before - 1));
        return this;
    }

    private void waitForCartBadgeToBe(int expected) {
        int seconds = Integer.parseInt(ConfigReader.get("explicitWait"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        if (expected <= 0) {
            // Wait for badge to be absent or text "0" (Swag Labs hides badge when 0)
            wait.until(d -> d.findElements(cartBadge).isEmpty());
        } else {
            wait.until(d -> {
                List<WebElement> badges = d.findElements(cartBadge);
                if (badges.isEmpty()) return false;
                String txt = badges.get(0).getText().trim();
                return txt.equals(String.valueOf(expected));
            });
        }
    }

    public CartPage openCart() {
        WaitUtils.untilClickable(cartLink).click();
        return new CartPage();
    }

    public int getCartBadgeCount() {
        List<WebElement> badges = driver.findElements(cartBadge);
        if (badges.isEmpty()) return 0;
        String text = badges.get(0).getText().trim();
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public List<String> getProductNames() {
        return driver.findElements(inventoryItems).stream()
                .map(el -> el.findElement(By.cssSelector(".inventory_item_name")).getText().trim())
                .toList();
    }

    public List<Double> getProductPrices() {
        return driver.findElements(inventoryItems).stream()
                .map(el -> el.findElement(By.cssSelector(".inventory_item_price")).getText().trim())
                .map(txt -> txt.replace("$", ""))
                .map(Double::parseDouble)
                .toList();
    }

    public boolean isProductVisible(String productName) {
        return driver.findElements(inventoryItems).stream()
                .anyMatch(el -> el.findElement(By.cssSelector(".inventory_item_name"))
                        .getText().trim().equalsIgnoreCase(productName));
    }
}