package com.comcast.crm.baseutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.comcast.crm.generic.databaseutility.DatabaseUtility;
import com.comcast.crm.generic.fileutility.ExcelFileUtility;
import com.comcast.crm.generic.fileutility.PropertiesFileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.UtilityClassObject;
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.LoginPage;

public class BaseClass {
	
	public DatabaseUtility dbUtil = new DatabaseUtility();
	public PropertiesFileUtility pUtil = new PropertiesFileUtility();
	public ExcelFileUtility eUtil = new ExcelFileUtility();
	public WebDriverUtility wUtil = new WebDriverUtility();
	public JavaUtility jUtil = new JavaUtility();
	public WebDriver driver;
	public static WebDriver sDriver;

	@BeforeSuite(alwaysRun = true)
	public void beforeSuiteConfig() {
		System.out.println("=== DB Connection Set, Report Config ===");
		dbUtil.getDbConnection();
	}

	@Parameters("browser")
	@BeforeClass(alwaysRun = true)
	public void beforeClassConfig(@Optional("chrome") String browser) throws Exception {
		System.out.println("=== Launch Browser ===");
		String BROWSER = System.getProperty("browser", pUtil.getDataFromPropertyFile("browser"));
				// browser;
				// pUtil.getDataFromPropertyFile("browser");
		switch (BROWSER) {
		case "chrome":
			driver = new ChromeDriver();
			break;

		case "firefox":
			driver = new FirefoxDriver();
			break;

		case "edge":
			driver = new EdgeDriver();
			break;

		default:
			driver = new ChromeDriver();
			break;
		}
//		sDriver = driver;
		UtilityClassObject.setDriver(driver);

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethodConfig() throws Exception {
		System.out.println("=== Login to Applocation ===");
		String URL = System.getProperty("url", pUtil.getDataFromPropertyFile("url"));
		String USERNAME = System.getProperty("username", pUtil.getDataFromPropertyFile("username"));
		String PASSWORD = System.getProperty("password", pUtil.getDataFromPropertyFile("password"));
		LoginPage lp = new LoginPage(driver);
		lp.loginToApp(URL, USERNAME, PASSWORD);
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethodCongig() {
		System.out.println("=== Logout of Application ===");
		HomePage hp = new HomePage(driver);
		hp.signout(driver);
	}

	@AfterClass(alwaysRun = true)
	public void afterClassConfig() {
		System.out.println("=== Close the Browser ===");
		driver.quit();
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuiteConfig() {
		System.out.println("=== DB Connection Close, Report Backup ===");
	}
}
