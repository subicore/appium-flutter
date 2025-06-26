package common;

import io.appium.java_client.AppiumDriver;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.FileReader;
import java.time.Duration;

import static utilities.WebElementProxyHandler.alias;
import static utilities.WebElementProxyHandler.locatorDetails;

/**
 * <b>This class provides utility methods that are common for both Android and IOS Appium operations.</b>
 */
public class AppiumUtils extends Core {

    public AppiumUtils() {
    }

    /**
     * <b>Pauses the execution for a specified timeout.</b>
     *
     * @param seconds the timeout duration in seconds.
     */
    public static void hardWait(int seconds) {
        try {
            logInfo("Starting to wait for " + seconds + " seconds..");
            Thread.sleep(seconds * 1000L);
            logInfo("Wait: " + seconds + " seconds");
        } catch (Exception e) {
            logError("[" + e.getClass().getSimpleName() + "] | Message --> " + e.getMessage());
        }
    }


    /**
     * <b>Checks if the specified element is displayed and logs the result.</b>
     * <p>This method verifies if the provided element is displayed on the screen, waits for the element to be visible for a specified duration, logs it to reporting.</p>
     *
     * @param element the WebElement to check for visibility.
     * @param driver  the AppiumDriver instance used to interact with the element.
     * @return true if the element is displayed, false otherwise.
     */
    public static boolean isElementDisplayed(WebElement element, AppiumDriver driver) {
        boolean flag = true;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(elementLoadWait));
            wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
            testStepPass(alias + " is displayed");
        } catch (Exception e) {
            flag = false;
            logError(alias + " is not displayed | " + locatorDetails);
            testStepFail("[" + e.getClass().getSimpleName() + "] | Message --> " + e.getMessage());
        }
        return flag;
    }

    /**
     * <b>Finds the specified element with a timeout</b>
     *
     * @param element the WebElement to find.
     * @param driver  the AppiumDriver instance to use for the action.
     * @return the found WebElement.
     */
    public static WebElement findElement(WebElement element, AppiumDriver driver) {
        return findElement(element, elementLoadWait, driver);
    }

    /**
     * <b>Finds the specified element with a timeout that's passed as an argument</b>
     *
     * @param element the WebElement to find.
     * @param timeout the timeout duration in seconds.
     * @param driver  the AppiumDriver instance to use for the action.
     * @return the found WebElement.
     */
    public static WebElement findElement(WebElement element, int timeout, AppiumDriver driver) {
        WebElement ele = null;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            ele = wait.until(ExpectedConditions.visibilityOf(element));
            logInfo(alias + " is visible");
        } catch (Exception e) {
            logError("Cannot find " + alias + " | " + locatorDetails);
            testStepFail("[" + e.getClass().getSimpleName() + "] | Message --> " + e.getMessage());
        }
        return ele;
    }

    /**
     * <b>Retrieves the content description attribute of the specified element.</b>
     * <p>This method fetches the 'content-desc' attribute of the provided element, logs the operation, and returns the value.</p>
     *
     * @param element the WebElement from which to retrieve the content description.
     * @param driver  the AppiumDriver instance used to interact with the element.
     * @return the content description attribute of the specified element.
     */
    public static String getAttribute(WebElement element, AppiumDriver driver) {
        String text = findElement(element, driver).getAttribute("content-desc");
        logInfo("Retrieved attribute (content-desc: '" + text + "') for element: " + alias);
        return text;
    }

    /**
     * <b>Retrieves the specified attribute of the specified element.</b>
     * <p>This method fetches the specified attribute of the provided element, logs the operation, and returns the value.</p>
     *
     * @param element   the WebElement from which to retrieve the attribute.
     * @param attribute the name of the attribute to retrieve.
     * @param driver    the AppiumDriver instance used to interact with the element.
     * @return the value of the specified attribute of the specified element.
     */
    public static String getAttribute(WebElement element, String attribute, AppiumDriver driver) {
        String text = findElement(element, driver).getAttribute(attribute);
        logInfo("Retrieved attribute (" + attribute + ": '" + text + "') for element: " + alias);
        return text;
    }


    /**
     * <b>Retrieves the text content of the specified element.</b>
     * <p>This method fetches the text content of the provided element, logs the operation, and returns the value.</p>
     *
     * @param element the WebElement from which to retrieve the text.
     * @param driver  the AppiumDriver instance used to interact with the element.
     * @return the text content of the specified element.
     */
    public static String getText(WebElement element, AppiumDriver driver) {
        String text = findElement(element, driver).getText();
        logInfo("Retrieved text: '" + text + "' for element: " + alias);
        return text;
    }

    /**
     * <b>Asserts that the text or content description of the specified element matches the expected text.</b>
     * <p>This method first tries to retrieve the text of the element using `getText()`. If the text is empty or an exception occurs, it attempts to retrieve the content description using `getAttribute("content-desc")`. It then asserts that the retrieved text matches the expected text, logging the result.</p>
     *
     * @param element      the WebElement whose text or content description is to be checked.
     * @param expectedText the expected text to assert against.
     * @param driver       the AppiumDriver instance used to interact with the element.
     */
    public void assertElementText(WebElement element, String expectedText, AppiumDriver driver) {
        String actualText = null;
        boolean success = true;

        try {
            actualText = getText(element, driver);
            if (actualText.isEmpty()) {
                logWarning("getText() returned empty, attempting to use getAttribute()");
                actualText = getAttribute(element, driver);
            }
        } catch (Exception e1) {
            logWarning("[" + e1.getClass().getSimpleName() + "] | Message --> " + e1.getMessage());
            try {
                actualText = getAttribute(element, driver);
            } catch (Exception e2) {
                logError("Both getText() and getAttribute() failed for " + alias + " | " + locatorDetails);
                testStepFail("[" + e2.getClass().getSimpleName() + "] | Message --> " + e2.getMessage());
                success = false;
            }
        }

        if (success) {
            assertValue(actualText, expectedText);
        } else {
            logError("Could not retrieve text for " + alias + " with " + locatorDetails);
            testStepFail("Unable to retrieve text or attribute: 'content-desc' from element - " + alias);
        }
    }

    /**
     * <b>Asserts that the actual value matches the expected value and logs the result.</b>
     * <p>This generic method checks if the actual value equals the expected value. If the assertion passes, it logs the result as a passed test step. If the assertion fails, it logs the failure and catches the exception.</p>
     *
     * @param <T>      the type of the values being compared.
     * @param actual   the actual value to be checked.
     * @param expected the expected value to compare against.
     */
    public <T> void assertValue(T actual, T expected) {
        try {
            Assert.assertEquals(actual, expected, "Checking value");
            testStepPass("Assertion passed: expected [" + expected + "] and found [" + actual + "]");
        } catch (AssertionError e) {
            logError("Assertion failed for " + alias + " | " + locatorDetails);
            testStepFail("Assertion failed: expected [" + expected + "] but found [" + actual + "]");
        } catch (Exception e) {
            logError("Unexpected error occurred asserting " + alias + " | " + locatorDetails);
            testStepFail("[" + e.getClass().getSimpleName() + "] | Message --> " + e.getMessage());
        }
    }

    /**
     * <b>Writes a value into the specified element.</b>
     *
     * @param element the WebElement to write into.
     * @param value   the value to write.
     * @param driver  the AppiumDriver instance to use for the action.
     */
    public static void write(WebElement element, String value, AppiumDriver driver) {
        findElement(element, smallWait, driver).sendKeys(value);
        testStepPass("Entered '" + value + "' in " + alias);
    }

    /**
     * <b>Taps on the specified element.</b>
     *
     * @param element the WebElement to tap on.
     * @param driver  the AppiumDriver instance to use for the action.
     */
    public static void tap(WebElement element, AppiumDriver driver) {
        findElement(element, driver).click();
        testStepPass("Tapped on " + alias);
    }

    /**
     * <b>Retrieves a value from a JSON file based on the provided keys.</b>
     * <p>This method reads a JSON file, navigates through the JSON structure using the provided keys, and returns the corresponding value.</p>
     *
     * @param jsonFileName the name of the JSON file (without the .json extension).
     * @param keys         the keys to navigate through the JSON structure.
     * @return the value corresponding to the final key in the JSON structure, or an empty string if an exception occurs.
     */
    public static String getJsonData(String jsonFileName, String... keys) {
        String jsonKeyValue = "";
        JSONParser jsonParser = new JSONParser();
        String filePath = userDirPath +
                jsonDirPath +
                jsonFileName +
                ".json";

        try (FileReader reader = new FileReader(filePath)) {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            for (int i = 0; i < keys.length - 1; i++) {
                if (jsonObject != null && jsonObject.get(keys[i]) instanceof JSONObject) {
                    jsonObject = (JSONObject) jsonObject.get(keys[i]);
                } else {
                    logError("Key '" + keys[i] + "' not found or is not a JSON object.");
                    return null;
                }
            }

            if (jsonObject != null && jsonObject.get(keys[keys.length - 1]) instanceof String) {
                jsonKeyValue = (String) jsonObject.get(keys[keys.length - 1]);
            } else {
                logError("Key '" + keys[keys.length - 1] + "' not found or is not a String.");
            }
        } catch (Exception e) {
            logError("[" + e.getClass().getSimpleName() + "] | Message --> " + e.getMessage());
        }
        return jsonKeyValue;
    }

    /**
     * <b>Asserts that each line of the actual text matches the corresponding expected string.</b>
     * <p>This method splits the actual text by newline characters and compares each line to the corresponding expected string. If there are more lines in the actual text than expected, it logs the additional lines. If an assertion fails, it logs the failure and continues checking the remaining lines.</p>
     *
     * @param actualText the actual text containing multiple lines.
     * @param expected   an array of expected strings, each corresponding to a line in the actual text.
     */
    public void assertMultiLineString(String actualText, String[] expected) {
        String[] actual = actualText.replaceAll("\n", "SPLIT-ME").split("SPLIT-ME");
        try {
            for (int i = 0; i < actual.length; i++) {
                if (i < expected.length) {
                    assertValue(actual[i], expected[i]);
                } else {
                    logWarning("Additional line/s found for " + alias + ": " + actual[i] + " | " + locatorDetails);
                }
            }
        } catch (Exception e) {
            testStepFail("[" + e.getClass().getSimpleName() + "] | Message --> " + e.getMessage() + ". Alias- " + alias + " | " + locatorDetails);
        }
    }

}