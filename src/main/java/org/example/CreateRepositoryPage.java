package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;


public class CreateRepositoryPage extends LoadableComponent<CreateRepositoryPage>{
    private WebDriver driver;
    private final String baseURL = "http://localhost:3000";

    @FindBy(id = "repo_name")
    private WebElement repoNameField;
    @FindBy(id = "_aria_auto_id_0")
    private WebElement visibilityField;
    @FindBy(id = "description")
    private WebElement descriptionField;
    @FindBy(id = "repo_template")
    private WebElement repoTemplateList;
    @FindBy(name = "issue_labels")
    private WebElement issueLabelsList;

    @FindBy(className = "primary")
    private WebElement createRepoButton;


    public CreateRepositoryPage(WebDriver driver){
        this.driver = driver;

        PageFactory.initElements(driver,this);
    }

    @Override
    protected void load() {
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.get(baseURL + "/repo/create");
        System.out.println(driver.getCurrentUrl());
    }

    @Override
    protected void isLoaded() throws Error {
        assertTrue(driver.getTitle().contains("New Repository"));
    }

    public CreateRepositoryPage inValidRepoNameCreation(String repoName){
        repoNameField.clear();
        repoNameField.sendKeys(repoName);
        createRepoButton.click();
        return this;
    }

    public boolean failCreation(){
        return driver.getTitle().contains("New Repository");
    }
}
