package Levich.google_sheets.baseComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverSingleton {

    public static WebDriver driverInstance() throws IOException {
        WebDriver driver = null;
        Properties pro = new Properties();
        FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/Levich/google_sheets/utilities/GlobalData.properties");
        pro.load(file);

        String browser = pro.getProperty("browser");

        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();

                // Set the path to the Chrome user profile to reuse login
                String userProfilePath = System.getProperty("user.dir") + "/ChromeProfile";
                options.addArguments("user-data-dir=" + userProfilePath);
                options.addArguments("--start-maximized");
                options.addArguments("--remote-allow-origins=*");

                driver = new ChromeDriver(options);
                break;

            case "safari":
                WebDriverManager.safaridriver().setup();
                driver = new SafariDriver();
                break;

            default:
                Assert.fail("Invalid Browser specified in properties file");
                break;
        }

        if (driver != null) {
            driver.manage().deleteAllCookies();
        }

        return driver;
    }

    public static WebDriver launchApplication(String URL) throws IOException {
        WebDriver driver = driverInstance();
        if (driver != null) {
            driver.get(URL);
        }
        return driver;
    }

    public static String Capture(WebDriver driver, String FileName) throws IOException {
        String path = System.getProperty("user.dir") + "/Screenshots";
        File tkdir = new File(path);
        if (!tkdir.exists()) {
            tkdir.mkdir();
        }

        File scFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File decFile = new File(path + "/" + FileName + ".png");
        FileUtils.copyFile(scFile, decFile);
        return path + "/" + FileName + ".png";
    }
}
