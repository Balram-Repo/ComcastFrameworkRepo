package com.comcast.crm.generic.listeners;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.UtilityClassObject;

public class ListenersImpl implements ITestListener, ISuiteListener {

	public ExtentReports report;
	public static ExtentTest test;

	@Override
	public void onStart(ISuite suite) {
		System.out.println("Suite Execution Started");
		// Extent Report Generation
		JavaUtility jUtil = new JavaUtility();
		ExtentSparkReporter spark = new ExtentSparkReporter(
				"./AdvancedReport/Report" + jUtil.getSystemDateAndTime() + ".html");

		spark.config().setDocumentTitle("CRM Test Suite Results");
		spark.config().setReportName("CRM Report");
		spark.config().setTheme(Theme.DARK);

		// Add Environment
		report = new ExtentReports();
		report.attachReporter(spark);
		report.setSystemInfo("Platform", "Windows-11");
		report.setSystemInfo("Browser", "Chrome-141");
	}

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("On Test Start---Executed");
		String methodName = result.getMethod().getMethodName();
		test = report.createTest(methodName);
		UtilityClassObject.setTest(test);
		test.log(Status.INFO, methodName + "----Started");

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		System.out.println("Test Script Success");
		test.log(Status.PASS, methodName + "----PASS");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		JavaUtility jUtil = new JavaUtility();
		TakesScreenshot ts = (TakesScreenshot) UtilityClassObject.getDriver();
		String filePath = ts.getScreenshotAs(OutputType.BASE64);
		test.addScreenCaptureFromBase64String(filePath, methodName + jUtil.getSystemDateAndTime());
		test.log(Status.FAIL, methodName + "----Failed");
		test.log(Status.INFO, result.getThrowable());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("Test Script Skipped");
	}

	@Override
	public void onFinish(ISuite suite) {
		System.out.println("Suite Execution Finished");
		report.flush();
	}
}
