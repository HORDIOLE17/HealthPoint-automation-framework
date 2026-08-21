package com.healthpoint.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InventoryPage {

    private final WebDriver driver;

    private final By pageTitle =
            By.cssSelector("[data-test='title']");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isInventoryPageDisplayed() {

        return driver.findElement(pageTitle)
                .isDisplayed();
    }

    public String getPageTitle() {

        return driver.findElement(pageTitle)
                .getText();
    }
}