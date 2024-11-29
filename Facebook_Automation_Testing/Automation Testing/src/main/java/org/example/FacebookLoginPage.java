package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FacebookLoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators for Login
    private By emailField = By.id("email");
    private By passwordField = By.id("pass");
    private By loginButton = By.name("login");

    public FacebookLoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofMillis(500)); // Initialize WebDriverWait
    }

    public void enterEmail(String email) {
        waitForElement(emailField).sendKeys(email);
        sleepForTwoSeconds();
    }

    public void enterPassword(String password) {
        waitForElement(passwordField).sendKeys(password);
        sleepForTwoSeconds();
    }

    public void clickLoginButton() {
        waitForElement(loginButton).click();
        sleepForTwoSeconds();
    }

    private WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void sleepForTwoSeconds() {
        try {
            Thread.sleep(2000); // Wait for 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

