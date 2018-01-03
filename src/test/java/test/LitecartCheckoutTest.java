package test;

import org.testng.Assert;
import org.testng.annotations.Test;

import static main.Application.tableOrders;

public class LitecartCheckoutTest extends TestBase {

    @Test
    public void addGoodsToCart() {
        app.fillCart(3);
        app.clearCart();
        System.out.println(tableOrders);
        Assert.assertTrue(tableOrders.equals(0));
    }
}
