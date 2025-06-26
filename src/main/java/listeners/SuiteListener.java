package listeners;

import common.AppiumUtils;
import org.testng.ISuite;
import org.testng.ISuiteListener;

/**
 * <b>This class implements a listener for TestNG suites to perform actions at the start and end of a suite.</b>
 * <p>It extends the AppiumUtils class, providing utility methods for Appium operations, and implements the ISuiteListener interface from TestNG.</p>
 */
public class SuiteListener extends AppiumUtils implements ISuiteListener {

    public static String suiteName = "";

    /**
     * <b>Called when a TestNG suite starts.</b>
     * <p>This method initializes pre-setups needed for appium testing, sets the suite name, and logs the start of the suite.</p>
     * @param suite the TestNG suite that is starting.
     */
    @Override
    public void onStart(ISuite suite) {
        initializeAppium();
        suiteName = suite.getName();
        logInfo("======================= Started " + suiteName + " =======================");
    }

    /**
     * <b>Called when a TestNG suite finishes.</b>
     * <p>This method logs the end of the suite and stops the Appium server.</p>
     * @param suite the TestNG suite that has finished.
     */
    @Override
    public void onFinish(ISuite suite) {
        logInfo("======================= End of " + suiteName + " =======================");
        stopServer();
    }
}
