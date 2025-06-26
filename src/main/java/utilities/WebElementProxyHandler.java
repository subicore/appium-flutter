package utilities;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static common.AppiumUtils.logError;

/**
 * <b> This class creates a dynamic proxy for WebElement fields, deferring the actual lookup until the element is accessed.<br></b>
 * <p>Proxy handler for WebElement to defer element lookup until accessed.</p>
 */
public class WebElementProxyHandler implements InvocationHandler {
    private final AndroidDriver driver;
    private final AndroidFindBy androidFindBy;
    private final String currentAlias;
    public static String alias = "";
    public static String locatorDetails = "";

    /**
     * Constructor for WebElementProxyHandler.
     *
     * @param driver        the AndroidDriver instance used to interact with elements.
     * @param androidFindBy the AndroidFindBy annotation containing the locator strategy.
     * @param currentAlias  the alias name for the current WebElement.
     */
    public WebElementProxyHandler(AndroidDriver driver, AndroidFindBy androidFindBy, String currentAlias) {
        this.driver = driver;
        this.androidFindBy = androidFindBy;
        this.currentAlias = currentAlias;
    }

    /**
     * <b>Finds the WebElement based on the AndroidFindBy locator strategy.</b>
     *
     * @return the located WebElement.
     * @throws IllegalArgumentException if the locator type is unsupported.
     */
    private WebElement findElement() {
        if (!androidFindBy.id().isEmpty()) {
            return driver.findElement(By.id(androidFindBy.id()));
        } else if (!androidFindBy.xpath().isEmpty()) {
            return driver.findElement(By.xpath(androidFindBy.xpath()));
        } else if (!androidFindBy.accessibility().isEmpty()) {
            return driver.findElement(new AppiumBy.ByAccessibilityId(androidFindBy.accessibility()));
        } else if (!androidFindBy.className().isEmpty()) {
            return driver.findElement(By.className(androidFindBy.className()));
        } else if (!androidFindBy.uiAutomator().isEmpty()) {
            return driver.findElement(AppiumBy.androidUIAutomator(androidFindBy.uiAutomator()));
        } else if (!androidFindBy.tagName().isEmpty()) {
            return driver.findElement(By.tagName(androidFindBy.tagName()));
        } else {
            logError("Unsupported locator type. Pass one of the following - \n" +
                    "1. id\n" +
                    "2. xpath\n" +
                    "3. tagName\n" +
                    "4. className\n" +
                    "5. uiAutomator\n" +
                    "6. accessibility\n");
        }
        throw new IllegalArgumentException("Unsupported locator type");
    }

    /**
     * <b>Returns a description of the element based on the locator strategy.</b>
     * <p>EDIT: Commented out Alias information, so now this returns only the locator</p>
     *
     * @return a string description of the locator.
     */
    private String getElementDescription() {
//        StringBuilder description = new StringBuilder("Alias- " + currentAlias + " | "); // to get alias as well
        StringBuilder description = new StringBuilder("");
        if (!androidFindBy.id().isEmpty()) {
            description.append("Locator: By.id(\"").append(androidFindBy.id()).append("\")");
        } else if (!androidFindBy.xpath().isEmpty()) {
            description.append("Locator: By.xpath(\"").append(androidFindBy.xpath()).append("\")");
        } else if (!androidFindBy.accessibility().isEmpty()) {
            description.append("Locator: AppiumBy.ByAccessibilityId(\"").append(androidFindBy.accessibility()).append("\")");
        } else if (!androidFindBy.className().isEmpty()) {
            description.append("Locator: By.className(\"").append(androidFindBy.className()).append("\")");
        } else if (!androidFindBy.uiAutomator().isEmpty()) {
            description.append("Locator: AppiumBy.androidUIAutomator(\"").append(androidFindBy.uiAutomator()).append("\")");
        } else if (!androidFindBy.tagName().isEmpty()) {
            description.append("Locator: By.tagName(\"").append(androidFindBy.tagName()).append("\")");
        } else {
            description.append("Unsupported locator type");
            logError("Unsupported locator type. Valid ones - \n" +
                    "1. id\n" +
                    "2. xpath\n" +
                    "3. tagName\n" +
                    "4. className\n" +
                    "5. uiAutomator\n" +
                    "6. accessibility\n");
        }
        return description.toString();
    }

    /**
     * <b>Invokes methods on the actual WebElement and logs the alias when the element is accessed.</b>
     *
     * @param proxy  the proxy instance that the method was invoked on.
     * @param method the method instance corresponding to the interface method invoked on the proxy instance.
     * @param args   an array of objects containing the values of the arguments passed in the method invocation on the proxy instance.
     * @return the result of invoking the method on the actual WebElement.
     * @throws Throwable if an exception occurs during method invocation.
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable, Exception {
        try {
            if (method.getName().equals("toString")) {
                return getElementDescription();
            }
            WebElement webElement = findElement();
            locatorDetails = getElementDescription();  // Store locator details
//            logInfo("Accessing element with alias: " + currentAlias); // DEBUG
            return method.invoke(webElement, args);
        } finally {
            alias = "'" + currentAlias + "'";
        }
    }

    /**
     * <b>Creates a proxy for the WebElement.</b>
     *
     * @param driver        the AndroidDriver instance used to interact with elements.
     * @param androidFindBy the AndroidFindBy annotation containing the locator strategy.
     * @param alias         the alias name for the current WebElement.
     * @return a proxy instance for the WebElement.
     */
    public static WebElement createProxy(AndroidDriver driver, AndroidFindBy androidFindBy, String alias) {
        return (WebElement) Proxy.newProxyInstance(
                WebElement.class.getClassLoader(),
                new Class[]{WebElement.class},
                new WebElementProxyHandler(driver, androidFindBy, alias)
        );
    }
}
