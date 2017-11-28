package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class LitecartLoginTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private String runServerString = "C:\\xampp\\xampp_start.exe";
    private String stopServerString = "C:\\xampp\\xampp_stop.exe";

    public String getRunServerString() {
        return runServerString;
    }

    public String getStopServerString() {
        return stopServerString;
    }

    @BeforeClass
    public void openBrowser() {
        try {
            Runtime.getRuntime().exec(getRunServerString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.setProperty("webdriver.chrome.driver","C:\\Program Files\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void checkLitecortLogin() throws InterruptedException {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        wait.until(titleIs("My Store"));
    }

    @AfterClass
    public void closeBrowser() {
        driver.quit();
        driver = null;
        try {
            Runtime.getRuntime().exec(getStopServerString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
