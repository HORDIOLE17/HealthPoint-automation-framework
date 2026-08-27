package com.healthpoint.automation.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static void initializeDriver() {

        ChromeOptions options = new ChromeOptions();

        String remoteUrl = System.getenv("SELENIUM_REMOTE_URL");

        if (remoteUrl != null && !remoteUrl.isBlank()) {

            try {
                driver.set(
                        new RemoteWebDriver(
                                new URL(remoteUrl),
                                options
                        )
                );
            } catch (MalformedURLException e) {
                throw new RuntimeException(
                        "Invalid SELENIUM_REMOTE_URL: " + remoteUrl,
                        e
                );
            }

        } else {

            WebDriverManager.chromedriver().setup();

            if (System.getenv("CI") != null) {
                options.addArguments("--headless=new");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
            }

            driver.set(new ChromeDriver(options));
        }

        getDriver().manage().window().maximize();
    }

    public static WebDriver getDriver() {

        if (driver.get() == null) {
            throw new IllegalStateException(
                    "WebDriver has not been initialized"
            );
        }

        return driver.get();
    }

    public static void quitDriver() {

        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
