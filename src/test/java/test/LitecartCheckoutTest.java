package test;

import main.Application;
import org.testng.Assert;
import org.testng.annotations.*;

import static constant.BrowserConstants.Chrome;
import static main.Application.orderSummary;
import static main.DriverFactory.startBrowser;
import static main.DriverFactory.stopBrowser;

public class LitecartCheckoutTest {

    @BeforeClass
    public void openBrowser() {
//        startXAMPP();
        startBrowser(Chrome);
    }

    @Test
    public void addGoodsToCart() {
        Application.fillCart(3);
        Application.clearCart();
        Assert.assertTrue(orderSummary.equals(0));
    }

    @AfterClass
    public void closeBrowser() {
//        stopXAMPP();
        stopBrowser();
    }
}
