package Base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.log.Log;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Random;

import static Base.DebugColor.*;

public class TestMethods {

    public static RemoteWebDriver driver;
    public ExtentReports report = new ExtentReports();
    public ExtentTest test;
    public ExtentTest node;
    public BaseMethods baseMethods = new BaseMethods();
    public ExtentHtmlReporter extentHtmlReporter;
    public WebDriverWait wait;
    public SoftAssert sa = new SoftAssert();

    @BeforeSuite
    public void driverSetup() {
        Platform platform = Platform.getCurrent();
        if (platform.is(Platform.MAC)) {
            System.out.println("This is Mac");
            System.setProperty("webdriver.chrome.driver", baseMethods.driverFilePath(BaseMethods.project, "chromedriver"));
        } else if (platform.is(Platform.WINDOWS)) {
            System.out.println("This is Windows");
            System.setProperty("webdriver.chrome.driver", baseMethods.driverFilePath(BaseMethods.project, "chromedriver.exe"));
        }

        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    // Test Methods: click element Methods
    public void clickElement (WebElement element, String elementName) {
        node = test.createNode("Verify ability to click " + elementName);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            element.click();
            //sa.assertTrue(element.isSelected());
            node.pass("Able to click \"" + elementName + "\" successfully.");
            System.out.println(ANSI_GREEN+"Able to click \"" + elementName + "\" successfully.");
        } catch (StaleElementReferenceException | NoSuchElementException| TimeoutException ex ) {
            try {
                node.fail("Unable to click the \"" + elementName + "\" web element", MediaEntityBuilder.createScreenCaptureFromBase64String("screenshot.png").build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(ANSI_RED+"Unable to click the \"" + elementName + "\" web element");
            System.out.println(ANSI_RED+"Exception = " + ex);
        }
    }

    public void clickElementWhenAttribute (WebElement elementClicked, WebElement attributeElement, String attribute, String attributeValue, String elementName) {
        node = test.createNode("Verify ability to click " + elementName);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            //wait.until(ExpectedConditions.attributeContains(attributeElement, attribute, attributeValue));
            ExpectedCondition<Boolean> elementAttributeContainsString = arg0 -> attributeElement.getAttribute(attribute).contains
                    (attributeValue);
            wait.until(elementAttributeContainsString);
            threadSleep(5);
            clickElement(elementClicked, elementName);
        } catch (StaleElementReferenceException | NoSuchElementException | TimeoutException ex) {
            try {
                node.fail("Unable to click the \"" + elementName + "\" web element", MediaEntityBuilder.createScreenCaptureFromBase64String("screenshot.png").build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(ANSI_RED+"Unable to click the \"" + elementName + "\" web element");
            System.out.println(ANSI_RED+"Exception = " + ex);
        }
    }

    public void jsClickElement(WebElement element, String elementName) {
        node = test.createNode("Verify ability to click " + elementName + " via JavaScript Execution");
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            JavascriptExecutor je = driver;
            je.executeScript("arguments[0].click()",element);
            sa.assertTrue(element.isSelected());
            node.pass(ANSI_GREEN+"Able to click " + elementName + " successfully. (Done using Javascript Execution)");
            System.out.println(ANSI_GREEN+"Able to click " + elementName + " successfully. (Done using Javascript Execution)");
        } catch (StaleElementReferenceException | NoSuchElementException | TimeoutException ex) {
            try {
                node.fail("Unable to click the " + elementName + "web element", MediaEntityBuilder.createScreenCaptureFromBase64String("screenshot.png").build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(ANSI_RED+"Unable to click the " + elementName + "web element. (Attempt using Javascript Execution");
            System.out.println(ANSI_RED+"Exception = " + ex);
        }
    }

    // Send Text to Input Methods
    public void sendTextToInputField (WebElement element, String textSent, String elementName)  {
        node = test.createNode("Verify ability to send " + textSent + " to " + elementName);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            element.clear();
            element.sendKeys(textSent);
            sa.assertEquals(textSent, element.getText());
            node.pass("Able to click " + elementName + " successfully and type \"" + textSent + "\" in input Field.");
            System.out.println(ANSI_GREEN+"Test Passed for " + elementName + " and able to enter \"" + textSent + "\"");
        } catch (StaleElementReferenceException | NoSuchElementException | TimeoutException ex) {
            try {
                node.fail("Unable to send keys \"" + textSent + "\" to the " + elementName + " input field.", MediaEntityBuilder.createScreenCaptureFromBase64String("screenshot.png").build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(ANSI_RED+"Unable to enter \"" + textSent + "\" in " + elementName);
            System.out.println(ANSI_RED+"Exception = " + ex);
        }
    }
    public void sendTextToInputFieldHideText (WebElement element, String textSent, String elementName)  {
        node = test.createNode("Verify ability to send " + textSent + " to " + elementName);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            element.clear();
            element.sendKeys(textSent);
            sa.assertEquals(textSent, element.getText());
            node.pass("Able to click " + elementName + " successfully and type ************** in input Field.");
            System.out.println(ANSI_GREEN+"Test Passed for " + elementName + " and able to enter ***********");
        } catch (StaleElementReferenceException | NoSuchElementException | TimeoutException ex) {
            try {
                node.fail("Unable to send keys ********* to the " + elementName + " input field.", MediaEntityBuilder.createScreenCaptureFromBase64String("screenshot.png").build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(ANSI_RED+"Unable to enter ********** in " + elementName);
            System.out.println(ANSI_RED+"Exception = " + ex);
        }
    }

    // Select elements From Dropdown methods
    public void selectElementFromDropdown (WebElement element, String textSelect, String elementName)  {
        node = test.createNode("Verify ability to select " + textSelect + " from " + elementName + " dropdown");
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            Select select = new Select (element);
            //select.selectByVisibleText(textSelect);
            new Select(element).selectByValue(textSelect);
            sa.assertEquals(textSelect, element.getText());
            node.pass("Able to select \"" + textSelect + "\" successfully from the \"" + elementName + "\" dropdown.");
            System.out.println(ANSI_GREEN+"Able to select \"" + textSelect + "\" successfully from the \"" + elementName + "\" dropdown.");
        } catch (StaleElementReferenceException | NoSuchElementException | TimeoutException ex) {
            try {
                node.fail("Unable to select " + textSelect + " from the " + elementName + " dropdown.", MediaEntityBuilder.createScreenCaptureFromBase64String("screenshot.png").build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(ANSI_RED+"Unable to select " + textSelect + " from the " + elementName + " dropdown.");
            System.out.println(ANSI_RED+"Exception = " + ex);
        }
    }
    public void selectElementFromDropdownByValue (WebElement element, String value, String elementName)  {
        node = test.createNode("Verify ability to select " + value + " from " + elementName + " dropdown");
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            wait.until((ExpectedCondition<Boolean>) new ExpectedCondition<Boolean>(){
                public Boolean apply(WebDriver driver)
                {
                    Select select = new Select(element);
                    return select.getOptions().size()>1;
                }
            });
            Select select = new Select (element);
            select.selectByValue(value);
            //new Select(element).selectByValue(value);
            node.pass("Able to select \"" + value + "\" successfully from the \"" + elementName + "\" dropdown.");
            System.out.println(ANSI_GREEN+"Able to select \"" + value + "\" successfully from the \"" + elementName + "\" dropdown.");
        } catch (StaleElementReferenceException | NoSuchElementException | TimeoutException ex) {
            try {
                node.fail("Unable to select " + value + " from the " + elementName + " dropdown.", MediaEntityBuilder.createScreenCaptureFromBase64String("screenshot.png").build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(ANSI_RED+"Unable to select " + value + " from the " + elementName + " dropdown.");
            System.out.println(ANSI_RED+"Exception = " + ex);
        }
    }

    //Random Selenium Methods
    public void jsAttachFile(WebElement element, String elementName, String filePath) {
        node = test.createNode("Verify ability to click " + elementName + " via JavaScript Execution");
        Platform platform = Platform.getCurrent();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        if (platform.is(Platform.WINDOWS)) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(element));
                JavascriptExecutor je = driver;
                je.executeScript("arguments[0].click()",element);
                Thread.sleep(3000);
                sa.assertTrue(element.isSelected());
                Runtime.getRuntime().exec(filePath);
                Thread.sleep(1000);
                node.pass(ANSI_GREEN+"Able to attach a file to " + elementName + " successfully. (Done using Javascript Execution and AutoIt) ");
                System.out.println(ANSI_GREEN+"\"Able to attach a file to " + elementName + " successfully. (Done using Javascript Execution)");
            } catch (StaleElementReferenceException | NoSuchElementException | TimeoutException | IOException | InterruptedException ex) {
                node.fail("UnAble to attach a file to " + elementName + "web element");
                System.out.println(ANSI_RED+"UnAble to attach a file to " + elementName + "web element. (Attempt using Javascript Execution");
                System.out.println(ANSI_RED+"Exception = " + ex);
            }
        } else if (platform.is(Platform.MAC)) {
            try {
                Robot robot = new Robot();
                StringSelection file = new StringSelection(filePath);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(file, null);
                wait.until(ExpectedConditions.elementToBeClickable(element));
                String current = driver.getWindowHandle();
                JavascriptExecutor je = driver;
                je.executeScript("arguments[0].click()",element);
                threadSleep(2);
                //je.executeScript("alert('Test')");
                //driver.switchTo().alert().accept();
                driver.switchTo().window(current);
                Thread.sleep(3000);
                robot.keyPress(KeyEvent.VK_META);
                robot.delay(200);
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.delay(200);
                robot.keyPress(KeyEvent.VK_G);
                robot.keyRelease(KeyEvent.VK_SHIFT);
                robot.keyRelease(KeyEvent.VK_META);
                robot.delay(500);
                robot.keyPress(KeyEvent.VK_META);
                robot.delay(200);
                robot.keyPress(KeyEvent.VK_V);
                robot.keyRelease(KeyEvent.VK_META);
                robot.delay(200);
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.delay(500);
                robot.keyPress(KeyEvent.VK_ENTER);
                Thread.sleep(3000);
                node.pass(ANSI_GREEN+"Able to attach a file to " + elementName + " successfully. (Done using Javascript Execution and AutoIt) ");
                System.out.println(ANSI_GREEN+"\"Able to attach a file to " + elementName + " successfully. (Done using Javascript Execution)");
            } catch (StaleElementReferenceException | NoSuchElementException | TimeoutException | AWTException | InterruptedException ex) {
                node.fail("UnAble to attach a file to " + elementName + "web element");
                System.out.println(ANSI_RED+"UnAble to attach a file to " + elementName + "web element. (Attempt using Javascript Execution");
                System.out.println(ANSI_RED+"Exception = " + ex);
            }
        }
    }
    public void acceptAlert() {
        try {
            node = test.createNode("Accepting Alert");
            threadSleep(1);
            driver.switchTo().alert().accept();
            threadSleep(1);
        }catch (NoAlertPresentException ex){
            System.out.println(ex);
        }
    }
    public void scrollDown(String horizontal, String vertical) {
        JavascriptExecutor js = driver;
        System.out.println("Page size (horizontal, vertical) is: "+js.executeScript("return $(document).height"));
        js.executeScript("window.scrollBy("+horizontal+","+vertical+")", "");
    }

    public void elementSelected (WebElement element, String elementName){
        node = test.createNode("Verify ability to check that " + elementName + " is selected");
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            sa.assertTrue(element.isSelected());
            node.pass(elementName + " is Selected correctly");
            System.out.println("Test Passed: " + elementName + " is selected.");
        } catch (StaleElementReferenceException | NoSuchElementException | TimeoutException ex) {
            node.fail(elementName + " is NOT selected in the UI.");
            System.out.println("Test Failed for " + elementName);
        }

    }
    public void elementIsNotSelected (WebElement element, String elementName){
        node = test.createNode("Verify ability to check that " + elementName + " is NOT selected");
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            sa.assertFalse(element.isSelected());
            node.pass(elementName + " is NOT Selected correctly");
            System.out.println("Test Passed: " + elementName + " is NOT selected.");
        } catch (StaleElementReferenceException | NoSuchElementException | TimeoutException ex) {
            node.fail(elementName + " is incorrectly selected in the UI.");
            System.out.println("Test Failed for " + elementName);
        }

    }
    public void elementIsDisplayed (WebElement element, String elementName) {
        node = test.createNode("Verify ability to check that " + elementName + " is displayed");
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            sa.assertTrue(element.isDisplayed());
            node.pass(elementName + " is correctly displayed in the UI.");
            System.out.println("Test Passed for " + elementName);
        } catch (StaleElementReferenceException | NoSuchElementException | TimeoutException ex) {
            node.fail(elementName + " is NOT  displayed in the UI.");
            System.out.println("Test Failed for " + elementName);

        }
    }
    public void elementIsNotDisplayed (WebElement element, String elementName) {
        node = test.createNode("Verify ability to check that " + elementName + " is NOT displayed");
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            wait.until(ExpectedConditions.invisibilityOf(element));
            sa.assertFalse(element.isDisplayed());
            node.pass(elementName + " is correctly NOT displayed in the UI.");
            System.out.println("Test Passed for " + elementName);
        } catch (StaleElementReferenceException | NoSuchElementException | TimeoutException ex) {
            node.fail(elementName + " is incorrectly displayed in the UI.");
            System.out.println("Test Failed for " + elementName);

        }
    }

    public void textDisplayedInElement (WebElement element, String textDisplayed, String elementName){
        node = test.createNode("Verify ability to check that " + textDisplayed + " is Displayed in the " + elementName + " textbox");
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(element, textDisplayed));
            sa.assertEquals(element.getText(), textDisplayed);
            node.pass("The text \"" + textDisplayed + "\" is displayed correctly in " + elementName);
            System.out.println("Test Passed for " + elementName);
        } catch (StaleElementReferenceException | NoSuchElementException | TimeoutException ex) {
            node.fail("The text \"" + textDisplayed + "\" is NOT displayed correctly in " + elementName);
            System.out.println("Test Failed for " + elementName);
        }
    }

    public void verifyAttributeFromElement (WebElement element, String attribute, String attributeText, String elementName){
        node = test.createNode("Verify ability to check that " + attribute + " of " + elementName + " is correct.");
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            sa.assertTrue(element.isDisplayed());
            sa.assertEquals(element.getAttribute(attribute), attributeText);
            node.pass("The text \"" + attributeText + "\" is displayed correctly in " + elementName);
            System.out.println("Test Passed for " + elementName);
        } catch (StaleElementReferenceException | NoSuchElementException | TimeoutException ex) {
            node.fail("The text \"" + attributeText + "\" is NOT displayed correctly in " + elementName);
            System.out.println("Test Failed for " + elementName);
        }
    }
    public void clickElementWithAttributeValue (WebElement element,String elementName, String attribute, String attributeValue) {
        node = test.createNode("Verify ability to click " + elementName + " with the " + attribute + " attribute and its value: " + attributeValue);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            sa.assertTrue(element.isDisplayed());
            sa.assertEquals(element.getAttribute(attribute),attributeValue);
            node.pass("Able to click " + elementName + "with the attribute: " + attribute + " and attribute value " + attributeValue);
            System.out.println("Test Passed for " + elementName);
        } catch (StaleElementReferenceException | NoSuchElementException | TimeoutException ex) {
            node.fail("Unable to click");
            System.out.println("Test Failed for " + elementName);
        }
    }

    public void verifyURL (String currentURL)  {
        node = test.createNode("Verify that " + currentURL + " is the current URL");
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.urlToBe(currentURL));
            sa.assertEquals(driver.getCurrentUrl(), currentURL);
            node.pass("Verified the current correct URL is \"" + currentURL + "\"");
            System.out.println(ANSI_GREEN+"Verified the current correct URL is \"" + currentURL + "\"");
            //consoleLogTest2(currentURL);
        } catch (StaleElementReferenceException | NoSuchElementException | TimeoutException ex){
            node.fail("Failed to verify current URL:" + currentURL);
            System.out.println(ANSI_RED+"Failed to verify current URL:" + currentURL);
            System.out.println(ANSI_RED+"Exception = " + ex);
        }
    }

    public void switchToIFrameEnterText(WebElement element, WebElement iFrame, String elementName, String elementText) {
        node = test.createNode("Verify switch to iFrame and enter text in " + elementName);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            driver.switchTo().frame(iFrame);
            sendTextToInputField(element, elementText, elementName);
            driver.switchTo().parentFrame();
        } catch (StaleElementReferenceException | NoSuchElementException | TimeoutException ex) {
            System.out.println(ANSI_RED+"Unable to switch to iframe and enter text in " +elementName);
            System.out.println(ANSI_RED+"Exception = " + ex);
        }
    }

    public void switchToIFrameVerifyText(WebElement element, WebElement iFrame, String elementText, String elementName) {
        node = test.createNode("Verify switch to iFrame and verify value in " + elementName);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            driver.switchTo().frame(iFrame);
            wait.until(ExpectedConditions.textToBePresentInElement(element, elementText));
            sa.assertEquals(element.getText(), elementText);
            driver.switchTo().parentFrame();
        } catch (StaleElementReferenceException | NoSuchElementException | TimeoutException ex) {
            System.out.println(ANSI_RED+"Unable to switch to iframe and verify value in " +elementName);
            System.out.println(ANSI_RED+"Exception = " + ex);
        }
    }

    public void verifyElementText (WebElement element, String elementName, String elementText)  {
        node = test.createNode("Verify that text in " + elementName + " is " + elementText);
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(element, elementText));
            sa.assertEquals(element.getText(), elementText);
            node.pass("Verify that the text \"" + elementText + "\" is displayed correctly in " + elementName);
            System.out.println(ANSI_GREEN+"Verify that the text \"" + elementText + "\" is displayed correctly in " + elementName);
        } catch (StaleElementReferenceException | NoSuchElementException | TimeoutException ex) {
            node.fail("The text \"" + elementText + "\" is NOT displayed correctly in " + elementName);
            System.out.println(ANSI_RED+"The text \"" + elementText + "\" is NOT displayed correctly in " + elementName);
            System.out.println(ANSI_RED+"Exception = " + ex);
        }
    }

    public void verifyElementTextNotEmpty (WebElement element, String elementName)  {
        node = test.createNode("Verify that text in " + elementName + " is not an Empty String.");
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            sa.assertNotEquals(element.getText(), "");
            node.pass("Verify that the text in \"" + elementName + "\" is not empty");
            System.out.println(ANSI_GREEN+"Verify that the text in \"" + elementName + "\" is not empty");
            System.out.println(ANSI_CYAN+"----------Text reads: " + element.getText());
        } catch (StaleElementReferenceException | NoSuchElementException | TimeoutException ex) {
            node.fail("The text \"" + elementName + "\" is an EMPTY string");
            System.out.println(ANSI_RED+"The text \"" + elementName + "\" is an EMPTY string");
            System.out.println(ANSI_RED+"Exception = " + ex);
        }
    }

    public void verifyElementValue (WebElement element, String elementValue, String elementName)  {
        node = test.createNode("Verify that value(text) in " + elementName + " is " + elementValue);
        try {
            wait.until(ExpectedConditions.textToBePresentInElementValue(element, elementValue));
            sa.assertEquals(element.getAttribute("value"), elementValue);
            node.pass("Verify that the text \"" + elementValue + "\" is displayed correctly in " + elementName);
            System.out.println(ANSI_GREEN+"Verify that the text \"" + elementValue + "\" is displayed correctly in " + elementName);
        } catch (StaleElementReferenceException | NoSuchElementException | TimeoutException ex) {
            node.fail("The text \"" + elementValue + "\" is NOT displayed correctly in " + elementName);
            System.out.println(ANSI_RED+"The text \"" + elementValue + "\" is NOT displayed correctly in " + elementName);
            System.out.println(ANSI_RED+"Exception = " + ex);
        }
    }

    public void verifyElementValueNotEmpty (WebElement element, String elementName)  {
        node = test.createNode("Verify that value(text) in " + elementName + " is not an Empty String.");
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            sa.assertNotEquals(element.getAttribute("value"), "");
            node.pass("Verify that the text in \"" + elementName + "\" is not empty");
            System.out.println(ANSI_GREEN+"Verify that the text in \"" + elementName + "\" is not empty");
        } catch (StaleElementReferenceException | NoSuchElementException | TimeoutException ex) {
            node.fail("The text \"" + elementName + "\" is an EMPTY string");
            System.out.println(ANSI_RED+"The text \"" + elementName + "\" is an EMPTY string");
            System.out.println(ANSI_RED+"Exception = " + ex);
        }
    }

    public void jsSetAttribute(WebElement element, String elementName, String attName, String attValue) {
        node = test.createNode("Verify that attribute " + attName + "in " + elementName + " is " + attValue);
        try {
            wait.until(ExpectedConditions.attributeToBe(element, attName, ""));
            driver.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);",
                    element, attName, attValue);
            node.pass("Verify that attribute " + attName + "in " + elementName + " is " + attValue);
            System.out.println(ANSI_GREEN+"Verify that attribute " + attName + "in " + elementName + " is " + attValue);
        } catch (StaleElementReferenceException | NoSuchElementException | TimeoutException ex){
            node.fail("Unable to verify that attribute " + attName + "in " + elementName + " is " + attValue);
            System.out.println(ANSI_RED+"Unable to verify that attribute " + attName + "in " + elementName + " is " + attValue);
            System.out.println(ANSI_RED+"Exception = " + ex);
        }
    }

    public void verifyTextMatch (WebElement elementOne, WebElement elementTwo, String elementTwoURL, String elementOneName, String elementTwoName) {
        node = test.createNode("Verify that the text from " + elementOneName + " matches the text from " +  elementTwoName + " in the URL: " + elementTwoURL);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String oldTab = driver.getWindowHandle();
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(elementOne, elementOne.getText()));
            String elementOneText = elementOne.getText();
            System.out.println(elementOneText);
            RemoteWebDriver newTab = (RemoteWebDriver) driver.switchTo().newWindow(WindowType.TAB);
            newTab.get(elementTwoURL);
            wait.until(ExpectedConditions.textToBePresentInElement(elementTwo, elementTwo.getText()));
            String elementTwoText = elementTwo.getText();
            System.out.println(elementTwoText);
            sa.assertEquals(elementOneText,elementTwoText);
            node.pass("The text in \"" + elementOneName + "\" is the same as the text in \"" + elementTwoName + "\".");
            System.out.println("Test Passed for " + elementOneName + " matching " + elementTwoName + ".");
        } catch (StaleElementReferenceException | NoSuchElementException | TimeoutException ex) {
            node.fail("Failed to verify that the text in " + elementOneName + " matches the text in " + elementTwoName + ".");
            System.out.println("Test Failed for " + elementOneName + " matching " + elementTwoName + ".");
        } finally {
            driver.switchTo().window(oldTab);
        }
    }
    public void verifyTextMatch (WebElement elementOne, WebElement elementTwo, WebElement optionalStep, String elementTwoURL, String elementOneName, String elementTwoName) {
        node = test.createNode("Verify that the text from " + elementOneName + " matches the text from " +  elementTwoName + " in the URL: " + elementTwoURL);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String oldTab = driver.getWindowHandle();

        try {
            String elementOneText = elementOne.getText();
            RemoteWebDriver newTab = (RemoteWebDriver) driver.switchTo().newWindow(WindowType.TAB);
            newTab.get(elementTwoURL);
            optionalStep.click();
            String elementTwoText = elementTwo.getText();
            sa.assertEquals(elementOneText,elementTwoText);
            node.pass("The text in \"" + elementOneName + "\" is the same as the text in \"" + elementTwoName + "\".");
            System.out.println("Test Passed for " + elementOneName + " matching " + elementTwoName + ".");
        } catch (StaleElementReferenceException | NoSuchElementException | TimeoutException ex) {
            node.fail("Failed to verify that the text in " + elementOneName + " matches the text in " + elementTwoName + ".");
            System.out.println("Test Failed for " + elementOneName + " matching " + elementTwoName + ".");
        } finally {
            driver.switchTo().window(oldTab);
        }
        driver.switchTo().window(oldTab);
    }

    public String getRandomNumber() {
        String numeric = "1234567890";
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        while (sb.length() < 6) { // length of the random string.
            int index = (int) (rnd.nextFloat() * numeric.length());
            sb.append(numeric.charAt(index));
        }
        return sb.toString();
    }

    public void consoleLogTest2(String url) {
        ChromeDriver driver = new ChromeDriver();
        DevTools devTools = driver.getDevTools();
        devTools.createSession();
        devTools.send(Log.enable());

        devTools.addListener(Log.entryAdded(), logEntry -> {
            System.out.println("-------------------------------------------");
            System.out.println("Request ID = " + logEntry.getNetworkRequestId());
            System.out.println("URL = " + logEntry.getUrl());
            System.out.println("Source = " + logEntry.getSource());
            System.out.println("Level = " + logEntry.getLevel());
            System.out.println("Text = " + logEntry.getText());
            System.out.println("Timestamp = " + logEntry.getTimestamp());
            System.out.println("-------------------------------------------");
        });
        driver.get(url);
    }

    public void threadSleep(int seconds) {
        System.out.println("<-------------Waiting " + seconds + " seconds------------>");
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("<-------------Timer has ended------------>");
    }

    public void scrollToElement(WebElement element) {
        JavascriptExecutor js = driver;
        js.executeScript("arguments[0].scrollIntoView(true);",element);
    }

}
