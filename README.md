# Google Sheets Test Automation Suite

This project automates functional test cases for **Google Sheets** using Selenium WebDriver, TestNG, and the Google Sheets API. It is built to demonstrate automation capability around spreadsheet manipulation such as reading, writing, editing, and validating data.

---

## 🔧 Technologies Used
- Java
- TestNG
- Selenium WebDriver
- Google Sheets API
- ChromeDriver
- Extent Reports

---

## ✅ Automated Test Cases (8/10 Implemented)

### Test Case 1: Verify Google Sheet loads correctly
- Checks if the sheet URL is accessible and page title matches expectations.

### Test Case 2: Verify data can be entered in a cell
- Uses Google Sheets API to input text in a specific cell.

### Test Case 3: Verify data can be edited in a cell
- Overwrites existing data in a cell and verifies the updated value.

### Test Case 4: Verify data can be deleted from a cell
- Clears the content of a specific cell using the Google Sheets API.

### Test Case 5: Verify ability to add a new row
- Simulates appending a new row of data and confirms via the API.

### Test Case 6: Verify ability to add a new column
- Adds a column programmatically and validates by checking headers.

### Test Case 7: Verify data is retained after browser refresh
- Enters data in the UI, refreshes the page, and checks persistence.

### Test Case 8: Verify unauthorized access is blocked
- Opens the sheet URL in incognito or another profile and checks for auth error.

---

## ❌ Not Yet Automated (Due to Time Constraints)

### Test Case 9: Verify import of large dataset
- Requires simulation of uploading or importing 1000+ records and validating correctness and performance.

### Test Case 10: Verify performance with large number of rows/columns
- Needed benchmarking read/write latency and UI behavior under high load. Skipped due to time limits.

### Remaining Testcases
- Refer: https://docs.google.com/spreadsheets/d/1qaAdFTbA6BoZxobzkMCagL563IAipdOlwpWPnLlyhFM/edit?usp=sharing
---

## 🧪 Setup Instructions

1. **Clone this repo**
   ```bash
   git clone [https://github.com/your-username/google-sheets-automation](https://github.com/krishnakumar2925/Google_sheets_automation).git
   cd google-sheets-automation
   ```

2. **Install dependencies** (if using Maven)
   ```bash
   mvn clean install
   ```

3. **Google Sheets API Setup**
   - Go to [Google Developer Console](https://console.developers.google.com/)
   - Create a project and enable **Google Sheets API**
   - Download `client_secret.json` and place it in `src/main/java/.../utilities/`

4. **Run the test**
   ```bash
   mvn test
   ```

---

## 🧩 Limitations & Production Considerations

- **Automation Tool Limitations**:
  - Selenium cannot directly interact with Google Sheets UI elements reliably due to dynamic DOM and iframe restrictions.
  - Addressed by integrating Google Sheets API for reliable and scalable testing.

- **Non-Automated Scenarios**:
  - Large dataset handling is complex due to sheet UI lag and API rate limits. Such cases are better validated through batch API operations and performance assertions.

---

## 📁 Project Structure
```
|- src
|   |- main
|   |   |- java
|   |       |- GoogleSheetAPI.java
|   |       |- GoogleSheetUtils.java
|   |- test
|       |- java
|           |- SheetTests.java
|- pom.xml
|- README.md
```

---

## 📋 Author
**Krishnakumar Muralidharan**

---

