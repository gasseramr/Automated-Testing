package com.example.tests;

import com.example.utils.ConfigReader;
import com.example.utils.DriverFactory;
import com.example.utils.ScreenshotUtil;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    protected final String baseUrl = ConfigReader.get("baseUrl");
    protected final String username = ConfigReader.get("username");
    protected final String password = ConfigReader.get("password");

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        DriverFactory.initDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result != null && !result.isSuccess()) {
            try {
                ScreenshotUtil.capture(result.getName());
            } catch (Exception ignored) { }
        }
        DriverFactory.quitDriver();
    }
}