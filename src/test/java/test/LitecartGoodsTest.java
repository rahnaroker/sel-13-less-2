package test;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

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
        /* Количество картинок с товарами совпадает с количеством стикеров на этих картинках */
        Assert.assertEquals(getDriver().findElements(By.cssSelector("div.image-wrapper > img.image")).size(),
                getDriver().findElements(By.cssSelector("div.image-wrapper > div.sticker")).size());

    }

    @AfterClass
    public void closeBrowser() {
        stopBrowser();
        try {
            Runtime.getRuntime().exec(getStopXamppServer());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
