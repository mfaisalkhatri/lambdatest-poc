package io.github.mfaisalkhatri.mobileautomation.driverSetup;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static io.appium.java_client.remote.MobileCapabilityType.*;
import static org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME;

/**
 * Created By Faisal Khatri on 01-06-2022
 */
public class BaseTest {

    public static ThreadLocal<AppiumDriver> driver;

    public BaseTest () {
        driver = new ThreadLocal<AppiumDriver> ();
    }

    @Parameters({ "platformName", "device", "version" })
    @BeforeClass
    public void setupBrowser (Platform platform, String device, String version) throws MalformedURLException {
        String ltUserName = System.getenv ("username");
        String ltAccessToken = System.getenv ("token");
        String gridURL = "@mobile-hub.lambdatest.com/wd/hub";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("build", "Android apk tests");
        capabilities.setCapability("name", "Swag mobile app automation tests");
        capabilities.setCapability("platformName", platform);
        capabilities.setCapability("deviceName", device);
        capabilities.setCapability("platformVersion",version);
        capabilities.setCapability("appiumVersion","1.22.1");
        capabilities.setCapability("appium:app","lt://APP10011121654063254314917");
        capabilities.setCapability("isRealMobile", true);
        capabilities.setCapability("network", true);
        capabilities.setCapability("visual", true);
        capabilities.setCapability("console", true);
        capabilities.setCapability("devicelog", true);

//        DesiredCapabilities capabilities = new DesiredCapabilities ();
//        capabilities.setCapability (PLATFORM_NAME, platform);
//        capabilities.setCapability (PLATFORM_VERSION, version);
//        capabilities.setCapability("appium:deviceName", device);
//        capabilities.setCapability("appium:app","lt://APP10011121654063254314917");
//        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
//        ltOptions.put("build", "Android apk automation");
//        ltOptions.put("name", "Mobile App Automation tests");
//        ltOptions.put("isRealMobile", true)
//        ltOptions.put("network", true);//to enable network logs
//        ltOptions.put("visual", true);//to enable screenshots
//        ltOptions.put("video", true);//to enable video
//        ltOptions.put ("console", true);//to enable console logs
//        ltOptions.put("devicelog",true);
//        capabilities.setCapability("LT:options", ltOptions);
        driver.set (new AppiumDriver(new URL("https://" + ltUserName + ":" + ltAccessToken + gridURL), capabilities));
        setupBrowserTimeouts ();

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

    private void setupBrowserTimeouts () {
        getDriver ()
                .manage ()
                .timeouts ()
                .implicitlyWait(30,TimeUnit.SECONDS);

    }

}