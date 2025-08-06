package io.github.mfaisalkhatri.mobileautomation.driverSetup;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

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

    public static        ThreadLocal<AppiumDriver> driver;
    private static final String                    LT_ACCESS_KEY = System.getenv ("LT_ACCESS_KEY");
    private static final String                    LT_USERNAME   = System.getenv ("LT_USERNAME");
    private static final String                    URL           = "@mobile-hub.lambdatest.com/wd/hub";

    public BaseTest () {
        driver = new ThreadLocal<> ();
    }

    public WebDriver getDriver () {
        return driver.get ();
    }

    @Parameters ({ "platformName", "device", "version" })
    @BeforeClass
    public void setupBrowser (Platform platform, String device, String version) throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities ();
        final HashMap<String, Object> ltOptions = new HashMap<> ();
        ltOptions.put ("build", "Android apk tests");
        ltOptions.put ("name", "Swag mobile app automation tests");
        ltOptions.put ("platformName", platform);
        ltOptions.put ("deviceName", device);
        ltOptions.put ("platformVersion", version);
        ltOptions.put ("appium:app", "lt://APP10160341071754500059009313");
        ltOptions.put ("isRealMobile", true);
        ltOptions.put ("network", true);
        ltOptions.put ("visual", true);
        ltOptions.put ("console", true);
        ltOptions.put ("devicelog", true);
        capabilities.setCapability ("lt:options", ltOptions);
        driver.set (new AppiumDriver (new URL ("https://" + LT_USERNAME + ":" + LT_ACCESS_KEY + URL), capabilities));
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
            .implicitlyWait (Duration.ofSeconds (30));
    }
}