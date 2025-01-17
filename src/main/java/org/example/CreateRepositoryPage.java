package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

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
    @FindBy(id = "repo_template_search")
    private WebElement repoTemplateList;
    @FindBy(css = "input[aria-controls='_aria_auto_id_25']")
    private WebElement issueLabelsList;

    @FindBy(className = "multiple")
    private WebElement gitIgnoreTemplatesList;

    private By inputSearchSelector = By.cssSelector("input.search");

    @FindBy(xpath = "//input[@aria-controls='_aria_auto_id_293']/..")
    private WebElement licenseList;

    @FindBy(css = ".ui.search.selection.dropdown.upward.active.visible .message")
    private WebElement message;

    @FindBy(id = "_aria_auto_id_8")
    private WebElement initializeWithOptionsBox;

    @FindBy(id = "default_branch")
    private WebElement defaultBranchField;

    @FindBy(id = "_aria_auto_id_9")
    private WebElement templateTheRepo;

    @FindBy(className = "primary")
    private WebElement createRepoButton;

    @FindBy(name = "webhooks")
    private WebElement webHooksBox;
    @FindBy(name = "avatar")
    private WebElement avatarBox;



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

    public RepositoryPage ValidRepoNameCreation(String repoName){
        repoNameField.clear();
        repoNameField.sendKeys(repoName);
        createRepoButton.click();
        return new EmptyRepoPage(driver);
    }

    public void validName(String repoName){
        repoNameField.clear();
        repoNameField.sendKeys(repoName);
    }

    public void inValidName(String repoName){
        repoNameField.clear();
        repoNameField.sendKeys(repoName);
    }

    public void setVisibilityField(){
        visibilityField.click(); //public repo
    }

    public void changeVisibility(){
        visibilityField.click();
    }

    public void setDescriptionFieldValid(String description){
        descriptionField.clear();
        descriptionField.sendKeys(description);
    }

    public void setDescriptionFieldTooLong(String description){
        descriptionField.clear();
        descriptionField.sendKeys(description);
    }

    public void selectTemplateByIndex(int index){
        repoTemplateList.click();
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        List<WebElement> options = repoTemplateList.findElements(By.cssSelector(".menu .item"));
        if(index>= options.size() || index<0) throw new IllegalArgumentException("Illegal index" + index);
        options.get(index).click();
    }

    public void selectTemplateByTappingAndFirstInKeyboard(String template){
        repoTemplateList.click();
        repoTemplateList.clear();
        repoTemplateList.sendKeys(template);
        new Actions(driver)
                .keyDown(Keys.DOWN)
                .keyDown(Keys.ENTER)
                .perform();
    }


    public void selectGitIgnoreTemplateByIndex(int index){
        gitIgnoreTemplatesList.click();
        List<WebElement> options = gitIgnoreTemplatesList.findElements(By.cssSelector(".menu .item"));
        if(index>= options.size() || index<0) throw new IllegalArgumentException("Illegal index" + index);
        options.get(index).click();
        new Actions(driver)
                .keyDown(Keys.ESCAPE)
                .perform();//press esc
    }

    public void selectGitIgnoreTemplateByTapping(String keyWord){
        WebElement dropdownSearchInput = gitIgnoreTemplatesList.findElement(inputSearchSelector);
        dropdownSearchInput.click();
        dropdownSearchInput.sendKeys(keyWord);

        new Actions(driver)
                .keyDown(Keys.ENTER)
                .perform();

        new Actions(driver)
                .keyDown(Keys.ESCAPE)
                .perform();
    }

    public void selectMultipleGitIgnoreTemplateByIndexed(List<Integer> indexes){
        gitIgnoreTemplatesList.click();
        List<WebElement> options = gitIgnoreTemplatesList.findElements(By.cssSelector(".menu .item"));
        for (Integer index: indexes){
            options.get(index).click();
        }
        new Actions(driver)
                .keyDown(Keys.ESCAPE)
                .perform();//press esc
    }

    public void deleteGitIgnoreTemplateByKeyboard(){
        gitIgnoreTemplatesList.click();
        new Actions(driver)
                .keyDown(Keys.DELETE)
                .perform();
    }

    public void selectLicenceByIndex(int index){
        // to remove licence call with index = 0
        licenseList.click();
        List<WebElement> options = licenseList.findElements(By.cssSelector(".menu .item"));
        if(index>= options.size() || index<0) throw new IllegalArgumentException("Illegal index" + index);
        options.get(index).click();
    }

    public void selectLicenceByIndexUsingKeyboard(int index){
        // to remove licence call with index = 0
        licenseList.click();
        List<WebElement> options = licenseList.findElements(By.cssSelector(".menu .item"));
        if(index>= options.size() || index<0) throw new IllegalArgumentException("Illegal index" + index);
        for(int i = 0;i<index-1;i++){
            new Actions(driver)
                    .keyDown(Keys.DOWN).perform();
        }
        new Actions(driver)
                .keyDown(Keys.DOWN).keyDown(Keys.ENTER).perform();
    }

    public void selectLicenceByTapping(String keyWord){
        WebElement dropdownSearchInput = licenseList.findElement(inputSearchSelector);
        dropdownSearchInput.click();
        dropdownSearchInput.sendKeys(keyWord);
        if (!isMessageEnabled()){
            new Actions(driver)
                    .keyDown(Keys.DOWN).keyDown(Keys.ENTER).perform();
        }
        else{
            new Actions(driver)
                    .keyDown(Keys.ESCAPE).perform();
        }

    }

    public void setInitializeWithOptionsBox(){
        initializeWithOptionsBox.click();
    }

    public void setDefaultBranchField(String branchName){
        defaultBranchField.clear();
        defaultBranchField.sendKeys(branchName);
    }

    public void setWebHooksBox(){
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(5), Duration.ofMillis(500));
        wait.until(d -> webHooksBox.isDisplayed());
        webHooksBox.click();
    }

    public void setAvatarBox(){
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(5), Duration.ofMillis(500));
        wait.until(d -> avatarBox.isDisplayed());
        avatarBox.click();
    }


    public void setTemplateTheRepo(){
        templateTheRepo.click();
    }

    public boolean isMessageEnabled() {
        if (licenseList.isDisplayed() && licenseList.isEnabled()) {
            return message == null;
        }
        return false;
    }

    public EmptyRepoPage createEmptyRepoSuccess(){
        createRepoButton.click();
        return new EmptyRepoPage(driver);
    }

    public NoneEmoptyRepoPage createNoneEmptyRepoSuccess(){
        createRepoButton.click();
        return new NoneEmoptyRepoPage(driver);
    }

    public boolean failCreation(){
        return driver.getTitle().contains("New Repository");
    }
}
