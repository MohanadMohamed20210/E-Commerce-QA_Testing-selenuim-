package app.mylisteners;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Properties;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.apache.commons.io.FileUtils;
import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;
import app.config.Configrations;

public class MyCustomListeners implements IRetryAnalyzer, IAnnotationTransformer, ISuiteListener {
    private int retryCount = 0;
    private static final int maxRetryCount = 0;

    @Override
    public void onStart(ISuite suite) {
        try {
            Configrations config = new Configrations();
            config.loadProperities();
            Properties property = new Properties();
            property.setProperty("testName", suite.getName());
            property.setProperty("driver", System.getProperty("driver"));
            property.setProperty("mode", System.getProperty("mode"));
            property.setProperty("OS", "Linux Ubuntu");
            property.setProperty("browserVersion", "114.0.5735.133");
            property.setProperty("tool", "Selenium 4.11.0");
            property.setProperty("testTool", "testng 7.8.0");
            property.setProperty("programming language", "Java 21.0");
            property.setProperty("build engine", "maven 3.9.2");

            if (!FileUtils.getFile("allure-results/environment.properties").exists()) {
                FileUtils.createParentDirectories(new File("allure-results"));
            }
            property.store(new FileOutputStream("allure-results/environment.properties"), null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean retry(ITestResult result) {
        if(result.getStatus() == ITestResult.FAILURE) {
            System.out.println("Retrying test: " + result.getMethod().getMethodName() + " (Retry count: " + (retryCount + 1) + ")");
            if (retryCount < maxRetryCount) {
                retryCount++;
                return true;
            }
        }
        retryCount = 0;
        return false;
    }

    @Override
    public void transform(ITestAnnotation annotation, @SuppressWarnings("rawtypes") Class testClass, @SuppressWarnings("rawtypes") Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(MyCustomListeners.class);
    }
}
