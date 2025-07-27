import pageObjects.*;
import enums.MainMenu;
import base.DriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import tests.ExtentReportExtension;
import tests.WebDriverExtension;

import java.time.Duration;
import java.util.Arrays;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.provider.Arguments;

@ExtendWith({ExtentReportExtension.class, WebDriverExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BtlTests {
    private WebDriver driver;
    private HomePage homePage;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        driver = DriverManager.getDriver();
        driver.get("https://www.btl.gov.il");
        homePage = new HomePage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testSearchAndBranchesFlow() {
        BtlBasePage btlBasePage = new BtlBasePage(driver);
        btlBasePage.clickOnBranchesButton();

        BranchesAndServicesPage branchesAndServicesPage = homePage.clickBranchesButton();

        btlBasePage.clickOnBranch();

        WebElement tableCell = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("tbl-cell")));
        WebElement kabalatKahal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("KabalatKahal")));
        WebElement phone = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".tbl-cell div")));

        assertTrue(tableCell.isDisplayed());
        assertTrue(kabalatKahal.isDisplayed());
        assertTrue(phone.isDisplayed());
    }

    @Test
    public void testYeshivaStudentInsuranceCalculation() {//
        BtlNotWorkingPage calcPage = new BtlNotWorkingPage(driver);

        calcPage.goToCalculator();
        calcPage.fillPersonalDetailsAndBirthDate("1", "11", "2006");
        calcPage.goToStep2();
        calcPage.selectDisabilityNoAndContinue();
        calcPage.goToStep3();

        assertTrue(calcPage.isFinalSummaryDisplayed());
        assertTrue(calcPage.containsExpectedSummaryText());
    }

    @Test
    public void testUnemploymentCalculationFlow() {
        BtlUnemploymentPage btlBasePage = new BtlUnemploymentPage(driver);

        btlBasePage.goToCalculator();
        btlBasePage.fillBasicInfo("1/05/2025", "מעל 28");
        btlBasePage.fillSalaries(Arrays.asList("7000", "7200", "7100", "7500", "7700", "7200"));
        assertTrue(btlBasePage.areResultsDisplayed(), "לא כל תוצאות החישוב הופיעו כמצופה");
    }


    static Stream<Arguments> pagesProvider() {
        return Stream.of(
                Arguments.of("אזרח ותיק (זקנה)", "אזרח ותיק (קצבת זקנה)"),
                Arguments.of("נכות כללית", "נכות כללית"),
                Arguments.of("שיקום מקצועי", "שיקום מקצועי"),
                Arguments.of("זכויות עובדים בפשיטות רגל", "זכויות עובדים בפשיטות רגל ופירוק תאגיד"),
                Arguments.of("קצבת ילדים", "ילדים")
        );
    }

    @ParameterizedTest
    @MethodSource("pagesProvider")
    public void testNavigateAndCheckTitle(String submenu, String expectedTitle) {
        BtlBasePage basePage = new BtlBasePage(driver);
        basePage.clickOnMainMenu(MainMenu.K_H);
        basePage.clickSubMenu(submenu);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleContains(expectedTitle));
    }
}
