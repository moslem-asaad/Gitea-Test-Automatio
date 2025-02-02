package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class Dashboard extends LoadableComponent<Dashboard>{

    private WebDriver driver;
    private final String baseURL = "http://localhost:3000";

    //@FindBy(css = "a.tw-flex.tw-items-center.muted")
    @FindBy(css = ".dashboard-repos h4 a")
    private WebElement createRepoButton;

    public Dashboard(WebDriver driver){
        this.driver = driver;
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        PageFactory.initElements(driver,this);
    }

    @Override
    protected void load() {
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.get(baseURL + "/");
        System.out.println(driver.getCurrentUrl());
    }

    @Override
    protected void isLoaded() throws Error {
        assertTrue(driver.getTitle().contains("Dashboard"));
    }

    public CreateRepositoryPage createRepo()  {
        //this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(5), Duration.ofMillis(500));
        wait.until(d -> createRepoButton.isDisplayed());
        createRepoButton.click();
        return new CreateRepositoryPage(driver);
    }
}
