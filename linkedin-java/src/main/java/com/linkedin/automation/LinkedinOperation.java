package com.linkedin.automation;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class LinkedinOperation {

	public void login(WebDriver driver,String username, String key) throws JsonParseException, JsonMappingException, IOException {
		PageOperation obj = new PageOperation();
		
		driver.manage().timeouts().pageLoadTimeout(200,TimeUnit.SECONDS);
		driver.navigate().to("https://www.linkedin.com");
		
		List<WebElement> loginButton = driver.findElements(By.xpath(".//a[text()='登录']"));
		if(!loginButton.isEmpty())
		{
			WebElement loginbutton = loginButton.get(0);
			loginbutton.sendKeys(Keys.ENTER);
			obj.sleep(1000);

			WebElement account = driver.findElements(By.xpath(".//input[@id='username']")).get(0);
			account.sendKeys(username);
			obj.sleep(1000);
	
			WebElement pass = driver.findElement(By.xpath(".//input[@id='password']"));
			pass.sendKeys(key);
			obj.sleep(1000);
	
			WebElement button = driver.findElement(By.xpath(".//button[text()='登录']"));
			button.sendKeys(Keys.ENTER);
			obj.sleep(1000);
			
		}else {

			WebElement account = driver.findElements(By.xpath(".//input[@id='login-email']")).get(0);
			account.sendKeys(username);
			obj.sleep(1000);
	
			WebElement pass = driver.findElement(By.xpath(".//input[@id='login-password']"));
			pass.sendKeys(key);
			obj.sleep(1000);
	
			WebElement button = driver.findElement(By.xpath(".//input[@id='login-submit']"));
			button.sendKeys(Keys.ENTER);
			obj.sleep(1000);
		}
		
		
		
		

	} 
}
