package app.actions;

import org.openqa.selenium.By;

import app.utils.Utils;
import io.qameta.allure.Step;

public class Shop {
    @SuppressWarnings("unused")
    private Login login;
    private Utils utils;

    By searchLocator;
    By categoryLocator;
    By applyFilterLocator;
    By clearFilterLocator;
    By filterEvidenceLocator;
    By noEelementsEvidenceLocator;
    By productLocator;
    By cartPageLocator;
    By cartEvidenceLocator;
    By allArticlesLocator;
    
    public Shop(Login login) {
        this.login = login;
        utils = login.getUtils();
        searchLocator = By.cssSelector(".toolbar #search");
        categoryLocator = By.cssSelector(".toolbar select[id='category']");
        applyFilterLocator = By.cssSelector("#applyFilters");
        clearFilterLocator = By.cssSelector("#clearFilters");
        noEelementsEvidenceLocator = By.cssSelector("#products .muted");
        cartPageLocator = By.cssSelector("a[href='/cart.html']");
        allArticlesLocator = By.cssSelector("article");
    }
    
    @Step("Search for a product")
    public boolean searchPositive(String searchText, String category) {
        filterEvidenceLocator = By.xpath("//article //ancestor::h2[.='%s']".formatted(searchText));
        utils.sendKeys(searchLocator, searchText);
        utils.select(categoryLocator, category);
        utils.click(applyFilterLocator);
        return !utils.getText(filterEvidenceLocator).isEmpty();
    }
    @Step("Search for a product with invalid data")
    public boolean searchNegative(String searchText, String category) {
        filterEvidenceLocator = By.xpath("//article //ancestor::h2[.='%s']".formatted(searchText));
        utils.sendKeys(searchLocator, searchText);
        utils.select(categoryLocator, category);
        utils.click(applyFilterLocator);
        return utils.getText(noEelementsEvidenceLocator).contains("No products found");
    }
    @Step("Clear search filters")
    public boolean clearFilters(String searchText, String category) {
        filterEvidenceLocator = By.xpath("//article //ancestor::h2[.='%s']".formatted(searchText));
        utils.sendKeys(searchLocator, searchText);
        utils.select(categoryLocator, category);
        utils.click(applyFilterLocator);
        utils.click(clearFilterLocator);
        return utils.getLength(allArticlesLocator) > 0;
    }
    @Step("Add product to cart: {productName}")
    public boolean addProductToCart(String productName) {
        productLocator = By.xpath("//h2[.='%s'] //ancestor::article //button[@data-testid='add-to-cart']".formatted(productName));
        cartEvidenceLocator = By.xpath("//strong[.='%s']".formatted(productName));
        if(utils.scrollToElement(productLocator)) {
            utils.click(productLocator);
            utils.click(cartPageLocator);
            return utils.getText(cartEvidenceLocator).contains(productName);
        } else {
            System.out.println("Product not found: " + productLocator.toString());
            return false;
        }
    }
}
