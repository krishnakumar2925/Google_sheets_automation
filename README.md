Google Sheets Automation Testing

This project automates UI interactions with Google Sheets using Selenium WebDriver and integrates Google Sheets API for reading and writing data. The goal is to ensure smooth and reliable UI tests while eliminating the need for continuous login during automation by utilizing Chrome user profiles.

Features

Automates Google Sheets UI operations like creating, renaming sheets, and entering data.
Integrates Google Sheets API to directly interact with Google Sheets without the need for manual UI operations.
Reuses logged-in Google Chrome session to avoid the need to log in every time during automation.
Generates HTML reports using ExtentReports for detailed test results.
Prerequisites

To run this project, you need the following:

Java: Version 8 or higher
Maven: For dependency management
Selenium: Web automation framework
Google Sheets API credentials: For reading and writing data using the API
ChromeDriver: Ensure that you have the appropriate version of chromedriver installed
Google Chrome: For running tests in headless or full browser mode
Project Structure

The project is organized into the following main packages:

Levich.google_sheets.baseComponents:

	BaseTest.java: Contains the setup and teardown logic for tests.

	DriverSingleton.java: Manages WebDriver initialization and browser setup.

Levich.google_sheets.pageObject:

	GoogleSheetPage.java: Page Object class for interacting with the Google Sheets UI.

Levich.google_sheets.testCase:

	Contains test cases for verifying Google Sheets UI functionality.

Levich.google_sheets.utilities:

	googleSheetAPI.java: Integrates Google Sheets API for reading and writing data.

	ExtentReportManager.java: Handles the generation of ExtentReports.

	GlobalData.properties: Stores configuration like the browser type (Chrome or Safari).

Levich.google_sheets.abstractComponents:

	SeleniumUtils: Handles selenium actions methods with webdriverwait.

Setup Instructions

1. Clone the repository:
	git clone [https://github.com/yourusername/google-sheets-automation.git](https://github.com/krishnakumar2925/Google_sheets_automation.git)
	cd google-sheets-automation

2. Install dependencies:
Make sure that you have Maven installed, then run the following command to install the required dependencies:
	mvn clean install

3. Set up Google Sheets API credentials:
Go to Google Cloud Console.
Create a new project and enable the Google Sheets API.
Create OAuth 2.0 credentials and download the client_secret.json file.
Place the client_secret.json file in the src/main/java/Levich/google_sheets/utilities/ directory.

4. Set up Chrome Profile (optional, for avoiding login every time):
To reuse the logged-in session for Google Sheets:
Create a new directory named ChromeProfile in your project’s root directory.
Use Chrome to log in to your Google account and then navigate to Google Sheets.
Close Chrome and use the user profile folder for automated tests.
Note: The Chrome profile path is configured in the DriverSingleton.java file.

5. Configure the GlobalData.properties file:
Set the browser you want to use (e.g., Chrome):
browser=chrome

6. Run the Tests:
You can run the tests using TestNG or Maven:
Using Maven:
mvn test
Using TestNG:
Right-click on TestNG.xml and choose Run As > TestNG Test in your IDE.

Test Cases:

TC1:Verify spreadsheet can be created

TC2:Verify spreadsheet can be renamed

TC3:Verify data can be entered in a cell

TC4:Verify data can be edited in a cell

TC5:Verify data can be deleted from a cell

TC6:Verify bold formatting on a cell

TC7:Verify cell background color can be changed

TC8:Verify SUM formula

remaining test cases are available here: https://docs.google.com/spreadsheets/d/1qaAdFTbA6BoZxobzkMCagL563IAipdOlwpWPnLlyhFM/edit?usp=sharing

Reports

After running tests, the ExtentReports will generate a report in the /ExtentReports folder, where you can see the detailed test execution status, including passed/failed/skipped tests.

Contribution

Feel free to fork the repository and submit pull requests for any improvements or new features.

Limitations
1. Limitations with Automation Tools when Accessing Google Sheets
While automating Google Sheets with Selenium and the Google Sheets API, there are certain limitations you may encounter:

Rate Limits and API Quotas: Google Sheets API has rate limits and quotas, which might affect large-scale automation. For example, writing or reading a large amount of data repeatedly in a short amount of time could trigger rate limits, causing delays or test failures. To mitigate this, it is important to handle retries, introduce delays between requests, and monitor API usage closely to stay within the allocated quota.
Complex User Interactions: Automating certain user interactions in Google Sheets, like drag-and-drop actions or complex formatting, may not always be reliable with Selenium. Although Selenium is capable of performing most interactions, certain intricate operations like formula manipulations and data linking between sheets may not always function as expected, leading to occasional test failures. In such cases, leveraging the Google Sheets API for direct data manipulation is a more reliable solution.
Session Management: While the Chrome profile approach helps reuse login sessions, any changes in the Chrome browser (such as updates or profile corruption) can cause the login session to fail, which would require a fresh login each time. To avoid this, the Google Sheets API should be integrated where possible, as it doesn't require a logged-in browser session.

2. Test Cases Automated and Pending Due to Time Constraints
Due to time constraints, only 8 out of 10 test cases have been automated. The remaining two test cases involve working with large datasets, which require special handling. The following points outline the reasoning behind this:

Large Data Handling: Two of the test cases involve dealing with large datasets, including verifying data integrity and the performance of Google Sheets with bulk data operations. These types of tests are more time-consuming to automate due to the following reasons:
Test Performance: Running large datasets requires significant computational resources, and performance testing with such data might slow down the execution of tests. Furthermore, depending on the system used to run the tests, resource limitations could lead to test timeouts or failure.
Data Integrity: Ensuring the correctness of data within large datasets involves multiple checks and cross-validations between the UI and the API. Automating these checks is complex because it requires both interaction with the sheet's UI and accessing backend data via the Google Sheets API to compare results.
In a production environment, these test cases would need to be prioritized separately. Automation would likely require splitting the tests into smaller chunks, optimizing data processing, and employing parallel test execution for efficiency.
