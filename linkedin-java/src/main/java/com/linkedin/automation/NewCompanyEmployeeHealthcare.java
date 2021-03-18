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

public class NewCompanyEmployeeHealthcare {
	public static int count = 0;
	public static final int maxCount = 1 + (new Random()).nextInt(50);

	public static boolean HandleAPage(PageOperation obj, WebDriver driver, HuntingCompany firm) {
		obj.scrollThePageWithPercent(driver, Double.valueOf(0.75));
		while (true) {
			// scroll to get all the candidate in the page
			// obj.scrollThePageWithPercent(driver, Double.valueOf(iter / elements.size()));

			List<WebElement> elements = driver.findElements(By.xpath(".//span[text()='加为好友']/.."));

			try {
				for (WebElement element : elements) {
					obj.scrollThePage(driver, element);
					element.sendKeys(Keys.ENTER);
					obj.sleep(5000);

					List<WebElement> emails = driver.findElements(By.xpath(".//input[@id='email']"));
					if (!emails.isEmpty()) {
						List<WebElement> sendbuttons = driver.findElements(By.xpath(".//button[@name='cancel']"));
						if (!sendbuttons.isEmpty()) {
							sendbuttons.get(0).sendKeys(Keys.ENTER);
						}
						continue;
					}

					List<WebElement> nameelements = driver
							.findElements(By.xpath(".//li-icon[@type='success-pebble-icon']/../span/strong"));
					String name = "";
					if (!nameelements.isEmpty()) {
						name = nameelements.get(0).getText();
//						name = name.replace("邀请", "");
//						name = name.replace("成为好友", "");
						name = name.split(" ")[0];
					}

					List<WebElement> sendbuttons = driver.findElements(By.xpath(".//span[text()='添加消息']/.."));
					if (!sendbuttons.isEmpty()) {
						sendbuttons.get(0).sendKeys(Keys.ENTER);

						String hintMessage = "";
						if (!firm.isCustomer()) {
							hintMessage = "Hi " + name + ",\r\n" + "我是William\r\n"
									+ "可以认识一下吗? \r\n" + "我的手机:18601793121（微信同号）,可以进一步沟通。\r\n";

						} else {
							hintMessage = "Hi " + name + ",我是William，希望可以与您建立联系 ！";
						}

						WebElement messageElement = driver.findElements(By.xpath(".//textarea[@id='custom-message']"))
								.get(0);
						messageElement.sendKeys(hintMessage);
						obj.sleep(3000);

						List<WebElement> sendinvitationElements = driver
								.findElements(By.xpath(".//span[text()='发送']/.."));
						if (!sendinvitationElements.isEmpty()) {
							sendinvitationElements.get(0).sendKeys(Keys.ENTER);
							obj.sleep(3000);
						}

						if (count > maxCount) {
							return true;
						}
						count++;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();

				obj.sleep(1000 * 60 * 3);

				String currentURL = driver.getCurrentUrl();
				WebGet(driver, currentURL);

				HandleAPage(obj, driver, firm);
			}
			obj.sleep(10000);
			break;
		}

		return false;
	}

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
		for (HuntingCompany firm : firmsSet) {
			if (!StringUtils.isEmpty(firm.getName())) {
				firm.setHasFinished(false);
			}
		}

		int iter = 0;
		WebDriver driver = getNewDriver();
		for (HuntingCompany firm : firmsSet) {
			try {
				iter++;
				if (firm.isHasFinished()) {
					continue;
				}
				if (StringUtils.isEmpty(firm.getCode())) {
					continue;
				}
//				if (iter != 0 && (iter % 9 == 0)) {
//					driver.close();
//					// driver.quit();
//					driver = getNewDriver();
//				}

				String company = "";
				if (firm.isLink()) {
					company = firm.getUrl();
				} else {
					StringBuilder argsStr = new StringBuilder("");
					argsStr.append("\"").append(firm.getCode()).append("\"");
					// BeiJing
					// company =
					// "http://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B" +
					// argsStr.toString()
					// +
					// "%5D&facetGeoRegion=%5B\"cn%3A8911\"%2C\"cn%3A8905\"%5D&facetNetwork=%5B\"S\"%5D&origin=FACETED_SEARCH&page=1";
					// ShangHai
					company = "http://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B"
							+ argsStr.toString()
							+ "%5D&facetGeoRegion=%5B\"cn%3A8909\"%2C\"cn%3A8883\"%5D&geoUrn=%5B\"102890883\"%5D&keywords=医&network=%5B\"S\"%2C\"O\"%5D&origin=FACETED_SEARCH&page=1";

				}

				obj.sleep(1000);
				WebGet(driver, company);
				obj.sleep(3000);// get company name
				if (StringUtils.isEmpty(firm.getName())) {
					List<WebElement> firmNameElments = driver.findElements(By.xpath(
							".//button[@class='search-s-facet__button artdeco-button artdeco-button--icon-right artdeco-button--2 artdeco-button--primary ember-view']//span[@class='artdeco-button__text']"));
					if (firmNameElments.size() != 0) {
						WebElement firmNameElement = firmNameElments.get(0);
						String firmName = firmNameElement.getText();
						if (firm.getName() == null && (!StringUtils.isEmpty(firmName))) {
							firm.setName(firmName);
						}
					}
				}

				while (true) {
					try {

						if (HandleAPage(obj, driver, firm)) {
							break;
						}
						// elements.forEach((element) -> {
						// element.sendKeys(Keys.ENTER);
						// obj.sleep(100);
						// });
						List<WebElement> nextPageElements = driver.findElements(By.xpath(".//span[text()='下页']/.."));
						if (nextPageElements.isEmpty()) {
							firm.setHasFinished(true);
							mapper.writeValue(huntingFirmFile, firmsSet);
							break;
						} else {
							WebElement element = nextPageElements.get(0);
							obj.scrollThePage(driver, element);
							if (element.isEnabled()) {
								element.sendKeys(Keys.ENTER);
							} else {
								firm.setHasFinished(true);
								mapper.writeValue(huntingFirmFile, firmsSet);
								break;
							}
							obj.sleep(10000);
						}
					} catch (Exception e) {
						e.printStackTrace();
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("Batch Finished!");

		// finished
		// driver.close();

	}

}
