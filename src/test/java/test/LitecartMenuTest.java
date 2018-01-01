package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static constant.BrowserConstants.Chrome;
import static main.DriverFactory.*;
import static main.SettingsProvider.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class LitecartMenuTest {

    @BeforeClass
    public void openBrowser() {
        startXAMPP();
        startBrowser(Chrome);
    }

    @Test
    public void checkMenuTree_Loop() {
        getDriver().get("http://localhost/litecart/admin/");
        getDriver().findElement(By.name("username")).sendKeys("admin");
        getDriver().findElement(By.name("password")).sendKeys("admin");
        getDriver().findElement(By.name("login")).click();
        getWait().until(titleIs("My Store"));

        List<WebElement> menus = getDriver().findElements(By.xpath("//ul[@id='box-apps-menu']/li"));

        for (int i = 1; i <= menus.size(); i++)
        {
            WebElement menu = getDriver().findElement(By.xpath("//ul[@id='box-apps-menu']/li[" + i + "]/a"));
            menu.click();
            getDriver().findElement(By.cssSelector("h1"));

            List <WebElement> subMenus = getDriver().findElements(By.xpath("//ul[@id='box-apps-menu']/li[" + i + "]/ul/li"));

            for (int j = 1; j <= subMenus.size(); j++) {
                WebElement subMenu = getDriver().findElement(By.xpath("//ul[@id='box-apps-menu']/li[" + i + "]/ul/li[" + j +"]"));
                subMenu.click();
                getDriver().findElement(By.cssSelector("h1"));
            }
        }
    }

    @AfterClass
    public void closeBrowser() {
        stopXAMPP();
        stopBrowser();
    }
}
