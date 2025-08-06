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

    public static        ThreadLocal<WebDriver> driver;
    private static final String                 LT_ACCESS_KEY = System.getenv ("LT_ACCESS_KEY");
    private static final String                 LT_USERNAME   = System.getenv ("LT_USERNAME");
    private static final String                 URL           = "@hub.lambdatest.com/wd/hub";

    public BaseTest () {
        driver = new ThreadLocal<WebDriver> ();
    }

    public WebDriver getDriver () {
        return driver.get ();
    }

    @Parameters ({ "platform", "browser" })
    @BeforeClass
    public void setupBrowser (Platform platform, String browser) throws MalformedURLException {

        DesiredCapabilities capability = new DesiredCapabilities ();
        capability.setCapability ("platform", platform);
        capability.setBrowserName (browser);
        capability.setCapability ("build", "Cross Browser Tests");
        capability.setCapability ("name", "Parallel Testing on Chrome and Firefox");
        capability.setCapability ("network", true);
        capability.setCapability ("visual", true);
        capability.setCapability ("video", true);
        capability.setCapability ("console", true);

        driver.set (new RemoteWebDriver (new URL ("https://" + LT_USERNAME + ":" + LT_ACCESS_KEY + URL), capability));
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
            .implicitlyWait (Duration.ofSeconds (60));

        getDriver ().manage ()
            .timeouts ()
            .pageLoadTimeout (Duration.ofSeconds (60));
    }
}