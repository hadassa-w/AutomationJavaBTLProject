import pageObjects.HomePage;
import base.DriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import tests.ExtentReportExtension;
import tests.WebDriverExtension;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

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
        String searchQuery = "חישוב סכום דמי לידה ליום";
        homePage.search(searchQuery);

        WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div#results h2")
        ));
        assertTrue(title.getText().contains("תוצאות חיפוש עבור " + searchQuery));

        homePage.clickBranchesButton();

        WebElement firstBranch = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".OP_List li div .snif-rashi a")
        ));
        firstBranch.click();

        WebElement tableCell = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("tbl-cell")));
        WebElement kabalatKahal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("KabalatKahal")));
        WebElement phone = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".tbl-cell div")));

        assertTrue(tableCell.isDisplayed());
        assertTrue(kabalatKahal.isDisplayed());
        assertTrue(phone.isDisplayed());
    }
}
