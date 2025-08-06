package io.github.mfaisalkhatri.mobileautomation.driverSetup;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

/**
 * Created By Faisal Khatri on 01-06-2022
 */
public class BaseTest {

    public static ThreadLocal<AppiumDriver> driver;

    public BaseTest () {
        driver = new ThreadLocal<> ();
    }

    public WebDriver getDriver () {
        return driver.get ();
    }

    @Parameters ({ "platformName", "device", "version" })
    @BeforeClass
    public void setupBrowser (Platform platform, String device, String version) throws MalformedURLException {
        String ltUserName = System.getenv ("username");
        String ltAccessToken = System.getenv ("token");
        String gridURL = "@mobile-hub.lambdatest.com/wd/hub";

        DesiredCapabilities capabilities = new DesiredCapabilities ();
        capabilities.setCapability ("build", "Android apk tests");
        capabilities.setCapability ("name", "Swag mobile app automation tests");
        capabilities.setCapability ("platformName", platform);
        capabilities.setCapability ("deviceName", device);
        capabilities.setCapability ("platformVersion", version);
        capabilities.setCapability ("appium:app", "lt://APP10011121654063254314917");
        capabilities.setCapability ("isRealMobile", true);
        capabilities.setCapability ("network", true);
        capabilities.setCapability ("visual", true);
        capabilities.setCapability ("console", true);
        capabilities.setCapability ("devicelog", true);

        driver.set (new AppiumDriver (new URL ("https://" + ltUserName + ":" + ltAccessToken + gridURL), capabilities));
        setupBrowserTimeouts ();

    }

    @AfterClass
    public void tearDown () {
        if (driver != null) {
            getDriver ().quit ();
            driver.remove ();
        }
    }

    private void setupBrowserTimeouts () {
        getDriver ().manage ()
            .timeouts ()
            .implicitlyWait (30, TimeUnit.SECONDS);
    }
}