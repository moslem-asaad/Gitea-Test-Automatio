package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class WelcomePage extends LoadableComponent<WelcomePage> {

    private WebDriver driver;

    private final String baseURL = "https://bf62-5-29-126-14.ngrok-free.app"/*"http://localhost:3000"*/;
    //private final String baseURL = "http://localhost:3000";
    @FindBy(linkText = "Sign In")
    private WebElement signInButton;

    public WelcomePage(WebDriver driver){
        this.driver = driver;

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
        assertTrue(driver.getTitle().contains("Gitea"));
    }

    public SignInPage signIn(){
        signInButton.click();
        return new SignInPage(driver);
    }
}
