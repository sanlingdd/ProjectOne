package com.linkedin.carol;

import java.io.File;
import java.io.IOException;
import java.util.List;
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
import com.linkedin.automation.CommonSetting;
import com.linkedin.automation.HuntingCompany;
import com.linkedin.automation.LinkedInCookie;
import com.linkedin.automation.PageOperation;

public class NewCompanyEmployeeKayla {

	public static void HandleAPage(PageOperation obj, WebDriver driver, HuntingCompany firm) {
		obj.scrollThePageWithPercent(driver, Double.valueOf(0.75));
		while (true) {
			// scroll to get all the candidate in the page
			// obj.scrollThePageWithPercent(driver, Double.valueOf(iter / elements.size()));

			List<WebElement> elements = driver.findElements(By.xpath(".//button[text()='加为好友']"));

			try {
				int iter = 0;
				for (WebElement element : elements) {
					if (iter > 0) {
						break;
					}
					iter++;
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

					List<WebElement> nameelements = driver.findElements(By.xpath(".//li-icon[@type='success-pebble-icon']/../span/strong"));
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
							hintMessage = "Dear " + name + ",\r\n" + "I'm Carol from a global headhunting firm."
									+ "Currently, I’m looking for the Human Resource Head for my clients.\r\n"
									+ "May I expect your cell phone number/wechat ID so that we could have a brief discussion? \r\n"
									+ "Look forward to your kind reply and maintain friendly and long-term cooperation.";
						} else {
							hintMessage = "Hi " + name + "我是William，希望可以与您建立联系 ！";
						}

						WebElement messageElement = driver.findElements(By.xpath(".//textarea[@id='custom-message']"))
								.get(0);
						messageElement.sendKeys(hintMessage);
						obj.sleep(3000);

						List<WebElement> sendinvitationElements = driver
								.findElements(By.xpath(".//span[text()='完成']/.."));
						if (!sendinvitationElements.isEmpty()) {
							sendinvitationElements.get(0).sendKeys(Keys.ENTER);
							obj.sleep(3000);
						}
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
		// chrome
		System.setProperty("webdriver.chrome.driver", "D:\\360Chrome\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.setBinary("C:\\Users\\Michael\\AppData\\Local\\360Chrome\\Chrome\\Application\\360chrome.exe");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();

		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		WebGet(driver, "https://www.linkedin.com");
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

		driver.manage().deleteAllCookies();
		Thread.sleep(3000);

		File cookieFile = new File(CommonSetting.cookieFilePrefix + "KaylaCookie.txt");
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

		File huntingFirmFile = new File(CommonSetting.cookieFilePrefix + "KaylaPureURL.txt");
		ObjectMapper mapper = new ObjectMapper();
		JavaType firmType = mapper.getTypeFactory().constructParametricType(List.class, HuntingCompany.class);
		// new TypeReference<List<Cookie>>() {}
		List<HuntingCompany> firmsSet = (List<HuntingCompany>) mapper.readValue(huntingFirmFile, firmType);
		// for (HuntingCompany firm : firmsSet) {
		// firm.setHasFinished(false);
		// }

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
				if (iter != 0 && (iter % 9 == 0)) {
					driver.close();
					// driver.quit();
					driver = getNewDriver();
				}

				String company = "";
				if (firm.isLink()) {
					company = firm.getUrl();
				} else {
					StringBuilder argsStr = new StringBuilder("");
					argsStr.append("\"").append(firm.getCode()).append("\"");

					String prefix = "https://www.linkedin.com/search/results/people/?facetGeoRegion=%5B\"cn%3A0\"%5D&facetIndustry=%5B%22";
					String suffix = "&origin=FACETED_SEARCH";
					String middfix = "%22%5D&keywords=";
					StringBuilder sb = new StringBuilder();
					sb.append(prefix);

					sb.append(firm.getName());
					sb.append(middfix).append(firm.getCode()).append(suffix);

					company = sb.toString();
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
				int count = 0;
				while (true) {
					if (count > 50) {
						break;
					}
					try {

						HandleAPage(obj, driver, firm);
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
						count++;
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
