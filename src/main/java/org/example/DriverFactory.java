package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {

    public static WebDriver getDriver() throws MalformedURLException {
        String browser = System.getenv("BROWSER");
        if (browser == null) browser = "chrome";
        String remote = System.getenv("REMOTE");
        if (remote == null) remote = "false";

//        if (browser == null) {
//            throw new IllegalArgumentException("BROWSER environment variable must be set.");
//        }

        if ("true".equalsIgnoreCase(remote)) {
            return getRemoteDriver(browser);
        } else {
            return getLocalDriver(browser);
        }
    }

    private static WebDriver getRemoteDriver(String browser) throws MalformedURLException {
        String hubUrl = "http://16.171.132.126:4444";

        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            return new RemoteWebDriver(new URL(hubUrl), options);
        } else if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("-headless");
            return new RemoteWebDriver(new URL(hubUrl), options);
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    private static WebDriver getLocalDriver(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            return new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            return new FirefoxDriver();
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }
}