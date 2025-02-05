package PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import Model.Employee;
import Utilities.Utilities;

/*
 * Created by: Juliana Valerio Villalobos. Date: 04/03/2020.
 */

public class EmployeesListPOM extends Utilities {
	public WebElement employeesTab() {
		return driver.findElement(By.linkText("Employees Information"));
	}
	
	public WebElement createEmployeesLink() {
		return driver.findElement(By.linkText("Create a new employee"));
	}

	public int searchPosition(String leaderName){
		int row = -1;
		List<WebElement> filas_collection = driver.findElements(By.xpath(".//*[@id=\"content\"]/table/tbody/tr"));
		for (int i = 0; i < filas_collection.size(); i++){
			List<WebElement> column_collection = driver.findElements(By.xpath(".//*[@id=\"content\"]/table/tbody/tr["+i+"]/td"));
			for (int j = 0; j < column_collection.size(); j++){
				if(column_collection.get(j).getText().equals(leaderName)){
					row = i;
					j = column_collection.size();
					i = filas_collection.size();
				}
			}
		}
		return row;
	}
	

	public Employee returnEmployee(int pos){
		Employee employee = new Employee();
		List<WebElement> column_names = driver.findElements(By.xpath(".//*[@id=\"content\"]/table/tbody/tr/th"));
		List<WebElement> column_collection = driver.findElements(By.xpath(".//*[@id=\"content\"]/table/tbody/tr["+pos+"]/td"));
		for (int i = 0; i < column_names.size(); i++){
				switch (column_names.get(i).getText()){
					case "First name":
						employee.setFirstName(column_collection.get(i).getText());
					break;
					case "Last name":
						employee.setLastName(column_collection.get(i).getText());
					break;
					case "Identification":
						employee.setIdentification(column_collection.get(i).getText());
					break;
					case "Leader name":
						employee.setLeaderName(column_collection.get(i).getText());
					break;
					case "Started working on":
						employee.setDate(column_collection.get(i).getText());
					break;
				}
		}
		return employee;
	}


	public WebElement deleteEmployee(int pos){
		return driver.findElement(By.xpath(".//*[@id=\"content\"]/table/tbody/tr["+pos+"]/td[9]/a"));
	}
}