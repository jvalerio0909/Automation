package Utilities;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
//conexion de archivos
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.*;

import org.openqa.selenium.JavascriptExecutor;
//web driver
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.Select;
import java.time.LocalDateTime;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/*
 * Created by: Juliana Valerio Villalobos. Date: 04/03/2020.
 */

public class Utilities {

	static SecureRandom secureRnd = new SecureRandom();

	public static WebDriver driver;
	static {
		driver = getDriver();
	}

	public static WebDriver getDriver() {

		try {
			Properties obj = new Properties();
			FileInputStream objfile = new FileInputStream(System.getProperty("user.dir") + "\\src\\config.properties");
			obj.load(objfile);

			switch (obj.getProperty("DriverType")) {
			case "CHROME":
				System.setProperty("webdriver.chrome.driver", obj.getProperty("chromedriverpath"));
				ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.addArguments("--start-maximized");
				driver = new ChromeDriver(chromeOptions);
				driver.manage().window().maximize();
				break;

			case "FIREFOX":
				System.setProperty("webdriver.gecko.driver", obj.getProperty("geckodriverpath"));
				FirefoxOptions dc = new FirefoxOptions();
				dc.setCapability("marionette", true);
				dc.addArguments("--lang=en-US");
				driver = new FirefoxDriver(dc);
				driver.manage().window().maximize();
				break;
			}
		} catch (Exception e) {

		}
		return driver;

	}

	public static void wait(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	public void waitForContent(WebElement webElement, String attribute) {
		int i = 0;
		String text;
		while (i < 90) {
			text = webElement.getAttribute(attribute);
			if (text.equals("")) {
				wait(1000);
				i++;
			} else {
				break;
			}
		}
	}
	
	public static void scrollDown() {
		JavascriptExecutor jsx = (JavascriptExecutor)driver;
		jsx.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}
	
	public static void SelectComboBox (WebElement comboB, String valor) {
		Select combo= new Select(comboB);
		combo.selectByValue(valor);
	}

}