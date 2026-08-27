package com.healthpoint.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class InventoryPage {

    private final WebDriverWait wait;

    private final By pageTitle =
            By.cssSelector("[data-test='title']");

    public InventoryPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isInventoryPageDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(pageTitle)
        ).isDisplayed();
    }

    public String getPageTitle() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(pageTitle)
        ).getText();
    }
}
