package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {

    private static final String baseURL = "http://localhost:3000";

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get(baseURL);

        String pageTitle = driver.getTitle();

        driver.quit();
    }
}