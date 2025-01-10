package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignInPage extends LoadableComponent<SignInPage> {

    private WebDriver driver;

    private final String baseURL = "http://localhost:3000";

    @FindBy(id = "user_name")
    private WebElement userName;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(id = "_aria_auto_id_0")
    private WebElement remeberDevice;

    @FindBy(css = "button.ui.primary.button.tw-w-full")
    private WebElement signInButton;

    public SignInPage(WebDriver driver){
        this.driver = driver;

        PageFactory.initElements(driver,this);
    }

    @Override
    protected void load() {
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.get(baseURL + "/user/login?redirect_to=%2f");
        System.out.println(driver.getCurrentUrl());
    }

    @Override
    protected void isLoaded() throws Error {
        assertTrue(driver.getTitle().contains("Sign In"));
    }

    public Dashboard SignInDoNotRememberDeviceValid(String userName, String password){
        this.userName.clear();
        this.userName.sendKeys(userName);
        this.password.clear();
        this.password.sendKeys(password);
        this.signInButton.click();
        return new Dashboard(driver);
    }

    public Dashboard SignInRememberDeviceValid(String userName, String password){
        this.userName.clear();
        this.userName.sendKeys(userName);
        this.password.clear();
        this.password.sendKeys(password);
        this.remeberDevice.click();
        this.signInButton.click();
        return new Dashboard(driver);
    }

    public SignInPage SignInDoNotRememberDeviceInValid(String userName, String password){
        this.userName.clear();
        this.userName.sendKeys(userName);
        this.password.clear();
        this.password.sendKeys(password);
        this.signInButton.click();
        return this;
    }

    public SignInPage SignInRememberDeviceInValid(String userName, String password){
        this.userName.clear();
        this.userName.sendKeys(userName);
        this.password.clear();
        this.password.sendKeys(password);
        this.remeberDevice.click();
        this.signInButton.click();
        return this;
    }

    public boolean failedLogIn(){
        return driver.getTitle().contains("Sign In");
    }


}
