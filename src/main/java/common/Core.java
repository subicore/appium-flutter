package common;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import utilities.LoggerManager;
import utilities.Redact;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import static listeners.TestListener.extentTest;
import static listeners.TestListener.testCaseName;
import static setup.AndroidBase.driver;
import static utilities.ExtentReporterNG.*;
import static utilities.PortKiller.startServer;
import static utilities.WebElementProxyHandler.alias;

public class Core {

    public Core() {

    }

    public static String sessionId;
    public static String ip;
    public static String applicationName;
    public static String appFilePath;
    public static String jsonDirPath;
    public static String appiumServerPath;
    public static String virtualDeviceName;
    public static String userDirPath = "";
    public static String userHome = "";
    public static String loggedInUsername = "";
    public static int port;
    public static int smallWait;
    public static int implicitWait;
    public static int elementLoadWait;
    public static int activityLoadWait;
    public static int stepFailCounter;
    public static int stepWarnCounter;
    public static final int MAX_FAILS = 4; // Maximum allowed failures, starts at 0
    public static final int MAX_WARNS = 19; // Maximum allowed warnings, starts at 0

    public static AppiumDriverLocalService service;

    /**
     * <b>Initializes the Appium pre-setup for testing.</b>
     * <p>This method performs the necessary steps to set up and start the Appium server, including activating logging, reading configuration settings, and starting the server.</p>
     *
     * <ul>
     *   <li><b>activateLogging():</b> Initializes and sets up the logging configuration.</li>
     *   <li><b>readConfigurationFile():</b> Reads the configuration settings from a properties file to set up the environment variables needed for the application.</li>
     *   <li><b>startServer():</b> Starts the Appium server with the configured settings.</li>
     * </ul>
     */
    public void initializeAppium() {
        activateLogging();
        readConfigurationFile();
        startServer();
    }

    /**
     * <b>Sets global user-related variables.</b>
     * <p>This method initializes global variables with system properties and environment variables related to the user's directory, home directory, and username.</p>
     *
     * <ul>
     *   <li><b>userDirPath:</b> Gets the path of the user's current working directory.</li>
     *   <li><b>userHome:</b> Gets the path of the user's home directory.</li>
     *   <li><b>welcomeUser:</b> Gets the username of the currently logged-in user.</li>
     * </ul>
     */
    public static void setGlobalUserVariables() {
        userDirPath = System.getProperty("user.dir");
        userHome = System.getProperty("user.home");
        loggedInUsername = System.getenv("USERNAME");
    }

    /**
     * <b>Starts the Appium server if it is not already running.</b>
     *
     * @param ipAddress the IP address to start the server on.
     * @param port      the port to start the server on.
     * @return the AppiumDriverLocalService instance representing the started server.
     */
    public static AppiumDriverLocalService startAppiumService(String ipAddress, int port) {
        if (!isServerRunning()) {
            logInfo("Starting Appium server on " + port);
            service = new AppiumServiceBuilder()
                    .withAppiumJS(new File(userHome + appiumServerPath))
                    .withIPAddress(ipAddress)
                    .usingPort(port)
//                    .withLogFile(new File(reportDirPath + "\\server-logs.txt")) // TODO fix this
                    .build();
            service.start();
            logInfo("Appium server started");
        }
        return service;
    }

    /**
     * <b>Stops the Appium server if it is running.</b>
     */
    public static void stopServer() {
        if (service != null && service.isRunning()) {
            service.stop();
            logInfo("Appium server stopped");
        } else {
            logInfo("Appium server is not running or already stopped.");
        }
    }

    /**
     * <b>Checks if the Appium server is running.</b>
     *
     * @return true if the server is running, false otherwise.
     */
    private static boolean isServerRunning() {

        return service != null && service.isRunning();
    }

    /**
     * <b>Retrieves the current Appium session ID and logs it.</b>
     * <p>This method fetches the current session ID from the Appium driver, logs it for debugging purposes, and returns it as a string.</p>
     */
    public static void getSessionID() {
        sessionId = driver.getSessionId().toString();
        logInfo("Appium Session ID: " + sessionId);
    }

    /**
     * <b>Activates logging for the whole framework.</b>
     */
    public static void activateLogging() {
        System.setProperty("logPath", reportDirPath);
        LoggerManager.logger.info("Booted logger service"); // Initialize logger (will use the path set above)
        welcomeMessage();
    }

    private static void welcomeMessage() {
        logInfo("Welcome to FlutterMander, " + loggedInUsername);
    }

    /**
     * <b>Reads the configuration file and sets the necessary properties.</b>
     * <p>This method retrieves various configuration properties such as IP, port, application details,
     * device name, Appium server path, and wait times. </p>
     *
     * <ul>
     *   <li><b>ip:</b> IP address for the Appium server.</li>
     *   <li><b>port:</b> Port number for the Appium server.</li>
     *   <li><b>application:</b> Name of the application to be tested.</li>
     *   <li><b>appFilePath:</b> File path of the application.</li>
     *   <li><b>virtualDeviceName:</b> Name of the virtual device (emulator).</li>
     *   <li><b>appiumServerPath:</b> Path to the Appium server executable.</li>
     *   <li><b>smallWait:</b> Small wait time for locating elements</li>
     *   <li><b>implicitWait:</b> Implicit wait time for locating elements.</li>
     *   <li><b>elementLoadWait:</b> Wait time for elements to load.</li>
     *   <li><b>activityLoadWait:</b> Wait time for activities/pages to load.</li>
     * </ul>
     */
    private static void readConfigurationFile() {
        logInfo("Opened Configuration file...");
        ip = getConfig("ip");
        port = Integer.parseInt(getConfig("port"));
        applicationName = getConfig("app");
        appFilePath = getConfig("appFilePath");
        virtualDeviceName = getConfig("deviceName");
        appiumServerPath = getConfig("appiumServerPath");
        jsonDirPath = getConfig("jsonDirPath");
        smallWait = Integer.parseInt(getConfig("smallWaitTime"));
        implicitWait = Integer.parseInt(getConfig("implicitWaitTime"));
        elementLoadWait = Integer.parseInt(getConfig("elementLoadWaitTime"));
        activityLoadWait = Integer.parseInt(getConfig("activityLoadWaitTime"));
    }

    /**
     * <b>Reads a property value from the configuration file.</b>
     *
     * @param key the property key to read.
     * @return the property value as a String.
     */
    private static String getConfig(String key) {
        String propertyValue = null;
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("./src/main/resources/Configuration.properties"));
            propertyValue = prop.getProperty(key);
            logInfo("Config: read property key '" + key + "' with value = " + propertyValue);
        } catch (Exception e) {
            logError("[" + e.getClass().getSimpleName() + "] | Message --> " + e.getMessage());
        }
        return propertyValue;
    }

    /**
     * <b>Clears Chrome data such as cache, cookies, and storage data.</b>
     */
    public static void clearChromeData() {
        try {
            String[] clearDataCommand = {
                    "adb", "shell", "pm", "clear", "com.android.chrome"
            };
            Process process = new ProcessBuilder(clearDataCommand).start();
            process.waitFor();
            logInfo("Chrome cache, cookies and storage data cleared");
        } catch (Exception e) {
            logError("[" + e.getClass().getSimpleName() + "] Failed to clear Chrome data | Message --> " + e.getMessage());
        }
    }

    /**
     * <b>Logs an info message to the log file.</b>
     *
     * @param message the message to log.
     */
    public static void logInfo(String message) {
        LoggerManager.logger.info(message);
    }

    /**
     * <b>Logs a warning message to the log file.</b>
     *
     * @param message the message to log.
     */
    public static void logWarning(String message) {
        LoggerManager.logger.warn(message);
    }

    /**
     * <b>Logs an error message to the log file.</b>
     *
     * @param message the message to log.
     */
    public static void logError(String message) {
        LoggerManager.logger.error(message);
    }

    /**
     * <b>Logs an info message to the log file and also to the test report.</b>
     *
     * @param message the message to log.
     */
    public static void testStepInfo(String message) {
        logInfo(message);
        extentTest.info(MarkupHelper.createLabel(message, ExtentColor.BLUE));
    }

    /**
     * <b>Logs a pass message to the log file and also to the test report with a screenshot.</b>
     *
     * @param message the message to log.
     */
    public static void testStepPass(String message) {
        logInfo(message);
        String screenshotPath = getScreenshot(testCaseName, "pass", driver);
        String combinedHtml = get25pxHtml(screenshotPath, message, "", ""); // default: black, regular
        extentTest.log(Status.PASS, combinedHtml);
    }


    /**
     * <b>Logs a warning message to the log file and also to the test report with a screenshot.</b>
     *
     * @param message the message to log.
     */
    public static void testStepWarn(String message) {
        logWarning(message);
        String screenshotPath = getScreenshot(testCaseName, "fail", driver);
        String combinedHtml = get25pxHtml(screenshotPath, message, "#ffc107", "bold"); // orange
        extentTest.log(Status.WARNING, combinedHtml);
        checkWarnLimit(); //fail counter
    }

    /**
     * <b>Logs a test step failure and attaches a screenshot.</b>
     * <p>This method captures a screenshot and logs the failure message with a clickable thumbnail in the Extent Report.</p>
     *
     * @param message the failure message to log.
     */
    public static void testStepFail(String message) {
        try {
            SoftAssert softAssert = new SoftAssert();
            logError(message);

            String screenshotPath = getScreenshot(testCaseName, "fail", driver);
            String combinedHtml = getFailureHtml(screenshotPath, message);
            extentTest.log(Status.FAIL, combinedHtml);

            checkFailureLimit(); // Fail counter
            softAssert.fail();
        } catch (Exception e) {
            logError("[" + e.getClass().getSimpleName() + "] | Message --> " + e.getMessage());
        }
    }

    /**
     * <b>Logs a test failure message to the report file. This will fail and halt the current running test.</b>
     *
     * @param message the message to log.
     */
    public static void testFailed(String message) {
        Assert.fail(message);
    }

    /**
     * <b>Checks and handles the condition when a test step has multiple warnings.</b>
     * <p>This method increments the warning counter each time a warning is triggered by the test step and fails the whole test when the counter is exceeded</p>
     */
    public static void checkWarnLimit() {
        stepWarnCounter++;
        if (stepWarnCounter > MAX_WARNS) {
            testFailed("Testcase has more than " + MAX_WARNS + " test step warnings. Failing this test now");
        }
    }

    /**
     * <b>Checks and handles the condition when a test step fails multiple times.</b>
     * <p>This method increments the failure counter each time a test step fails and fails the whole test when the counter is exceeded</p>
     */
    public static void checkFailureLimit() {
        stepFailCounter++;
        if (stepFailCounter > MAX_FAILS) {
            testFailed("Test has more than " + MAX_FAILS + " test step failures. Failing this test now");
        }
    }

    /**
     * <b>Generates HTML for a failure message with a 250px thumbnail image aligned to the right.</b>
     * <p>This method creates a combined HTML string that includes a failure message styled in red and bold, alongside a thumbnail image aligned to the right.
     * The thumbnail links to the full-size screenshot.</p>
     *
     * @param screenshotPath the path to the screenshot image.
     * @param message        the failure message to display.
     * @return the combined HTML string.
     */
    public static String getFailureHtml(String screenshotPath, String message) {
        String combinedHtml;
        String thumbnailHtml = "<div style='float: right;'>" +
                "<a href='" + screenshotPath + "' target='_blank'>" +
                "<img src='" + screenshotPath + "' style='max-width: 250px; " +
                "max-height: 250px; vertical-align: middle; border: none; padding: 0;' /></a>" +
                "</div>";
        String messageHtml = "<div style='display: inline-block; vertical-align: middle; color: #e57373; font-weight: bold;'>" + message + "</div>"; // red
        combinedHtml = "<div style='display: flex; align-items: center; justify-content: space-between;'>" + messageHtml + thumbnailHtml + "</div>";
        return combinedHtml;
    }

    /**
     * <b>Generates HTML for a message with a 25px thumbnail image aligned to the right.</b>
     * <p>This method creates a combined HTML string that includes a message styled with a specified color and text style, alongside a thumbnail image aligned to the right.
     * The thumbnail links to the full-size screenshot.</p>
     *
     * @param screenshotPath the path to the screenshot image.
     * @param message        the message to display.
     * @param hexCode        the color hex code for the message text.
     * @param textStyle      the style for the message text (e.g., "bold", "normal").
     * @return the combined HTML string.
     */
    public static String get25pxHtml(String screenshotPath, String message, String hexCode, String textStyle) {
        String customThumbnail = userDirPath + "\\src\\main\\resources\\thumbnails\\thumbnail_dummy.png";
        String combinedHtml;
        String thumbnailHtml = "<div style='float: right;'>" +
                "<a href='" + screenshotPath + "' target='_blank'>" +
                "<img src='" + customThumbnail + "' style='width: 25px; " +
                "height: 25px; vertical-align: middle; border: none; padding: 0;' /></a>" +
                "</div>";
        String messageHtml = "<div style='display: inline-block; vertical-align: middle; color: " + hexCode + "; font-weight: " + textStyle + ";'>" + message + "</div>";
        combinedHtml = "<div style='display: flex; align-items: center; justify-content: space-between; border: none; padding: 0;'>" + messageHtml + thumbnailHtml + "</div>";
        return combinedHtml;
    }

    /**
     * <b>Writes the password into the specified web element.</b>
     * <p>This method decrypts the encrypted password, enters it into the specified web element,
     * and logs the action with the password masked as *****.</p>
     *
     * @param element the WebElement where the password will be entered.
     * @param value   the encrypted password to be decrypted and entered.
     * @param driver  the AppiumDriver instance used to perform the action.
     */
    public static void writePassword(WebElement element, String value, AppiumDriver driver) {
        String password = Redact.decode(value);
        findElement(element, smallWait, driver).sendKeys(password);
        testStepPass("Entered ***** in " + alias);
    }
}
