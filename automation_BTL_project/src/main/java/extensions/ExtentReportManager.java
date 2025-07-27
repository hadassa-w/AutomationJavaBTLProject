package extensions;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {
    private static ExtentReports extent;

    public static ExtentReports getReporter() {
        if (extent == null) {
            String timestamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
            ExtentSparkReporter htmlReporter = new ExtentSparkReporter("test-output/extent-report_" + timestamp + ".html");
            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
        }
        return extent;
    }
}
