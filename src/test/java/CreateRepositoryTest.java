
import org.example.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import static org.junit.jupiter.api.Assertions.*;


import java.net.MalformedURLException;

public class CreateRepositoryTest {

    private WebDriver driver;

    private WelcomePage welcomePage;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        welcomePage = new WelcomePage(driver).get();
    }

    @Test
    public void testAccessToCreateRepoSuccess() {
        SignInPage signInPage = welcomePage.signIn();
        Dashboard dashboard = signInPage.SignInDoNotRememberDeviceValid("moslem","80517moslem");
        dashboard.createRepo();
    }

    //TC1 -------------------------

    @Test
    public void testCreateRepoInvalidName() {
        SignInPage signInPage = welcomePage.signIn();
        Dashboard dashboard = signInPage.SignInDoNotRememberDeviceValid("moslem","80517moslem");
        assertTrue(dashboard.createRepo().inValidRepoNameCreation("@@invalid!!").failCreation());

    }

    //TC2----------------------------
    @Test
    public void testCreateRepoValidName() {
        String repoName = "test_repo_" + System.currentTimeMillis(); //todo: use mock to not load the database?
        SignInPage signInPage = welcomePage.signIn();
        Dashboard dashboard = signInPage.SignInDoNotRememberDeviceValid("moslem","80517moslem");
        CreateRepositoryPage createRepositoryPage=  dashboard.createRepo();
        EmptyRepoPage repositoryPage = (EmptyRepoPage) createRepositoryPage.ValidRepoNameCreation(repoName);
        assertTrue(repositoryPage.inRepoPage());
        repositoryPage.clickIsues();
    }

    //TC3-----------------------------
    @Test
    public void testCreateRepoDublicateName() {
        SignInPage signInPage = welcomePage.signIn();
        Dashboard dashboard = signInPage.SignInDoNotRememberDeviceValid("moslem","80517moslem");
        assertTrue(dashboard.createRepo().inValidRepoNameCreation("testRepo").failCreation());
    }

    //TC4-----------------------------
    @Test
    public void testCreateRepoEmptyName() {
        SignInPage signInPage = welcomePage.signIn();
        Dashboard dashboard = signInPage.SignInDoNotRememberDeviceValid("moslem","80517moslem");
        assertTrue(dashboard.createRepo().inValidRepoNameCreation("").failCreation());
    }

    //TC5-----------------------------
    @Test
    public void testCreateRepoWithGitIgnoreTemplateByIndex() {
        SignInPage signInPage = welcomePage.signIn();
        Dashboard dashboard = signInPage.SignInDoNotRememberDeviceValid("moslem","80517moslem");
        CreateRepositoryPage createRepositoryPage = dashboard.createRepo();
        createRepositoryPage.selectGitIgnoreTemplateByIndex(0);
        //todo: add the create repo button and click
        assertTrue(createRepositoryPage.failCreation());

    }

    @Test
    public void testCreateRepoWithGitIgnoreTemplateByTapping() {
        SignInPage signInPage = welcomePage.signIn();
        Dashboard dashboard = signInPage.SignInDoNotRememberDeviceValid("moslem","80517moslem");
        CreateRepositoryPage createRepositoryPage = dashboard.createRepo();
        createRepositoryPage.selectGitIgnoreTemplateByTapping("a");
        //todo: add the create repo button and click
        assertTrue(createRepositoryPage.failCreation());

    }




    @AfterEach
    public void tearDown() throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
    }

}