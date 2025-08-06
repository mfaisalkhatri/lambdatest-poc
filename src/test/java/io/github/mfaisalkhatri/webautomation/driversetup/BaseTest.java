package io.github.mfaisalkhatri.webautomation.driversetup;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseTest {

    public static        ThreadLocal<RemoteWebDriver> driver;
    private static final String                       LT_ACCESS_KEY = System.getenv ("LT_ACCESS_KEY");
    private static final String                       LT_USERNAME   = System.getenv ("LT_USERNAME");
    private static final String                       URL           = "@hub.lambdatest.com/wd/hub";

    public BaseTest () {
        driver = new ThreadLocal<> ();
    }

    public WebDriver getDriver () {
        return driver.get ();
    }

    @Parameters ({ "browser", "platform" })
    @BeforeClass
    public void setupBrowser (String browser, String platform) throws MalformedURLException {
        MutableCapabilities options;

        switch (browser.toLowerCase ()) {
            case "chrome":
                final var chromePrefs = new HashMap<String, Object> ();
                chromePrefs.put ("profile.password_manager_leak_detection", false);
                ChromeOptions chromeOptions = new ChromeOptions ();
                chromeOptions.setPlatformName (platform);
                chromeOptions.setBrowserVersion ("latest");
                chromeOptions.setExperimentalOption ("prefs", chromePrefs);
                chromeOptions.setCapability ("LT:Options", getLTOptions (browser));
                options = chromeOptions;
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions ();
                firefoxOptions.setPlatformName (platform);
                firefoxOptions.setBrowserVersion ("latest");
                firefoxOptions.setCapability ("LT:Options", getLTOptions (browser));
                options = firefoxOptions;
                break;

            default:
                throw new IllegalArgumentException ("Unsupported browser: " + browser);
        }

        driver.set (new RemoteWebDriver (new URL ("https://" + LT_USERNAME + ":" + LT_ACCESS_KEY + URL), options));

        setupBrowserTimeouts ();
    }

    @AfterClass
    public void tearDown () {
        if (driver != null) {
            getDriver ().quit ();
            driver.remove ();
        }
    }

    private Map<String, Object> getLTOptions (String browserName) {
        Map<String, Object> ltOptions = new HashMap<> ();
        ltOptions.put ("username", LT_USERNAME);
        ltOptions.put ("accessKey", LT_ACCESS_KEY);
        ltOptions.put ("project", "Cross Browser Tests Demo");
        ltOptions.put ("build", "Cross Browser verification");
        ltOptions.put ("name", "Parallel Test - " + browserName);
        ltOptions.put ("console", true);
        ltOptions.put ("network", true);
        ltOptions.put ("visual", true);
        ltOptions.put ("video", true);
        return ltOptions;
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