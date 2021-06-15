package tests;

import config.BaseApi;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.response.Response;
import microservices.score.models.ScoreInfo;
import microservices.lead.sources.models.SourceInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class DemoTests extends BaseApi {
    @Test
    public void demo(){
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://staging-crm.sabye-songkran.com/");
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(By.id("identifier")).sendKeys("dung10@gmail.com");
        driver.findElement(By.id("password")).sendKeys("Dungnguyen@");
        driver.findElement(By.id("password")).submit();
        driver.findElement(By.xpath("//div[text()='Welcome to the Lead Management system!']"));
        Set<Cookie> a = driver.manage().getCookies();
        initReqSpec();
        for (Cookie c :
                a) {
            setCookie(c.getName(),c.getValue());
        }
        setBaseUrl("https://staging-crm.sabye-songkran.com");
        SourceInfo sourceInfo = new SourceInfo();
        sendPost("/api/lead/v1alpha2/sources", sourceInfo);
        String jsonValue = getJsonValue("name");
        jsonValue = jsonValue.replace("sources/","");
        ScoreInfo scoreInfo = new ScoreInfo(jsonValue);
        sendPost("api/score/v1alpha1/scores/sources", scoreInfo);
        Response response =sendGet("api/view/v1alpha1/views/sources/sources?currentPage=1&pageSize=15&showDeleted=true&filter=product in (\"products/car-insurance\")");
        driver.quit();
    }
}
