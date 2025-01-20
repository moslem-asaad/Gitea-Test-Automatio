package org.example;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.List;

public class NewFilePage extends LoadableComponent<NewFilePage> {

    private WebDriver driver;
    private final String baseURL = "http://localhost:3000";

    @FindBy(id = "file-name")
    private WebElement fileNameField;

    @FindBy(xpath = "//span[contains(text(), 'or')]/a[text()='Cancel']")
    private WebElement firstCancelButton;

    @FindBy(className = "segment")
    private WebElement fileContentField;

    @FindBy(name = "commit_summary")
    private WebElement commitSummary;

    @FindBy(name = "commit_message")
    private WebElement commitMessage;

    @FindBy(className = "radio")
    private List<WebElement> commitOptions;//f the repo is empty then this list is of 1 length

    @FindBy(name = "new_branch_name")
    private WebElement newBranchNameField;//should be wait

    @FindBy(id = "commit-button")
    private WebElement commitButton;// the name may change

    @FindBy(className = "red")
    private WebElement secondCancelButton;


    public NewFilePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @Override
    protected void load() {

    }

    @Override
    protected void isLoaded() throws Error {
        assertTrue(driver.getCurrentUrl().contains("new"));
    }

    public boolean inNewFilePage(){
        return driver.getCurrentUrl().contains("new");
    }

    public void fillValidFileName(String fileName){
        fileNameField.clear();
        fileNameField.sendKeys(fileName);
    }

    /**
     * used to send empty file name or file name with length > 255
     * @param fileName
     */
    public void fillInValidFileName(String fileName){
        fileNameField.clear();
        fileNameField.sendKeys(fileName);
    }

    public void addFileContent(String content){
        fileContentField.clear();
        fileContentField.sendKeys(content);
    }

    public void addCommitSummary(String summary){
        commitSummary.clear();
        commitSummary.sendKeys(summary);
    }

    public void addCommitMessage(String message){
        commitMessage.clear();
        commitMessage.sendKeys(message);
    }

    public void createWithNewBranchOption(){
        if(!isEmptyRepo()){
            commitOptions.get(1).click();
        }
    }

    public void addNewBranchName(String branchName){
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(5), Duration.ofMillis(500));
        wait.until(d -> newBranchNameField.isDisplayed());
        newBranchNameField.clear();
        newBranchNameField.sendKeys(branchName);
    }

    public NoneEmoptyRepoPage clickCreateRepo(){
        commitButton.click();
        return new NoneEmoptyRepoPage(driver);
    }

    public NoneEmoptyRepoPage clickCreateRepoEmptyContent(){
        commitButton.click();
        new Actions(driver).keyDown(Keys.TAB).keyDown(Keys.ENTER).perform();
       /* WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try{
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
        }catch (TimeoutException ignored){

        }*/
        return new NoneEmoptyRepoPage(driver);
    }

    public RepositoryPage clickFirstCancel(){
        firstCancelButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try{
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
        }catch (TimeoutException ignored){

        }
        if (isEmptyRepo()){
            return new EmptyRepoPage(driver);
        }else{
            return new NoneEmoptyRepoPage(driver);
        }
    }

    public RepositoryPage clickSecondCancel(){
        secondCancelButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try{
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
        }catch (TimeoutException ignored){

        }
        if (isEmptyRepo()){
            return new EmptyRepoPage(driver);
        }else{
            return new NoneEmoptyRepoPage(driver);
        }
    }

    private boolean isEmptyRepo(){
        return commitOptions.size()<=1;
    }


}
