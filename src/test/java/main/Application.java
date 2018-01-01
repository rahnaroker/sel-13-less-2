package main;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

import static main.DriverFactory.getDriver;
import static main.DriverFactory.getWait;

public class Application {

    static boolean isElementPresent(WebDriver driver, By locator) {
        return driver.findElements(locator).size() > 0;
    }

    public static Integer orderSummary;

    public static void fillCart(Integer numOfGoods) {
        getDriver().get("http://localhost/litecart/");
        for (int i = 1; i <= numOfGoods; i++) {
            getDriver().findElement(By.cssSelector("div#box-most-popular li:first-child")).click();
            if (isElementPresent(getDriver(), By.cssSelector("[name^=options]"))) {
                WebElement options = getDriver().findElement(By.cssSelector("[name^=options]"));
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].selectedIndex = 1; arguments[0].dispatchEvent(new Event('change'))", options);
                getDriver().findElement(By.cssSelector("[name=add_cart_product]")).click();
            } else {
                getDriver().findElement(By.cssSelector("[name=add_cart_product]")).click();
            }
            WebElement element = getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='cart']//span[text()='" + i + "']")));
            Assert.assertTrue(element.isDisplayed());
            getDriver().findElement(By.cssSelector("div#logotype-wrapper a")).click();
        }
    }

    public static void clearCart() {
        getDriver().findElement(By.cssSelector("div#cart")).click();
        orderSummary = getDriver().findElements(By.cssSelector("table.dataTable.rounded-corners tr td.item")).size();
        List<WebElement> shortcuts = getDriver().findElements(By.cssSelector("ul.shortcuts li"));
        getDriver().findElement(By.cssSelector("ul.shortcuts li:first-child")).click();
        for (int i = 1; i <= shortcuts.size(); i++) {
            WebElement shortcut = getDriver().findElement(By.xpath("//ul/li[1]/form/a/img"));
            getDriver().findElement(By.xpath("//li[1]//button[text()='Remove']")).click();
            getWait().until(ExpectedConditions.stalenessOf(shortcut));
            orderSummary = orderSummary - 1;
            orderSummary.equals(getDriver().findElements(By.cssSelector("table.dataTable.rounded-corners tr")).size());
        }
    }
}
