package locators;

import annotations.AndroidFindByWithAlias;
import common.AppiumUtils;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * <b>Class containing web elements (locators) with custom annotations (alias)</b>
 */
public class AndroidLocator extends AppiumUtils {

    AndroidDriver driver;

    public AndroidLocator(AndroidDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    // xpath locators
    // Login Screen
    @AndroidFindByWithAlias(androidFindBy = @AndroidFindBy(xpath = "//android.widget.Button[@resource-id='com.android.chrome:id/signin_fre_dismiss_button']"), alias = "Dismiss button in Chrome Welcome page")
    public static WebElement btn_dismissChromeStartup;
    @AndroidFindByWithAlias(androidFindBy = @AndroidFindBy(xpath = "//android.widget.TextView[@text='Sign In']"), alias = "Sign In screen")
    public static WebElement lbl_signIn;
    @AndroidFindByWithAlias(androidFindBy = @AndroidFindBy(xpath = "//android.widget.EditText[@resource-id='input28']"), alias = "Username textfield")
    public static WebElement txt_username;
    @AndroidFindByWithAlias(androidFindBy = @AndroidFindBy(xpath = "//android.widget.Button[@text='Next']"), alias = "Next button in login")
    public static WebElement btn_next;
    @AndroidFindByWithAlias(androidFindBy = @AndroidFindBy(xpath = "//android.widget.TextView[@text='Verify with your password']"), alias = "Password screen")
    public static WebElement lbl_passwordScreen;
    @AndroidFindByWithAlias(androidFindBy = @AndroidFindBy(xpath = "//android.widget.EditText[@resource-id='input59']"), alias = "Password textfield")
    public static WebElement txt_password;
    @AndroidFindByWithAlias(androidFindBy = @AndroidFindBy(xpath = "//android.widget.Button[@text='Verify']"), alias = "Verify button in login")
    public static WebElement btn_verify;

    // Landing screen
    @AndroidFindByWithAlias(androidFindBy = @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc, 'Team Dashboard')]"), alias = "Slider: Team Dashboard")
    public static WebElement slider_teamDash;
    @AndroidFindByWithAlias(androidFindBy = @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc, 'eXp Quick Links')]"), alias = "Slider: eXp Quick Links")
    public static WebElement slider_quickLinks;
    @AndroidFindByWithAlias(androidFindBy = @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc, \"What's new with My eXp\")]"), alias = "Slider: What's new with My eXp")
    public static WebElement slider_whatsNew;

    @AndroidFindByWithAlias(androidFindBy = @AndroidFindBy(xpath = "//android.widget.ImageView[contains(@content-desc, 'Capping')]"), alias = "Achievements: Capping container")
    public static WebElement con_capping;
    @AndroidFindByWithAlias(androidFindBy = @AndroidFindBy(xpath = "//android.widget.ImageView[contains(@content-desc, 'ICON - Production')]"), alias = "Achievements: ICON-Prod container")
    public static WebElement con_iconProd;
    @AndroidFindByWithAlias(androidFindBy = @AndroidFindBy(xpath = "//android.widget.ImageView[contains(@content-desc, 'ICON - Cultural')]"), alias = "Achievements: ICON - Cultural container")
    public static WebElement con_iconCultural;

    // Bottom navigation bar
    @AndroidFindByWithAlias(androidFindBy = @AndroidFindBy(xpath = "//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View[1]"), alias = "Home icon in footer")
    public static WebElement ico_home;
    @AndroidFindByWithAlias(androidFindBy = @AndroidFindBy(xpath = "//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View[2]"), alias = "Settings icon in footer")
    public static WebElement ico_settings;
    @AndroidFindByWithAlias(androidFindBy = @AndroidFindBy(xpath = "//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View[3]"), alias = "Hamburger menu icon in footer")
    public static WebElement ico_hamburgerMenu;
    @AndroidFindByWithAlias(androidFindBy = @AndroidFindBy(xpath = "//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View[4]"), alias = "Notification icon in footer")
    public static WebElement ico_bell;
    @AndroidFindByWithAlias(androidFindBy = @AndroidFindBy(xpath = "//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View[5]"), alias = "My Profile icon in footer")
    public static WebElement ico_profile;

    // Accessibility ID locators
    // Login Screen
    @AndroidFindByWithAlias(androidFindBy = @AndroidFindBy(accessibility = "SIGN IN"), alias = "Sign In button")
    public static WebElement btn_signIn;

    // Landing screen
    @AndroidFindByWithAlias(androidFindBy = @AndroidFindBy(accessibility = "Welcome Brent"), alias = "Login: Welcome User header")
    public static WebElement hdr_welcomeBrent;
    @AndroidFindByWithAlias(androidFindBy = @AndroidFindBy(accessibility = "Welcome Cosmo"), alias = "Login: Welcome User header")
    public static WebElement hdr_welcomeCosmo;

    @AndroidFindByWithAlias(androidFindBy = @AndroidFindBy(accessibility = "What's New"), alias = "What's New header")
    public static WebElement hdr_whatsNew;
    @AndroidFindByWithAlias(androidFindBy = @AndroidFindBy(accessibility = "My Achievements"), alias = "My Achievements header")
    public static WebElement hdr_myAchievements;
    @AndroidFindByWithAlias(androidFindBy = @AndroidFindBy(accessibility = "My Upline Partners"), alias = "My Upline Partners header")
    public static WebElement hdr_myUplines;
    @AndroidFindByWithAlias(androidFindBy = @AndroidFindBy(accessibility = "My Influencer Status"), alias = "My Influencer Status header")
    public static WebElement hdr_myInfluencerStatus;

    // Logout Screen
    @AndroidFindByWithAlias(androidFindBy = @AndroidFindBy(accessibility = "Logout"), alias = "Logout link")
    public static WebElement lnk_logout;

}
