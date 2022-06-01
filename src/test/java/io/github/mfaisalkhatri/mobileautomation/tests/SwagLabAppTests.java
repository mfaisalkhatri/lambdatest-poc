package io.github.mfaisalkhatri.mobileautomation.tests;

import io.github.mfaisalkhatri.mobileautomation.driverSetup.BaseTest;
import io.github.mfaisalkhatri.mobileautomation.pages.LoginPage;
import io.github.mfaisalkhatri.mobileautomation.pages.MainPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
/**
 * Created By Faisal Khatri on 01-06-2022
 */
public class SwagLabAppTests extends BaseTest {

    private static final String usrName = "standard_user";
    private static final String pass = "secret_sauce";
    private LoginPage loginPage;
    private MainPage mainPage;

    @BeforeClass
    public void setupTest() {
        loginPage = new LoginPage(getDriver());
        mainPage = new MainPage(getDriver());
    }

    @Test
    public void loginTests () {
        loginPage.loginIntoApp(usrName,pass);
        assertTrue(mainPage.menuIcon().isDisplayed());
        mainPage.logout();
    }
}