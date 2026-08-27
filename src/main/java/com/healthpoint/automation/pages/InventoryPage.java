package com.healthpoint.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class InventoryPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By pageTitle = By.cssSelector("[data-test='title']");
    private final By sortDropdown = By.cssSelector("[data-test='product-sort-container']");
    private final By itemPrices = By.cssSelector("[data-test='inventory-item-price']");
    private final By menuButton = By.id("react-burger-menu-btn");
    private final By menuContainer = By.className("bm-menu-wrap");
    private final By logoutLink = By.id("logout_sidebar_link");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
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

    public void sortProductsBy(String visibleText) {
        Select sort = new Select(
                wait.until(ExpectedConditions.elementToBeClickable(sortDropdown))
        );
        sort.selectByVisibleText(visibleText);
    }

    public List<Double> getItemPrices() {
        return wait.until(
                        ExpectedConditions.visibilityOfAllElementsLocatedBy(itemPrices)
                )
                .stream()
                .map(element -> element.getText().replace("$", ""))
                .map(Double::parseDouble)
                .toList();
    }

    public LoginPage logout() {
        wait.until(ExpectedConditions.elementToBeClickable(menuButton)).click();

        waitForMenuToBeFullyOpen();

        WebElement logout = wait.until(
                ExpectedConditions.elementToBeClickable(logoutLink)
        );

        new Actions(driver)
                .moveToElement(logout)
                .click()
                .perform();

        return new LoginPage(driver).waitUntilLoaded();
    }

    private void waitForMenuToBeFullyOpen() {
        wait.until(currentDriver -> {
            WebElement menu = currentDriver.findElement(menuContainer);

            if (!"false".equals(menu.getAttribute("aria-hidden"))) {
                return false;
            }

            Number left = (Number) ((JavascriptExecutor) currentDriver)
                    .executeScript(
                            "return arguments[0].getBoundingClientRect().left;",
                            menu
                    );

            return Math.abs(left.doubleValue()) < 1.0;
        });
    }
}
