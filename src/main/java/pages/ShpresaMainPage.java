package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShpresaMainPage {

    private WebDriver driver;
   private By searchFill = By.id("esSearchInput");
   private By buttonSearch = By.xpath("//button[contains(@class, 'font-bold') and contains(@class, 'py-2') and contains(@class, 'px-4') and contains(@class, 'rounded-r') and contains(@class, 'bg-gray-200') and contains(@class, 'text-gray-800')]");

    public ShpresaMainPage (WebDriver driver) {
       // this.driver = driver;

    }



    public void findInexpensiveProduct() {
        Map<WebElement, Double> itemPriceMap = new HashMap<>();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        boolean loadMore = true;

        while (loadMore) {
            List<WebElement> containerOfProduct = driver.findElements(By.xpath("//*[@id=main]/div/div[2]/div[1]"));

            for (WebElement item : containerOfProduct) {
                try {
                    WebElement priceElement = item.findElement(By.xpath(" //*[@id=main]/div/div[2]/div[2]/div/div[2]/span"));
                    String priceSymbol = priceElement.getText().replaceAll("L", "").replaceAll(",", "").trim();
                    System.out.println("Price Found: " + priceSymbol);

                    if (!priceSymbol.isEmpty()) {
                        double price = Double.parseDouble(priceSymbol);
                        itemPriceMap.put(item, price);
                    }
                } catch (Exception e) {
                    System.out.println("Error parsing product price: " + e.getMessage());
                }
            }
 try {
            WebElement LoadMoreButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=main]/div/nav/ul/li[3]/a")));

            if (LoadMoreButton != null && LoadMoreButton.isDisplayed()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", LoadMoreButton);
                wait.until(ExpectedConditions.stalenessOf(containerOfProduct.get(containerOfProduct.size() - 1)));  // Wait for the next page to load
            } else {
                loadMore = false;
            }
        } catch (TimeoutException e) {
            loadMore = false;
        } catch (Exception e) {
            System.out.println("Error navigating to the next page: " + e.getMessage());
            loadMore = false;
        }
    }

    } }
