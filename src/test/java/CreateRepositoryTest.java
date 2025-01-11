
import org.example.Dashboard;
import org.example.DriverFactory;
import org.example.WelcomePage;
import org.example.SignInPage;
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

    @Test
    public void testCreateRepoInvalidName() {
        SignInPage signInPage = welcomePage.signIn();
        Dashboard dashboard = signInPage.SignInDoNotRememberDeviceValid("moslem","80517moslem");
        assertTrue(dashboard.createRepo().inValidRepoNameCreation("@@invalid!!").failCreation());

    }

    @Test
    public void testCreateRepoEmptyName() {
        SignInPage signInPage = welcomePage.signIn();
        Dashboard dashboard = signInPage.SignInDoNotRememberDeviceValid("moslem","80517moslem");
        assertTrue(dashboard.createRepo().inValidRepoNameCreation("").failCreation());
    }


    @AfterEach
    public void tearDown() throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
    }

}