package Levich.google_sheets.pageObject;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Levich.google_sheets.abstractComponents.SeleniumUtils;

public class GoogleSheetPage extends SeleniumUtils {
	WebDriver driver;
	WebDriverWait wait;

	public GoogleSheetPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		AjaxElementLocatorFactory factor = new AjaxElementLocatorFactory(driver, 10);
		PageFactory.initElements(factor, this);
	}

	@FindBy(xpath = "//div[@role='option' and .//div[text()='Blank spreadsheet']]")
	WebElement blankSheetButton;
	@FindBy(xpath = "//input[@aria-label='Rename']")
	WebElement titleInputField;
	@FindBy(xpath = "//input[@aria-label='Rename']/preceding-sibling::div/span")
	WebElement renamedTitleText;

	By Cell = By.xpath("//div[@class='waffle-background-container']");

	public void clickBlankSheet() {
		click(blankSheetButton);
	}

	public void renameSheet(String newName) {
		sendkeys(titleInputField, newName);
	}

	public String getRenamedSheetTitle() {
		return wait.until(ExpectedConditions.visibilityOf(renamedTitleText)).getText();
	}

	public void enterDataUsingKeyboard(String testData) {
		// TODO Auto-generated method stub
		Actions actions = new Actions(driver);
		WebElement cell = wait.until(ExpectedConditions.elementToBeClickable(Cell));
		cell.click(); // activate the cell
		actions.sendKeys(testData).sendKeys(Keys.ENTER).perform();

	}

	public String getDataFromActiveCell() {
		WebElement cell = wait.until(
				ExpectedConditions.visibilityOfElementLocated(Cell));
		return cell.getText();
	}

}
