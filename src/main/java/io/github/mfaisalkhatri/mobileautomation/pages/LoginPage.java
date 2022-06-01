package io.github.mfaisalkhatri.mobileautomation.pages;

import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created By Faisal Khatri on 01-06-2022
 */
public class LoginPage {

    private WebDriver driver;
    public LoginPage (WebDriver driver) {
        this.driver = driver;
    }

    public WebElement userNameField () {
        return driver.findElement(MobileBy.AccessibilityId("test-Username"));
    }

    public WebElement passwordField () {
        return driver.findElement(MobileBy.AccessibilityId("test-Password"));
    }

    public WebElement loginBtn() {
        return driver.findElement(MobileBy.AccessibilityId("test-LOGIN"));
    }

    public void loginIntoApp(String username,String password) {
        userNameField().clear();
        userNameField().click();
        userNameField().sendKeys(username);
        passwordField().clear();
        passwordField().click();
        passwordField().sendKeys(password);
        loginBtn().click();
    }
}