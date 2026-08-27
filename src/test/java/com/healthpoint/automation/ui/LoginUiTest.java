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

@Feature("Authentication UI")
public class LoginUiTest extends BaseUiTest {

    @Test(groups = {"smoke", "regression"})
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verifies that a valid user can log in successfully")
    public void shouldLoginWithValidCredentials() {

        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
        InventoryPage inventoryPage = new InventoryPage(DriverFactory.getDriver());

        loginPage.login("standard_user", "secret_sauce");

        Assert.assertTrue(inventoryPage.isInventoryPageDisplayed());
        Assert.assertEquals(inventoryPage.getPageTitle(), "Products");
    }

    @Test(groups = {"regression"})
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that login fails when invalid credentials are provided")
    public void shouldRejectInvalidCredentials() {

        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());

        loginPage.login("invalid_user", "invalid_password");

        Assert.assertTrue(
                loginPage.getErrorMessage().contains("Username and password do not match")
        );
    }

    @Test(groups = {"regression"})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that a locked-out user cannot authenticate")
    public void shouldRejectLockedOutUser() {

        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());

        loginPage.login("locked_out_user", "secret_sauce");

        Assert.assertTrue(
                loginPage.getErrorMessage().contains("Sorry, this user has been locked out")
        );
    }
}
