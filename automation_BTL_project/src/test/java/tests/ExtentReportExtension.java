package tests;

import base.DriverManager;
import com.aventstack.extentreports.ExtentTest;
import extensions.ExtentReportManager;
import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback, AfterEachCallback {

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        ExtentTest test = ExtentReportManager.getReporter().createTest(context.getDisplayName());
        ExtentTestManager.setTest(test);
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        ExtentTest test = ExtentTestManager.getTest();
        boolean failed = context.getExecutionException().isPresent();
        WebDriver driver = DriverManager.getDriver();

        if (failed) {
            Throwable exception = context.getExecutionException().get();
            if (driver != null) {
                String screenshotRelativePath = takeScreenshot(driver, context.getDisplayName());
                test.fail(exception.getMessage());
                test.addScreenCaptureFromPath(screenshotRelativePath); // ✅ יחסי לדוח
            } else {
                test.fail("Driver is null, cannot take screenshot");
            }
        } else {
            test.pass("Test Passed");
        }
    }

    @Override
    public void afterEach(ExtensionContext context) {
        ExtentReportManager.getReporter().flush();
    }

    private String takeScreenshot(WebDriver driver, String testName) throws IOException {
        String timestamp = new SimpleDateFormat("ddMMyyyy_HHmmss_SSS").format(new Date());
        String safeName = testName.replaceAll("[^a-zA-Z0-9_-]", "_");
        String fileName = safeName + timestamp + ".png";

        // שמור בנתיב test-output/screenshots/
        Path screenshotsDir = Paths.get("test-output", "screenshots");
        Files.createDirectories(screenshotsDir);

        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Path destinationPath = screenshotsDir.resolve(fileName);
        Files.copy(screenshotFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

        // מחזיר נתיב יחסי למיקום של הדוח
        return "screenshots/" + fileName;
    }
}
