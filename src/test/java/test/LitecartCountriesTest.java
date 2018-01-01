package test;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.*;

import static constant.BrowserConstants.Chrome;
import static main.DriverFactory.*;
import static main.SettingsProvider.*;

public class LitecartCountriesTest {

    public void sortedListChecker(String cssSelector, List webelementList) {

        ArrayList<String> obtainedList = new ArrayList<>();
        List<WebElement> elementList = webelementList;

        for (WebElement webElement : elementList) {
            obtainedList.add(webElement.findElement(By.cssSelector(cssSelector)).getAttribute("textContent"));
        }
        ArrayList<String> sortedList = new ArrayList<>();
        for (String s : obtainedList) {
            sortedList.add(s);
        }
        Collections.sort(obtainedList);
        Assert.assertTrue(sortedList.equals(obtainedList));
    }

    @BeforeClass
    public void openBrowser() {
        startXAMPP();
        startBrowser(Chrome);
        getDriver().get("http://localhost/litecart/admin/");
        getDriver().findElement(By.name("username")).sendKeys("admin");
        getDriver().findElement(By.name("password")).sendKeys("admin");
        getDriver().findElement(By.name("login")).click();
    }

    @Test
    public void clickCountriesLinks() {
        Integer numOfTabs = 0;
        getDriver().findElement(By.xpath("//span[text()='Countries']")).click();
        getDriver().findElement(By.cssSelector("a.button")).click();
        String mainWindow = getDriver().getWindowHandle();
        List<WebElement> links = getDriver().findElements(By.cssSelector("td#content tbody [target=_blank]"));
        for (WebElement webElement : links) {
            webElement.click();
            Set<String> windows = getDriver().getWindowHandles();
            for (String window : windows) {
                if (!window.equals(mainWindow)) {
                    getDriver().switchTo().window(window);
                    numOfTabs++;
                    getDriver().close();
                    getDriver().switchTo().window(mainWindow);
                }
            }
        }
        Assert.assertTrue(numOfTabs.equals(links.size()));
    }

    @Test
    public void checkCountriesAlphabeticalOrder() {
        getDriver().findElement(By.xpath("//span[text()='Countries']")).click();

        //Получаем список стран
        List<WebElement> countries = getDriver().findElements(By.cssSelector("table.dataTable tr.row"));

        //Проверяем алфавитный порядок стран
        sortedListChecker("td:nth-child(5) > a:first-child", countries);

        //Удаляем страны без зон из списка
        for (Iterator<WebElement> iter = countries.listIterator(); iter.hasNext(); ) {
            WebElement w = iter.next();
            if (w.findElement(By.cssSelector("td:nth-child(6)")).getAttribute("textContent").equals("0")) {
                iter.remove();
            }
        }

        //Создаем список кодов стран с зонами
        ArrayList<String> countriesTZ = new ArrayList<>();
        for (WebElement webElement : countries) {
            countriesTZ.add(webElement.findElement(By.cssSelector("td:nth-child(4)")).getAttribute("textContent"));
        }

        //Проверяем алфавитный порядок зон
        for (String s : countriesTZ) {
            getDriver().get("http://localhost/litecart/admin/?app=countries&doc=edit_country&country_code=" + s);
            List<WebElement> zones = getDriver().findElements(By.cssSelector("table#table-zones > tbody > tr td:nth-child(3)"));
            sortedListChecker("input", zones);
        }
    }

    @Test
    public void checkGeoZonesAlphabeticalOrder() {
        getDriver().findElement(By.xpath("//span[text()='Geo Zones']")).click();

        //Создаем список ссылок на страны с геозонами
        ArrayList<String> geoZoneLinks = new ArrayList<>();
        List<WebElement> countries = getDriver().findElements(By.cssSelector("tr.row td:nth-child(3) a"));
        for (WebElement webElement : countries) {
            geoZoneLinks.add(webElement.getAttribute("href"));
        }

        //Проверяем алфавитный порядок гео зон
        for (String s : geoZoneLinks) {
            getDriver().get(s);
            List<WebElement> geozones = getDriver().findElements(By.cssSelector("[name*=zone_code]"));
            sortedListChecker("[selected=selected]", geozones);
        }
    }

    @AfterClass
    public void closeBrowser() {
        stopXAMPP();
        stopBrowser();
    }
}
