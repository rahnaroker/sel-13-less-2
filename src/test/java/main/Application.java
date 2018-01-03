package main;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import page.CartPage;
import page.GoodsPage;
import page.MainPage;

import java.util.concurrent.TimeUnit;

import static main.SettingsProvider.stopXAMPP;

public class Application {

    private WebDriver driver;
    private WebDriverWait wait;

    private MainPage mainPage;
    private GoodsPage goodsPage;
    private CartPage cartPage;

    public static Integer tableOrders;

    public Application() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);

        mainPage = new MainPage(driver);
        goodsPage = new GoodsPage(driver);
        cartPage = new CartPage(driver);
    }

    public void quit() {
        driver.quit();
        stopXAMPP();
    }

    private boolean isElementPresent(WebDriver driver, By locator) {
        return driver.findElements(locator).size() > 0;
    }

    public void fillCart(Integer numOfGoods) {
        mainPage.open();
        for (int i = 1; i <= numOfGoods; i++) {
            mainPage.firstGoods.click();
            if (isElementPresent(driver, By.cssSelector("[name^=options]"))) {
                WebElement options = driver.findElement(By.cssSelector("[name^=options]"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].selectedIndex = 1; arguments[0].dispatchEvent(new Event('change'))", options);
                goodsPage.addToCart.click();
            } else {
                goodsPage.addToCart.click();
            }
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='cart']//span[text()='" + i + "']")));
            Assert.assertTrue(element.isDisplayed());
            goodsPage.logo.click();
        }
    }

    public void clearCart() {
        cartPage.open();
        tableOrders = cartPage.tableOrders.size();
        cartPage.firstCarouselOrder.click();
        for (int i = 1; i <= cartPage.carouselOrders.size(); i++) {
            WebElement shortcut = driver.findElement(By.xpath("//ul/li[1]/form/a/img"));
            cartPage.removeButton.click();
            wait.until(ExpectedConditions.stalenessOf(shortcut));
            tableOrders = tableOrders - 1;
            tableOrders.equals(cartPage.tableOrders.size());
        }
    }
}
