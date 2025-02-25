package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MainPage {

    private WebDriver driver;
    private By searchBox = By.id("small-searchterms");
    private By searchButton = By.cssSelector("button[title='Kërko']=");

    public MainPage(WebDriver driver) {
        this.driver = driver;

    }

    public void searchForProduct(String productName) {
        WebElement searchBox = driver.findElement(By.id("small-searchterms"));
        searchBox.sendKeys(productName);
    }


    public void findCheapestProduct() {
        Map<WebElement, Double> productPriceMap = new HashMap<>();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        boolean nextPage = true;

        while (nextPage) {
            List<WebElement> productContainer = driver.findElements(By.xpath("//div[contains(@class, 'item-box')]"));

            for (WebElement product : productContainer) {
                try {
                    WebElement priceElement = product.findElement(By.xpath(".//span[contains(@class, 'price') and contains(@class, 'font-semibold') and contains(@class, 'text-gray-700') and contains(@class, 'text-base')]"));
                    String priceText = priceElement.getText().replaceAll("€", "").replaceAll(",", "").trim();
                    System.out.println("Found price: " + priceText);

                    if (!priceText.isEmpty()) {
                        double price = Double.parseDouble(priceText);
                        productPriceMap.put(product, price);
                    }
                } catch (Exception e) {
                    System.out.println("Error parsing product price: " + e.getMessage());
                }
            }

            try {
                WebElement nextPageButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(@class, 'load-more-products-btn')]")));

                if (nextPageButton != null && nextPageButton.isDisplayed()) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextPageButton);
                    wait.until(ExpectedConditions.stalenessOf(productContainer.get(productContainer.size() - 1)));  // Wait for the next page to load
                } else {
                    nextPage = false;
                }
            } catch (TimeoutException e) {
                nextPage = false;
            } catch (Exception e) {
                System.out.println("Error navigating to the next page: " + e.getMessage());
                nextPage = false;
            }
        }

        List<Map.Entry<WebElement, Double>> sortedEntries = new ArrayList<>(productPriceMap.entrySet());
        sortedEntries.sort(Map.Entry.comparingByValue());

        if (!sortedEntries.isEmpty()) {
            WebElement cheapestProduct = sortedEntries.get(0).getKey();
            WebElement cheapestProductLink = cheapestProduct.findElement(By.xpath(".//a[@class='relative block']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cheapestProductLink);

            double cheapestPrice = sortedEntries.get(0).getValue();
            System.out.println("Cheapest product price: €" + cheapestPrice);
        }
    }
}