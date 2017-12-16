package test;

import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.UUID;

import static main.SettingsProvider.getRunXamppServer;
import static main.SettingsProvider.getStopXamppServer;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import static main.DriverFactory.*;

public class LitecartLoginTest {

    private static final String EMAIL = "test" + generateString() + "@mail.com";
    private static final String PASSWORD = generateString();
    private static final String BROWSER = "chrome";

    public static String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
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
    public void checkLitecortLogin() {
        getDriver().get("http://localhost/litecart/admin/");
        getDriver().findElement(By.name("username")).sendKeys("admin");
        getDriver().findElement(By.name("password")).sendKeys("admin");
        getDriver().findElement(By.name("login")).click();
        getWait().until(titleIs("My Store"));
    }

    @Test
    public void createNewAccount() {
        getDriver().get("http://localhost/litecart/");
        getDriver().findElement(By.cssSelector("[name=login_form] a")).click();
        getDriver().findElement(By.cssSelector("[name=firstname]")).sendKeys("John");
        getDriver().findElement(By.cssSelector("[name=lastname]")).sendKeys("Doe");
        getDriver().findElement(By.cssSelector("[name=address1]")).sendKeys("the Earth");
        getDriver().findElement(By.cssSelector("[name=postcode]")).sendKeys("001002");
        getDriver().findElement(By.cssSelector("[name=city]")).sendKeys("N");
        getDriver().findElement(By.cssSelector("[name=email]")).sendKeys(EMAIL);
        getDriver().findElement(By.cssSelector("[name=phone]")).sendKeys("+74815162342");
        getDriver().findElement(By.cssSelector("[name=password]")).sendKeys(PASSWORD);
        getDriver().findElement(By.cssSelector("[name=confirmed_password]")).sendKeys(PASSWORD);
        getDriver().findElement(By.cssSelector("[name=create_account]")).click();
        getDriver().findElement(By.cssSelector("div#notices > div")); //Заходим после создания учетной записи
        getDriver().findElement(By.cssSelector("div#box-account li:last-child a")).click();
        getDriver().findElement(By.cssSelector("div#notices > div")); //Выходим
        getDriver().findElement(By.cssSelector("[name=email]")).sendKeys(EMAIL);
        getDriver().findElement(By.cssSelector("[name=password]")).sendKeys(PASSWORD);
        getDriver().findElement(By.cssSelector("[name=login]")).click();
        getDriver().findElement(By.cssSelector("div#notices > div")); //Повторно заходим
        getDriver().findElement(By.cssSelector("div#box-account li:last-child a")).click();
        getDriver().findElement(By.cssSelector("div#notices > div")); //Повторно выходим
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
