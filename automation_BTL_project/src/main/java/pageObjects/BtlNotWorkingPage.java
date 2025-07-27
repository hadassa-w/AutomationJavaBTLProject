package pageObjects;

import enums.MainMenu;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BtlNotWorkingPage extends BasePage {

    @FindBy(id = "ctl00_ctl43_g_642b1586_5c41_436a_a04c_e3b5ba94ba69_ctl00_InsuranceNotSachirWizard_rdb_Gender_0")
    private WebElement genderMaleRadio;

    @FindBy(id = "ctl00_ctl43_g_642b1586_5c41_436a_a04c_e3b5ba94ba69_ctl00_InsuranceNotSachirWizard_rdb_employeType_2_lbl")
    private WebElement yeshivaStudentRadio;

    @FindBy(name = "ctl00$ctl43$g_642b1586_5c41_436a_a04c_e3b5ba94ba69$ctl00$InsuranceNotSachirWizard$DynDatePicker_BirthDate$Date")
    private WebElement birthDateField;

    @FindBy(className = "btnNext")
    private WebElement nextButton;

    @FindBy(id = "ctl00_ctl43_g_642b1586_5c41_436a_a04c_e3b5ba94ba69_ctl00_InsuranceNotSachirWizard_rdb_GetNechut_1")
    private WebElement noDisabilityPensionRadio;

    @FindBy(className = "ResultText")
    private WebElement resultsContainer;

    public BtlNotWorkingPage(WebDriver driver) {
        super(driver);
    }

    public void goToCalculator() {
        BtlBasePage basePage = new BtlBasePage(driver);
        basePage.clickOnMainMenu(MainMenu.D_B);
        basePage.clickSubMenu("דמי ביטוח לאומי");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleContains("דמי ביטוח לאומי"));

        WebElement calculatorLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(., 'מחשבון לחישוב דמי הביטוח')]")
        ));
        calculatorLink.click();

    }

    public void selectGenderMale() {
        waitUntilVisible(genderMaleRadio).click();
    }

    public void selectYeshivaStudentType() {
        waitUntilVisible(yeshivaStudentRadio).click();
    }

    public void fillBirthDate(String day, String month, String year) {
        birthDateField.clear();
        birthDateField.sendKeys(day + "/" + month + "/" + year);
    }

    public void fillPersonalDetailsAndBirthDate(String day, String month, String year) {
        selectGenderMale();
        selectYeshivaStudentType();
        fillBirthDate(day, month, year);
    }

    public void goToStep2() {
        nextButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "צעד שני"));
    }

    public void selectDisabilityNoAndContinue() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(noDisabilityPensionRadio));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true); window.scrollBy(0, -100);", element);

        element.click();
    }

    public void goToStep3() {
        nextButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "סיום"));
    }


    public boolean isFinalSummaryDisplayed() {
        return waitUntilVisible(resultsContainer).isDisplayed();
    }

    public boolean containsExpectedSummaryText() {
        String summary = resultsContainer.getText();
        return summary.contains("דמי ביטוח לאומי: 43 ש\"ח.") &&
                summary.contains("דמי ביטוח בריאות: 120.00 ש\"ח.") &&
                summary.contains("סך הכל דמי ביטוח לחודש: 163 ש\"ח.");
    }

    private WebElement waitUntilVisible(WebElement element) {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(element));
    }
}
