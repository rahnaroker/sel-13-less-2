package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DriverFactory {
    
    private static WebDriver driver;
    private static WebDriverWait wait;

    public static WebDriverWait getWait()
    {
        return wait;
    }

    public static void startChrome() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    public static void startFF() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10);
    }

    public static void startIE() {
        driver = new InternetExplorerDriver();
        wait = new WebDriverWait(driver, 10);
    }

    public static void startBrowser(String browser) {
        if (browser.equals("chrome")) {
            startChrome();
        } else if (browser.equals("ff")) {
            startFF();
        } else if (browser.equals("ie")) {
            startIE();
        } else {
            throw new RuntimeException();
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static void stopBrowser() {
        driver.quit();
        driver = null;
    }
}
