
package com.healthpoint.automation.listeners;

import com.healthpoint.automation.driver.DriverFactory;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {

        try {
            WebDriver driver = DriverFactory.getDriver();

            byte[] screenshot =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.BYTES);

            Allure.addAttachment(
                    "Screenshot on failure",
                    new ByteArrayInputStream(screenshot)
            );

        } catch (Exception e) {
            System.out.println(
                    "Unable to capture screenshot: " + e.getMessage()
            );
        }
    }
}