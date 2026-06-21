package app.driver;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public enum DriverSelection {
    Chrome {
        @Override
        public WebDriver driverCreation() {
            ChromeOptions options = new ChromeOptions();
            options.setPageLoadStrategy(PageLoadStrategy.EAGER);
            options.addArguments("--start-maximized");
            if (System.getProperty("mode").equals("headless"))
                options.addArguments("--headless");
            WebDriver driver = new ChromeDriver(options);
            driver.get("http://localhost:3000/");
            return driver;
        }
    },
    Firefox {
        @Override
        public WebDriver driverCreation() {
            FirefoxOptions options = new FirefoxOptions();
            options.setPageLoadStrategy(PageLoadStrategy.EAGER);
            options.addArguments("--start-maximized");
            if (System.getProperty("mode").equals("headless"))
                options.addArguments("--headless");
            WebDriver driver = new FirefoxDriver(options);
            driver.get("http://localhost:3000/");
            return driver;
        }
    };
    abstract public WebDriver driverCreation();
}
