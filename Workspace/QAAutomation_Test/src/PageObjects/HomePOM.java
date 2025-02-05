package PageObjects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import Utilities.Utilities;
/*
 * Created by: Juliana Valerio Villalobos. Date: 05/03/2020.
 */

public class HomePOM extends Utilities {
	
	public WebElement logoImg() {
		return driver.findElement(By.cssSelector("#logo"));
	}
	
	public WebElement userInfoLb() {
		return driver.findElement(By.cssSelector("#user_information > span"));
	}
	
	public WebElement sucessfulBanner() {
		return driver.findElement(By.cssSelector("#content > p.flash_notice"));
	}
	

}
