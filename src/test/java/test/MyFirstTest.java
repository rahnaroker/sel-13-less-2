package test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import static test.DriverFactory.*;

public class MyFirstTest {

    private static final String BROWSER = "chrome";

    @BeforeClass
    public void openBrowser() {
        startBrowser(BROWSER);
    }

    @Test
    public void checkSoftwareTestingSite() {

        getDriver(BROWSER).get("http://software-testing.ru/");
        getWait().until(titleIs("Software-Testing.Ru"));
        getDriver(BROWSER).findElement(By.xpath("//span[text()='Библиотека']")).click();
        Assert.assertEquals("Библиотека", getDriver(BROWSER).getTitle());
    }

    @AfterClass
    public void closeBrowser() {
        stopBrowser(BROWSER);
    }
}
