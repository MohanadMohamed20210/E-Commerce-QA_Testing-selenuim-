package app.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import app.utils.Utils;
import io.qameta.allure.Step;

public class Registeration {
    @SuppressWarnings("unused")
    private WebDriver driver;
    private By registerButtonLocator;
    private By fullNameLocator;
    private By emailLocator;
    private By passLocator;
    private By phoneLocator;
    private By cityLocator;
    private By addrLocator;
    private By bioLocator;
    private By registerLocator;
    private By nameErrLocator;
    private By emailErrLocator;
    private By passErrLocator;
    private By successAlertLocator;

    private Utils utils;

    public Registeration(WebDriver driver) {

        this.driver = driver;
        utils = new Utils(driver);
        registerButtonLocator = By.cssSelector("#showRegister");
        fullNameLocator = By.cssSelector("form[id=registerForm] input[id=registerName]");
        emailLocator = By.cssSelector("form[id=registerForm] input[id=registerEmail]");
        passLocator = By.cssSelector("form[id=registerForm] input[id=registerPassword]");
        phoneLocator = By.cssSelector("form[id=registerForm] input[id=registerPhone]");
        cityLocator = By.cssSelector("form[id=registerForm] input[id=registerCity]");
        addrLocator = By.cssSelector("form[id=registerForm] textarea[id=registerAddress]");
        bioLocator = By.cssSelector("form[id=registerForm] textarea[name=bio]");
        registerLocator = By.cssSelector("form[id=registerForm] button[type=submit]");
        nameErrLocator = By.cssSelector("form[id=registerForm] small[id=registerNameError]");
        emailErrLocator = By.cssSelector("form[id=registerForm] small[id=registerEmailError]");
        passErrLocator = By.cssSelector("form[id=registerForm] small[id=registerPasswordError]");
        successAlertLocator = By.cssSelector("#loginAlert");
    }

    @Step("Register with positive flow")
    public String registerPostive() {
        String uniqueEmail = "test_" + System.currentTimeMillis() + "@gmail.com";
        utils.click(registerButtonLocator);
        utils.sendKeys(fullNameLocator, System.getProperty("fullname"));
        utils.sendKeys(emailLocator, uniqueEmail);
        utils.sendKeys(passLocator, System.getProperty("password"));
        utils.sendKeys(phoneLocator, System.getProperty("phone"));
        utils.sendKeys(cityLocator, System.getProperty("city"));
        utils.sendKeys(addrLocator, System.getProperty("address"));
        utils.sendKeys(bioLocator, System.getProperty("bio"));
        utils.click(registerLocator);
        return utils.getText(successAlertLocator);
    }

    @Step("Register with negative flow")
    public boolean registerNegative() {
        utils.click(registerButtonLocator);
        utils.sendKeys(passLocator, "0125");
        utils.sendKeys(phoneLocator, System.getProperty("phone"));
        utils.sendKeys(cityLocator, System.getProperty("city"));
        utils.sendKeys(addrLocator, System.getProperty("address"));
        utils.sendKeys(bioLocator, System.getProperty("bio"));
        utils.click(registerLocator);

        boolean nameErr  = !utils.getText(nameErrLocator).isEmpty();
        boolean emailErr = !utils.getText(emailErrLocator).isEmpty();
        boolean passErr  = !utils.getText(passErrLocator).isEmpty();

        return nameErr && emailErr && passErr;
    }

}
