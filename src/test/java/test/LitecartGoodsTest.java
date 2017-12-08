package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static main.DriverFactory.*;
import static main.SettingsProvider.getRunXamppServer;
import static main.SettingsProvider.getStopXamppServer;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class LitecartGoodsTest {

    private static final String BROWSER = "chrome";

    @BeforeClass
    public void openBrowser() {
        try {
            Runtime.getRuntime().exec(getRunXamppServer());
        } catch (IOException e) {
            e.printStackTrace();
        }
        startBrowser(BROWSER);
    }

    @Test
    public void checkStickers() {
        getDriver().get("http://localhost/litecart/");
        getWait().until(titleIs("Online Store | My Store"));

        int numOfStickeres = 0;
        getDriver().findElements(By.cssSelector("li.product")).equals(numOfStickeres);

        List<WebElement> popularProducts = getDriver().findElements(By.cssSelector("div#box-most-popular li"));
        for (int i = 1; i <= popularProducts.size(); i++)
        {
            WebElement popProduct = getDriver().findElement(By.cssSelector("div#box-most-popular li:nth-of-type(" + i + ") div.sticker"));
            Assert.assertTrue(popProduct.isDisplayed());
            numOfStickeres++;
        }

        List<WebElement> campaignsProducts = getDriver().findElements(By.cssSelector("div#box-campaigns li"));
        for (int j = 1; j <= campaignsProducts.size(); j++)
        {
            WebElement campProduct = getDriver().findElement(By.cssSelector("div#box-campaigns li:nth-of-type(" + j + ") div.sticker"));
            Assert.assertTrue(campProduct.isDisplayed());
            numOfStickeres++;
        }

        List<WebElement> latestProducts = getDriver().findElements(By.cssSelector("div#box-latest-products li"));
        for (int k = 1; k <= latestProducts.size(); k++)
        {
            WebElement latProduct = getDriver().findElement(By.cssSelector("div#box-latest-products li:nth-of-type(" + k + ") div.sticker"));
            Assert.assertTrue(latProduct.isDisplayed());
            numOfStickeres++;
        }

        System.out.println("Total Amount of stickered Products is " + numOfStickeres);
    }

    @AfterClass
    public void closeBrowser() {
        try {
            Runtime.getRuntime().exec(getStopXamppServer());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stopBrowser();
    }
}
