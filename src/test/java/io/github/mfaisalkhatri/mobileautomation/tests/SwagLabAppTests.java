package io.github.mfaisalkhatri.mobileautomation.tests;

import static org.testng.Assert.assertTrue;

import io.github.mfaisalkhatri.mobileautomation.driverSetup.BaseTest;
import io.github.mfaisalkhatri.mobileautomation.pages.LoginPage;
import io.github.mfaisalkhatri.mobileautomation.pages.MainPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created By Faisal Khatri on 01-06-2022
 */
public class SwagLabAppTests extends BaseTest {

    private static final String    PASSWORD  = "secret_sauce";
    private static final String    USER_NAME = "standard_user";
    private              LoginPage loginPage;
    private              MainPage  mainPage;

    @Test
    public void loginTests () {
        loginPage.loginIntoApp (USER_NAME, PASSWORD);
        assertTrue (mainPage.menuIcon ()
            .isDisplayed ());
        mainPage.logout ();
    }

    @BeforeClass
    public void setupTest () {
        loginPage = new LoginPage (getDriver ());
        mainPage = new MainPage (getDriver ());
    }
}