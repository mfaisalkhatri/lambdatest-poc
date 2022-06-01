package io.github.mfaisalkhatri.webautomation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SecurePage {

    private final WebDriver driver;

    public SecurePage (WebDriver driver) {
        this.driver = driver;
    }

    public WebElement logoutBtn () {
        return driver.findElement (By.linkText ("Logout"));
    }

    public String getFlashMessage () {
        return driver.findElement (By.id ("flash"))
            .getText ();
    }

    public String getHeaderText () {
        return driver.findElement (By.tagName ("h2"))
            .getText ();
    }

    public String getSubHeaderText () {
        return driver.findElement (By.tagName ("h4"))
            .getText ();
    }
}