package com.example.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Alert;

import java.time.Duration;

public class WaitUtils {
    private static WebDriverWait driverWait() {
        WebDriver driver = DriverFactory.getDriver();
        if (driver == null) {
            throw new IllegalStateException("WebDriver is null in WaitUtils; ensure DriverFactory.initDriver() ran before using waits.");
        }
        int seconds = Integer.parseInt(ConfigReader.get("explicitWait"));
        return new WebDriverWait(driver, Duration.ofSeconds(seconds));
    }

    public static WebElement untilVisible(By locator) {
        return driverWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement untilClickable(By locator) {
        return driverWait().until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement untilPresent(By locator) {
        return driverWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static boolean untilUrlContains(String fragment) {
        return driverWait().until(ExpectedConditions.urlContains(fragment));
    }

    public static Alert untilAlertPresent() {
        return driverWait().until(ExpectedConditions.alertIsPresent());
    }
}