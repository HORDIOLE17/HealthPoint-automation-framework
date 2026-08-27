package com.healthpoint.automation.driver;

import com.healthpoint.automation.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static void initializeDriver() {

        String browser = getBrowser();
        String remoteUrl = System.getenv("SELENIUM_REMOTE_URL");

        if (remoteUrl != null && !remoteUrl.isBlank()) {
            initializeRemoteDriver(browser, remoteUrl);
        } else {
            initializeLocalDriver(browser);
        }

        configureWindow();
    }

    private static String getBrowser() {

        String browser = System.getenv("BROWSER");

        if (browser == null || browser.isBlank()) {
            browser = ConfigReader.get("browser");
        }

        return browser.toLowerCase();
    }

    private static void initializeLocalDriver(String browser) {

        switch (browser) {

            case "chrome" -> {
                WebDriverManager.chromedriver().setup();

                ChromeOptions options = new ChromeOptions();

                if (isCiEnvironment()) {
                    options.addArguments("--headless=new");
                    options.addArguments("--no-sandbox");
                    options.addArguments("--disable-dev-shm-usage");
                }

                driver.set(new ChromeDriver(options));
            }

            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();

                FirefoxOptions options = new FirefoxOptions();

                if (isCiEnvironment()) {
                    options.addArguments("-headless");
                }

                driver.set(new FirefoxDriver(options));
            }

            default -> throw new IllegalArgumentException(
                    "Unsupported browser: " + browser
            );
        }
    }

    private static void initializeRemoteDriver(
            String browser,
            String remoteUrl
    ) {

        try {

            switch (browser) {

                case "chrome" -> driver.set(
                        new RemoteWebDriver(
                                new URL(remoteUrl),
                                new ChromeOptions()
                        )
                );

                case "firefox" -> driver.set(
                        new RemoteWebDriver(
                                new URL(remoteUrl),
                                new FirefoxOptions()
                        )
                );

                default -> throw new IllegalArgumentException(
                        "Unsupported browser: " + browser
                );
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(
                    "Invalid SELENIUM_REMOTE_URL: " + remoteUrl,
                    e
            );
        }
    }

    private static boolean isCiEnvironment() {
        return System.getenv("CI") != null;
    }

    private static void configureWindow() {

        if (isCiEnvironment()) {
            getDriver().manage()
                    .window()
                    .setSize(new Dimension(1920, 1080));
        } else {
            getDriver().manage()
                    .window()
                    .maximize();
        }
    }

    public static WebDriver getDriver() {

        if (driver.get() == null) {
            throw new IllegalStateException(
                    "WebDriver has not been initialized"
            );
        }

        return driver.get();
    }

    public static boolean hasDriver() {
        return driver.get() != null;
    }

    public static void quitDriver() {

        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
