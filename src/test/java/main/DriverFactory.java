package main;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static constant.BrowserConstants.FF_DEV_PATH;
import static constant.BrowserConstants.FF_ESR_PATH;

public class DriverFactory {
    
    private static WebDriver driver;
    private static WebDriverWait wait;

    public static WebDriverWait getWait()
    {
        return wait;
    }

    public static void startChrome() {
        driver = new ChromeDriver();
    }

    public static void startFF() {
        driver = new FirefoxDriver();
    }

    public static void startFF_DEV() {
        FirefoxOptions options = new FirefoxOptions().setBinary(FF_DEV_PATH);
        driver = new FirefoxDriver(options);
    }

    public static void startFF_ESR() {
        FirefoxOptions options = new FirefoxOptions().setLegacy(true).setBinary(FF_ESR_PATH);
        driver = new FirefoxDriver(options);
    }

    public static void startIE() {
        driver = new InternetExplorerDriver();
    }

    public static void startBrowser(String browser) {
        if (browser.equals("chrome")) {
            startChrome();
        } else if (browser.equals("ff")) {
            startFF();
        } else if (browser.equals("ie")) {
            startIE();
        } else if (browser.equals("ff_dev")) {
            startFF_DEV();
        } else if (browser.equals("ff_esr")) {
            startFF_ESR();
        } else {
            throw new RuntimeException();
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static void stopBrowser() {
        driver.quit();
        driver = null;
    }
}
