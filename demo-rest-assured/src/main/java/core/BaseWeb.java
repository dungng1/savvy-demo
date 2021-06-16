package core;

import config.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class BaseWeb {
    WebDriverWait waitExplicit;
    private WebDriver driver;
    public BaseWeb() {
        this.driver = DriverManager.getDriver();
        this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public void sendKeyToElement(String locator, String sendKeyValue,
                                 String... values) {
        locator = String.format(locator, (Object[]) values);
        WebElement element = waitForElementVisible(locator);
        element.sendKeys(sendKeyValue);
    }

    public void submitToElement(String locator){
        WebElement element = waitForElementVisible(locator);
        element.submit();
    }

    public WebElement waitForElementVisible(String locator, String... values) {
        waitExplicit = new WebDriverWait(driver, DriverManager.getLONG_TIMEOUT());
        locator = String.format(locator, (Object[]) values);
        By byLocator = By.xpath(locator);
        try {
            return waitExplicit.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
        } catch (Exception ex) {

            System.err.println(
                    "================================== Element not visible===================================");
            System.err.println(ex.getMessage() + "\n");
            return null;
        }
    }
}
