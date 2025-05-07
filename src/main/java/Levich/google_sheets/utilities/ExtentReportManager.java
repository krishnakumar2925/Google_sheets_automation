package Levich.google_sheets.utilities;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {
	
	private static ExtentReports report;
	private static ThreadLocal<ExtentTest> extentTest=new ThreadLocal<>();
	
	private  static synchronized ExtentReports getReport() {
		if(report ==null) {
			String path =  System.getProperty("user.dir")+"/Reports";
			File tkdir = new File(path);
			if(!tkdir.exists()) {
				tkdir.mkdir();
			}
			 String reportFilePath = path + "/report.html";
			 ExtentSparkReporter reporter= new ExtentSparkReporter(reportFilePath);
			 reporter.config().setReportName("QTrip Automation");
			reporter.config().setDocumentTitle("Test result");
			 report = new ExtentReports();
			 report.attachReporter(reporter);
			 report.setSystemInfo("Tester", "Krishnakumar");	 
		 
		}
		return report;
		
	}
	
	public static void initReport() {
		report = getReport();
	}
	
	
	public static synchronized void startTest(String FileName) {
		ExtentTest test = report.createTest(FileName);
		extentTest.set(test);
		
	}
	
	public static synchronized ExtentTest getExtentTest() {
	    return extentTest.get();
	}
	
	public static synchronized void flushReport() {
		report.flush();
    }
	
	 public static void logPass(String message) {
	        getExtentTest().log(Status.PASS, message);
	    }

	    public static void logFail(String message) {
	        getExtentTest().log(Status.FAIL, message);
	    }

	    public static void logSkip(String message) {
	        getExtentTest().log(Status.SKIP, message);
	    }

	    public static void logInfo(String message) {
	        getExtentTest().log(Status.INFO, message);
	    }
	
	
	 


}
