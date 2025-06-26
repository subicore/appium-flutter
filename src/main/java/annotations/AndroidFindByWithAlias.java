package annotations;

import io.appium.java_client.pagefactory.AndroidFindBy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <b>This annotation combines AndroidFindBy with an alias for better logging and reporting.
 * <ul>
 * <li> <b>androidFindBy:</b> The locator strategy for the element.</li>
 * <li> <b>alias:</b> The alias to be used for logging and reporting.</li>
 * </ul></b>
 * <code>DO NOT CHANGE ANYTHING HERE.</code>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface AndroidFindByWithAlias {
    AndroidFindBy androidFindBy();

    String alias();
}
