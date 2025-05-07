package Levich.google_sheets.testCase;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import Levich.google_sheets.baseComponents.BaseTest;
import Levich.google_sheets.pageObject.GoogleSheetPage;

public class TestCases extends BaseTest {
	private GoogleSheetPage sheetPage;

	@Test(description = "Verify user can create and rename a new Google Sheet")
	public void testCase_1() {
		try {
	        sheetPage = new GoogleSheetPage(driver);
	        sheetPage.clickBlankSheet();
	        String newName = "TestSheet_Auto";
	        sheetPage.renameSheet(newName);
	        String actualName = sheetPage.getRenamedSheetTitle();

	        Assert.assertEquals(actualName, newName, "Failed to rename");

	    } catch (Exception e) {
	        e.printStackTrace();
	        Assert.fail("Test failed due to exception: " + e.getMessage());
	    }
	}
	
	@Test(description = "Verify data can be entered in a cell using keyboard actions")
	public void testCase_2() {
	    try {
	        sheetPage = new GoogleSheetPage(driver);

	        sheetPage.clickBlankSheet();
	        String testData = "CellValue";
	        sheetPage.enterDataUsingKeyboard(testData);
	        String actualData = sheetPage.getDataFromActiveCell();

	        Assert.assertEquals(actualData, testData, "Mismatch in entered and displayed data");

	    } catch (Exception e) {
	        e.printStackTrace();
	        Assert.fail("Test case 2 failed due to: " + e.getMessage());
	    }
	}

}
