package app.utils;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Allure;

public class Utils {
    private WebDriver driver;
    WebDriverWait wait;

    public Utils(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(6));
    }

    public void click(By element) {
        WebElement webElement = wait.until(ExpectedConditions.elementToBeClickable(element));
        webElement.click();
    }

    public void sendKeys(By element, String data) {
        WebElement webElement = wait.until(driver -> {
            WebElement el = driver.findElement(element);
            return el.isDisplayed() ? el : null;
        });
        webElement.clear();
        webElement.sendKeys(data);
    }

    public String getText(By element) {
        try {
            WebElement webElement = wait.until(driver -> {
                try {
                    WebElement el = driver.findElement(element);
                    return el.isDisplayed() ? el : null;
                } catch (Exception e) {
                    return null;
                }
            });
            if (webElement != null) {
                takeScreenShot(webElement);
                return webElement.getText();
            }
            return "";
        } catch (Exception e) {
            System.out.println("Error getting text from element: " + element.toString() + " - " + e.getMessage());
            return "";
        }
    }

    public void clearText(By element) {
        WebElement webElement = wait.until(driver -> {
            WebElement el = driver.findElement(element);
            return el.isDisplayed() ? el : null;
        });
        webElement.clear();
    }

    public void select(By element, String visibleText) {
        WebElement webElement = wait.until(driver -> {
            WebElement el = driver.findElement(element);
            return el.isDisplayed() ? el : null;
        });
        Select select = new Select(webElement);
        select.selectByVisibleText(visibleText);
    }

    public boolean scrollToElement(By element) {
        return wait.until(driver -> {
            Actions actions = new Actions(driver);
            actions.scrollToElement(driver.findElement(element)).perform();
            return driver.findElement(element).isDisplayed();
        });
    }

    public int getLength(By element) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(element));
        return driver.findElements(element).size();
    }

    public boolean calculate(By elementLocator) {
        List<WebElement> elements = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(elementLocator));
        double sum = 0.0;
        for (WebElement ele : elements) {
            String priceText = ele.findElement(By.cssSelector("div span"))
                    .getText().split(" ")[0];
            double actualPrice = Double.parseDouble(priceText.replace("$", ""));
            String qtyStr = ele.findElement(By.cssSelector("input"))
                    .getDomProperty("value");
            double quantity = Double.parseDouble(qtyStr);
            double expectedTotal = actualPrice * quantity;
            String lineTotalText = ele.findElement(By.cssSelector("article > strong"))
                    .getText();
            double actualTotal = Double.parseDouble(lineTotalText.replace("$", ""));
            if (Math.abs(expectedTotal - actualTotal) > 0.009) {
                return false;
            }
            sum += actualTotal;
        }
        String cartTotalText = driver.findElement(By.cssSelector(".cart-total"))
                .getText().split(" ")[1];
        double cartTotal = Double.parseDouble(cartTotalText.replace("$", ""));
        return Math.abs(cartTotal - sum) < 0.009;
    }

    public void takeScreenShot(By element) {
        try {
            Random random = new Random();
            int randomNum = random.nextInt(1000);
            String path = System.getProperty("user.dir") + "/src/test/resources/screenshot_" + randomNum + ".png";
            File src = driver.findElement(element).getScreenshotAs(OutputType.FILE);
            File dest = new File(path);
            FileUtils.copyFile(src, dest);
            Allure.addAttachment(path, new FileInputStream(dest));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void takeScreenShot(WebElement element) {
        try {
            Random random = new Random();
            int randomNum = random.nextInt(1000);
            String path = System.getProperty("user.dir") + "/src/test/resources/screenshot_" + randomNum + ".png";
            File src = element.getScreenshotAs(OutputType.FILE);
            File dest = new File(path);
            FileUtils.copyFile(src, dest);
            Allure.addAttachment(path, new FileInputStream(dest));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
