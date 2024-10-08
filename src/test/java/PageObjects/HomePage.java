package PageObjects;

import Base.TestMethods;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    public HomePage(RemoteWebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public String homePageURL = "https://rugietmen.com/";

    // HomePage Header

    @FindBy(css = "div.hidden.items-center > a")
    public WebElement headerRugietTitleLink;
    @FindBy(css = "div.hidden.items-center > div > a:nth-child(1) > button")
    public WebElement headerCustomizeYourTreatmentButton;
    @FindBy(css = "div.hidden.items-center > div > a:nth-child(2) > button")
    public WebElement headerLoginButton;
    @FindBy(css = "div.hidden.items-center > div > button.desktop-hamburger")
    public WebElement headerHamburgerButton;

    // Login Page




}