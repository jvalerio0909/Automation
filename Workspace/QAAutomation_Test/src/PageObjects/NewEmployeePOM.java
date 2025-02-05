package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import Utilities.Utilities;

public class NewEmployeePOM extends Utilities {
	
	public WebElement firstNameTxt() {
		return driver.findElement(By.id("employee_first_name"));
	}
	
	public WebElement lastNameTxt() {
		return driver.findElement(By.id("employee_last_name"));
	}
	
	public WebElement emailTxt() {
		return driver.findElement(By.id("employee_email"));
	}
	
	public WebElement idTxt() {
		return driver.findElement(By.id("employee_identification"));
	}
	
	public WebElement leaderNameTxt() {
		return driver.findElement(By.id("employee_leader_name"));
	}
	
	public WebElement anio() {
		return driver.findElement(By.id("employee_start_working_on_1i"));
	}
	
	public WebElement month() {
		return driver.findElement(By.id("employee_start_working_on_2i"));
	}
	
	public WebElement day() {
		return driver.findElement(By.id("employee_start_working_on_3i"));
	}
	
	public WebElement submitBtn() {
		return driver.findElement(By.cssSelector("#new_employee > div.actions > input[type=submit]"));
	}
	
	public WebElement confirmMsj() {
		return driver.findElement(By.id("notice"));
	}

}
