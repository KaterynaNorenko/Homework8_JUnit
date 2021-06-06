import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;

public class Homework8 {

    public static WebDriver driver;

    @BeforeClass
    public static void openBrowser(){
        String path = "C:/selenium-java-3.5.2/client-combined-3.5.2-nodeps-sources/org/openqa/selenium/chrome/chromedriver.exe";
        System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, path);
        driver = new ChromeDriver();
    }

    @Test
    public void verifyAddToBasketFromHomePage () {

        driver.get("https://0964e.epm-ddl.projects.epam.com:9002/yacceleratorstorefront/?site=electronics");

        WebElement advancedBtn = driver.findElement(By.xpath("//button[@id='details-button']"));
        advancedBtn.click();
        WebElement proceedLink = driver.findElement(By.xpath("//a[@id='proceed-link']"));
        proceedLink.click();

        WebElement searchInput = driver.findElement(By.xpath("//*[@id='js-site-search-input']"));
        searchInput.sendKeys("1934793");
        searchInput.submit();
        WebElement searchBtn = driver.findElement(By.xpath("//form[@id='addToCartForm1934793']/descendant::button[@type='submit']"));
        searchBtn.click();

        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/yacceleratorstorefront/electronics/en/cart' and @class='btn btn-primary btn-block add-to-cart-button']")));
        WebElement checkOutBtn = driver.findElement(By.xpath("//a[@href='/yacceleratorstorefront/electronics/en/cart' and @class='btn btn-primary btn-block add-to-cart-button']"));
        checkOutBtn.click();

        WebElement orderSubtotal = driver.findElement(By.xpath("//div[@class='col-xs-6 cart-totals-right text-right']"));
        Actions scroll = new Actions(driver);
        scroll.moveToElement(orderSubtotal);
        scroll.perform();

        String actualOrderSubtotal = driver.findElement(By.xpath("//div[@class='col-xs-6 cart-totals-right text-right']")).getText();
        String expectedOrderSubtotal = "$99.85";
        String actualOrderTotal = driver.findElement(By.xpath("//div[@class='col-xs-6 cart-totals-right text-right grand-total']")).getText();
        String expectedOrderTotal = "$99.85";
        Assertions.assertAll(
                () -> assertEquals(expectedOrderSubtotal, actualOrderSubtotal),
                () -> assertEquals(expectedOrderTotal, actualOrderTotal)
        );

//OR hard assertions:
//        String orderSubtotalCheck = driver.findElement(By.xpath("//div[@class='col-xs-6 cart-totals-right text-right']")).getText();
//        assertEquals("$99.85",orderSubtotalCheck);
//        String orderTotalCheck = driver.findElement(By.xpath("//div[@class='col-xs-6 cart-totals-right text-right grand-total']")).getText();
//        assertEquals("$99.85",orderTotalCheck);

        WebElement checkOutBtnProceed = driver.findElement(By.cssSelector(":nth-child(6) > div:nth-child(2) > div > div:nth-child(1) > button"));
        checkOutBtnProceed.click();

        WebElement emailInput = driver.findElement(By.xpath("//input[@id='guest.email']"));
        emailInput.sendKeys("test@user.com");
        WebElement emailInputConfirmation = driver.findElement(By.xpath("//input[@id='guest.confirm.email']"));
        emailInputConfirmation.sendKeys("test@user.com");
        emailInputConfirmation.submit();

        WebElement orderSubtotalFinal = driver.findElement(By.cssSelector(".subtotal > span"));
        Actions scrollFinal = new Actions(driver);
        scrollFinal.moveToElement(orderSubtotalFinal);
        scrollFinal.perform();

        String actualOrderSubtotalFinal = driver.findElement(By.cssSelector(".subtotal > span")).getText();
        String expectedOrderSubtotalFinal = "$99.85";
        String actualOrderTotalFinal = driver.findElement(By.cssSelector(".totals > span")).getText();
        String expectedOrderTotalFinal = "$99.85";
        String actualTax = driver.findElement(By.cssSelector(".realTotals > p")).getText();
        String expectedTax = "Your order includes $4.75 tax.";
        Assertions.assertAll(
                () -> assertEquals(expectedOrderSubtotalFinal, actualOrderSubtotalFinal),
                () -> assertEquals(expectedOrderTotalFinal, actualOrderTotalFinal),
                () -> assertEquals(expectedTax, actualTax)
        );

//        OR hard assertions
//        String orderSubtotalFinalCheck = driver.findElement(By.cssSelector(".subtotal > span")).getText();
//        assertEquals("$99.85",orderSubtotalFinalCheck);
//        String orderTotalFinalCheck = driver.findElement(By.cssSelector(".totals > span")).getText();
//        assertEquals("$99.85",orderTotalFinalCheck);
//        String orderTaxCheck = driver.findElement(By.cssSelector(".realTotals > p")).getText();
//        assertEquals("Your order includes $4.75 tax.",orderTaxCheck);
    }

    @After
    public void browserQuit(){
        driver.quit();
    }
}
