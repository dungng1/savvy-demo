package tests;

import core.BaseApi;
import config.DriverManager;
import io.restassured.response.Response;
import microservices.score.models.ScoreInfo;
import microservices.lead.sources.models.SourceInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.testng.annotations.Test;
import pageobject.LoginPage;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class DemoTests extends BaseApi {
    @Test
    public void demo(){
        DriverManager.openBrowser("chrome");
        DriverManager.getDriver().get("https://staging-crm.sabye-songkran.com/");
        LoginPage loginPage = new LoginPage();
        loginPage.loginByUser("dung10@gmail.com","Dungnguyen@");
        Set<Cookie> cookies = DriverManager.getDriver().manage().getCookies();
        initReqSpec();
        for (Cookie c :
                cookies) {
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

        DriverManager.getDriver().quit();
    }
}
