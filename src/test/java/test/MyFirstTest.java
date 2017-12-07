package test;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import static main.DriverFactory.*;

public class MyFirstTest {

    private static final String BROWSER = "ff";

    @BeforeClass
    public void openBrowser() {
        startBrowser(BROWSER);
    }

    @Test
    public void checkSoftwareTestingSite() {
        getDriver().get("http://software-testing.ru/");
        getWait().until(titleIs("Software-Testing.Ru"));
        getDriver().findElement(By.xpath("//span[text()='Библиотека']")).click();
        Assert.assertEquals("Библиотека", getDriver().getTitle());
    }

    @AfterClass
    public void closeBrowser() {
        stopBrowser();
    }
}
