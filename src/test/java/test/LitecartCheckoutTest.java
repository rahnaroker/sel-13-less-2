package test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

public class LitecartCheckoutTest {

    private static final String BROWSER = "chrome";

    boolean isElementPresent(WebDriver driver, By locator) {
        return driver.findElements(locator).size() > 0;
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
    public void addGoodsToCart() {
        getDriver().get("http://localhost/litecart/");
        getWait().until(titleIs("Online Store | My Store"));
        for (int i = 0; i < 4; i++) {
            getDriver().findElement(By.cssSelector("div#box-most-popular li:first-child")).click();
            //Выбираем обязательный к заполнению параметр, если у товара таковой есть
            if (isElementPresent(getDriver(), By.cssSelector("[name^=options]"))) {
                WebElement options = getDriver().findElement(By.cssSelector("[name^=options]"));
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].selectedIndex = 1; arguments[0].dispatchEvent(new Event('change'))", options);
            }
            getDriver().findElement(By.cssSelector("[name=add_cart_product]")).click();
            WebElement element = getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='cart']//span[text()='" + i + "']")));
            Assert.assertTrue(element.isDisplayed());
            getDriver().findElement(By.cssSelector("div#logotype-wrapper a")).click();
        }
        getDriver().findElement(By.cssSelector("div#cart")).click();
        Integer orderSummary = getDriver().findElements(By.cssSelector("table.dataTable.rounded-corners tr")).size();
        List<WebElement> shortcuts = getDriver().findElements(By.cssSelector("ul.shortcuts li"));
        getDriver().findElement(By.cssSelector("ul.shortcuts li:first-child")).click();
        for (int i = 1; i <= shortcuts.size(); i++) {
            WebElement shortcut = getDriver().findElement(By.xpath("//ul/li[1]/form/a/img"));
            getDriver().findElement(By.xpath("//li[1]//button[text()='Remove']")).click();
            getWait().until(ExpectedConditions.stalenessOf(shortcut));
            orderSummary = orderSummary - 1;
            //Проверяем, что после удаления товара из корзины, товар удаляется и из таблицы
            orderSummary.equals(getDriver().findElements(By.cssSelector("table.dataTable.rounded-corners tr")).size());
        }
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
