package activities.android;

import common.AndroidActions;
import io.appium.java_client.android.AndroidDriver;

import static locators.AndroidLocator.*;

public class FooterBar extends AndroidActions {

    AndroidDriver driver;

    public FooterBar(AndroidDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void lookForFooterElements() {
        findElement(ico_home, 5, driver);
        findElement(ico_settings, 5, driver);
        findElement(ico_hamburgerMenu, 5, driver);
        findElement(ico_bell, 5, driver);
    }
}