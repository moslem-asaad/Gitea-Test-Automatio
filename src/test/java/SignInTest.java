import org.example.DriverFactory;
import org.example.WelcomePage;
import org.example.SignInPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import static org.junit.jupiter.api.Assertions.*;


import java.net.MalformedURLException;

public class SignInTest {

    private WebDriver driver;

    private WelcomePage welcomePage;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        welcomePage = new WelcomePage(driver).get();

    }

    @Test
    public void testAccessToSignInSuccess() {
        SignInPage signInPage = welcomePage.signIn();
    }

    @Test
    public void testInvalidSignInExistUserName() {
        SignInPage signInPage = welcomePage.signIn();
        assertTrue(signInPage.SignInDoNotRememberDeviceInValid("unexist","password").failedLogIn());
    }

    @Test
    public void testInvalidSignEmptyUserName() {
        SignInPage signInPage = welcomePage.signIn();

        assertTrue(signInPage.SignInDoNotRememberDeviceInValid("","password").failedLogIn());
    }


    @Test
    public void testInvalidSignInExistEmail() {
        SignInPage signInPage = welcomePage.signIn();
        assertTrue(signInPage.SignInDoNotRememberDeviceInValid("abc@gmail.com","password").failedLogIn());
    }

    @Test
    public void testInvalidSignInWrongPassword() {
        SignInPage signInPage = welcomePage.signIn();
        assertTrue(signInPage.SignInDoNotRememberDeviceInValid("moslem","password").failedLogIn());
    }

    @Test
    public void testInvalidSignInEmptyPassword() {
        SignInPage signInPage = welcomePage.signIn();
        assertTrue(signInPage.SignInDoNotRememberDeviceInValid("moslem","").failedLogIn());
    }

    @Test
    public void testSignInSuccess() {
        SignInPage signInPage = welcomePage.signIn();
        signInPage.SignInDoNotRememberDeviceValid("moslem","80517moslem");
        assertFalse(signInPage.failedLogIn());
    }

    @Test
    public void testSignInSuccess2() {
        SignInPage signInPage = welcomePage.signIn();
        signInPage.SignInDoNotRememberDeviceValid("asaadmoslem2000@gmail.com","80517moslem");
        assertFalse(signInPage.failedLogIn());
    }




    @AfterEach
    public void tearDown() throws InterruptedException {
        driver.quit();
    }
}
