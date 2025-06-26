package annotations;

import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IOSFindByAlias {
    iOSXCUITFindBy value(); // Embedding the original AndroidFindBy

    String alias() default ""; // Optional alias defaulting to an empty string
    // TODO fix this with what we have for Android
}
