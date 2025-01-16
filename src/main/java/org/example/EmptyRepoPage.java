package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EmptyRepoPage extends RepositoryPage{

    @FindBy(xpath = "//a[contains(text(), 'New File')]")
    private WebElement newFileButton;

    public EmptyRepoPage(WebDriver driver) {
        super(driver);
    }

    public NewFilePage clickNewFileButton(){
        newFileButton.click();
        return new NewFilePage(driver);
    }

}
