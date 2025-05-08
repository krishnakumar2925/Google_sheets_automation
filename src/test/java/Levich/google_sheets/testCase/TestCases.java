package Levich.google_sheets.testCase;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.api.services.sheets.v4.Sheets;

import Levich.google_sheets.baseComponents.BaseTest;
import Levich.google_sheets.pageObject.GoogleSheetPage;
import Levich.google_sheets.utilities.googleSheetAPI;

public class TestCases extends BaseTest {
	private GoogleSheetPage sheetPage;

	@Test(description = "Verify user can create and rename a new Google Sheet",priority=1)
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
	
	@Test(description = "Verify data can be entered in a cell",priority=1)
	public void testCase_2() {
		try {
	        String spreadsheetId = "1jxZE2j9cJbCsHlzHI0Gm8TVp5fMR_4ytd-q8za2BrCs"; 
	        String range = "Sheet1!A1";
	        String testValue = "Test_Automation_Value";
	        
	        sheetPage = new GoogleSheetPage(driver);
	        sheetPage.clickBlankSheet();
	        googleSheetAPI.writeToCell(spreadsheetId, range, testValue);
	        Object actualValue = googleSheetAPI.readCell(spreadsheetId, range);

	        Assert.assertEquals(actualValue, testValue, "Mismatch between written and read value");

	    } catch (Exception e) {
	        e.printStackTrace();
	        Assert.fail("Test case 2 failed: " + e.getMessage());
	    }

}
	
	@Test(description = "Verify data can be edited in a cell using reusable method",priority=1)
	public void testCase_3() {
	    try {
	        Sheets sheetService = googleSheetAPI.getSheetService();
	        String spreadsheetId = "1jxZE2j9cJbCsHlzHI0Gm8TVp5fMR_4ytd-q8za2BrCs"; // replace with yours
	        String range = "Sheet1!B2";

	        // Initial write
	        googleSheetAPI.updateCellValue(sheetService, spreadsheetId, range, "Initial Value");

	        // Edit cell
	        googleSheetAPI.updateCellValue(sheetService, spreadsheetId, range, "Edited Value");

	        // Read and assert
	        String actualValue = googleSheetAPI.readCellValue(sheetService, spreadsheetId, range);
	        Assert.assertEquals(actualValue, "Edited Value", "Cell update failed!");

	    } catch (Exception e) {
	        e.printStackTrace();
	        Assert.fail("Exception during cell update test: " + e.getMessage());
	    }
	}
	
	@Test(description="Verify data can be deleted from a cell",priority=1)
	public void testCase_4() throws IOException, GeneralSecurityException {
        String spreadsheetId = "1jxZE2j9cJbCsHlzHI0Gm8TVp5fMR_4ytd-q8za2BrCs"; // replace with yours
	    String range = "Sheet1!A1";

	    // Step 1: Write sample data first
	    googleSheetAPI.writeToCell(spreadsheetId, range, "Temporary Text");

	    // Step 2: Delete/Clear data from the cell
	    googleSheetAPI.clearCellData(spreadsheetId, range);

	    // Step 3: Read back and verify the cell is empty
	    Object values = googleSheetAPI.readCell(spreadsheetId, range);
	    boolean isEmpty = values == null || ((String) values).isEmpty();
	    Assert.assertTrue(isEmpty, "Cell data was not deleted.");
	}
	
	@Test(description = "Verify bold formatting on a cell")
	public void testCase_5() {
	    try {
	       
	        // Define range and values to check for bold formatting
	        String spreadsheetId = "1jxZE2j9cJbCsHlzHI0Gm8TVp5fMR_4ytd-q8za2BrCs"; // replace with yours

	        String range = "Sheet1!A1"; // Update with the desired cell
	        String cellValue = "BoldText";
	        
	        // Write the cell value
	        googleSheetAPI.writeToCell(spreadsheetId, range, "Temporary Text");
	        
	        // Apply bold formatting
	        googleSheetAPI.applyBoldFormatting(spreadsheetId,range);
	        
	        // Retrieve cell value and its style to verify bold formatting
	        Object actualValue = googleSheetAPI.readCell(spreadsheetId,range);
	        boolean isBold = false;
			try {
				isBold = googleSheetAPI.isCellBold(spreadsheetId,range);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Assuming isCellBold checks for the bold style
	        
	        // Assert that the value is set correctly and bold formatting is applied
	        Assert.assertEquals(actualValue, cellValue, "Cell value mismatch.");
	        Assert.assertTrue(isBold, "Cell is not bold.");
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        Assert.fail("Test failed due to exception: " + e.getMessage());
	    }
	}
}
