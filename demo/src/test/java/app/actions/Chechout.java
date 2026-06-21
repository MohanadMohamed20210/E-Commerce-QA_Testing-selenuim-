package app.actions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.openqa.selenium.By;
import org.testng.Reporter;

import app.utils.Utils;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

public class Chechout {
    @SuppressWarnings("unused")
    private Login login;
    private Utils utils;
    private Connection connection;

    private By wholeArticalLocator;
    private By addressLocator;
    private By paymentLocator;
    private By buttonLocator;
    private By ordersLocator;
    private By cartAlert;

    public Chechout(Login login) throws Exception {
        this.login = login;
        utils = login.getUtils();
        connection = DriverManager.getConnection(System.getProperty("sql_url"),
                System.getProperty("sql_user"),
                System.getProperty("sql_password"));
        wholeArticalLocator = By.cssSelector("article");
        addressLocator = By.cssSelector("textarea");
        paymentLocator = By.cssSelector("form select");
        buttonLocator = By.cssSelector("form button");
        ordersLocator = By.cssSelector("a[href='/orders.html']");
        cartAlert = By.cssSelector("#cartAlert");
    }

    @Step("Validate the total price")
    public boolean validateTotalPrice() {
        return utils.calculate(wholeArticalLocator);
    }

    @Step("checkout the selected products")
    public boolean checkout() {
        utils.sendKeys(addressLocator, System.getProperty("address"));
        utils.select(paymentLocator, "Cash");
        utils.click(buttonLocator);
        String alert = utils.getText(cartAlert);
        int orderNumber = 0;
        if (!alert.isEmpty()) {
            orderNumber = Integer.valueOf(alert.split(" ")[1].replace("#", ""));
        }
        utils.click(ordersLocator);
        if (utils.scrollToElement(By.xpath("//article //div //h2[contains(text(),%d)]".formatted(orderNumber)))) {
            utils.takeScreenShot(By.xpath("//h2 //ancestor::article"));
            return true;
        } else {
            return false;
        }
    }

    @Step("Validate the cart table in database")
    public boolean sqlCartValidation(String product) {
        String query = """
                    SELECT c.*
                    FROM cart_items c
                    JOIN products p ON c.product_id = p.id
                    WHERE p.name = ?
                    LIMIT 1
                """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, product);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {
                    ResultSetMetaData meta = result.getMetaData();
                    int columnCount = meta.getColumnCount();

                    StringBuilder row = new StringBuilder();

                    for (int i = 1; i <= columnCount; i++) {
                        row.append(meta.getColumnName(i))
                                .append(" = ")
                                .append(result.getString(i))
                                .append("\n");
                    }

                    Reporter.log(row.toString(), true);
                    Allure.addAttachment("Cart DB Row", row.toString());

                    return true;
                }

                Reporter.log("No cart row found for product: " + product, true);
                Allure.addAttachment("Cart DB Row", "No cart row found for product: " + product);

                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
