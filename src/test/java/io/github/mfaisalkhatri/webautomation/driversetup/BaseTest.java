package io.github.mfaisalkhatri.webautomation.driversetup;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseTest {

    public static ThreadLocal<WebDriver> driver;

    public BaseTest () {
        driver = new ThreadLocal<WebDriver> ();
    }

    @Parameters ({ "platform", "browser" })
    @BeforeClass
    public void setupBrowser (Platform platform, String browser) throws MalformedURLException {
        String ltUserName = System.getenv ("username");
        String ltAccessToken = System.getenv ("token");
        String URL = "@hub.lambdatest.com/wd/hub";

        DesiredCapabilities capability = new DesiredCapabilities ();
        capability.setCapability ("platform", platform);
        capability.setBrowserName (browser);
        capability.setCapability ("build", "Cross Browser Tests");
        capability.setCapability ("name", "Parallel Testing on Chrome and Firefox");
        capability.setCapability ("network", true);//to enable network logs
        capability.setCapability ("visual", true);//to enable screenshots
        capability.setCapability ("video", true);//to enable video
        capability.setCapability ("console", true);//to enable console logs

        driver.set (new RemoteWebDriver (new URL ("https://" + ltUserName + ":" + ltAccessToken + URL), capability));
     //  setupBrowserTimeouts ();

    }
    public WebDriver getDriver () {
        return  driver.get ();
    }

    @AfterClass
    public void tearDown () {
        if (driver != null) {
           getDriver ().quit ();
           driver.remove ();
        }

    }

//    private void setupBrowserTimeouts () {
//
//        getDriver ()
//            .manage ()
//            .timeouts ()
//            .implicitlyWait (Duration.ofSeconds (60));
//
//        getDriver ()
//            .manage ()
//            .timeouts ()
//            .pageLoadTimeout (Duration.ofSeconds (60));
//    }
}