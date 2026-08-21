package com.healthpoint.automation.base;

import com.healthpoint.automation.config.ConfigReader;
import com.healthpoint.automation.driver.DriverFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseUiTest {

    @BeforeMethod
    public void setUpUi() {

        DriverFactory.initializeDriver();

        DriverFactory.getDriver()
                .get(ConfigReader.get("ui.url"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownUi() {

        DriverFactory.quitDriver();
    }
}