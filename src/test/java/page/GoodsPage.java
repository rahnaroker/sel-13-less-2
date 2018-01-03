package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GoodsPage  extends Page {

    public GoodsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(name="add_cart_product")
    public WebElement addToCart;

    @FindBy(css="div#logotype-wrapper a")
    public WebElement logo;
}
