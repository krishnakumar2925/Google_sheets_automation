package Levich.google_sheets.baseComponents;

import java.io.IOException;
import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import static Levich.google_sheets.utilities.ExtentReportManager.*;
import static Levich.google_sheets.baseComponents.DriverSingleton.*;

public class BaseTest {
    public static WebDriver driver;
    public String URL = "https://sheets.google.com";

    @BeforeSuite(alwaysRun = true)
    public void reportSetUp() {
        initReport();
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method m) throws IOException {
        driver = launchApplication(URL);
        
        String description = m.getAnnotation(Test.class).description();
        String testname = (description != null && !description.isEmpty()) ? description : m.getName();
        startTest(testname);
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult m) throws IOException {
        if (m.getStatus() == ITestResult.SUCCESS) {
            logPass("TestCase Passed");
        } else if (m.getStatus() == ITestResult.FAILURE) {
            logFail("TestCase Failed - StackTrace " + m.getThrowable().getMessage());
            getExtentTest().addScreenCaptureFromPath(Capture(driver, m.getName()));
        } else {
            logSkip("TestCase Skipped");
        }

        if (driver != null) {
            driver.quit(); // Ensures full cleanup
        }
    }

    @AfterSuite(alwaysRun = true)
    public void teardownReport() {
        flushReport();
    }
}
