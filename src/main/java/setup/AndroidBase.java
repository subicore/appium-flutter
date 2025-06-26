package setup;

import common.AppiumUtils;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import locators.AndroidLocator;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import utilities.AnnotationProcessor;

import java.net.URI;
import java.time.Duration;

import static listeners.TestListener.testCaseName;

/**
 * <b>This class sets up the Android driver and configures Appium for testing.</b>
 * <p>It extends the AppiumUtils class, providing utility methods for Appium operations.</p>
 */
public class AndroidBase extends AppiumUtils {

    public static AndroidDriver driver;
    public static UiAutomator2Options options;
    public static DesiredCapabilities capabilities;
    public static AndroidLocator androidLocator;
    public static AnnotationProcessor annotationProcessor;

    public AndroidBase() {
    }

    /**
     * <b>Configures Appium before any tests are run.</b>
     * <p>This method clears Chrome data, sets up the desired capabilities, and starts the application.</p>
     */
    @BeforeClass(alwaysRun = true)
    public void baseSetup() {
        try {

            logInfo("---------------- Base Setup ----------------");
            clearChromeData();
            getDesiredCapabilities();
            configEmulator(capabilities);

            String uri = "http://" + ip + ":" + port;
            driver = new AndroidDriver(new URI(uri).toURL(), options);
            logInfo("Launching application on server address --> " + uri);
            getSessionID();

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
            loadAndroidLocators();
            logInfo("---------------------------------------------");
        } catch (Exception e) {
            logError("[" + e.getClass().getSimpleName() + "] | Message --> " + e.getMessage());
        }
    }

    /**
     * <b>Sets the desired capabilities for the Chrome browser in Android Studio emulator.</b>
     *
     * @return <b><u>capabilities</u>: the desired capabilities</b>
     */
    private static DesiredCapabilities getDesiredCapabilities() {
        ChromeOptions chromeOptions = new ChromeOptions();
        // TODO fix chrome settings reset - not working
        chromeOptions.addArguments("--disable-fre");
        chromeOptions.addArguments("--no-first-run");
        chromeOptions.addArguments("--no-default-browser-check");
        chromeOptions.addArguments("--disable-popup-blocking");
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--disable-background-networking");
        chromeOptions.addArguments("--disable-background-timer-throttling");
        chromeOptions.addArguments("--disable-client-side-phishing-detection");
        chromeOptions.addArguments("--disable-default-apps");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--disable-sync");
        chromeOptions.addArguments("--metrics-recording-only");

        capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        logInfo("Chrome settings set");
        return capabilities;
    }

    /**
     * <b>Configures the emulator with the desired capabilities.</b>
     *
     * @param capabilities the desired capabilities
     */
    private static void configEmulator(DesiredCapabilities capabilities) {
        options = new UiAutomator2Options();
        options.setDeviceName(virtualDeviceName);
        options.setApp(userDirPath + appFilePath);
        options.setAutoGrantPermissions(true);
        options.merge(capabilities);
        logInfo("Emulator and app permissions set");
    }

    /**
     * <b>Loads and processes Android locators using the AnnotationProcessor.</b>
     */
    private void loadAndroidLocators() {
        androidLocator = new AndroidLocator(driver);
        annotationProcessor = new AnnotationProcessor(driver);
        annotationProcessor.processAnnotations(androidLocator);
        logInfo("Android locators loaded");
    }

    /**
     * <b>Tears down the driver after the tests are completed.</b>
     */
    @AfterClass(alwaysRun = true)
    public void tearDown() {
        driver.quit();
        logInfo("Driver closed");
        logInfo("END OF TESTCASE '" + testCaseName + "'");
    }
}
