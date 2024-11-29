package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FacebookSignUpPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators for Sign Up
    private By firstNameField = By.name("firstname");
    private By lastNameField = By.name("lastname");
    private By mobileEmailField = By.name("reg_email__");
    private By passwordField = By.id("password_step_input");
    private By dayDropdown = By.id("day");
    private By monthDropdown = By.id("month");
    private By yearDropdown = By.id("year");
    private By genderRadio = By.xpath("//input[@name='sex' and @value='1']");
    private By signUpButton = By.name("websubmit");

    public FacebookSignUpPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofMillis(500)); // Initialize WebDriverWait
    }

    public void enterFirstName(String firstName) {
        waitForElement(firstNameField).sendKeys(firstName);
        sleepForTwoSeconds();
    }

    public void enterLastName(String lastName) {
        waitForElement(lastNameField).sendKeys(lastName);
        sleepForTwoSeconds();
    }

    public void enterMobileEmail(String mobileEmail) {
        waitForElement(mobileEmailField).sendKeys(mobileEmail);
        sleepForTwoSeconds();
    }

    public void enterPassword(String password) {
        waitForElement(passwordField).sendKeys(password);
        sleepForTwoSeconds();
    }

    public void selectDay(String day) {
        new Select(waitForElement(dayDropdown)).selectByVisibleText(day);
        sleepForTwoSeconds();
    }

    public void selectMonth(String month) {
        new Select(waitForElement(monthDropdown)).selectByVisibleText(month);
        sleepForTwoSeconds();
    }

    public void selectYear(String year) {
        new Select(waitForElement(yearDropdown)).selectByVisibleText(year);
        sleepForTwoSeconds();
    }

    public void selectGender() {
        waitForElement(genderRadio).click();
        sleepForTwoSeconds();
    }

    public void clickSignUpButton() {
        waitForElement(signUpButton).click();
        sleepForTwoSeconds();
    }

    public boolean isSignUpButtonEnabled() {
        return waitForElement(signUpButton).isEnabled();
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

