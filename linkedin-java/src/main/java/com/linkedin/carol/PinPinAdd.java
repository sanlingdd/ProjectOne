package com.linkedin.carol;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkedin.automation.CommonSetting;
import com.linkedin.automation.LinkedInCookie;
import com.linkedin.automation.PageOperation;
import com.linkedin.jpa.entity.Phone;
import com.linkedin.jpa.entity.Profile;
import com.linkedin.spider.SpiderConstants;

public class PinPinAdd {

	public static void WebGet(WebDriver driver, String url) {
		try {
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			driver.navigate().to(url);
		} catch (TimeoutException e) {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.stop()");
		}
	}

	public static void main(String[] args)
			throws InterruptedException, JsonParseException, JsonMappingException, IOException {

//		File huntingFirmFile = new File(CommonSetting.cookieFilePrefix + "YaqiPureURL.txt");
		ObjectMapper mapper = new ObjectMapper();
//		JavaType firmType = mapper.getTypeFactory().constructParametricType(List.class, HuntingCompany.class);
//		// new TypeReference<List<Cookie>>() {}
//		List<HuntingCompany> firmsSet = (List<HuntingCompany>) mapper.readValue(huntingFirmFile, firmType);
		PageOperation obj = new PageOperation();
		WebDriver driver;
		// chrome
		System.setProperty("webdriver.chrome.driver", CommonSetting.Chrome360DriverPath);
		ChromeOptions options = new ChromeOptions();
		options.setBinary("C:\\Users\\Michael\\AppData\\Local\\360Chrome\\Chrome\\Application\\360chrome.exe");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();

		driver.manage().timeouts().pageLoadTimeout(500, TimeUnit.SECONDS);
		WebGet(driver, "http://39.98.32.38:5678/webapp/#/login");

		driver.manage().deleteAllCookies();
		Thread.sleep(10000);

		File cookieFile = new File(CommonSetting.cookieFilePrefix + "PinPinWilliamCookie.txt");
		JavaType linkedinCookieType = mapper.getTypeFactory().constructParametricType(List.class, LinkedInCookie.class);
		List<LinkedInCookie> cookieSet = (List<LinkedInCookie>) mapper.readValue(cookieFile, linkedinCookieType);
		obj.sleep(1000);
		for (LinkedInCookie cook : cookieSet) {
			Cookie coo = new Cookie(cook.getName(), cook.getValue(), cook.getDomain(), cook.getPath(), cook.getExpiry(),
					cook.isSecure(), cook.isHttpOnly());
			driver.manage().addCookie(coo);
		}

		WebGet(driver, "http://39.98.32.38:5678/webapp/#/nav/candidate/edit");

		Map<String, Profile> profiles = null;
		File file = new File("C:\\Profiles\\profiles.txt");
		if (file.exists()) {
			FileInputStream fos;
			try {
				fos = new FileInputStream(file);
				BufferedInputStream bos = new BufferedInputStream(fos);
				byte[] bytes = bos.readAllBytes();
				String profilesStr = new String(bytes);
				profiles = JSON.parseObject(profilesStr, new TypeReference<HashMap<String, Profile>>() {
				});
				SpiderConstants.profiles.putAll(profiles);
				bos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("output");
		for (String publicindentifier : profiles.keySet()) {

			//
			Profile profile = profiles.get(publicindentifier);

			//if (profile.getEmailAddress() == null && profile.getPhones().isEmpty()) {
			if (profile.getPhones().isEmpty()) {
				continue;
			}
			
			try {
				WebElement name = driver.findElement(By.xpath(".//input[@name='chineseName']"));
				name.sendKeys(profile.getFirstName() + " " + profile.getLastName());
				obj.sleep(1000);

				// Industry
				WebElement industry = driver.findElement(By.xpath(".//input[@name='industry']"));
				industry.click();
				obj.sleep(1000);
				WebElement industrydroplist = driver.findElement(By.xpath(
						".//input[@name='industry']/../div[@form-control-tree-popup='treeData']/div[@class='dropdown-menu']/div[@class='select2-search']/input"));
				industrydroplist.sendKeys("猎头");
				obj.sleep(1000);
				WebElement hunterdroplist = driver.findElement(By.xpath(".//a[text()='猎头']"));
				hunterdroplist.click();
				obj.sleep(1000);
				WebElement confirmbutton = driver.findElement(By.xpath(".//button[text()='确定']"));
				confirmbutton.click();
				obj.sleep(1000);

				// function
				WebElement function = driver.findElement(By.xpath(".//input[@name='fun']"));
				function.click();
				obj.sleep(1000);
				WebElement functiondroplist = driver.findElement(By.xpath(
						".//input[@name='fun']/../div[@form-control-tree-popup='treeData']/div[@class='dropdown-menu']/div[@class='select2-search']/input"));
				functiondroplist.sendKeys("猎头顾问/助理");
				obj.sleep(1000);
				WebElement hunterfunctiondroplist = driver.findElement(By.xpath(".//a[text()='猎头顾问/助理']"));
				hunterfunctiondroplist.click();
				obj.sleep(1000);
				List<WebElement> functionconfirmbutton = driver.findElements(By.xpath(".//button[text()='确定']"));
				for (WebElement ele : functionconfirmbutton) {
					if (ele.isDisplayed()) {
						ele.click();
						break;
					}
				}
				obj.sleep(1000);

				WebElement mobile = driver.findElement(By.xpath(".//input[@name='mobile']"));
				for (Phone phone : profile.getPhones()) {
					// if(phone.getType() == )
				}

				obj.sleep(1000);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
