package config;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class DriverManager {
    private static final String workingDir = System.getProperty("user.dir");
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    @Getter
    @Setter
    private static int LONG_TIMEOUT = 30;
    @Getter
    @Setter
    private static int SHORT_TIMEOUT = 5;

    public static WebDriver getDriver(){
        return driver.get();
    }

    private static void setDriver(WebDriver webDriver){
        driver.set(webDriver);
    }

    public static void openBrowser(String browserName){
        WebDriver tmpDriver = null;
        if (browserName.equalsIgnoreCase("hfirefox")){
            WebDriverManager.firefoxdriver().setup();
            System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
            System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,
                    workingDir + "\\FirefoxLog.txt");
            tmpDriver = new FirefoxDriver();

        } else if (browserName.equalsIgnoreCase("chrome")){
            WebDriverManager.chromedriver().setup();
            ChromeOptions option = new ChromeOptions();
            option.addArguments("--incognito");
            option.addArguments("--disable-extensions");
            option.addArguments("--disable-infobars");
            tmpDriver = new ChromeDriver(option);

        } else if (browserName.equalsIgnoreCase("hchrome")){
            WebDriverManager.chromedriver().setup();
            ChromeOptions option = new ChromeOptions();
            option.addArguments("headless");
            option.addArguments("window-size=1366x768");
            tmpDriver = new ChromeDriver(option);

        } else if (browserName.equalsIgnoreCase("firefox")){
            WebDriverManager.firefoxdriver().setup();
            System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
            System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,
                    workingDir + "\\FirefoxLog.txt");
            tmpDriver = new FirefoxDriver();
        }
        setDriver(tmpDriver);

        getDriver().manage().timeouts().implicitlyWait(getLONG_TIMEOUT(), TimeUnit.SECONDS);
        getDriver().manage().window().maximize();
    }
}
