package tests.android.scenarios;

import activities.android.LoginScreen;
import org.testng.annotations.Test;
import setup.AndroidBase;

import static locators.AndroidLocator.hdr_welcomeCosmo;

class INTL_0002 extends AndroidBase {

    LoginScreen loginAlias;

    @Test(groups = {"regression", "sanity"})
    public void intl_0002() {
        loginAlias = new LoginScreen(driver);

        // Get Data
        String username = getJsonData("credentials", "US", "Agent", "Test", "John Jay Kinder", "username");
        String password = getJsonData("credentials", "US", "Agent", "Test", "John Jay Kinder", "password");

        try {

            loginAlias.login(username, password);
            testStepInfo("THIS IS AN INFO");
            testStepWarn("THIS IS A WARNING");
            assertElementText(hdr_welcomeCosmo, "Welcome Bosco", driver);
            loginAlias.logout();

        } catch (Exception e) {
            logError("[" + e.getClass().getSimpleName() + "] | Message --> " + e.getMessage());
            testFailed(e.getClass().getName() + " occurred. Check log file");
        }
    }
}
