package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NoneEmoptyRepoPage extends RepositoryPage{

    @FindBy(css = ".dropdown.basic")
    private WebElement addFileButton;

    @FindBy(id = "_aria_auto_id_13")
    private WebElement newFileOption; //make wait after pressing addFile

    public NoneEmoptyRepoPage(WebDriver driver) {
        super(driver);
    }
}
