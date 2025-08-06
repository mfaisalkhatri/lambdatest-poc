package io.github.mfaisalkhatri.webautomation.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.github.mfaisalkhatri.webautomation.driversetup.BaseTest;
import io.github.mfaisalkhatri.webautomation.pages.FormAuthentication;
import io.github.mfaisalkhatri.webautomation.pages.MainPage;
import io.github.mfaisalkhatri.webautomation.pages.SecurePage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class FormAuthenticationTests extends BaseTest {
    private static final String             PASSWORD  = "SuperSecretPassword!";
    private static final String             USER_NAME = "tomsmith";
    private              FormAuthentication formAuthenticationPage;
    private              SecurePage         securePage;

    @DataProvider
    public Iterator<Object[]> loginData () {
        List<Object[]> testData = new ArrayList<> ();
        testData.add (new Object[] { " ", PASSWORD, false });
        testData.add (new Object[] { USER_NAME, " ", false });
        testData.add (new Object[] { " ", " ", false });
        testData.add (new Object[] { USER_NAME, "invalid", false });
        testData.add (new Object[] { USER_NAME, PASSWORD, true });
        return testData.iterator ();
    }

    @Test (dataProvider = "loginData")
    public void loginTests (String userName, String password, boolean isValid) {
        formAuthenticationPage.login (userName, password);

        if (!isValid) {
            assertTrue (formAuthenticationPage.getFlashMessage ()
                .contains (" is invalid!"));
        } else {
            assertTrue (securePage.getFlashMessage ()
                .contains ("You logged into a secure area!"));
            assertEquals (securePage.getHeaderText (), "Secure Area");
            assertEquals (securePage.getSubHeaderText (),
                "Welcome to the Secure Area. When you are done click logout below.");
            assertTrue (securePage.logoutBtn ()
                .isDisplayed ());
            securePage.logoutBtn ()
                .click ();
            assertTrue (formAuthenticationPage.getFlashMessage ()
                .contains ("You logged out of the secure area!"));
        }
    }

    @BeforeClass
    public void testSetup () {
        final String websiteLink = "http://the-internet.herokuapp.com/";
        driver.get ()
            .get (websiteLink);
        MainPage mainPage = new MainPage (getDriver ());
        mainPage.clickLink ("Form Authentication");
        formAuthenticationPage = new FormAuthentication (getDriver ());
        securePage = new SecurePage (getDriver ());
    }
}