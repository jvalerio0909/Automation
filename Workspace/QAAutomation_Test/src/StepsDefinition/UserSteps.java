package StepsDefinition;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.AfterClass;

import Model.Employee;
import PageObjects.EmployeesListPOM;
import PageObjects.HomePOM;
import PageObjects.LoginPOM;
import PageObjects.NewEmployeePOM;
import Utilities.Utilities;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class UserSteps extends Utilities {

	// Config
	private Properties prop = new Properties();
	private FileInputStream objfile;

	// Datadriven
	private static Properties propData = new Properties();
	private FileInputStream objfileData;

	// POM Objects Creation
	private static LoginPOM loginP = new LoginPOM();
	private static HomePOM homeP = new HomePOM();
	private static EmployeesListPOM employeesP = new EmployeesListPOM();
	private static NewEmployeePOM newemplP = new NewEmployeePOM();

	
	@Given("^Open the browser and start application$")
	public void setUp() throws IOException {
		objfile = new FileInputStream(System.getProperty("user.dir") + "\\src\\config.properties");
		prop.load(objfile);
		objfileData = new FileInputStream(System.getProperty("user.dir") + "\\src\\Datadriven\\Data.properties");
		propData.load(objfileData);
		driver.get(prop.getProperty("BaseURL"));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("--- Open the browser and start application: OK ---");

	}

	@When("^I enter a username and a password$")
	public static void EnterLoginAndPassword() throws AWTException {

		// var assignment
		String username = propData.getProperty("Username");
		String password = propData.getProperty("Password");

		// actions
		loginP.usernameTxt().sendKeys(username);
		loginP.passwordTxt().sendKeys(password);
		loginP.signInBtn().click();
		wait(2000);

		System.out.println("--- I enter a username and a password: OK ---");
	}

	@Then("^the user should do login successfully$")
	public static void ValidateLoginSuccess() throws AWTException {
		String accountName = propData.getProperty("AccountName");

		// Validating login successful
		Assert.assertTrue(homeP.logoImg().isDisplayed(), "The image didn't display");
		Assert.assertTrue(homeP.userInfoLb().getText().contains("Welcome " + accountName),
				"The account name is incorrect");
		Assert.assertEquals(homeP.sucessfulBanner().getText(), "Signed in successfully.", "The banner didn't display");

		System.out.println("--- the user should do login successfully: OK ---");
	}

	@Given("^I do click in create a new user$")
	public static void CreateNewUser() throws AWTException {
		// actions
		employeesP.employeesTab().click();
		wait(2000);
		scrollDown();
		employeesP.createEmployeesLink().click();
		wait(2000);

		System.out.println("--- I do click in create a new user: OK ---");
	}

	@When("^I enter information in the new user textfields$")
	public static void EnterUserInformation() throws AWTException {

		// var assignment
		String firstName = propData.getProperty("Firstname");
		String lastName = propData.getProperty("Lastname");
		String email = propData.getProperty("Email");
		String identificacion = propData.getProperty("Identification");
		String leaderName = propData.getProperty("LeaderName");
		String year = propData.getProperty("Year");
		String month = propData.getProperty("Month");
		String day = propData.getProperty("Day");

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
		System.out.println("--- I enter information in the new user textfields: OK ---");
	}

	@Then("^Verify confirmation message$")
	public static void VerifyUserinTable() throws AWTException {
		Assert.assertEquals(newemplP.confirmMsj().getText(), "Employee was successfully created.",
				"Message is different");
		System.out.println("--- Verify confirmation message: OK ---");
	}

	@Given("^I do click in the employess tab$")
	public static void NavEmployeeTab() throws AWTException {
		employeesP.employeesTab().click();
		System.out.println("--- I do click in the employess tab: OK ---");
	}

	@When("^I Search a user in the table and I found it")
	public static void EmployeeSearch() throws AWTException {
		Boolean flag = false;
		String leaderName = propData.getProperty("LeaderName");
		int row = employeesP.searchPosition(leaderName); 
		if (row > 0) { 
			Employee emp = employeesP.returnEmployee(row);
			flag = true;
		}
		Assert.assertTrue(flag, "The new user added isn't in the table");
		System.out.println("--- I Search a user in the table and I found it: OK ---");
	}

	@Then("^the application return and validate the user information$")
	public static void ReturnEmployeeInfo() throws AWTException {

		// var assignment
		String firstName = propData.getProperty("Firstname");
		String lastName = propData.getProperty("Lastname");
		String identificacion = propData.getProperty("Identification");
		String leaderName = propData.getProperty("LeaderName");
		String year = propData.getProperty("Year");
		String month = propData.getProperty("Month");
		String day = propData.getProperty("Day");

		// Row Returning Validations
		int row = employeesP.searchPosition(leaderName); 
		if (row > 0) { 
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

			System.out.println(emp.toString()); 
		} else {
			System.out.print("User didn't find it");
		}

		System.out.println("--- the application return and validate the user informatione: OK ---");
	}

	@Then("^I delete it$")
	public static void DeleteUser() throws AWTException {
		String leaderName = propData.getProperty("LeaderName");

		// Deleting the row
		int row = employeesP.searchPosition(leaderName);
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

		System.out.println("--- I delete it: OK ---");
	}
	
	
	@When("^I Search a user in the table and I didn't find it")
	public static void EmployeeSearchFailed() throws AWTException {
		Boolean flag = false;
		String leaderName = propData.getProperty("LeaderName");
		int row = employeesP.searchPosition(leaderName); 
		if (row > 0) { 
			Employee emp = employeesP.returnEmployee(row);
			flag = true;
		}
		Assert.assertFalse(flag, "The user is still in the table");
		System.out.println("--- I Search a user in the table and I didn't find it: OK ---");
	}
	
	@Then("^I close the browser$")
	public void tearDown() {
		driver.quit();
	}
	 
	
}
