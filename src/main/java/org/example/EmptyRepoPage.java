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

    public void clickIsues(){
        this.issuesButton.click();
    }
}
