package com.linkedin.automation;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.jadira.usertype.spi.utils.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WithdrawRequest {
	public static void WebGet(WebDriver driver, String url) {
		try {

			driver.navigate().to(url);
		} catch (TimeoutException e) {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.stop()");
		}
	}

	public static WebDriver getNewDriver()
			throws InterruptedException, JsonParseException, JsonMappingException, IOException {
		WebDriver driver;
		PageOperation obj = new PageOperation();
		ObjectMapper mapper = new ObjectMapper();
		JavaType firmType = mapper.getTypeFactory().constructParametricType(List.class, HuntingCompany.class);
		// chrome
//		System.setProperty("webdriver.chrome.driver", CommonSetting.chromeDrivePath);
//		driver = new ChromeDriver();

		System.setProperty("webdriver.chrome.driver", CommonSetting.chromeDrivePath);
		ChromeOptions options = new ChromeOptions();
		// options.setBinary("C:\\Users\\Michael\\AppData\\Local\\360Chrome\\Chrome\\Application\\360chrome.exe");
		driver = new ChromeDriver(options);

		driver.manage().window().maximize();

		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		WebGet(driver, "https://www.linkedin.com");
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

		driver.manage().deleteAllCookies();
		Thread.sleep(3000);

		File cookieFile = new File(CommonSetting.cookieFilePrefix + "WilliamCookie.txt");
		JavaType linkedinCookieType = mapper.getTypeFactory().constructParametricType(List.class, LinkedInCookie.class);
		List<LinkedInCookie> cookieSet = (List<LinkedInCookie>) mapper.readValue(cookieFile, linkedinCookieType);
		obj.sleep(1000);
		for (LinkedInCookie cook : cookieSet) {
			Cookie coo = new Cookie(cook.getName(), cook.getValue(), cook.getDomain(), cook.getPath(), cook.getExpiry(),
					cook.isSecure(), cook.isHttpOnly());
			driver.manage().addCookie(coo);
		}

		return driver;
	}

	public static void main(String[] args)
			throws InterruptedException, JsonParseException, JsonMappingException, IOException {

		File huntingFirmFile = new File(CommonSetting.cookieFilePrefix + "HealthcarePureCode.txt");
		ObjectMapper mapper = new ObjectMapper();
		JavaType firmType = mapper.getTypeFactory().constructParametricType(List.class, HuntingCompany.class);
		// new TypeReference<List<Cookie>>() {}
		List<HuntingCompany> firmsSet = (List<HuntingCompany>) mapper.readValue(huntingFirmFile, firmType);
		for (HuntingCompany firm : firmsSet) {
			firm.setHasFinished(false);
		}

		PageOperation obj = new PageOperation();

		WebDriver driver = getNewDriver();

		//System.out.println("Batch Finished!");

		WebGet(driver, "https://www.linkedin.com/mynetwork/invitation-manager/sent/");
		obj.sleep(5000);
		List<WebElement> elements = driver.findElements(By.xpath(".//span[text()='撤回']/.."));
		while (!elements.isEmpty()) {
			for (WebElement element : elements) {
				obj.scrollThePage(driver, element);
				element.sendKeys(Keys.ENTER);
				obj.sleep(1000);

				WebElement alerdialog = driver.findElement(By.xpath(".//div[@role='alertdialog']"));
				obj.sleep(1000);
				WebElement confirmWithDraw = alerdialog.findElement(By.xpath(".//span[text()='撤回']/.."));
				obj.sleep(1000);
				confirmWithDraw.sendKeys(Keys.ENTER);
				obj.sleep(1000);
			}
			elements = driver.findElements(By.xpath(".//span[text()='撤回']/.."));
		}

		// finished
		// driver.close();

	}

}
