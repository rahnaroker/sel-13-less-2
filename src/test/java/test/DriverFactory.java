package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DriverFactory {

    private static ChromeDriver chromeDriver;
    private static FirefoxDriver geckoDriver;
    private static InternetExplorerDriver ieDriver;
    private static WebDriverWait wait;

    public static WebDriverWait getWait()
    {
        return wait;
    }

    public static void startChrome() {
        chromeDriver = new ChromeDriver();
        wait = new WebDriverWait(chromeDriver, 10);
    }

    public static void startFF() {
        geckoDriver = new FirefoxDriver();
        wait = new WebDriverWait(geckoDriver, 10);
    }

    public static void startIE() {
        ieDriver = new InternetExplorerDriver();
        wait = new WebDriverWait(ieDriver, 10);
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

    public static WebDriver getDriver(String browser) {
        if (browser.equals("chrome")) {
            return chromeDriver;
        } else if (browser.equals("ff")) {
            return geckoDriver;
        } else if (browser.equals("ie")) {
            return ieDriver;
        } else {
            throw new RuntimeException();
        }
    }

    public static void stopBrowser(String browser) {
        if (browser.equals("chrome")) {
            chromeDriver.quit();
            chromeDriver = null;
        } else if (browser.equals("ff")) {
            geckoDriver.quit();
            geckoDriver = null;
        } else if (browser.equals("ie")) {
            ieDriver.quit();
            ieDriver = null;
        } else {
            throw new RuntimeException();
        }
    }
}
