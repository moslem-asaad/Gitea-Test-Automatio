package seleniumTests;

import org.example.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

public class CreateRepositoryTest {

    private WebDriver driver;

    private WelcomePage welcomePage;

    private String repositoryName ;

    private static String apiToken;
    private static String password;

    private final String userName = "moslem";

    //private final String URL = "https://76e6-5-29-126-14.ngrok-free.app";
    private final String URL = "http://localhost:3000";

    @BeforeEach
    public void setUp(){
        readENV();
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        driver.get(URL);
        try {
            Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement visitSiteButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Visit Site']")));
            visitSiteButton.click();
        } catch (TimeoutException err) {
            //System.out.println("Ngrok warning page was not loaded");
        }
        welcomePage = new WelcomePage(driver).get();

        repositoryName = "test_repo_" + System.currentTimeMillis();
    }

    private void readENV(){
        apiToken = System.getenv("GITEA_API_TOKEN");
        password = System.getenv("Password");
    }

    @Test
    @DisplayName("test - create repo access ")
    public void createRepoAccess() {
        SignInPage signInPage = welcomePage.signIn();
        Dashboard dashboard = signInPage.SignInDoNotRememberDeviceValid(userName,password);
        dashboard.createRepo();
    }

    //TC1 -------------------------

    @Test
    @DisplayName("test - create repo invalid name ")
    public void invalidName() {
        SignInPage signInPage = welcomePage.signIn();
        Dashboard dashboard = signInPage.SignInDoNotRememberDeviceValid(userName,password);
        assertTrue(dashboard.createRepo().inValidRepoNameCreation("@@invalid!!").failCreation());

    }

    //TC2----------------------------
    @Test
    @DisplayName("test - create repo valid name ")
    public void validName() {
        String repoName = repositoryName;
        SignInPage signInPage = welcomePage.signIn();
        Dashboard dashboard = signInPage.SignInDoNotRememberDeviceValid(userName,password);
        CreateRepositoryPage createRepositoryPage=  dashboard.createRepo();
        EmptyRepoPage repositoryPage = (EmptyRepoPage) createRepositoryPage.ValidRepoNameCreation(repoName);
        assertTrue(repositoryPage.inRepoPage());
    }

    //TC3-----------------------------
    @Test
    @DisplayName("test - create repo duplicate name ")
    public void duplicateName() {
        SignInPage signInPage = welcomePage.signIn();
        Dashboard dashboard = signInPage.SignInDoNotRememberDeviceValid(userName,password);
        assertTrue(dashboard.createRepo().inValidRepoNameCreation("testRepo").failCreation());
    }

    //TC4-----------------------------
    @Test
    @DisplayName("test - create repo empty name ")
    public void emptyName() {
        SignInPage signInPage = welcomePage.signIn();
        Dashboard dashboard = signInPage.SignInDoNotRememberDeviceValid(userName,password);
        assertTrue(dashboard.createRepo().inValidRepoNameCreation("").failCreation());
    }

    //TC5-----------------------------
    @Test
    @DisplayName("test - create repo with .gitIgnore1 ")
    public void gitIgnoreByIndex() {
        SignInPage signInPage = welcomePage.signIn();
        Dashboard dashboard = signInPage.SignInDoNotRememberDeviceValid(userName,password);
        CreateRepositoryPage createRepositoryPage = dashboard.createRepo();
        createRepositoryPage.validName(repositoryName);
        createRepositoryPage.selectGitIgnoreTemplateByIndex(0);
        NoneEmoptyRepoPage repoPage =  createRepositoryPage.createNoneEmptyRepoSuccess();
        assertTrue(repoPage.inRepoPage());
    }

    @Test
    @DisplayName("test - create repo with .gitIgnore2 ")
    public void gitIgnoreByName() {
        SignInPage signInPage = welcomePage.signIn();
        Dashboard dashboard = signInPage.SignInDoNotRememberDeviceValid(userName,password);
        CreateRepositoryPage createRepositoryPage = dashboard.createRepo();
        createRepositoryPage.validName(repositoryName);
        createRepositoryPage.selectGitIgnoreTemplateByTapping("a");
        NoneEmoptyRepoPage repoPage =  createRepositoryPage.createNoneEmptyRepoSuccess();
        assertTrue(repoPage.inRepoPage());
    }

    //TC6----------------------------------
    @Test
    @DisplayName("test - create repo with licence1 ")
    public void licenseByIndex() {
        SignInPage signInPage = welcomePage.signIn();
        Dashboard dashboard = signInPage.SignInDoNotRememberDeviceValid(userName,password);
        CreateRepositoryPage createRepositoryPage = dashboard.createRepo();
        createRepositoryPage.validName(repositoryName);
        createRepositoryPage.selectLicenceByIndex(3);
        NoneEmoptyRepoPage repoPage =  createRepositoryPage.createNoneEmptyRepoSuccess();
        assertTrue(repoPage.inRepoPage());
    }

    @Test
    @DisplayName("test - create repo with licence2 ")
    public void licenseByName() {
        SignInPage signInPage = welcomePage.signIn();
        Dashboard dashboard = signInPage.SignInDoNotRememberDeviceValid(userName,password);
        CreateRepositoryPage createRepositoryPage = dashboard.createRepo();
        createRepositoryPage.validName(repositoryName);
        createRepositoryPage.selectLicenceByTapping("aa");
        NoneEmoptyRepoPage repoPage =  createRepositoryPage.createNoneEmptyRepoSuccess();
        assertTrue(repoPage.inRepoPage());
    }

    //TC7--------------------------------
    @Test
    @DisplayName("test - create public repo ")
    public void publicRepo() {
        SignInPage signInPage = welcomePage.signIn();
        Dashboard dashboard = signInPage.SignInDoNotRememberDeviceValid(userName,password);
        CreateRepositoryPage createRepositoryPage = dashboard.createRepo();
        createRepositoryPage.validName(repositoryName);
        createRepositoryPage.changeVisibility();
        EmptyRepoPage repoPage = createRepositoryPage.createEmptyRepoSuccess();
        assertTrue(repoPage.inRepoPage());
    }

    //TC8--------------------------------
    @Test
    @DisplayName("test - create private repo ")
    public void privateRepo() {
        SignInPage signInPage = welcomePage.signIn();
        Dashboard dashboard = signInPage.SignInDoNotRememberDeviceValid(userName,password);
        CreateRepositoryPage createRepositoryPage = dashboard.createRepo();
        createRepositoryPage.validName(repositoryName);
        createRepositoryPage.changeVisibility();
        createRepositoryPage.changeVisibility();
        EmptyRepoPage repoPage = createRepositoryPage.createEmptyRepoSuccess();
        assertTrue(repoPage.inRepoPage());
    }

    //TC10--------------------------------
    @Test
    @DisplayName("test - create repo with long description ")
    public void longDescription() {
        SignInPage signInPage = welcomePage.signIn();
        Dashboard dashboard = signInPage.SignInDoNotRememberDeviceValid(userName,password);
        CreateRepositoryPage createRepositoryPage = dashboard.createRepo();
        createRepositoryPage.validName(repositoryName);
        createRepositoryPage.setDescriptionFieldTooLong("this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n" +
                "this is a very long description\n");
        EmptyRepoPage repoPage = createRepositoryPage.createEmptyRepoSuccess();
        assertTrue(repoPage.inRepoPage());
    }

    //TC11--------------------------------
    @Test
    @DisplayName("test - create repo with special characters ")
    public void specialCharsDescription() {
        SignInPage signInPage = welcomePage.signIn();
        Dashboard dashboard = signInPage.SignInDoNotRememberDeviceValid(userName,password);
        CreateRepositoryPage createRepositoryPage = dashboard.createRepo();
        createRepositoryPage.validName(repositoryName);
        createRepositoryPage.setDescriptionFieldValid("@@@IIN1@@#$%$^%$&%^&*($%^^%#$^");
        EmptyRepoPage repoPage = createRepositoryPage.createEmptyRepoSuccess();
        assertTrue(repoPage.inRepoPage());
    }

    //TC14--------------------------------
    @Test
    @DisplayName("test - create repo with long repo name ")
    public void longName() {
        SignInPage signInPage = welcomePage.signIn();
        Dashboard dashboard = signInPage.SignInDoNotRememberDeviceValid(userName,password);
        CreateRepositoryPage createRepositoryPage = dashboard.createRepo();
        createRepositoryPage.inValidName("this is a long name this is a long name this is a long name this is a long name this is a long name this is a long name this is a long name this is a long name this is a long name this is a long name this is a long name this is a long name this is a long name this is a long name this is a long name this is a long name this is a long name this is a long name this is a long name ");
        EmptyRepoPage repoPage = createRepositoryPage.createEmptyRepoSuccess();
        assertFalse(repoPage.inRepoPage());
    }

    //TC17--------------------------------
    @Test
    @DisplayName("test - create repo with long repo name ")
    public void templateARepo() {
        SignInPage signInPage = welcomePage.signIn();
        Dashboard dashboard = signInPage.SignInDoNotRememberDeviceValid(userName,password);
        CreateRepositoryPage createRepositoryPage = dashboard.createRepo();
        createRepositoryPage.validName(repositoryName);
        createRepositoryPage.setTemplateTheRepo();
        EmptyRepoPage repoPage = createRepositoryPage.createEmptyRepoSuccess();
        assertTrue(repoPage.inRepoPage());
    }

    //TC19--------------------------------
    @Test
    @DisplayName("test - template .gitignore ")
    public void templateGitIgnore() {
        SignInPage signInPage = welcomePage.signIn();
        Dashboard dashboard = signInPage.SignInDoNotRememberDeviceValid(userName,password);
        CreateRepositoryPage createRepositoryPage = dashboard.createRepo();
        createRepositoryPage.validName(repositoryName);
        createRepositoryPage.selectGitIgnoreTemplateByIndex(3);
        createRepositoryPage.setTemplateTheRepo();
        NoneEmoptyRepoPage repoPage = createRepositoryPage.createNoneEmptyRepoSuccess();
        assertTrue(repoPage.inRepoPage());
    }

    //TC20--------------------------------
    @Test
    @DisplayName("test - create repo and template it with .gitignore and licence ")
    public void templateGitIgnoreLicense() {
        SignInPage signInPage = welcomePage.signIn();
        Dashboard dashboard = signInPage.SignInDoNotRememberDeviceValid(userName,password);
        CreateRepositoryPage createRepositoryPage = dashboard.createRepo();
        createRepositoryPage.validName(repositoryName);
        createRepositoryPage.selectGitIgnoreTemplateByIndex(3);
        createRepositoryPage.selectLicenceByTapping("aa");
        createRepositoryPage.setTemplateTheRepo();
        NoneEmoptyRepoPage repoPage = createRepositoryPage.createNoneEmptyRepoSuccess();
        assertTrue(repoPage.inRepoPage());
    }

    //TC23--------------------------------
    @Test
    public void templateAvatarWebHook() {
        SignInPage signInPage = welcomePage.signIn();
        Dashboard dashboard = signInPage.SignInDoNotRememberDeviceValid(userName,password);
        CreateRepositoryPage createRepositoryPage = dashboard.createRepo();
        createRepositoryPage.validName(repositoryName);
        createRepositoryPage.selectTemplateByIndex(3);
        createRepositoryPage.setWebHooksBox();
        createRepositoryPage.setAvatarBox();
        EmptyRepoPage repoPage = createRepositoryPage.createEmptyRepoSuccess();
        assertTrue(repoPage.inRepoPage());
    }

    public void deleteRepo() throws IOException {
        String token = apiToken;
        if (repositoryName != null) {
            String apiUrl = URL + "/api/v1/repos/moslem/" + repositoryName;
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Authorization", "token " + token);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                //System.out.println("Repository deleted successfully: " + repositoryName);
            } else {
                //System.err.println("Failed to delete repository. Response code: " + responseCode);
            }
        } else {
            //System.err.println("Repository name is null or empty.");
        }
    }

    @AfterEach
    public void tearDown() throws IOException {
        deleteRepo();
        driver.quit();
    }

}