package com.example.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.nio.file.Path;

public class ExtentTestListener implements ITestListener {
    @Override
    public void onStart(ITestContext context) {
        ExtentManager.getInstance();
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.flush();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        result.setAttribute("extentTest", test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTest test = (ExtentTest) result.getAttribute("extentTest");
        if (test != null) {
            test.pass("Test passed");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = (ExtentTest) result.getAttribute("extentTest");
        if (test != null) {
            String message = result.getThrowable() != null ? result.getThrowable().getMessage() : "Test failed";
            try {
                Path screenshot = ScreenshotUtil.capture(result.getMethod().getMethodName());
                test.fail(message, MediaEntityBuilder.createScreenCaptureFromPath(screenshot.toString()).build());
            } catch (Exception e) {
                test.fail(message + " (screenshot capture failed: " + e.getMessage() + ")");
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest test = (ExtentTest) result.getAttribute("extentTest");
        if (test != null) {
            test.skip("Test skipped");
        }
    }
}