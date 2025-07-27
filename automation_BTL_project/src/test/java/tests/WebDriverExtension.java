package tests;

import base.DriverManager;
import org.junit.jupiter.api.extension.*;

public class WebDriverExtension implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        DriverManager.getDriver();
    }

    @Override
    public void afterEach(ExtensionContext context) {
        DriverManager.quitDriver();
    }


}
