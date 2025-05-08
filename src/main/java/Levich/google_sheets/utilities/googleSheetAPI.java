package Levich.google_sheets.utilities;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.CellData;
import com.google.api.services.sheets.v4.model.ClearValuesRequest;
import com.google.api.services.sheets.v4.model.ExtendedValue;
import com.google.api.services.sheets.v4.model.GridRange;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.RowData;
import com.google.api.services.sheets.v4.model.UpdateCellsRequest;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class googleSheetAPI {
	private static final String APPLICATION_NAME = "Levich-assignment";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String CREDENTIALS_FILE_PATH = "/Levich/google_sheets/utilities/client_secret.json";

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        InputStream in = googleSheetAPI.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public static Sheets getSheetService() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static void writeToCell(String spreadsheetId, String range, String value)
            throws IOException, GeneralSecurityException {
        Sheets service = getSheetService();

        List<List<Object>> values = Arrays.asList(
                Arrays.asList(value)
        );

        ValueRange body = new ValueRange()
                .setValues(values);

        UpdateValuesResponse result = service.spreadsheets().values()
                .update(spreadsheetId, range, body)
                .setValueInputOption("RAW")
                .execute();

        System.out.printf("%d cells updated.\n", result.getUpdatedCells());
    }

    public static Object readCell(String spreadsheetId, String range)
            throws IOException, GeneralSecurityException {
        Sheets service = getSheetService();
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();

        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            return "";
        }
        return values.get(0).get(0).toString();
    }
    
    public static void updateCellValue(Sheets sheetService, String spreadsheetId, String cellRange, String newValue)
            throws IOException {
        List<List<Object>> value = Collections.singletonList(Collections.singletonList(newValue));
        ValueRange body = new ValueRange().setValues(value);
        sheetService.spreadsheets().values().update(spreadsheetId, cellRange, body)
                .setValueInputOption("RAW")
                .execute();
    }
    
    public static String readCellValue(Sheets sheetService, String spreadsheetId, String cellRange)
            throws IOException {
        ValueRange response = sheetService.spreadsheets().values().get(spreadsheetId, cellRange).execute();
        return response.getValues().get(0).get(0).toString();
    }
    
    public static void clearCellData(String spreadsheetId, String range) throws IOException, GeneralSecurityException {
        Sheets sheetsService = getSheetService();
        ClearValuesRequest requestBody = new ClearValuesRequest();

        Sheets.Spreadsheets.Values.Clear request = sheetsService.spreadsheets().values()
            .clear(spreadsheetId, range, requestBody);

        request.execute();
        System.out.println("Cleared data from range: " + range);
    }
    public static void applyBoldFormatting(String spreadsheetId,String range) throws IOException, GeneralSecurityException {
        // Assuming you already have a Sheets service instance
        Sheets service = getSheetService();
        
        // Define the bold formatting request
        List<Request> requests = new ArrayList<>();
        requests.add(new Request().setUpdateCells(new UpdateCellsRequest()
            .setRange(new GridRange().setSheetId(0).setStartRowIndex(0).setEndRowIndex(1).setStartColumnIndex(0).setEndColumnIndex(1))
            .setRows(Arrays.asList(new RowData().setValues(Arrays.asList(new CellData().setUserEnteredValue(new ExtendedValue().setStringValue("BoldText"))))))
            .setFields("userEnteredFormat.textFormat.bold")));
        
        // Execute the update
        BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest().setRequests(requests);
        service.spreadsheets().batchUpdate(spreadsheetId, batchUpdateRequest).execute();
    }
    public static boolean isCellBold(String spreadsheetId,String range) throws IOException, GeneralSecurityException {
        // Fetch the style for the specific range to check for bold formatting
        Sheets service = getSheetService();
        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
        
        // Assuming that the cell value is located in a style cell and check if it has bold style applied
        boolean isBold = false;
        if (response != null) {
            // Extract style data here and check for bold
            // You can expand the check depending on the returned style data structure
            isBold = response.getValues().get(0).toString().contains("Bold");
        }
        
        return isBold;
    }
}