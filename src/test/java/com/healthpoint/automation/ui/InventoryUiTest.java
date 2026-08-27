package com.healthpoint.automation.ui;

import com.healthpoint.automation.base.BaseUiTest;
import com.healthpoint.automation.driver.DriverFactory;
import com.healthpoint.automation.pages.InventoryPage;
import com.healthpoint.automation.pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

@Feature("Inventory UI")
public class InventoryUiTest extends BaseUiTest {

    @Test(groups = {"regression"})
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that products can be sorted by price from low to high")
    public void shouldSortProductsByPriceLowToHigh() {

        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
        InventoryPage inventoryPage = new InventoryPage(DriverFactory.getDriver());

        loginPage.login("standard_user", "secret_sauce");

        inventoryPage.sortProductsBy("Price (low to high)");

        List<Double> actualPrices = inventoryPage.getItemPrices();
        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        expectedPrices.sort(Double::compareTo);

        Assert.assertEquals(
                actualPrices,
                expectedPrices,
                "Inventory prices should be sorted from low to high"
        );
    }

    @Test(groups = {"smoke", "regression"})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that an authenticated user can log out successfully")
    public void shouldLogoutSuccessfully() {

        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
        InventoryPage inventoryPage = new InventoryPage(DriverFactory.getDriver());

        loginPage.login("standard_user", "secret_sauce");

        LoginPage returnedLoginPage = inventoryPage.logout();

        Assert.assertTrue(
                returnedLoginPage.isLoginFormDisplayed(),
                "Login form should be displayed after logout"
        );
    }
}
