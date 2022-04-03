package io.github.mfaisalkhatri.tests;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.github.mfaisalkhatri.driversetup.BaseTest;
import io.github.mfaisalkhatri.pages.FormAuthentication;
import io.github.mfaisalkhatri.pages.MainPage;
import io.github.mfaisalkhatri.pages.SecurePage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class FormAuthenticationTests extends BaseTest {
    private static final String userName = "tomsmith";
    private static final String             password = "SuperSecretPassword!";
    private FormAuthentication formAuthenticationPage;
    private SecurePage         securePage;

    @BeforeClass
    public void testSetup () {
        final String websiteLink = "http://the-internet.herokuapp.com/";
        driver.get().get(websiteLink);
        MainPage mpage = new MainPage(getDriver ());
        mpage.clickLink("Form Authentication");
        formAuthenticationPage = new FormAuthentication(getDriver ());
        securePage = new SecurePage (getDriver ());
    }


    @DataProvider
    public Iterator<Object[]> loginData () {
        List<Object[]> testData = new ArrayList<> ();
        testData.add(new Object[]{" ", password, false});
        testData.add(new Object[]{userName, " ", false});
        testData.add(new Object[]{" ", " ", false});
        testData.add(new Object[]{userName, "invalid", false});
        testData.add(new Object[]{userName, password, true});
        return testData.iterator();
    }


    @Test (dataProvider = "loginData")
    public void loginTests (String userName, String password, boolean isValid) {
        formAuthenticationPage.login(userName, password);

        if (!isValid) {
            Assert.assertTrue(formAuthenticationPage.getFlashMessage().contains(" is invalid!"));
        } else {
            Assert.assertTrue(securePage.getFlashMessage().contains("You logged into a secure area!"));
            Assert.assertEquals(securePage.getHeaderText(), "Secure Area");
            Assert.assertEquals(securePage.getSubHeaderText(), "Welcome to the Secure Area. When you are done click logout below.");
            Assert.assertTrue(securePage.logoutBtn().isDisplayed());
            securePage.logoutBtn().click();
            Assert.assertTrue(formAuthenticationPage.getFlashMessage().contains("You logged out of the secure area!"));
        }
    }
}
