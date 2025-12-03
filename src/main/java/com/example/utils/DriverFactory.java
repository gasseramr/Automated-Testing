package com.example.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

public class DriverFactory {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    public static void initDriver() {
        if (DRIVER.get() != null) return;

        String browser = ConfigReader.get("browser").toLowerCase();
        boolean headless = Boolean.parseBoolean(ConfigReader.get("headless"));

        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions ff = new FirefoxOptions();
                if (headless) ff.addArguments("-headless");
                // Disable password manager and autofill in Firefox
                ff.addPreference("signon.rememberSignons", false);
                ff.addPreference("signon.autofillForms", false);
                ff.addPreference("signon.generation", false);
                ff.addPreference("extensions.formautofill.available", "off");
                ff.addPreference("extensions.formautofill.creditCards.enabled", false);
                ff.addPreference("extensions.formautofill.addresses.enabled", false);
                ff.addPreference("dom.webnotifications.enabled", false);
                DRIVER.set(new FirefoxDriver(ff));
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions co = new ChromeOptions();
                if (headless) co.addArguments("--headless=new");
                co.addArguments("--window-size=1920,1080");
                // Reduce test interference from Chrome UI features
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                prefs.put("autofill.profile_enabled", false);
                prefs.put("autofill.credit_card_enabled", false);
                prefs.put("autofill.address_enabled", false);
                co.setExperimentalOption("prefs", prefs);
                co.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
                co.setExperimentalOption("useAutomationExtension", false);
                co.addArguments("--disable-notifications");
                co.addArguments("--disable-infobars");
                co.addArguments("--no-default-browser-check");
                co.addArguments("--no-first-run");
                co.addArguments("--disable-features=PasswordManagerOnboarding,AutofillServerCommunication,EnablePasswordsAccountStorage,TranslateUI");
                DRIVER.set(new ChromeDriver(co));
                break;
        }

        WebDriver driver = DRIVER.get();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(ConfigReader.get("implicitWait"))));
        driver.manage().window().maximize();
    }

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }
}