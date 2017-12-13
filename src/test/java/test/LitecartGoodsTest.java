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

    private static final String BROWSER = "ie";

    @BeforeClass
    public void openBrowser() {
//        try {
//            Runtime.getRuntime().exec(getRunXamppServer());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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

    @Test
    public void checkPage() {
        getDriver().get("http://localhost/litecart/");
        getWait().until(titleIs("Online Store | My Store"));
        //Оформление на главной странице
        String mainPage_TITLE = getDriver().findElement(By.cssSelector("div#box-campaigns div.name")).getText();
        String mainPage_REGULAR_PRICE = getDriver().findElement(By.cssSelector("div#box-campaigns s.regular-price")).getText();
        String mainPage_REGULAR_PRICE_TEXT_STYLE = getDriver().findElement(By.cssSelector("div#box-campaigns s.regular-price")).getCssValue("text-decoration");
        String mainPage_REGULAR_PRICE_COLOR = getDriver().findElement(By.cssSelector("div#box-campaigns s.regular-price")).getCssValue("color");
        String mainPage_DISCOUNT_PRICE = getDriver().findElement(By.cssSelector("div#box-campaigns strong.campaign-price")).getText();
        String mainPage_DISCOUNT_PRICE_TEXT_STYLE = getDriver().findElement(By.cssSelector("div#box-campaigns strong.campaign-price")).getCssValue("font-weight");
        String mainPage_DISCOUNT_PRICE_COLOR = getDriver().findElement(By.cssSelector("div#box-campaigns strong.campaign-price")).getCssValue("color");
        getDriver().findElement(By.cssSelector("div#box-campaigns img.image")).click();
        //Оформление на странице товара
        String goodsPage_TITLE = getDriver().findElement(By.cssSelector("h1.title")).getText();
        String goodsPage_REGULAR_PRICE = getDriver().findElement(By.cssSelector("s.regular-price")).getText();
        String goodsPage_REGULAR_PRICE_TEXT_STYLE = getDriver().findElement(By.cssSelector("s.regular-price")).getCssValue("text-decoration");
        String goodsPage_REGULAR_PRICE_COLOR = getDriver().findElement(By.cssSelector("s.regular-price")).getCssValue("color");
        String goodsPage_DISCOUNT_PRICE = getDriver().findElement(By.cssSelector("strong.campaign-price")).getText();
        String goodsPage_DISCOUNT_PRICE_TEXT_STYLE = getDriver().findElement(By.cssSelector("strong.campaign-price")).getCssValue("font-weight");
        String goodsPage_DISCOUNT_PRICE_COLOR = getDriver().findElement(By.cssSelector("strong.campaign-price")).getCssValue("color");
        //Сравнения
        mainPage_TITLE.equals(goodsPage_TITLE);
        mainPage_REGULAR_PRICE.equals(goodsPage_REGULAR_PRICE);
        mainPage_DISCOUNT_PRICE.equals(goodsPage_DISCOUNT_PRICE);
        mainPage_REGULAR_PRICE_TEXT_STYLE.contains("line-through solid");
        goodsPage_REGULAR_PRICE_TEXT_STYLE.contains("line-through solid");
        mainPage_REGULAR_PRICE_TEXT_STYLE.contains("line-through");
        goodsPage_REGULAR_PRICE_TEXT_STYLE.contains("line-through");
        mainPage_REGULAR_PRICE_COLOR.contains("119, 119, 119");
        goodsPage_REGULAR_PRICE_COLOR.contains("119, 119, 119");
        mainPage_DISCOUNT_PRICE_TEXT_STYLE.equals("700");
        goodsPage_DISCOUNT_PRICE_TEXT_STYLE.equals("700");
        mainPage_DISCOUNT_PRICE_COLOR.contains("204, 0, 0");
        goodsPage_DISCOUNT_PRICE_COLOR.contains("204, 0, 0");
    }

    @AfterClass
    public void closeBrowser() {
//        try {
//            Runtime.getRuntime().exec(getStopXamppServer());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        stopBrowser();
    }
}
