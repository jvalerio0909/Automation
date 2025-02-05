package Testcases;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import Model.Employee;
import PageObjects.HomePOM;
import PageObjects.LoginPOM;
import PageObjects.NewEmployeePOM;
import PageObjects.EmployeesListPOM;
import Utilities.Utilities;
import cucumber.api.java.en.Given;

/*
 * Created by: Juliana Valerio Villalobos. Date: 04/03/2020.
 */

public class UsersTestE2E extends Utilities {

	// Objects Creation

	// Config
	private Properties prop = new Properties();
	private FileInputStream objfile;

	// DatadrivenCreation
	private static Properties propData = new Properties();
	private FileInputStream objfileData;

	// Reports Objects 
	private ExtentHtmlReporter htmlReporter;
	private ExtentReports extent;
	private ExtentTest test;

	// POM Objects Creation
	private static LoginPOM loginP = new LoginPOM();
	private static HomePOM homeP = new HomePOM();
	private static EmployeesListPOM employeesP = new EmployeesListPOM();
	private static NewEmployeePOM newemplP = new NewEmployeePOM();


	@BeforeClass
	public void setUp() throws IOException {
		objfile = new FileInputStream(System.getProperty("user.dir") + "\\src\\config.properties");
		prop.load(objfile);
		objfileData = new FileInputStream(System.getProperty("user.dir") + "\\src\\Datadriven\\Data.properties");
		propData.load(objfileData);
		driver.get(prop.getProperty("BaseURL"));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	@BeforeTest
	public void setUpReports(ITestContext ctx) {

		this.htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "\\ExtendReports\\UsersTestReport.html");
		this.extent = new ExtentReports();
		this.extent.attachReporter(htmlReporter);

		this.htmlReporter.config().setChartVisibilityOnOpen(true);
		this.htmlReporter.config().setDocumentTitle("Execution Report");
		this.htmlReporter.config().setReportName("Vacation Management App");
		this.htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		this.htmlReporter.config().setTheme(Theme.DARK);
	}

	@AfterMethod
	public void getResult(ITestResult result) throws IOException {
		this.test = this.extent.createTest(result.getMethod().getDescription());
		this.test.log(Status.INFO, "Nombre de Metodo :: " + result.getName());
		if (result.getStatus() == ITestResult.FAILURE) {
			this.test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " Failed", ExtentColor.RED));
			this.test.fail(result.getThrowable());
		} else {
			if (result.getStatus() == ITestResult.SUCCESS) {
				this.test.log(Status.PASS,
						MarkupHelper.createLabel(result.getName() + " Successful", ExtentColor.GREEN));
			} else {
				this.test.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " Skiped", ExtentColor.ORANGE));
				this.test.skip(result.getThrowable());
			}
		}
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

	@AfterTest
	public void closeReport() {
		this.extent.flush();
	}

	@Test(priority = 1, description = "Login Test")
	public static void LoginTest() throws AWTException {
		System.out.println("---LoginTest Execution Started---");

		// var assignment
		String username = propData.getProperty("Username");
		String password = propData.getProperty("Password");
		String accountName = propData.getProperty("AccountName");

		// actions
		loginP.usernameTxt().sendKeys(username);
		loginP.passwordTxt().sendKeys(password);
		loginP.signInBtn().click();
		wait(2000);

		// Validating login successful
		Assert.assertTrue(homeP.logoImg().isDisplayed(), "The image didn't display");
		Assert.assertTrue(homeP.userInfoLb().getText().contains("Welcome " + accountName),
				"The account name is incorrect");
		Assert.assertEquals(homeP.sucessfulBanner().getText(), "Signed in successfully.", "The banner didn't display");

		System.out.println("---LoginTest Execution Finished---");
	}

	@Test(priority = 2, description = "Create User Test")
	public static void CreateUserTest() throws AWTException {
		System.out.println("---CreateUserTest Execution Started---");

		// var assignment
		String firstName = propData.getProperty("Firstname");
		String lastName = propData.getProperty("Lastname");
		String email = propData.getProperty("Email");
		String identificacion = propData.getProperty("Identification");
		String leaderName = propData.getProperty("LeaderName");
		String year = propData.getProperty("Year");
		String month = propData.getProperty("Month");
		String day = propData.getProperty("Day");

		// actions
		employeesP.employeesTab().click();
		wait(2000);
		scrollDown();
		employeesP.createEmployeesLink().click();
		wait(2000);

		// new user
		newemplP.firstNameTxt().sendKeys(firstName);
		newemplP.lastNameTxt().sendKeys(lastName);
		newemplP.emailTxt().sendKeys(email);
		newemplP.idTxt().sendKeys(identificacion);
		newemplP.leaderNameTxt().sendKeys(leaderName);
		SelectComboBox(newemplP.anio(), year);
		SelectComboBox(newemplP.month(), month);
		SelectComboBox(newemplP.day(), day);
		newemplP.submitBtn().click();
		wait(2000);
		Assert.assertEquals(newemplP.confirmMsj().getText(), "Employee was successfully created.",
				"Message is different");
		System.out.println("---CreateUserTestExecution Finished---");
	}

	@Test(priority = 3, description = "Validate user created")
	public static void ValidateUserTest() throws AWTException {
		System.out.println("---ValidateUserTest Execution Started---");

		// var assignment
		String firstName = propData.getProperty("Firstname");
		String lastName = propData.getProperty("Lastname");
		String identificacion = propData.getProperty("Identification");
		String leaderName = propData.getProperty("LeaderName");
		String year = propData.getProperty("Year");
		String month = propData.getProperty("Month");
		String day = propData.getProperty("Day");

		// actions
		employeesP.employeesTab().click();

		// Row Returning Validations
		int row = employeesP.searchPosition(leaderName); // primero busca la fila en la que el empleado se encuentra
		if (row > 0) { // Valida que no sea -1, si es -1 el empleado no existe
			Employee emp = employeesP.returnEmployee(row); 

			// validating row
			Assert.assertTrue(emp.getFirstName().contains(firstName), "The first name didn't add correctly");
			Assert.assertTrue(emp.getLastName().contains(lastName), "The last name didn't add correctly");
			Assert.assertTrue(emp.getIdentification().contains(identificacion),
					"The identification didn't add correctly");
			Assert.assertTrue(emp.getLeaderName().contains(leaderName), "The leader name didn't add correctly");
			Assert.assertTrue(emp.getDate().contains(year), "The year didn't add correctly");
			Assert.assertTrue(emp.getDate().contains(month), "The month didn't add correctly");
			Assert.assertTrue(emp.getDate().contains(day), "The day didn't add correctly");

			System.out.println(emp.toString()); // Imprime datos del empleado que se busco
		} else {
			System.out.print("User didn't find it");
		}

		System.out.println("---ValidateUserTest Execution Finished---");
	}

	@Test(priority = 4, description = "Delete User Test")
	public static void DeleteUserTest() throws AWTException {
		System.out.println("---DeleteUserTest Execution Started---");

		// var assignment
		String leaderName = propData.getProperty("LeaderName");

		// Deleting the row
		int row = employeesP.searchPosition(leaderName); // primero busca la fila en la que el empleado se encuentra
		if (row > 0) {
			employeesP.deleteEmployee(row).click();
			wait(2000);
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ENTER);
			wait(1000);
			robot.keyRelease(KeyEvent.VK_ENTER);
			System.out.println("---User Eliminated---");
		} else {
			System.out.println("Employee does not exist");
		}
		
		//Validating user deleted
		int userSearch = employeesP.searchPosition(leaderName);
		Assert.assertEquals(userSearch, -1, "User is still in the table");
		
		System.out.println("---DeleteUserTest Execution Finished---");
	}

}
