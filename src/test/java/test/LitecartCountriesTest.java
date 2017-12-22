package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static main.DriverFactory.getDriver;
import static main.DriverFactory.startBrowser;
import static main.DriverFactory.stopBrowser;
import static main.SettingsProvider.getRunXamppServer;
import static main.SettingsProvider.getStopXamppServer;

public class LitecartCountriesTest {

    private static final String BROWSER = "chrome";

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
        try {
            Runtime.getRuntime().exec(getRunXamppServer());
        } catch (IOException e) {
            e.printStackTrace();
        }
        startBrowser(BROWSER);
        getDriver().get("http://localhost/litecart/admin/");
        getDriver().findElement(By.name("username")).sendKeys("admin");
        getDriver().findElement(By.name("password")).sendKeys("admin");
        getDriver().findElement(By.name("login")).click();
    }

    @Test
    public void checkCountriesAlphabeticalOrder() {
        getDriver().findElement(By.xpath("//span[text()='Countries']")).click();

        //Получаем список стран
        List<WebElement> countries = getDriver().findElements(By.cssSelector("table.dataTable tr.row"));

        //Проверяем алфавитный порядок стран
        sortedListChecker("td:nth-child(5) > a:first-child", countries);

        //Удаляем страны без зон из списка
        for(Iterator<WebElement> iter = countries.listIterator(); iter.hasNext();) {
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
        for(String s : countriesTZ) {
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
        try {
            Runtime.getRuntime().exec(getStopXamppServer());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stopBrowser();
    }
}
