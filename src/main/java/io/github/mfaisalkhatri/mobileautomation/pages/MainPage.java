package io.github.mfaisalkhatri.mobileautomation.pages;

import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created By Faisal Khatri on 01-06-2022
 */
public class MainPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public MainPage (final WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver,30);
    }

    public WebElement menuIcon () {
        return wait.until(ExpectedConditions.elementToBeClickable((MobileBy.AccessibilityId("test-Menu"))));
    }

    public WebElement logoutLink () {
        return wait.until(ExpectedConditions.elementToBeClickable((MobileBy.AccessibilityId("test-LOGOUT"))));
    }

    public void logout() {
        menuIcon().click();
        logoutLink().click();
    }
}