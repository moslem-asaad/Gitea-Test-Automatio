package seleniumTests;

import org.example.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.*;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;

public class AddNewFileTest {
    private WebDriver driver;

    private String repositoryName ;

    private CreateRepositoryPage createRepositoryPage;

    private static String apiToken;

    private static String password;



    @BeforeEach
    public void setUp(){
        readENV();
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();

        driver.get("https://daa5-5-29-126-14.ngrok-free.app");
        try {
            Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement visitSiteButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Visit Site']")));
            visitSiteButton.click();
        } catch (TimeoutException err) {
            System.out.println("Ngrok warning page was not loaded");
        }

        WelcomePage welcomePage = new WelcomePage(driver).get();
        SignInPage signInPage = welcomePage.signIn();
        String userName = "moslem";
        Dashboard dashboard = signInPage.SignInDoNotRememberDeviceValid(userName,password);
        createRepositoryPage = dashboard.createRepo();
        repositoryName = "test_repo_" + System.currentTimeMillis();
    }

    private void readENV(){
        EnvLoader.loadEnv(".env");
        apiToken =  EnvLoader.getEnv("GITEA_API_TOKEN");
        password = EnvLoader.getEnv("Password");
        if (password == null || password.isEmpty()){
            password = System.getenv("Password");
        }
    }

    @Test
    public void emptyFileNameInEmptyRepo() {
        createRepositoryPage.validName(repositoryName);
        EmptyRepoPage repoPage = createRepositoryPage.createEmptyRepoSuccess();
        NewFilePage newFilePage =  repoPage.clickNewFileButton();
        assertThrows(Exception.class,newFilePage::clickCreateRepo);
        assertTrue(newFilePage.inNewFilePage());
    }

    @Test
    public void validFileNameEmptyContent() {
        createRepositoryPage.validName(repositoryName);
        EmptyRepoPage repoPage = createRepositoryPage.createEmptyRepoSuccess();
        NewFilePage newFilePage =  repoPage.clickNewFileButton();
        newFilePage.fillValidFileName("test file 1");
        NoneEmoptyRepoPage noneEmoptyRepoPage =  newFilePage.clickCreateRepoEmptyContent();
        assertTrue(noneEmoptyRepoPage.inRepoPage());
    }

    @Test
    public void validFileOnNewBranch() {
        createRepositoryPage.validName(repositoryName);
        createRepositoryPage.selectGitIgnoreTemplateByIndex(3);
        NoneEmoptyRepoPage repoPage = createRepositoryPage.createNoneEmptyRepoSuccess();
        NewFilePage newFilePage =  repoPage.clickNewFileButton();
        assertTrue(newFilePage.inNewFilePage());
        newFilePage.fillValidFileName("test file 3");
        newFilePage.createWithNewBranchOption();
        newFilePage.addNewBranchName("test3_branch");
        NoneEmoptyRepoPage noneEmoptyRepoPage =  newFilePage.clickCreateRepoEmptyContent();
        assertTrue(noneEmoptyRepoPage.inRepoPage());
    }

    @Test
    public void validFileOnNewBranchWithCommit() {
        createRepositoryPage.validName(repositoryName);
        createRepositoryPage.selectGitIgnoreTemplateByIndex(3);
        NoneEmoptyRepoPage repoPage = createRepositoryPage.createNoneEmptyRepoSuccess();
        NewFilePage newFilePage =  repoPage.clickNewFileButton();
        assertTrue(newFilePage.inNewFilePage());
        newFilePage.fillValidFileName("test file 4");
        newFilePage.addCommitSummary("creating new file");
        newFilePage.addCommitMessage("this new file is a test file");
        newFilePage.createWithNewBranchOption();
        newFilePage.addNewBranchName("test4_branch");
        NoneEmoptyRepoPage noneEmoptyRepoPage =  newFilePage.clickCreateRepoEmptyContent();
        assertTrue(noneEmoptyRepoPage.inRepoPage());
    }


    public void deleteRepo() throws IOException {
        String token = apiToken;
        if (token == null || token.isEmpty())
            token = System.getenv("GITEA_API_TOKEN");

        if (token == null || token.isEmpty()) {
            System.err.println("GITEA_API_TOKEN is not set in the environment variables.");
            return;
        }

        if (repositoryName != null) {
            String apiUrl = "http://localhost:3000/api/v1/repos/moslem/" + repositoryName;
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Authorization", "token " + token);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                System.out.println("Repository deleted successfully: " + repositoryName);
            } else {
                System.err.println("Failed to delete repository. Response code: " + responseCode);
            }
        } else {
            System.err.println("Repository name is null or empty.");
        }
    }

    @AfterEach
    public void tearDown() throws IOException {
        deleteRepo();
        driver.quit();
    }


}