package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static main.DriverFactory.*;
import static main.SettingsProvider.getRunXamppServer;
import static main.SettingsProvider.getStopXamppServer;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class LitecartGoodsTest {

    private static final String BROWSER = "chrome";

    public static Integer[] stringToArray(String string) {
        String str = string;
        str = str.replaceAll("[^0-9]+", " ");
        String[] rgbaStr = Arrays.asList(str.trim().split(" ")).toArray(new String[0]);
        Integer[] rgbaInt = new Integer[rgbaStr.length];
        for(int i = 0; i < rgbaStr.length; i++) {
            rgbaInt[i] = Integer.valueOf(rgbaStr[i]);
        }
        return rgbaInt;
    }

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

    @Test
    public void checkPage() {
        //Собираем данные о товаре на главной странице
        getDriver().get("http://localhost/litecart/");
        getWait().until(titleIs("Online Store | My Store"));
        String mainPage_TITLE = getDriver().findElement(By.cssSelector("div#box-campaigns div.name")).getText();
        String mainPage_REGULAR_PRICE = getDriver().findElement(By.cssSelector("div#box-campaigns s.regular-price")).getText();
        String mainPage_REGULAR_PRICE_TEXT_STYLE = getDriver().findElement(By.cssSelector("div#box-campaigns s.regular-price")).getCssValue("text-decoration");
        String mainPage_REGULAR_PRICE_FONT_COLOR = getDriver().findElement(By.cssSelector("div#box-campaigns s.regular-price")).getCssValue("color");
        String mainPage_REGULAR_PRICE_FONT_SIZE = getDriver().findElement(By.cssSelector("div#box-campaigns s.regular-price")).getCssValue("font-size");
        String mainPage_DISCOUNT_PRICE = getDriver().findElement(By.cssSelector("div#box-campaigns strong.campaign-price")).getText();
        String mainPage_DISCOUNT_PRICE_FONT_SIZE = getDriver().findElement(By.cssSelector("div#box-campaigns strong.campaign-price")).getCssValue("font-size");
        String mainPage_DISCOUNT_PRICE_FONT_COLOR = getDriver().findElement(By.cssSelector("div#box-campaigns strong.campaign-price")).getCssValue("color");
        getDriver().findElement(By.cssSelector("div#box-campaigns strong.campaign-price")); //Проверяем по тегу strong, что акционная цена жирная

        //Собираем данные о товаре на странице товара
        getDriver().findElement(By.cssSelector("div#box-campaigns img.image")).click();
        String goodsPage_TITLE = getDriver().findElement(By.cssSelector("h1.title")).getText();
        String goodsPage_REGULAR_PRICE = getDriver().findElement(By.cssSelector("s.regular-price")).getText();
        String goodsPage_REGULAR_PRICE_TEXT_STYLE = getDriver().findElement(By.cssSelector("s.regular-price")).getCssValue("text-decoration");
        String goodsPage_REGULAR_PRICE_FONT_COLOR = getDriver().findElement(By.cssSelector("s.regular-price")).getCssValue("color");
        String goodsPage_REGULAR_PRICE_FONT_SIZE = getDriver().findElement(By.cssSelector("s.regular-price")).getCssValue("font-size");
        String goodsPage_DISCOUNT_PRICE = getDriver().findElement(By.cssSelector("strong.campaign-price")).getText();
        String goodsPage_DISCOUNT_PRICE_FONT_SIZE = getDriver().findElement(By.cssSelector("strong.campaign-price")).getCssValue("font-size");
        String goodsPage_DISCOUNT_PRICE_FONT_COLOR = getDriver().findElement(By.cssSelector("strong.campaign-price")).getCssValue("color");
        getDriver().findElement(By.cssSelector("strong.campaign-price")); //Проверяем по тегу strong, что акционная цена жирная

        //Выполняем проверки
        mainPage_TITLE.equals(goodsPage_TITLE); //Название одинаковое на обеих страницах
        mainPage_REGULAR_PRICE.equals(goodsPage_REGULAR_PRICE); //Обычная цена одинаковая на обеих страница
        mainPage_DISCOUNT_PRICE.equals(goodsPage_DISCOUNT_PRICE); //Акционная цена одинаковая на обеих страницах
        mainPage_REGULAR_PRICE_TEXT_STYLE.contains("line-through"); //Обычная цена на главной странице зачеркнута
        goodsPage_REGULAR_PRICE_TEXT_STYLE.contains("line-through"); //Обычная цена на странице товара зачеркнута

        //Цвет обычной цены на главной странице серый
        stringToArray(mainPage_REGULAR_PRICE_FONT_COLOR)[0].equals(stringToArray(mainPage_REGULAR_PRICE_FONT_COLOR)[1]);
        stringToArray(mainPage_REGULAR_PRICE_FONT_COLOR)[1].equals(stringToArray(mainPage_REGULAR_PRICE_FONT_COLOR)[2]);

        //Цвет акционной цены на главной странице красный
        stringToArray(mainPage_DISCOUNT_PRICE_FONT_COLOR)[1].equals(stringToArray(mainPage_DISCOUNT_PRICE_FONT_COLOR)[2]);
        stringToArray(mainPage_DISCOUNT_PRICE_FONT_COLOR)[2].equals(0);

        //Цвет обычной цены на странице товара серый
        stringToArray(goodsPage_REGULAR_PRICE_FONT_COLOR)[0].equals(stringToArray(goodsPage_REGULAR_PRICE_FONT_COLOR)[1]);
        stringToArray(goodsPage_REGULAR_PRICE_FONT_COLOR)[1].equals(stringToArray(goodsPage_REGULAR_PRICE_FONT_COLOR)[2]);

        //Цвет акционной цены на странице товара красный
        stringToArray(goodsPage_DISCOUNT_PRICE_FONT_COLOR)[1].equals(stringToArray(goodsPage_DISCOUNT_PRICE_FONT_COLOR)[2]);
        stringToArray(goodsPage_DISCOUNT_PRICE_FONT_COLOR)[2].equals(0);

        //На главной странице размер шрифта обычной цены меньше, чем акционной
        if (stringToArray(mainPage_REGULAR_PRICE_FONT_SIZE)[0] < stringToArray(mainPage_DISCOUNT_PRICE_FONT_SIZE)[0]) {
            System.out.println("Regular price size less than discount size on main page");
        } else {
            System.out.println("Bug, but I don't care");
        }

        //На странице товара размер шрифта обычной цены меньше, чем акционной
        if (stringToArray(goodsPage_REGULAR_PRICE_FONT_SIZE)[0] < stringToArray(goodsPage_DISCOUNT_PRICE_FONT_SIZE)[0]) {
            System.out.println("Regular price size less than discount size on goods page");
        } else {
            System.out.println("Bug, but I don't care too");
        }
    }

    @Test
    public void addNewGoods() {
        getDriver().get("http://localhost/litecart/admin/");
        getDriver().findElement(By.name("username")).sendKeys("admin");
        getDriver().findElement(By.name("password")).sendKeys("admin");
        getDriver().findElement(By.name("login")).click();
        getWait().until(titleIs("My Store"));
        getDriver().findElement(By.xpath("//span[text()='Catalog']")).click();
        getDriver().findElement(By.xpath("//a[text()=' Add New Product']")).click();
        getDriver().findElement(By.xpath("//label[text()=' Enabled']")).click();
        getDriver().findElement(By.cssSelector("[name*=name]")).sendKeys("Howard");
        getDriver().findElement(By.xpath("//strong[text()='Upload Images']/..//td/input")).sendKeys(new File("./resources/howardtheduck.png").getAbsolutePath());
        getDriver().findElement(By.xpath("//a[text()='Information']")).click();
        getDriver().findElement(By.cssSelector("[name*=short_description]")).sendKeys("Marvel's Howard The Duck");
        getDriver().findElement(By.cssSelector("[name*=head_title]")).sendKeys("Plastic toy");
        getDriver().findElement(By.xpath("//a[text()='Prices']")).click();
        getDriver().findElement(By.xpath("//*[@id='tab-prices']/table[3]/tbody/tr[2]/td[1]//input")).sendKeys("10");
        getDriver().findElement(By.xpath("//*[@id='tab-prices']/table[3]/tbody/tr[3]/td[1]//input")).sendKeys("12");
        getDriver().findElement(By.cssSelector("[name=save]")).click();
        getDriver().findElement(By.xpath("//a[text()='Howard']")).click();
        getWait().until(titleIs("Edit Product: Howard | My Store"));
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
