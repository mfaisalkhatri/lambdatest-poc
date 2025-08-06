package io.github.mfaisalkhatri.mobileautomation.pages;

import java.time.Duration;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created By Faisal Khatri on 01-06-2022
 */
public class MainPage {
    private final WebDriver     driver;
    private final WebDriverWait wait;

    public MainPage (final WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait (driver, Duration.ofSeconds (30));
    }

    public void logout () {
        menuIcon ().click ();
        logoutLink ().click ();
    }

    public WebElement logoutLink () {
        return wait.until (ExpectedConditions.elementToBeClickable (AppiumBy.accessibilityId ("test-LOGOUT")));
    }

    public WebElement menuIcon () {
        return wait.until (ExpectedConditions.elementToBeClickable (AppiumBy.accessibilityId ("test-Menu")));
    }
}