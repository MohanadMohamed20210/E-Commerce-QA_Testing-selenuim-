package app.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import app.utils.Utils;
import io.qameta.allure.Step;

public class Login {
    WebDriver driver;
    Utils utils;

    By emailLocator;
    By passLocator;
    By loginButtonLocator;
    By showLoginFormLocator;
    By loginAlertLocator;
    By loginEmailErrLocator;
    By loginPassErrLocator;
    By loginEvidenceLocator;
    By loginEvidenceAdminLocator;

    public Login(WebDriver driver) {
        this.driver = driver;
        utils = new Utils(driver);
        emailLocator = By.cssSelector("#loginEmail");
        passLocator = By.cssSelector("#loginPassword");
        loginButtonLocator = By.xpath("//form //button[text()='Login']");
        showLoginFormLocator = By.cssSelector("#showLogin");
        loginEvidenceLocator = By.xpath("//div //h1[contains(text(),'Pick')]");
        loginEmailErrLocator = By.cssSelector("#loginEmailError");
        loginPassErrLocator = By.cssSelector("#loginPasswordError");
        loginAlertLocator = By.xpath("//form //following::div");
        loginEvidenceAdminLocator = By.xpath("//p //following::h1[contains(text(),'Manage products')]");
    }

    @Step("Login with positive flow")
    public boolean loginPositive() {
        utils.click(showLoginFormLocator);
        utils.sendKeys(emailLocator, System.getProperty("email"));
        utils.sendKeys(passLocator, System.getProperty("password"));
        utils.click(loginButtonLocator);
        return utils.getText(loginEvidenceLocator).contains("Pick products");
    }

    @Step("Login with positive flow admin")
    public boolean loginPositiveAdmin() {
        utils.click(showLoginFormLocator);
        utils.sendKeys(emailLocator, System.getProperty("emailAdmin"));
        utils.sendKeys(passLocator, System.getProperty("passwordAdmin"));
        utils.click(loginButtonLocator);
        return utils.getText(loginEvidenceAdminLocator).contains("Manage products");
    }

    @Step("Login with negative flow empty email and password")
    public boolean loginNegative() {
        utils.click(showLoginFormLocator);
        utils.clearText(emailLocator);
        utils.clearText(passLocator);
        utils.click(loginButtonLocator);
        return utils.getText(loginEmailErrLocator).contains("Email is required") &&
                utils.getText(loginPassErrLocator).contains("Password is required");
    }

    @Step("Login with negative flow invalid email and password")
    public boolean loginNegativeInvalid() {
        utils.click(showLoginFormLocator);
        utils.sendKeys(emailLocator, "invalidemail");
        utils.sendKeys(passLocator, "123");
        utils.click(loginButtonLocator);
        return utils.getText(loginAlertLocator).contains("Invalid email or password");
    }

    public Utils getUtils() {
        return utils;
    }

    public WebDriver getDriver() {
        return driver;
    }
}