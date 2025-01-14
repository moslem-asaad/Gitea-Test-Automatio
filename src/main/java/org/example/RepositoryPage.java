package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

abstract public class RepositoryPage extends LoadableComponent<RepositoryPage> {

    private WebDriver driver;
    private final String baseURL = "http://localhost:3000";

    @FindBy(css = "a.muted")
    private List<WebElement> links;

    @FindBy(css = ".resize-for-semibold[data-text='Code']")
    private WebElement codeButton;

    @FindBy(css = ".resize-for-semibold[data-text=\"Issues\"]")
    protected WebElement issuesButton;
    @FindBy(css = ".resize-for-semibold[data-text='Actions']")
    private WebElement actionsButton;




    public RepositoryPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }



    @Override
    protected void load() {
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.get(baseURL + links.get(2).getDomProperty("href"));
        System.out.println(driver.getCurrentUrl());
    }

    @Override
    protected void isLoaded() throws Error {
        assertTrue(driver.getTitle().contains(links.get(2).getDomProperty("href")));
    }

    public boolean inRepoPage(){
        System.out.println(driver.getTitle());
        System.out.println(links.get(1).getDomProperty("href"));
        return (baseURL+"/"+driver.getTitle()).contains(links.get(1).getDomProperty("href"));
    }


}
