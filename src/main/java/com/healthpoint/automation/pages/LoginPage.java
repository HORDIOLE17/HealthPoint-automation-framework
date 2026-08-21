package com.healthpoint.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private final WebDriver driver;

    private final By usernameInput =
            By.id("user-name");

    private final By passwordInput =
            By.id("password");

    private final By loginButton =
            By.id("login-button");

    private final By errorMessage =
            By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage enterUsername(String username) {

        driver.findElement(usernameInput)
                .sendKeys(username);

        return this;
    }

    public LoginPage enterPassword(String password) {

        driver.findElement(passwordInput)
                .sendKeys(password);

        return this;
    }

    public void clickLogin() {

        driver.findElement(loginButton)
                .click();
    }

    public String getErrorMessage() {

        return driver.findElement(errorMessage)
                .getText();
    }

    public void login(String username, String password) {

        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }
}