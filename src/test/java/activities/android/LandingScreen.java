package activities.android;

import common.AndroidActions;
import io.appium.java_client.android.AndroidDriver;
import tests.android.testData.Data;

import static locators.AndroidLocator.*;

public class LandingScreen extends AndroidActions {

    AndroidDriver driver;

    public LandingScreen(AndroidDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void lookForAllHeaders() {
        findElement(hdr_whatsNew, 5, driver);
        findElement(hdr_myAchievements, 5, driver);
        scrollToFindElement(hdr_myUplines, "down");
        scrollToFindElement(hdr_myInfluencerStatus, "down");
    }

    public void validateCappingContainerContent() {
        scrollToFindElement(con_capping, "up");

        String cappingDetails = getAttribute(con_capping, driver);
        assertMultiLineString(cappingDetails, Data.Cosmo.CAPPING_TEXT);

        String iconProdDetails = getAttribute(con_iconProd, driver);
        assertMultiLineString(iconProdDetails, Data.Cosmo.ICON_PROD_TEXT);

        String iconCulturalDetails = getAttribute(con_iconCultural, driver);
        assertMultiLineString(iconCulturalDetails, Data.Cosmo.ICON_CULTURAL_TEXT);
    }


    public void swipeSliderActions() {
        swipe(slider_teamDash, "LEFT");
        findElement(slider_quickLinks, 5, driver);
        swipe(slider_quickLinks, "LEFT");
        findElement(slider_whatsNew, 5, driver);
    }
}
