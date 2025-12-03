package com.example.pages;

import com.example.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;

public class DemoblazeCartPage extends BasePage {
    private final By tableBody = By.id("tbodyid");
    private final By placeOrderButton = By.xpath("//button[text()='Place Order']");

    public DemoblazeCartPage waitForLoaded() {
        WaitUtils.untilVisible(tableBody);
        return this;
    }

    public int getItemCount() {
        List<WebElement> rows = driver.findElements(By.cssSelector("#tbodyid > tr"));
        return rows.size();
    }

    public DemoblazeCartPage removeItemByName(String name) {
        // Ensure there is at least one row before attempting removal
        int seconds = Integer.parseInt(com.example.utils.ConfigReader.get("explicitWait"));
        org.openqa.selenium.support.ui.WebDriverWait wait =
                new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(seconds));
        wait.until(d -> d.findElements(By.cssSelector("#tbodyid > tr")).size() > 0);

        java.util.List<org.openqa.selenium.WebElement> rows = driver.findElements(By.cssSelector("#tbodyid > tr"));
        int before = rows.size();
        org.openqa.selenium.WebElement targetRow = null;
        for (org.openqa.selenium.WebElement row : rows) {
            String title = row.findElement(By.cssSelector("td:nth-child(2)")).getText().trim();
            if (title.equalsIgnoreCase(name)) {
                targetRow = row;
                break;
            }
        }

        if (targetRow == null) {
            // If the item is not found, return without change
            return this;
        }

        org.openqa.selenium.WebElement deleteLink = targetRow.findElement(By.linkText("Delete"));
        deleteLink.click();

        // Wait for the specific row to be removed (stale) and count to decrement by 1
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf(targetRow));
        wait.until(d -> d.findElements(By.cssSelector("#tbodyid > tr")).size() == before - 1);
        return this;
    }

    public DemoblazeCheckoutModal openPlaceOrder() {
        WaitUtils.untilClickable(placeOrderButton).click();
        return new DemoblazeCheckoutModal();
    }
}