package us.codecraft.webmagic.downloader;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

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

	@Test
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
		
//		try {
//			webDriver.manage().addCookie(new Cookie("bcookie","v=2^&9fbee1ca-79a5-47cc-8810-eb18e753ff44"));
//			webDriver.manage().addCookie(new Cookie("bscookie","v=1^&201610010831302ee66e54-22ff-4608-84ac-0f2f7511eea0AQHT-9IP6dXurZuyOFX72-6doqjLS_kN"));
//			webDriver.manage().addCookie(new Cookie("visit","v=1^&M"));
//			webDriver.manage().addCookie(new Cookie("SID","4b5771bc-b4f9-4836-a8f4-74bcc65d9517"));
//			webDriver.manage().addCookie(new Cookie("VID","V_2016_12_12_01_217755"));
//			webDriver.manage().addCookie(new Cookie("share_setting","PUBLIC"));
//			webDriver.manage().addCookie(new Cookie("hc_survey","true"));
//			webDriver.manage().addCookie(new Cookie("_cb_ls","1"));
//			webDriver.manage().addCookie(new Cookie("_chartbeat2","B4SLZSCbyTk6B5AWo_.1479287732844.1491303514668.0000000000000001"));
//			webDriver.manage().addCookie(new Cookie("ELOQUA","GUID=F341FA27D0614A4DAA111812E8DBBAF9"));
//			webDriver.manage().addCookie(new Cookie("transaction_state","AgHB5iQXCxfKfAAAAVtbf0UL6j9y9bB2zlhMbJIZoKwQN5PdJRdW5QElhMD1z0XxEjO85zKBmAJxOCrB-Dc-28EnUm4ONNp5_sFUvqrZjfFEMs1ElZYKuQbLgmuao___C7gYBv4ewI8I8I4PdsPZwW37DuiQ6H0LmrVEJYMb3_FPSffynfj1a9UatbldutM5J8RPGlZr9w"));
//			webDriver.manage().addCookie(new Cookie("PLAY_SESSION","0eece363f8cb0a08775a5646951edb5c4688bf67-chsInfo=102a4255-43be-4432-a488-2783e5ca313c+premium_inmail_profile_upsell"));
//			webDriver.manage().addCookie(new Cookie("lang","v=2^&lang=en-us"));
//			webDriver.manage().addCookie(new Cookie("JSESSIONID","ajax:5571266308247153066"));
//			webDriver.manage().addCookie(new Cookie("liap","true"));
//			webDriver.manage().addCookie(new Cookie("li_at","AQEDARxstRYE3gasAAABW1xjvW4AAAFbXhsxbk4Al0jsqQBOYrgyCF0L3MQ0A6RD3o9d5ZcEKniwdK9lyoNs6PiZwtFBE9Q9U4rAaaJyqfiRS7B8w02lgLNFqJuOCL0yHxLc9FZKKNTtZP32a73pKR9Q"));
//			webDriver.manage().addCookie(new Cookie("lidc","b=SB94:g=26:u=93:i=1491903692:t=1491959898:s=AQHfPtvzwT9wr-aAz2uvlBKSst9Fn3hp"));
//			webDriver.manage().addCookie(new Cookie("RT","s=1491903693577^&r=https^%^3A^%^2F^%^2Fwww.linkedin.com^%^2Fuas^%^2Flogin^%^3FformSignIn^%^3Dtrue^%^26session_redirect^%^3D^%^252Fvoyager^%^252FloginRedirect.html"));
//			webDriver.manage().addCookie(new Cookie("sdsc","22^%^3A1^%^2C1491903720907^%^7ECONN^%^2C0BA^%^2FPCC6aQudYGk1ykojXYHeXKR0^%^3D"));
//			webDriver.manage().addCookie(new Cookie("_ga","GA1.2.1251577009.1475482809"));
//			webDriver.manage().addCookie(new Cookie("_gat","1"));
//			webDriver.manage().addCookie(new Cookie("_lipt","CwEAAAFbXGVECaXZHkOnD84CqQIer2KE9Hgcl88GfHx4zPKz4-Vg8f9QDzvtv34kSjnOJqL5JB7HNWMenSap9xx0QbRNUfKiaeTZ7YHjBvwraY5RbsVPfYD-H-TjKHged8vJhHjg3UTv0T8h2jUxzVlKsFgBdbDG4rRGdIh_D4CD9ZehHFwIXxxZkxL11vucYd4LLr9AwoUHhSGKw6Q-WKYKYOfLBfl6SPsaIPVaudQbCcYsemUnPaHpyKNjfoEBkz_ApHwV-z-ZYOSLAa47gsSI3RZw8byRirNFhQYDXrLhHirGJv0ULR8wl6Etl2WSRaj4TtsdRKWasbDlmcKWzK03rDFSrDa4gVn-AgWAhnB7QEsWuEbwJkyfI4mNoAvUyLR3jEMkQzwbLcikgH4gMDttp-fqfa8dm4NuE7rTsFbHVsWb6aFf4PUYnE9fZeTz91MgnSJlt_nvVa3HfILmIsnaOvKlZezItuwMdYikfEMIz3xuWGeauBQfO5h9wSsRvebxqo5qzDI_ALdy49rfX2GPrQGs8XbwgALhoj5fWOXZZxJG-SV_kjkPN4ewrSTClJC6-fmRFUugezDWzRffHB6rVGN98zja4o5LpSV8tDyPMzCOczMj-3OVv2dBuZ0"));
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}



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

		WebElement webFirstCheckbookElement = webDriver.findElement(By.xpath("//input[@id='sf-facetNetwork-F']"));
		webFirstCheckbookElement.isSelected();
		
		WebElement webFirstLabelbookElement = webDriver.findElement(By.xpath("//label[@title='1st']"));
		webFirstLabelbookElement.click();
		WebElement webConnectButtonElement = webDriver
				.findElement(By.xpath("//button[@class='search-result__actions--primary button-secondary-medium m5']"));
		webConnectButtonElement.click();

		WebElement comfirmButtonElement = webDriver
				.findElement(By.xpath("//button[@class='button-primary-large ml3']"));
		comfirmButtonElement.click();

		System.out.println();

		webDriver.close();
	}

	// @Test
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
