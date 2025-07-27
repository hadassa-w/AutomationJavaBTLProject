package pageObjects;

import enums.MainMenu;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class BtlUnemploymentPage extends BasePage {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    public BtlUnemploymentPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(name = "ctl00$ctl43$g_2ccdbe03_122a_4c30_928f_60300c0df306$ctl00$AvtalaWizard$DynDatePicker_PiturimDate$Date")
    private WebElement birthDateField;

    @FindBy(className = "btnNext")
    private WebElement nextButton;

    @FindBy(xpath = "//a[contains(., 'למחשבוני דמי אבטלה')]")
    private WebElement calculatorsLink;

    @FindBy(xpath = "//a[contains(., 'חישוב דמי אבטלה')]")
    private WebElement calculatorLink;

    @FindBy(id = "ctl00_ctl43_g_2ccdbe03_122a_4c30_928f_60300c0df306_ctl00_AvtalaWizard_rdb_age_1")
    private WebElement ageRadio;

    public void goToCalculator() {
        BtlBasePage basePage = new BtlBasePage(driver);
        basePage.clickOnMainMenu(MainMenu.K_H);
        basePage.clickSubMenu("אבטלה");

        wait.until(ExpectedConditions.titleContains("אבטלה"));
        wait.until(ExpectedConditions.visibilityOf(calculatorsLink)).click();
        wait.until(ExpectedConditions.visibilityOf(calculatorLink)).click();
    }

    public void fillBasicInfo(String stopDate, String ageLabel) {
        birthDateField.clear();
        birthDateField.sendKeys(stopDate);

        ageRadio.click();

        nextButton.click();
    }

    public void fillSalaries(List<String> salaries) {
        List<WebElement> salaryInputs = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.cssSelector("input[id*='IncomeGrid'][id*='Txt_Sallary']")
        ));

        for (int i = 0; i < Math.min(salaries.size(), salaryInputs.size()); i++) {
            WebElement input = salaryInputs.get(i);
            input.clear();
            input.sendKeys(salaries.get(i));
        }

        nextButton.click();
    }


    public boolean areResultsDisplayed() {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "חישוב דמי אבטלה עבור הנתונים"));

        return wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(text(),'שכר יומי ממוצע לצורך חישוב דמי אבטלה')]"))) != null &&
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[contains(text(),'דמי אבטלה ליום')]"))) != null &&
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[contains(text(),'דמי אבטלה לחודש')]"))) != null;
    }
}
