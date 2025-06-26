package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.appium.java_client.AppiumDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utilities.ExtentReporterNG;

/**
 * <b>This class implements a listener for TestNG tests to perform actions at the start and end of tests.</b>
 * <p>It extends the ExtentReporterNG class for reporting purposes and implements the ITestListener interface from TestNG.</p>
 */
public class TestListener extends ExtentReporterNG implements ITestListener {

    AppiumDriver driver;
    public static ExtentTest extentTest;
    public static ExtentReports extentReports = getReporterObject();
    public static String testCaseName = "";
    private static final int SUCCESS_PERCENTAGE = 80;

    /**
     * <b>Called when a test starts.</b>
     * <p>This method sets useful configs for the framework to function with extent reports, logging, etc.</p>
     *
     * @param result the ITestResult instance containing information about the test method that is starting.
     */
    @Override
    public void onTestStart(ITestResult result) {
        testCaseName = result.getMethod().getMethodName();
        String[] groups = result.getMethod().getGroups();
        extentTest = extentReports.createTest(testCaseName).assignAuthor(loggedInUsername).assignDevice(virtualDeviceName);

        for (String group : groups) {
            extentTest.assignCategory(group);
        }

        logInfo("START OF TESTCASE '" + testCaseName + "'");
        logInfo("Running on device: " + virtualDeviceName);
        // Reset the counter at the start of each test
        stepFailCounter = 0;
        stepWarnCounter = 0;
    }

    /**
     * <b>Called when a test succeeds.</b>
     * <p>This method sets useful configs for the framework to function with extent reports, logging, etc.</p>
     *
     * @param result the ITestResult instance containing information about the test method that has passed.
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        String styledMessage = "<span style='color: green; font-weight: bold;'>TEST PASSED</span>";
        extentTest.log(Status.PASS, styledMessage);
    }

    /**
     * <b>Handles actions to be performed when a test fails.</b>
     * <p>This method is triggered when a test method fails. It captures the failure, logs the error, and adds a screenshot thumbnail to the Extent Report, which can be clicked to view the full-size image.</p>
     *
     * @param result the ITestResult instance containing information about the test method that has failed.
     */
    @Override
    public void onTestFailure(ITestResult result) {
        String message = "TEST FAILED";
        extentTest.fail(result.getThrowable());
        try {
            // TestClass - return me the class<> mentioned in xml for the running test
            // getRealClass - return me the java class name mentioned in xml for the running test
            // getField - return me the driver instance the getRealClass is using

            // Retrieve the AppiumDriver instance
            driver = (AppiumDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());

            String screenshotPath = getScreenshot(testCaseName, "fail", driver);
            String combinedHtml = getFailureHtml(screenshotPath, message);

            // extentTest.fail("Screenshot", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build()); // bigger image
            logError("Testcase '" + testCaseName + "' failed");
            extentTest.log(Status.FAIL, combinedHtml);
        } catch (Exception e) {
            logError("[" + e.getClass().getSimpleName() + "] | Message --> " + e.getMessage());
        }
    }

    /**
     * <b>Called when a test is skipped.</b>
     * <p>This method sets useful configs for the framework to function with extent reports, logging, etc.</p>
     *
     * @param result the ITestResult instance containing information about the test method that was skipped.
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        String styledMessage = "<span style='color: yellow; font-weight: bold;'>TEST SKIPPED</span>";
        extentTest.log(Status.SKIP, styledMessage);
        logInfo("Testcase '" + testCaseName + "' got skipped");
    }

    /**
     * <b>Called when a test fails but is within the success percentage.</b>
     *
     * @param result the ITestResult instance containing information about the test method that failed but is within the success percentage.
     */
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // TODO code here
    }

    /**
     * <b>Called before any test methods in the current test context are run.</b>
     *
     * @param context the ITestContext instance containing information about the current test context.
     */
    @Override
    public void onStart(ITestContext context) {
        // TODO code here
    }

    /**
     * <b>Called after all the test methods in the current test context have run.</b>
     * <p>This method flushes the ExtentReports to ensure all logs and results are written to the report.</p>
     *
     * @param context the ITestContext instance containing information about the current test context.
     */
    @Override
    public void onFinish(ITestContext context) {
        extentReports.flush();
    }
}
