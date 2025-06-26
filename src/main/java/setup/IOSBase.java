package setup;

import common.IOSActions;
import io.appium.java_client.ios.IOSDriver;

public class IOSBase extends IOSActions {

    IOSDriver driver;

    public IOSBase(IOSDriver driver) {
        this.driver = driver;
    }
}
