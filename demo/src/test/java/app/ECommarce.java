package app;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import app.driver.DriverSelection;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import app.actions.Registeration;
import app.actions.Login;
import app.actions.Shop;
import app.actions.Chechout;

public class ECommarce {

    private Registeration reg;
    private Login login;
    private WebDriver driver;
    private Shop shop;
    private Chechout checkout;
    private Cookie cook;

    @BeforeMethod
    public void setup() throws Exception{
        driver = DriverSelection.Chrome.driverCreation();
        reg = new Registeration(driver);
        login = new Login(driver);
        shop = new Shop(login);
        checkout = new Chechout(login);
    }

    @Description("Test case 1: Register with positive flow")
    @Owner("Mohanad")
    @Severity(SeverityLevel.CRITICAL)
    @Story("User registration")
    @Test
    public void testCase_1() {
        Assert.assertTrue(reg.registerPostive().contains("Account created"));
    }
    

    @Description("Test case 2: Register with negative flow")
    @Owner("Mohanad")
    @Severity(SeverityLevel.NORMAL)
    @Story("User registration")
    @Test
    public void testCase_2() {
        Assert.assertTrue(reg.registerNegative());
    }

    @Description("Test case 3: Login with positive flow")
    @Owner("Mohanad")
    @Severity(SeverityLevel.CRITICAL)
    @Story("User login")
    @Test
    public void testCase_3() {
        Assert.assertTrue(login.loginPositive());
        cook = driver.manage().getCookieNamed("session_id");
    }

    @Description("Test case 4: Login with negative flow empty email and password")
    @Owner("Mohanad")
    @Severity(SeverityLevel.NORMAL)
    @Story("User login")
    @Test
    public void testCase_4() {
        Assert.assertTrue(login.loginNegative());
    }

    @Description("Test case 5: Login with negative flow invalid email and password")
    @Owner("Mohanad")
    @Severity(SeverityLevel.NORMAL)
    @Story("User login")
    @Test
    public void testCase_5() {
        Assert.assertTrue(login.loginNegativeInvalid());
    }

    @Description("Test case 6: Login with positive flow admin")
    @Owner("Mohanad")
    @Severity(SeverityLevel.CRITICAL)
    @Story("User login")
    @Test
    public void testCase_6() {
        Assert.assertTrue(login.loginPositiveAdmin());
    }

    @Description("Test case 7: Search for a product")
    @Owner("Mohanad")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Product search")
    @Test
    public void testCase_7() {
        login.loginPositive();
        Assert.assertTrue(shop.searchPositive("Bug Report Planner", "Books"));
    }

    @Description("Test case 8: Search for a product with invalid data")
    @Owner("Mohanad")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Product search")
    @Test
    public void testCase_8() {
        login.loginPositive();
        Assert.assertTrue(shop.searchNegative("Invalid Product", "Books"));
    }

    @Description("Test case 9: Clear search filters")
    @Owner("Mohanad")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Product search")
    @Test
    public void testCase_9() {
        login.loginPositive();
        Assert.assertTrue(shop.clearFilters("Bug Report Planner", "Books"));
    }

    @Description("Test case 10: Add product to cart")
    @Owner("Mohanad")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Product search")
    @Test(dataProvider = "productsToAdd")
    public void testCase_10(String product) {
        login.loginPositive();
        Assert.assertTrue(shop.addProductToCart(product));
    }

    @Description("Test case 11: Validate the total price inside cart")
    @Owner("Mohanad")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Cart validation")
    @Test
    public void testCase_11() {
        login.loginPositive();
        shop.addProductToCart("Sticky Notes Pack");
        Assert.assertTrue(checkout.validateTotalPrice());
    }

    @Description("Test case 12: Validate purchase")
    @Owner("Mohanad")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Cart validation")
    @Test
    public void testCase_12() {
        login.loginPositive();
        shop.addProductToCart("Sticky Notes Pack");
        Assert.assertTrue(checkout.checkout());
    }

    @Description("Test case 13: Validate cart table content after add item")
    @Owner("Mohanad")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Cart validation")
    @Test
    public void testCase_13() {
        // login.loginPositive();
        driver.manage().addCookie(cook);
        driver.navigate().refresh();
        driver.get("http://localhost:3000/shop.html");
        shop.addProductToCart("Sticky Notes Pack");
        Assert.assertTrue(checkout.sqlCartValidation("Sticky Notes Pack"));
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @DataProvider(name = "productsToAdd")
    public Object[][] productsToAdd() {
        return new Object[][] {
            {"Sticky Notes Pack"},
            {"USB Hub"},
            {"QA Notebook"}
        };
    }
};