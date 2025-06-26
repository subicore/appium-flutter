package utilities;

import annotations.AndroidFindByWithAlias;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;

import static common.AppiumUtils.logError;


/**
 * <b>Processes custom annotations and injects proxy WebElement instances.</b>
 * <p>This class is responsible for processing the custom `@AndroidFindByWithAlias` annotations on page object fields. It creates proxy WebElement instances and injects them into the annotated fields.</p>
 */
public class AnnotationProcessor {

    public AndroidDriver driver;

    public AnnotationProcessor(AndroidDriver driver) {
        this.driver = driver;
    }

    /**
     * <b>Processes the annotations on the provided page object, creates proxy WebElement instances, and injects them into the fields.</b>
     * <p>This method scans the provided page object for fields annotated with `@AndroidFindByWithAlias`, creates proxy WebElement instances for them, and injects these proxies into the fields.</p>
     *
     * @param page the page object containing the fields to be processed.
     */
    public void processAnnotations(Object page) {
        try {
            Field[] fields = page.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(AndroidFindByWithAlias.class)) {
                    AndroidFindByWithAlias annotation = field.getAnnotation(AndroidFindByWithAlias.class);
                    String currentAlias = annotation.alias();
                    AndroidFindBy androidFindBy = annotation.androidFindBy();

                    // Create a proxy WebElement
                    WebElement webElement = WebElementProxyHandler.createProxy(driver, androidFindBy, currentAlias);

                    // Set proxy WebElement to the field
                    field.setAccessible(true);
                    field.set(page, webElement);
//                    logInfo(currentAlias + ", WebElement proxy created."); // DEBUG - non-proxy currentAlias
                }
            }
        } catch (Exception e) {
            logError("[EXCEPTION] " + e.getClass().getName() + " | Message --> " + e.getClass().getSimpleName());
        }
    }
}

