package com.example.pages;

import com.example.utils.DriverFactory;
import com.example.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BasePage {
    protected final WebDriver driver;

    public BasePage() {
        this.driver = DriverFactory.getDriver();
    }

    protected WebElement find(By locator) {
        return driver.findElement(locator);
    }

    protected void click(By locator) {
        WaitUtils.untilClickable(locator).click();
    }

    protected void type(By locator, String text) {
        WebElement el = WaitUtils.untilVisible(locator);
        el.clear();
        el.sendKeys(text);
    }
}