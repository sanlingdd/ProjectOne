package com.linkedin.carol;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkedin.automation.CommonSetting;
import com.linkedin.automation.LinkedinOperation;
import com.linkedin.automation.PageOperation;

public class PinPinCookie {
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		// TODO Auto-generated method stub
		PageOperation obj = new PageOperation();
		WebDriver driver;
		// chrome
		// chrome
		System.setProperty("webdriver.chrome.driver", CommonSetting.Chrome360DriverPath);
		ChromeOptions options = new ChromeOptions();
		options.setBinary("C:\\Users\\Michael\\AppData\\Local\\360Chrome\\Chrome\\Application\\360chrome.exe");
		driver = new ChromeDriver(options);


		LinkedinOperation lop = new LinkedinOperation();
		driver.manage().window().maximize();

		driver.manage().timeouts().pageLoadTimeout(200,TimeUnit.SECONDS);
		driver.navigate().to("http://39.98.32.38:5678/webapp/#/login");
		String username = "williambin";
		String password = "Initial0";
		
		WebElement account = driver.findElements(By.xpath(".//input[@name='email']")).get(0);
		account.sendKeys(username);
		obj.sleep(1000);

		WebElement pass = driver.findElement(By.xpath(".//input[@name='password']"));
		pass.sendKeys(password);
		obj.sleep(1000);
		

		WebElement button = driver.findElement(By.xpath(".//strong[text()='登陆']/.."));
		button.sendKeys(Keys.ENTER);
		obj.sleep(1000);
				
		Set<org.openqa.selenium.Cookie> cookies = driver.manage().getCookies();
		//File cookieFile = new File("D:/git/linkedin/projectone/linkedin-java/YaqiCookie.txt");
		File cookieFile = new File(CommonSetting.cookieFilePrefix+"PinPinWilliamCookie.txt");
		//File cookieFile = new File(CommonSetting.cookieFilePrefix+"AdaCookie.txt");
		ObjectMapper mapper = new ObjectMapper(); 
		mapper.writeValue(cookieFile, cookies);
	}

}
