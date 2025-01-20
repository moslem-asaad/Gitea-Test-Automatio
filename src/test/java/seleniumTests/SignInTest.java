package seleniumTests;

import org.example.DriverFactory;
import org.example.EnvLoader;
import org.example.WelcomePage;
import org.example.SignInPage;
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

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;



public class SignInTest {

    private WebDriver driver;

    private WelcomePage welcomePage;

    private final String userName = "moslem";
    private static String password;

    private final String URL = "https://76e6-5-29-126-14.ngrok-free.app";
    //private final String URL = "http://localhost:3000";


    @BeforeEach
    public void setUp(){
        System.out.println("in set up sign in tests");
        readENV();
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        driver.get(URL);
        try {
            Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement visitSiteButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Visit Site']")));
            visitSiteButton.click();
        } catch (TimeoutException err) {
            System.out.println("Ngrok warning page was not loaded");
        }
        System.out.println("before init welcome page");
        welcomePage = new WelcomePage(driver).get();

    }

    private void readENV(){
//        EnvLoader.loadEnv(".env");
//        password = EnvLoader.getEnv("Password");
//        if (password == null || password.isEmpty()){
            password = System.getenv("Password");
        //} //
    }

    @Test
    public void testAccessToSignInSuccess() {
        System.out.println("test - sign in page access success");
        SignInPage signInPage = welcomePage.signIn();
    }

    @Test
    public void testInvalidSignInExistUserName() {
        System.out.println("test - Invalid sign in username");
        SignInPage signInPage = welcomePage.signIn();
        assertTrue(signInPage.SignInDoNotRememberDeviceInValid("unexist","password").failedLogIn());
    }

    @Test
    public void testInvalidSignEmptyUserName() {
        System.out.println("test - Invalid sign in empty username");
        SignInPage signInPage = welcomePage.signIn();

        assertTrue(signInPage.SignInDoNotRememberDeviceInValid("","password").failedLogIn());
    }


    @Test
    public void testInvalidSignInExistEmail() {
        System.out.println("test - Invalid sign in email");
        SignInPage signInPage = welcomePage.signIn();
        assertTrue(signInPage.SignInDoNotRememberDeviceInValid("abc@gmail.com","password").failedLogIn());
    }

    @Test
    public void testInvalidSignInWrongPassword() {
        System.out.println("test - Invalid sign in wrong password");
        SignInPage signInPage = welcomePage.signIn();
        assertTrue(signInPage.SignInDoNotRememberDeviceInValid(userName,"password").failedLogIn());
    }

    @Test
    public void testInvalidSignInEmptyPassword() {
        System.out.println("test - Invalid sign in empty password");
        SignInPage signInPage = welcomePage.signIn();
        assertTrue(signInPage.SignInDoNotRememberDeviceInValid(userName,"").failedLogIn());
    }

    @Test
    public void testSignInSuccess() {
        System.out.println("test -  sign in success");
        SignInPage signInPage = welcomePage.signIn();
        signInPage.SignInDoNotRememberDeviceValid(userName,password);
        assertFalse(signInPage.failedLogIn());
    }

    @Test
    public void testSignInSuccess2() {
        System.out.println("test - sign in success2");
        SignInPage signInPage = welcomePage.signIn();
        signInPage.SignInDoNotRememberDeviceValid("asaadmoslem2000@gmail.com",password);
        assertFalse(signInPage.failedLogIn());
    }


    @AfterEach
    public void tearDown(){
        driver.quit();
    }
}
