package com.healthpoint.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

        wait.until(
                ExpectedConditions.attributeToBe(
                        menuContainer,
                        "aria-hidden",
                        "false"
                )
        );

        WebElement stableLogoutLink = waitForElementToStopMoving(logoutLink);
        stableLogoutLink.click();

        wait.until(
                ExpectedConditions.not(
                        ExpectedConditions.urlContains("inventory")
                )
        );

        return new LoginPage(driver).waitUntilLoaded();
    }

    private WebElement waitForElementToStopMoving(By locator) {
        Point[] previousPosition = {null};
        int[] stableChecks = {0};

        return wait.until(currentDriver -> {
            WebElement element = currentDriver.findElement(locator);

            if (!element.isDisplayed()) {
                previousPosition[0] = null;
                stableChecks[0] = 0;
                return null;
            }

            Point currentPosition = element.getLocation();

            if (currentPosition.equals(previousPosition[0])) {
                stableChecks[0]++;
            } else {
                previousPosition[0] = currentPosition;
                stableChecks[0] = 0;
            }

            return stableChecks[0] >= 2 ? element : null;
        });
    }
}
