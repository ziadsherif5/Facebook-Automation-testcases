import org.example.FacebookLoginPage;
import org.example.FacebookSignUpPage;
import org.example.NotificationPopupHandler; // Import the new class
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.testng.AssertJUnit.assertTrue;

public class FacebookTestSuite {
    private WebDriver driver;
    private FacebookSignUpPage signUpPage;
    private FacebookLoginPage loginPage;
    private NotificationPopupHandler notificationPopupHandler;

    @BeforeTest
    public void setup() {
        // Create a map to store preferences to switch off browser notifications
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);  // 1: Allow, 2: Block

        // Create an instance of ChromeOptions
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);

        // Initialize ChromeDriver with options
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        driver.manage().window().maximize();

        // Initialize NotificationPopupHandler
        notificationPopupHandler = new NotificationPopupHandler(driver);
    }

    @Test(priority = 1)
    public void testSignUp() {
        driver.get("https://www.facebook.com/r.php");
        notificationPopupHandler.handleNotificationPopup();

        signUpPage = new FacebookSignUpPage(driver);
        signUpPage.enterFirstName("Enas");
        signUpPage.enterLastName("Gamal");
        signUpPage.selectDay("1");
        signUpPage.selectMonth("Jun");
        signUpPage.selectYear("2003");
        signUpPage.selectGender();
        signUpPage.enterMobileEmail("01018127745");
        signUpPage.enterPassword("123456789");
        signUpPage.clickSignUpButton();

        // Optionally verify if registration was successful
    }

    @Test(priority = 2)
    public void testSignUpButtonEnabled() {
        driver.get("https://www.facebook.com/r.php");
        notificationPopupHandler.handleNotificationPopup();

        signUpPage = new FacebookSignUpPage(driver);
        Assert.assertTrue(signUpPage.isSignUpButtonEnabled(), "Sign Up button should be enabled.");
    }

    @Test(priority = 3)
    public void testValidLogin() {
        driver.get("https://www.facebook.com/");
        notificationPopupHandler.handleNotificationPopup();

        loginPage = new FacebookLoginPage(driver);
        loginPage.enterEmail("----------");
        loginPage.enterPassword("-----------");
        loginPage.clickLoginButton();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertNotEquals(currentUrl, "https://www.facebook.com/login.php", "Login failed, still on login page.");
    }

    @Test(priority = 4)
    public void logout() {
        driver.get("https://www.facebook.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {
            WebElement profileIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@aria-label='Your profile']")));
            profileIcon.click();
            WebElement logoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Log Out']")));
            logoutButton.click();
            wait.until(ExpectedConditions.urlContains("https://www.facebook.com/login"));
        } catch (TimeoutException e) {
            System.out.println("Profile icon was not found: " + e.getMessage());
        }
    }

    @Test(priority = 5)
    public void testInvalidLogin() {
        logout(); // Ensure user is logged out before testing invalid login
        driver.get("https://www.facebook.com/");
        notificationPopupHandler.handleNotificationPopup();

        loginPage = new FacebookLoginPage(driver);
        loginPage.enterEmail("invalid-email@example.com");
        loginPage.enterPassword("wrong-password");
        loginPage.clickLoginButton();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://www.facebook.com/login.php", "Login should fail, but redirected to a different page.");
    }

    @Test(priority = 6)
    public void testLoginAgain() {
        driver.get("https://www.facebook.com/");
        notificationPopupHandler.handleNotificationPopup();

        loginPage = new FacebookLoginPage(driver);
        loginPage.enterEmail("------------");
        loginPage.enterPassword("--------");
        loginPage.clickLoginButton();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertNotEquals(currentUrl, "https://www.facebook.com/login.php", "Login failed, still on login page.");
    }


    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}





