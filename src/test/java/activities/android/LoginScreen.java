package activities.android;

import common.AndroidActions;
import io.appium.java_client.android.AndroidDriver;

import static locators.AndroidLocator.*;

public class LoginScreen extends AndroidActions {

    AndroidDriver driver;

    public LoginScreen(AndroidDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void login(String username, String password) {
        tap(btn_signIn, driver);
        tap(btn_dismissChromeStartup, driver);
        getText(lbl_signIn, driver);
        write(txt_username, username, driver);
        tap(btn_next, driver);
        findElement(lbl_passwordScreen, driver);
        dismissKeyboard();
        writePassword(txt_password, password, driver);
        tap(btn_verify, driver);
        findElement(hdr_whatsNew, driver);
    }

    public void logout() {
        tap(ico_profile, driver);
        tap(lnk_logout, driver);
        findElement(btn_signIn, driver);
    }
}
