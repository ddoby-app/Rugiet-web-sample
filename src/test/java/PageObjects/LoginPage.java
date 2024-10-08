package PageObjects;

import Base.TestMethods;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    public LoginPage(RemoteWebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "[id=user_email]")
    public WebElement loginUserEmail;
    @FindBy(css = "[id=user_password]")
    public WebElement loginUserPassword;
    @FindBy(css = "body > main > section > div.mx-auto.max-w-\\[350px\\].md\\:max-w-\\[500px\\] > form > input.w-full.h-\\[57px\\].bg-black-charcoal.text-white.py-2.mt-4.rounded.text-xl.font-bold.uppercase.hover\\:bg-black-charcoal-dark.tracking-wider.hover\\:opacity-90")
    public WebElement loginSubmitButton;
    @FindBy(css = "body[data-controller=alerts] > p.alert.alert-danger")
    public WebElement loginEmailPasswordErrorBanner;
}
