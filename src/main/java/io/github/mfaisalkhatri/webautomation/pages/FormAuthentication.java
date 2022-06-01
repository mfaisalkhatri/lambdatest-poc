package io.github.mfaisalkhatri.webautomation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FormAuthentication {
    private final WebDriver driver;

    public FormAuthentication(WebDriver driver) {
        this.driver= driver;
    }

    private WebElement userNameField() {
        return driver.findElement(By.id("username"));
    }

    private WebElement passwordField() {
        return driver.findElement(By.id("password"));
    }

    private WebElement loginBtn() {
        return driver.findElement(By.cssSelector("#login > button"));
    }

    public String getFlashMessage() {
        return driver.findElement(By.id("flash")).getText();
    }

    public SecurePage login(String userName, String password) {
        userNameField().click();
        userNameField().clear();
        userNameField().sendKeys(userName);
        passwordField().click();
        passwordField().clear();
        passwordField().sendKeys(password);
        loginBtn().click();
        return new SecurePage(driver);
    }
}