package TestCases;

import Base.BaseMethods;
import Base.TestMethods;
import PageObjects.HomePage;
import PageObjects.LoginPage;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.Properties;

public class TestLogin extends TestMethods {

    private final BaseMethods bm = new BaseMethods();
    public HomePage home;
    public LoginPage login;
    Properties props;
    {
        try {
            props = bm.readPropertiesFile("src/test/java/resources/config.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Parameters({"environment", "environmentLabel", "reportType", "extension"})
    @BeforeClass
    public void beforeTest(@Optional("https://rugietmen.com/") String environment, @Optional ("PROD") String environmentLabel, @Optional ("Sample") String reportType, @Optional (".html") String extension) throws IOException {
        home = new HomePage(driver);
        login = new LoginPage(driver);
        extentHtmlReporter = new ExtentHtmlReporter(baseMethods.reportFilePath(BaseMethods.project, environmentLabel, reportType, extension));
        report.attachReporter(extentHtmlReporter);

        driver.get(environment);
    }

    @Parameters({"environmentLabel", "reportType", "extension"})
    @AfterClass
    public void sendEmail (@Optional("PROD") String environmentLabel, @Optional ("Test Login") String reportType, @Optional (".html") String extension) {
        report.flush();
        String[] emails = {"domoniq.doby@gmail.com"};
        for (int email = 0; email < emails.length; email++) {
            baseMethods.sendEmail(BaseMethods.project, environmentLabel, reportType, extension, emails[email], "Environment: " +environmentLabel+ ": Automation: Test Core Workflow", "test test");
        }

        driver.quit();
    }

    @Test(priority = 1)
    public void testLoginError() {
        test = report.createTest("Test login Error");

        // verify correct home URL
        verifyURL(home.homePageURL);
        // navigate to login page via login button
        clickElement(home.headerLoginButton, "Login Button on homepage");
        // verify email/password error is not displayed
        elementIsNotDisplayed(login.loginEmailPasswordErrorBanner, "Email/Password error");
        // enter email and password, then click submit
        sendTextToInputField(login.loginUserEmail, props.getProperty("login.email"), "Email text field");
        sendTextToInputFieldHideText(login.loginUserPassword, "wrongPass", "Password text field");
        clickElement(login.loginSubmitButton, "Submit button");
        // verify email/password error is displayed
        elementIsDisplayed(login.loginEmailPasswordErrorBanner, "Email/Password error");

    }

    @Test(priority = 2)
    public void testLoginSuccess(){
        test = report.createTest("Test login Success");

        //verify correct home URL
        verifyURL(home.homePageURL);
        // navigate to login page via login button
        clickElement(home.headerLoginButton, "Login Button on homepage");
        // enter email and password, then click submit
        sendTextToInputField(login.loginUserEmail, props.getProperty("login.email"), "Email text field");
        sendTextToInputFieldHideText(login.loginUserPassword, props.getProperty("login.password"), "Password text field");
        clickElement(login.loginSubmitButton, "Submit button");
    }
}
