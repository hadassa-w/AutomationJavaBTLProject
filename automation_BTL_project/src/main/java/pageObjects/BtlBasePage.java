package pageObjects;

import enums.MainMenu;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BtlBasePage extends BasePage {
    @FindBy(id = "TopQuestions")
    private WebElement searchField;

    @FindBy(className = "SearchBtn")
    private WebElement searchIcon;

    @FindBy(className = "snifim")
    private WebElement branchesButton;

    public BtlBasePage(WebDriver driver) {
        super(driver);
    }

    public void clickOnMainMenu(MainMenu menuItem) {
        WebElement menuElement = driver.findElement(
                org.openqa.selenium.By.linkText(menuItem.getMenuText())
        );
        menuElement.click();
    }

    public void clickSubMenu(String subMenuText) {
        WebElement subMenuElement = driver.findElement(
                org.openqa.selenium.By.xpath("//a[contains(text(),'" + subMenuText + "')]")
        );
        subMenuElement.click();
    }

    public void search(String searchText) {
        searchField.clear();
        searchField.sendKeys(searchText);
        searchIcon.click();
    }

    public BranchesAndServicesPage clickBranchesButton() {
        branchesButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                org.openqa.selenium.By.cssSelector(".snifSearchTitle.snifSearchByList")
        ));

        return new BranchesAndServicesPage(driver);
    }
}