package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.reporters.jq.Main;
import pages.MainPage;
import pages.ShpresaMainPage;

import java.util.Scanner;

public class BaseTestss {

    protected static WebDriver driver;
    protected MainPage mainPage;


    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://gjirafa50.com/");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter product name to search");
        String productName = scanner.nextLine();


        WebElement SearchBox = driver.findElement(By.id("small-searchterms"));
        SearchBox.sendKeys(productName);

        WebElement searchButton = driver.findElement(By.xpath("//button[@title='Kërko' and contains (@class, 'icon-search-find-alt')]"));
        searchButton.click();

        System.out.println(driver.getTitle());
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


    public void searchProduct(String productName) {
        mainPage.searchForProduct(productName);
    }


    public static void main(String args[]) {
        BaseTestss test = new BaseTestss();
        test.setUp();

        MainPage mainPage1 = new MainPage(driver);
        mainPage1.findCheapestProduct();

       driver.get("https://shop.shpresa.al/");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a name to look for");
        String productName = scanner.nextLine();


        WebElement SearchFill = driver.findElement(By.id("esSearchInput"));
        SearchFill.sendKeys(productName);

        WebElement buttonSearch = driver.findElement(By.xpath("//button[contains(@class, 'font-bold') and contains(@class, 'py-2') and contains(@class, 'px-4') and contains(@class, 'rounded-r') and contains(@class, 'bg-gray-200') and contains(@class, 'text-gray-800')]"));
        buttonSearch.click();

        WebElement clickForMore = driver.findElement(By.xpath("/html/body/div[1]/header/div[2]/div/div[2]/div/div/div/div/div[3]/div/div[1]/div/div[1]/div/a"));
        clickForMore.sendKeys();

        System.out.println(driver.getTitle());
    }

    public void setUpp() {
        BaseTestss testt = new BaseTestss();
        testt.setUpp();
        ShpresaMainPage mainShpresa = new ShpresaMainPage(driver);
        mainShpresa.findInexpensiveProduct();

    }


}



