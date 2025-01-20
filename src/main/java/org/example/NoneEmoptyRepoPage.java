package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class NoneEmoptyRepoPage extends RepositoryPage{

    @FindBy(xpath = "//button[contains(., 'Add File')]")
    private WebElement addFileButton;

    @FindBy(id = "_aria_auto_id_13")
    private WebElement newFileOption; //make wait after pressing addFile

    public NoneEmoptyRepoPage(WebDriver driver) {
        super(driver);
    }

    public NewFilePage clickNewFileButton(){
        try{
            Thread.sleep(3000);
        }catch (Exception e){

        }
        addFileButton.click();
        newFileOption.click();
        return new NewFilePage(driver);
    }






}
