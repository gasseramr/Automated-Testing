package com.example.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtil {
    private static final DateTimeFormatter TS = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    public static Path capture(String name) {
        WebDriver driver = DriverFactory.getDriver();
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Path dir = Paths.get("target", "screenshots");
        Path dest = dir.resolve(TS.format(LocalDateTime.now()) + "_" + sanitize(name) + ".png");
        try {
            Files.createDirectories(dir);
            Files.copy(src.toPath(), dest);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save screenshot: " + e.getMessage(), e);
        }
        return dest;
    }

    private static String sanitize(String s) {
        return s.replaceAll("[^a-zA-Z0-9-_]", "_");
    }
}