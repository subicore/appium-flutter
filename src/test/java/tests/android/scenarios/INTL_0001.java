package tests.android.scenarios;

import activities.android.FooterBar;
import activities.android.LandingScreen;
import activities.android.LoginScreen;
import org.testng.annotations.Test;
import setup.AndroidBase;

class INTL_0001 extends AndroidBase {

    LoginScreen loginScreen;
    LandingScreen landingScreen;
    FooterBar footerBar;

    @Test(groups = {"sanity"})
    public void intl_0001() {
        loginScreen = new LoginScreen(driver);
        landingScreen = new LandingScreen(driver);
        footerBar = new FooterBar(driver);

        // Get Data
        String username = getJsonData("credentials", "US", "Agent", "Test", "John Jay Kinder", "username"); // cosmo
        String password = getJsonData("credentials", "US", "Agent", "Test", "John Jay Kinder", "password");

        try {

            loginScreen.login(username, password);
            landingScreen.swipeSliderActions();
            landingScreen.lookForAllHeaders();
            footerBar.lookForFooterElements();
            landingScreen.validateCappingContainerContent();
            loginScreen.logout();

        } catch (Exception e) {
            logError("[" + e.getClass().getSimpleName() + "] | Message --> " + e.getMessage());
            testFailed(e.getClass().getName() + " occurred. Check log file");
        }
    }
}
