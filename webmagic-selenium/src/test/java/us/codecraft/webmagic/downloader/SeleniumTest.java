package us.codecraft.webmagic.downloader;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author code4crafter@gmail.com <br>
 *         Date: 13-7-26 <br>
 *         Time: 下午12:27 <br>
 */
public class SeleniumTest {

	private String chromeDriverPath = "/Users/i071944/chromedriver/chromedriver.exe";

	// @Test
	// public void test() {
	// System.getProperties().setProperty("webdriver.chrome.driver",
	// chromeDriverPath);

	// @Test
	public void testSelenium() {
		System.getProperties().setProperty("webdriver.chrome.driver", chromeDriverPath);

		// System.getProperties().setProperty("webdriver.chrome.driver",
		// "/Users/i071944/chromedriver");
		Map<String, Object> contentSettings = new HashMap<String, Object>();
		contentSettings.put("images", 2);

		Map<String, Object> preferences = new HashMap<String, Object>();
		preferences.put("profile.default_content_settings", contentSettings);

		DesiredCapabilities caps = DesiredCapabilities.chrome();
		caps.setCapability("chrome.prefs", preferences);
		caps.setCapability("chrome.switches", Arrays.asList("--user-data-dir=/Users/i071944/temp/chrome"));
		WebDriver webDriver = new ChromeDriver(caps);
		webDriver.get("http://www.linkedin.com");
		WebElement webEmailElement = webDriver.findElement(By.xpath("//input[@id='login-email']"));
		webEmailElement.sendKeys("huangxianbin_2005@aliyun.com");

		WebElement webPasswordElement = webDriver.findElement(By.xpath("//input[@id='login-password']"));
		webPasswordElement.sendKeys("pa22word");

		WebElement webSubmitElement = webDriver.findElement(By.xpath("//input[@id='login-submit']"));
		webSubmitElement.submit();

		WebElement webSearchElement = webDriver.findElement(By.xpath("//input[@placeholder='Search']"));
		webSearchElement.sendKeys("SAP");

		WebElement webSearchButtonElement = webDriver.findElement(By.xpath("//button[@class='nav-search-button']"));
		webSearchButtonElement.click();

		WebElement webConnectButtonElement = webDriver
				.findElement(By.xpath("//button[@class='search-result__actions--primary button-secondary-medium m5']"));
		webConnectButtonElement.click();

		WebElement comfirmButtonElement = webDriver
				.findElement(By.xpath("//button[@class='button-primary-large ml3']"));
		comfirmButtonElement.click();

		System.out.println();

		webDriver.close();
	}

	@Test
	public void testWechat() {
		System.getProperties().setProperty("webdriver.chrome.driver", chromeDriverPath);

		// System.getProperties().setProperty("webdriver.chrome.driver",
		// "/Users/i071944/chromedriver");
		Map<String, Object> contentSettings = new HashMap<String, Object>();
		contentSettings.put("images", 2);

		Map<String, Object> preferences = new HashMap<String, Object>();
		preferences.put("profile.default_content_settings", contentSettings);

		DesiredCapabilities caps = DesiredCapabilities.chrome();
		caps.setCapability("chrome.prefs", preferences);
		caps.setCapability("chrome.switches", Arrays.asList("--user-data-dir=/Users/i071944/temp/chrome"));
		WebDriver webDriver = new ChromeDriver(caps);
		webDriver.get("https://wx.qq.com/");

		WebElement webHTMLElement = webDriver.findElement(By.xpath("//html"));
		System.out.println(webHTMLElement.getAttribute("outerHTML"));
		WebElement webEmailElement = webDriver.findElement(By.xpath("//input[@id='login-email']"));
		webEmailElement.sendKeys("huangxianbin_2005@aliyun.com");

		WebElement webPasswordElement = webDriver.findElement(By.xpath("//input[@id='login-password']"));
		webPasswordElement.sendKeys("pa22word");

		WebElement webSubmitElement = webDriver.findElement(By.xpath("//input[@id='login-submit']"));
		webSubmitElement.submit();

		WebElement webSearchElement = webDriver.findElement(By.xpath("//input[@placeholder='Search']"));
		webSearchElement.sendKeys("SAP");

		WebElement webSearchButtonElement = webDriver.findElement(By.xpath("//button[@class='nav-search-button']"));
		webSearchButtonElement.click();

		WebElement webConnectButtonElement = webDriver
				.findElement(By.xpath("//button[@class='search-result__actions--primary button-secondary-medium m5']"));
		webConnectButtonElement.click();

		WebElement comfirmButtonElement = webDriver
				.findElement(By.xpath("//button[@class='button-primary-large ml3']"));
		comfirmButtonElement.click();

		System.out.println();

		webDriver.close();
	}

	public void takescreenShot() {
		try {
			DesiredCapabilities caps = DesiredCapabilities.chrome();
			WebDriver webDriver = new ChromeDriver(caps);
			TakesScreenshot ts = (TakesScreenshot) webDriver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(source, new File("E:\\Selenium_Shubham\\ScreenShotPricepage"));

			System.out.println("Screenshot is printed");
		} catch (Exception e) {
			System.out.println("Exception is handled");
			e.getMessage();
		}
	}

}
