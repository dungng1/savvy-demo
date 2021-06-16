package pageobject;

import core.BaseWeb;
import org.openqa.selenium.By;

public class LoginPage extends BaseWeb {
    String inputEmail = "identifier";
    String inputPassword = "password";
    String lblWelcome = "//div[text()='Welcome to the Lead Management system!']";

    public void loginByUser(String user, String password) {
        sendKeyToElement(inputEmail, user);
        sendKeyToElement(inputPassword, password);
        submitToElement(inputPassword);
        waitForElementVisible(lblWelcome);
    }
}
