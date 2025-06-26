package common;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.HashMap;
import java.util.Map;

/**
 * <b>This class provides common actions for interacting with iOS elements in Appium.</b>
 * <p>It extends the AppiumUtils class, providing utility methods for Appium operations.</p>
 */
public class IOSActions extends AppiumUtils {
    IOSDriver driver;

    public IOSActions(IOSDriver driver) {
        this.driver = driver;
    }

    public IOSActions() {

    }

    /**
     * <b>Performs a long press action on the specified element.</b>
     * @param element the WebElement to long press on.
     */
    public void longPressAction(WebElement element) {
        Map<String, Object> params = new HashMap<>();
        params.put("element", ((RemoteWebElement) element).getId());
        params.put("duration", 5);
        driver.executeScript("mobile:touchAndHold", params);
    }

    /**
     * <b>Scrolls to the specified element.</b>
     * @param element the WebElement to scroll to.
     */
    public void scrollToWebElement(WebElement element) {
        Map<String, Object> params = new HashMap<>();
        params.put("direction", "down");
        params.put("element", ((RemoteWebElement) element).getId());
        driver.executeScript("mobile:scroll", params);
    }

    /**
     * <b>Swipes in the specified direction on the given element.</b>
     * @param element   the WebElement to swipe on.
     * @param direction the direction to swipe ("up", "down", "left", "right").
     */
    public void swipe(WebElement element, String direction) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("direction", "left");
        driver.executeScript("mobile:swipe", params);

    }
}