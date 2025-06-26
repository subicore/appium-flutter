package common;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static utilities.WebElementProxyHandler.alias;
import static utilities.WebElementProxyHandler.locatorDetails;

/**
 * <b>This class provides common actions for interacting with Android elements in Appium.</b>
 * <p>It extends the AppiumUtils class, providing utility methods for Appium operations.</p>
 */
public class AndroidActions extends AppiumUtils {

    public static AndroidDriver driver;

    /**
     * <b>Constructor for AndroidActions.</b>
     *
     * @param driver the AndroidDriver instance to be used for actions.
     */
    public AndroidActions(AndroidDriver driver) {
        AndroidActions.driver = driver;
    }

    /**
     * <b>Gets the current context of the driver.</b>
     *
     * @return the current context.
     */
    public static String getCurrentContext() {
        return driver.getContext();
    }

    public static void dismissKeyboard() {
        if (driver.isKeyboardShown()) {
            driver.pressKey(new KeyEvent(AndroidKey.BACK));
            logInfo("Keyboard dismissed");
        } else {
            testStepWarn("Keyboard was not displayed");
        }
    }

    /**
     * <b>Performs a long press on the specified element.</b>
     *
     * @param element the WebElement to long press on.
     */
    public static void longPress(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("mobile: longClickGesture",
                ImmutableMap.of("elementId", ((RemoteWebElement) element).getId(),
                        "duration", 2000));
    }

    /**
     * <b>Performs a swipe action on the specified element in the given direction.</b>
     * <p>This method calculates the start and end points for the swipe action based on the direction and the element's location and size. It then performs the swipe action using the Appium driver.</p>
     *
     * @param element   the WebElement to swipe on.
     * @param direction the direction to swipe ("LEFT" or "RIGHT").
     */
    public static void swipe(WebElement element, String direction) {
        try {
            // Get element location and size
            int startX = element.getLocation().getX() + (element.getSize().getWidth() / 2);
            int startY = element.getLocation().getY() + (element.getSize().getHeight() / 2);

            int endX = startX;
            int endY = startY;

            // Define end points based on direction
            switch (direction.toUpperCase()) {
                case "LEFT":
                    endX = startX - (element.getSize().getWidth() / 2); // Adjust the swipe length as needed
                    break;
                case "RIGHT":
                    endX = startX + (element.getSize().getWidth() / 2); // Adjust the swipe length as needed
                    break;
                default:
                    testStepFail("Invalid direction: '" + direction + "' Please pass: \n" +
                            "1. LEFT\n" +
                            "2. RIGHT");
                    return;
            }

            // Perform swipe action using PointerInput
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger, 1);
            swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), endX, endY));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(Arrays.asList(swipe));
            testStepPass("Swiped " + direction + " from " + alias);

        } catch (Exception e) {
            logError("Cannot swipe " + direction + " from " + alias + " | " + locatorDetails);
            testStepFail("[" + e.getClass().getSimpleName() + "] | Message --> " + e.getMessage());
        }
    }


    /**
     * <b>Scrolls to the element containing the specified text.</b>
     *
     * @param text the text to scroll to.
     *             <pre><code>!!!!Not working for my_exp!!!!</code></pre>
     */
    public void scrollToText(String text) {
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + text + "\"));"));
    }


    /**
     * <b>Scrolls until it finds the specified element in viewport.</b>
     * <p>This method performs a series of scroll actions until the specified element is displayed in the viewport.
     * If the element is not found after scrolling to the end, a NoSuchElementException is caught.</p>
     *
     * @param element   the WebElement to scroll to.
     * @param direction the direction to scroll ("up" or "down").
     * @catches NoSuchElementException if the element is not found after scrolling to the end.
     */
    public void scrollToFindElement(WebElement element, String direction) {
        Dimension size = driver.manage().window().getSize();
        int startX = size.getWidth() / 2;
        int startY, endY;
        int smallScrollStartY, smallScrollEndY;

        if ("up".equalsIgnoreCase(direction)) {
            startY = (int) (size.getHeight() * 0.2);
            endY = (int) (size.getHeight() * 0.8);
            smallScrollStartY = (int) (size.getHeight() * 0.4);
            smallScrollEndY = (int) (size.getHeight() * 0.6);
        } else if ("down".equalsIgnoreCase(direction)) {
            startY = (int) (size.getHeight() * 0.8);
            endY = (int) (size.getHeight() * 0.2);
            smallScrollStartY = (int) (size.getHeight() * 0.6);
            smallScrollEndY = (int) (size.getHeight() * 0.4);
        } else {
            testStepFail("Invalid direction: '" + direction + "'. Please pass any one of the following\n" +
                    "up\n" +
                    "down");
            return;
        }

        // Save the original implicit wait time
        Duration originalImplicitWait = driver.manage().timeouts().getImplicitWaitTimeout();

        try {
            // Set a new implicit wait time (1 second)
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

            int scrollCount = 0;
            while (!isElementInViewPort(element)) {
                boolean scrolled;
                if (scrollCount > 0) {
                    scrolled = performScroll(startX, smallScrollStartY, smallScrollEndY);
                    logWarning(alias + " is still not in current viewport, performing smaller scrolls in '" + direction + "' direction");
                } else {
                    scrolled = performScroll(startX, startY, endY);
                    logWarning(alias + " is not in current viewport, scrolling " + direction + " little more");
                }
                if (!scrolled) {
                    testStepFail("Reached the end of the scroll view, but " + alias + " is not found." + locatorDetails);
                }
                scrollCount++;
            }
            testStepPass("Scrolled " + direction + " to " + alias );
        } catch (Exception e) {
            logError("Cannot scroll to " + alias + " | " + locatorDetails);
            testStepFail("[" + e.getClass().getSimpleName() + "] | Message --> " + e.getMessage());
        } finally {
            // Reset the implicit wait time to the original value
            driver.manage().timeouts().implicitlyWait(originalImplicitWait);
        }
    }

    /**
     * <b>Checks if the specified element is displayed on the screen.</b>
     *
     * @param element the WebElement to check for visibility.
     * @return true if the element is displayed, false otherwise.
     */
    private boolean isElementInViewPort(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * <b>Performs a scroll action on the screen.</b>
     * <p>This method simulates a swipe gesture from the start coordinates to the end coordinates using a finger input.</p>
     *
     * @param startX the starting X coordinate for the swipe.
     * @param startY the starting Y coordinate for the swipe.
     * @param endY   the ending Y coordinate for the swipe.
     * @return true if the screen content changed after the swipe, false otherwise.
     */
    private boolean performScroll(int startX, int startY, int endY) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), startX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(swipe));

        // Verify if the screen content changed after the swipe
        List<WebElement> elementsAfterSwipe = driver.findElements(AppiumBy.androidUIAutomator("new UiSelector().scrollable(true).instance(0)"));
        return !elementsAfterSwipe.isEmpty();
    }


    /**
     * <b>Scrolls to the end of the page.</b>
     */
    public void scrollToEnd() {
        boolean spaceAvailable;
        do {
            spaceAvailable = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
                    "left", 100, "top", 100, "width", 200, "height", 200,
                    "direction", "down",
                    "percent", 3.0

            ));
        } while (spaceAvailable);
    }
}
