package com.linkedin.spider.processor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class mainStoreCookie {

	public static void main(String[] args) {
		WebDriver driver;
		System.setProperty("webdriver.chrome.driver", "/Users/i071944/chromedriver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://people.wdf.sap.corp/search#/results?page=2&query=office:CN");

		// Input Email id and Password If you are already Register
//		driver.findElement(By.id("session_uid")).sendKeys("I071944");
//		driver.findElement(By.id("session_password")).sendKeys("Pa00word");
//		driver.findElement(By.name("commit")).click();

		// create file named Cookies to store Login Information
		File file = new File("Cookies.data");
		try {
			// Delete old file if exists
			file.delete();
			file.createNewFile();
			FileWriter fileWrite = new FileWriter(file);
			BufferedWriter Bwrite = new BufferedWriter(fileWrite);
			// loop for getting the cookie information

			// loop for getting the cookie information
			for (Cookie ck : driver.manage().getCookies()) {
				Bwrite.write((ck.getName() + ";" + ck.getValue() + ";" + ck.getDomain() + ";" + ck.getPath() + ";"
						+ ck.getExpiry() + ";" + ck.isSecure()));
				Bwrite.newLine();
			}
			Bwrite.close();
			fileWrite.close();

			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
