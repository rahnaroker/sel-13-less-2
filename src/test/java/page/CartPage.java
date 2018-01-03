package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CartPage extends Page {
    @FindBy(css = "table.dataTable.rounded-corners tr td.item")
    public List<WebElement> tableOrders;
    @FindBy(css = "ul.shortcuts li")
    public List<WebElement> carouselOrders;
    @FindBy(css="ul.shortcuts li:first-child")
    public WebElement firstCarouselOrder;
    @FindBy(xpath="//ul/li[1]/form/a/img")
    public WebElement carouselOrderImage;
    @FindBy(xpath="//li[1]//button[text()='Remove']")
    public WebElement removeButton;

        public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    public void open() {
        driver.get("http://localhost/litecart/checkout");
    }
}