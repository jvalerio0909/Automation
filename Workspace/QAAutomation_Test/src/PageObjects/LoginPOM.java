package PageObjects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import Utilities.Utilities;

/*
 * Created by: Juliana Valerio Villalobos. Date: 05/03/2020.
 */
public class LoginPOM extends Utilities {

	public WebElement usernameTxt() {
		return driver.findElement(By.id("user_email"));
	}
	
	public WebElement passwordTxt() {
		return driver.findElement(By.id("user_password"));
	}
	
	public WebElement signInBtn() {
		return driver.findElement(By.name("commit"));
	}

}
