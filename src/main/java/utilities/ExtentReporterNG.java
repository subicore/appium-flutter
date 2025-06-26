package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import common.AppiumUtils;
import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <b>This class provides utilities for generating ExtentReports for test results.</b>
 * <p>It extends the AppiumUtils class and provides methods for initializing ExtentReports,
 * capturing screenshots, and formatting report timestamps.</p>
 */
public class ExtentReporterNG extends AppiumUtils {

    public static ExtentReports extentReports;
    public static ExtentSparkReporter reporter;
    public static String reportingDate = "";
    public static String reportFilePath = "";
    public static String reportDirPath = "";

    /**
     * <b>Initializes and returns the ExtentReports object for reporting.</b>
     * <p>This method sets the report file path, configures the report, and sets system info.</p>
     *
     * @return the ExtentReports instance.
     */
    public static ExtentReports getReporterObject() {

        setGlobalUserVariables();
        reportingDate = getReportingTime(); // get Date and time for reporting

        reportFilePath = "./testReports/" + reportingDate + "/report.html";
        reportDirPath = "./testReports/" + reportingDate;
        reporter = new ExtentSparkReporter(reportFilePath);
        setupReportConfig();

        extentReports = new ExtentReports();
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("Tester", loggedInUsername); // set tester name in report

        return extentReports;
    }

    /**
     * <b>Sets up the configuration for the Extent Report.</b>
     * <p>This method loads the Extent Report configuration from a XML/JSON file and sets the view order for the report.</p>
     */
    private static void setupReportConfig() {
        // The following lines are commented out and can be customized if needed, without config file
//        reporter.config().setReportName("Automation Results");
//        reporter.config().setDocumentTitle("Test Results");
//        reporter.config().setTheme(Theme.DARK);
        try {
            final File CONF = new File("src/main/resources/reporting/spark-config.xml");
            reporter.loadXMLConfig(CONF);
//            final File CONF = new File("src/main/resources/spark-config.json");
//            reporter.loadJSONConfig(CONF);

            reporter.viewConfigurer().viewOrder()
                    .as(new ViewName[]{
                            ViewName.DASHBOARD,
                            ViewName.TEST,
                            ViewName.CATEGORY,
//                            ViewName.DEVICE,
//                            ViewName.AUTHOR,
                            ViewName.EXCEPTION
                    }).apply();
        } catch (Exception e) {
            testFailed("[" + e.getClass().getSimpleName() + "] | Message --> " + e.getMessage() + "\n Error setting up Report Configs");
        }
    }

    /**
     * <b>Captures a screenshot and returns the file path.</b>
     * <p>This method captures a screenshot using the AppiumDriver and saves it to the specified directory.</p>
     *
     * @param testcaseName the name of the test case.
     * @param status       the status of the test step (e.g., "pass" or "fail").
     * @param driver       the AppiumDriver instance to capture the screenshot.
     * @return the file path of the captured screenshot.
     */
    public static String getScreenshot(String testcaseName, String status, AppiumDriver driver) {
        String destination = "";
        String stats = status.equalsIgnoreCase("pass") ? "passed" : "failed";
        String imgPath = "\\testReports\\"
                + reportingDate + "\\screenshots\\"
                + testcaseName + "\\" + stats
                + "\\Image-"
                + System.currentTimeMillis() + ".png";
        try {
            File source = driver.getScreenshotAs(OutputType.FILE);
            destination = userDirPath + imgPath;
            FileUtils.copyFile(source, new File(destination));
        } catch (Exception e) {
            logError("[" + e.getClass().getSimpleName() + "] | Message --> " + e.getMessage());
        }
        return destination;
    }

    /**
     * <b>Creates a format of date and time for reporting.</b>
     *
     * @return the current date and time as a formatted string.
     */
    public static String getReportingTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        return now.format(formatter);
    }
}
