package test;

import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static main.DriverFactory.*;
import static main.SettingsProvider.getRunXamppServer;
import static main.SettingsProvider.getStopXamppServer;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class LitecartMenuTest {

    private static final String BROWSER = "chrome";

    private static final String MENU_XPATH_SELECTOR = "//span[text()='%s']";

    private static final String SUB_MENU_CSS_SELECTOR = "li#%s";

    private static final String HEADER_XPATH_SELECTOR = "//h1[text()=' %s']";

    public void menuHeaderIs(String menu, String subMenu, String header) {
        getDriver().findElement(By.xpath(String.format(MENU_XPATH_SELECTOR, menu))).click();
        if (!subMenu.equals("")) {
            getDriver().findElement(By.cssSelector(String.format(SUB_MENU_CSS_SELECTOR, subMenu))).click();
        }
        getDriver().findElement(By.xpath(String.format(HEADER_XPATH_SELECTOR, header)));
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
    public void checkMenuTree() {
        getDriver().get("http://localhost/litecart/admin/");
        getDriver().findElement(By.name("username")).sendKeys("admin");
        getDriver().findElement(By.name("password")).sendKeys("admin");
        getDriver().findElement(By.name("login")).click();
        getWait().until(titleIs("My Store"));
        menuHeaderIs("Appearence", "doc-template", "Template");
        menuHeaderIs("Appearence", "doc-logotype", "Logotype");
        menuHeaderIs("Catalog", "doc-catalog", "Catalog");
        menuHeaderIs("Catalog", "doc-product_groups", "Product Groups");
        menuHeaderIs("Catalog", "doc-option_groups", "Option Groups");
        menuHeaderIs("Catalog", "doc-manufacturers", "Manufacturers");
        menuHeaderIs("Catalog", "doc-suppliers", "Suppliers");
        menuHeaderIs("Catalog", "doc-delivery_statuses", "Delivery Statuses");
        menuHeaderIs("Catalog", "doc-sold_out_statuses", "Sold Out Statuses");
        menuHeaderIs("Catalog", "doc-quantity_units", "Quantity Units");
        menuHeaderIs("Catalog", "doc-csv", "CSV Import/Export");
        menuHeaderIs("Countries", "", "Countries");
        menuHeaderIs("Currencies", "", "Currencies");
        menuHeaderIs("Customers", "doc-customers", "Customers");
        menuHeaderIs("Customers", "doc-csv", "CSV Import/Export");
        menuHeaderIs("Customers", "doc-newsletter", "Newsletter");
        menuHeaderIs("Geo Zones", "", "Geo Zones");
        menuHeaderIs("Languages", "doc-languages", "Languages");
        menuHeaderIs("Storage Encoding", "doc-storage_encoding", "Storage Encoding");
        menuHeaderIs("Modules", "doc-jobs", "Job Modules");
        menuHeaderIs("Modules", "doc-customer", "Customer Modules");
        menuHeaderIs("Modules", "doc-shipping", "Shipping Modules");
        menuHeaderIs("Modules", "doc-payment", "Payment Modules");
        menuHeaderIs("Modules", "doc-order_total", "Order Total Modules");
        menuHeaderIs("Modules", "doc-order_success", "Order Success Modules");
        menuHeaderIs("Modules", "doc-order_action", "Order Action Modules");
        menuHeaderIs("Orders", "doc-orders", "Orders");
        menuHeaderIs("Orders", "doc-order_statuses", "Order Statuses");
        menuHeaderIs("Pages", "", "Pages");
        menuHeaderIs("Reports", "doc-monthly_sales", "Monthly Sales");
        menuHeaderIs("Reports", "doc-most_sold_products", "Most Sold Products");
        menuHeaderIs("Reports", "doc-most_shopping_customers", "Most Shopping Customers");
        menuHeaderIs("Settings", "doc-store_info", "Settings");
        menuHeaderIs("Settings", "doc-defaults", "Settings");
        menuHeaderIs("Settings", "doc-general", "Settings");
        menuHeaderIs("Settings", "doc-listings", "Settings");
        menuHeaderIs("Settings", "doc-images", "Settings");
        menuHeaderIs("Settings", "doc-checkout", "Settings");
        menuHeaderIs("Settings", "doc-advanced", "Settings");
        menuHeaderIs("Settings", "doc-security", "Settings");
        menuHeaderIs("Slides", "", "Slides");
        menuHeaderIs("Tax", "doc-tax_classes", "Tax Classes");
        menuHeaderIs("Tax", "doc-tax_rates", "Tax Rates");
        menuHeaderIs("Translations", "doc-search", "Search Translations");
        menuHeaderIs("Translations", "doc-scan", "Scan Files For Translations");
        menuHeaderIs("Translations", "doc-csv", "CSV Import/Export");
        menuHeaderIs("Users", "", "Users");
        menuHeaderIs("vQmods", "doc-vqmods", "vQmods");
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
