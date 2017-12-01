package test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class MyFirstTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void openBrowser() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void checkSoftwareTestingSite() throws InterruptedException {
        driver.get("http://software-testing.ru/");
        wait.until(titleIs("Software-Testing.Ru"));
        driver.findElement(By.xpath("//span[text()='Библиотека']")).click();
        Assert.assertEquals("Библиотека", driver.getTitle());
    }

    @AfterClass
    public void closeBrowser() {
        driver.quit();
        driver = null;
    }
}
