package com.linkedin.carol;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jadira.usertype.spi.utils.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkedin.automation.HuntingCompany;
import com.linkedin.automation.LinkedInCookie;
import com.linkedin.automation.PageOperation;

public class CompanyMessageAllFriends {

	public static void HandleAPage(PageOperation obj, WebDriver driver, HuntingCompany firm) {
		int skip = 0;// skip email check
		int sendedNumber = 0;
		obj.scrollThePageWithPercent(driver, Double.valueOf(0.75));
		int currentNumber = 0;
		while (true) {
			// scroll to get all the candidate in the page
			// obj.scrollThePageWithPercent(driver, Double.valueOf(iter / elements.size()));

			List<WebElement> elementsFriendAdd = driver.findElements(By.xpath(".//button[text()='加为好友']"));
			List<WebElement> elementsSendMessage = driver.findElements(By.xpath(".//button[text()='发消息']"));
			if (elementsFriendAdd.size() == skip && elementsSendMessage.size() == sendedNumber) {
				break;
			}
			
			String name = "";
			if (elementsFriendAdd.size() > skip) {
				try {
					WebElement element = elementsFriendAdd.get(0 + skip);
					obj.scrollThePage(driver, element);
					element.sendKeys(Keys.ENTER);
					obj.sleep(5000);

					List<WebElement> emails = driver.findElements(By.xpath(".//input[@id='email']"));
					if (!emails.isEmpty()) {

						List<WebElement> sendbuttons = driver.findElements(By.xpath(".//button[@name='cancel']"));
						if (!sendbuttons.isEmpty()) {
							sendbuttons.get(0).sendKeys(Keys.ENTER);
						}
						skip++;
						continue;
					}

					List<WebElement> sendbuttons = driver.findElements(By.xpath(".//button[text()='添加消息']"));
					if (!sendbuttons.isEmpty()) {
						sendbuttons.get(0).sendKeys(Keys.ENTER);

						String hintMessage = "";
						if (!firm.isCustomer()) {
							hintMessage = "Hi "+name+",我是William，挖猎头的RTR猎头顾问，也免费帮猎头顾问做生涯规划，希望可以与您建立联系，我为国内外一些非常专业的猎头公司招聘猎头，从R,AC,C,SC,MC,TL,Director,Partner的机会都有，近期考虑看看外面的机会吗？我的微信号rtrwilliam,手机号18601793121，欢迎您分享您的联系方式，期待您的回复！";
						} else {
							hintMessage = "Hi "+name+",我是William，RTR猎头顾问，希望可以与您建立联系！";
						}

						WebElement messageElement = driver.findElements(By.xpath(".//textarea[@id='custom-message']"))
								.get(0);
						messageElement.sendKeys(hintMessage);
						obj.sleep(3000);

						List<WebElement> sendinvitationElements = driver
								.findElements(By.xpath(".//button[text()='发邀请']"));
						if (!sendinvitationElements.isEmpty()) {
							sendinvitationElements.get(0).sendKeys(Keys.ENTER);
							obj.sleep(3000);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();

					obj.sleep(1000 * 60 * 60 * 1);

					String currentURL = driver.getCurrentUrl();
					driver.get(currentURL);

					HandleAPage(obj, driver, firm);
				}
				currentNumber++;
			}

			if (elementsSendMessage.size() > sendedNumber) {
				try {
					WebElement elementSentButton = elementsSendMessage.get(0 + sendedNumber);
					obj.scrollThePage(driver, elementSentButton);
					List<WebElement> sendMessageElements = driver.findElements(By.xpath(".//button[text()='发送']"));
					if (!sendMessageElements.isEmpty()) {
						sendMessageElements.get(0).sendKeys(Keys.ENTER);
						obj.sleep(3000);
					}
					
					closeAllMessageWindow(driver);
					elementSentButton.sendKeys(Keys.ENTER);//driver.findElements(By.xpath(".//span[@class='msg-connections-typeahead__recipient-name']"))

					List<WebElement> namespans =driver.findElements(By.xpath(".//span[@class='artdeco-pill__text']"));
					if(!namespans.isEmpty()){
						name = namespans.get(0).getText().split(" ")[0];						
					}
					String hintMessage = "Hi "+name+", 我是招聘猎头的William，很高兴认识你，方便留个联系方式认识一下吗？我的微信18601793121\r\n" + 
							"如近期有看机会的计划，请务必回复哈，Big Thanks～\r\n" + 
							"";
					WebElement messageElement = driver.findElements(By.xpath(".//div[contains(@class,'msg-form__contenteditable')]")).get(0);
					messageElement.sendKeys(hintMessage);

					sendMessageElements = driver.findElements(By.xpath(".//button[text()='发送']"));
					if (!sendMessageElements.isEmpty()) {
						sendMessageElements.get(0).sendKeys(Keys.ENTER);
					}

					obj.sleep(3000);
					closeAllMessageWindow(driver);
//					List<WebElement> msgCloseBtn = driver.findElements(By.xpath(".//button[@class='msg-overlay-bubble-header__control js-msg-close artdeco-button artdeco-button--circle artdeco-button--muted artdeco-button--1 artdeco-button--tertiary ember-view']"));
//					if (!msgCloseBtn.isEmpty()) {
//						for(WebElement element : msgCloseBtn)
//						{
//							element.sendKeys(Keys.ENTER);
//						}
//					}
					sendedNumber++;
				} catch (Exception e) {
					e.printStackTrace();
					// obj.sleep(1000 * 60 * 60 * 1);
					String currentURL = driver.getCurrentUrl();
					driver.get(currentURL);
					HandleAPage(obj, driver, firm);
				}
				currentNumber++;
			}
			obj.sleep(10000);
		}
	}
	
	public static void closeAllMessageWindow(WebDriver driver) {
		List<WebElement> msgCloseBtn = driver.findElements(By.xpath(".//button[@data-control-name='overlay.close_conversation_window']"));
		if (!msgCloseBtn.isEmpty()) {
			for(WebElement element : msgCloseBtn)
			{
				element.sendKeys(Keys.ENTER);
			}
		}
	}

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {

		File huntingFirmFile = new File("C://temp/huntingfirmsPureCode.txt");
		ObjectMapper mapper = new ObjectMapper();
		JavaType firmType = mapper.getTypeFactory().constructParametricType(List.class, HuntingCompany.class);
		// new TypeReference<List<Cookie>>() {}
		List<HuntingCompany> firmsSet = (List<HuntingCompany>) mapper.readValue(huntingFirmFile, firmType);
		// for (HuntingCompany firm : firmsSet) {
		// firm.setHasFinished(false);
		// }

		PageOperation obj = new PageOperation();
		WebDriver driver;
		// chrome
		System.setProperty("webdriver.chrome.driver", "/temp/chromedriver_win32/chromedriver.exe");
		driver = new ChromeDriver();

		driver.get("https://www.linkedin.com");
		driver.manage().window().maximize();

		driver.manage().deleteAllCookies();

		File cookieFile = new File("C://temp/cookie.txt");
		JavaType linkedinCookieType = mapper.getTypeFactory().constructParametricType(List.class, LinkedInCookie.class);
		List<LinkedInCookie> cookieSet = (List<LinkedInCookie>) mapper.readValue(cookieFile, linkedinCookieType);
		obj.sleep(1000);
		for (LinkedInCookie cook : cookieSet) {
			Cookie coo = new Cookie(cook.getName(), cook.getValue(), cook.getDomain(), cook.getPath(), cook.getExpiry(),
					cook.isSecure(), cook.isHttpOnly());
			driver.manage().addCookie(coo);
		}

		for (HuntingCompany firm : firmsSet) {

			String company = "";
			if (firm.isLink()) {
				company = firm.getUrl();
			} else {
				StringBuilder argsStr = new StringBuilder("");
				argsStr.append("\"").append(firm.getCode()).append("\"");
				company = "http://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B" + argsStr.toString()
						+ "%5D&facetGeoRegion=%5B\"cn%3A0\"%5D&facetNetwork=%5B\"F\"%5D&origin=FACETED_SEARCH&page=1";

			}

			obj.sleep(1000);
			driver.get(company);
			obj.sleep(3000);// get company name
			if (StringUtils.isEmpty(firm.getName())) {
				List<WebElement> firmNameElments = driver
						.findElements(By.xpath(".//div[@class='search-s-facet__name']"));
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

					HandleAPage(obj, driver, firm);
					// elements.forEach((element) -> {
					// element.sendKeys(Keys.ENTER);
					// obj.sleep(100);
					// });
					List<WebElement> nextPageElements = driver.findElements(By.xpath(
							".//button[@class='artdeco-pagination__button artdeco-pagination__button--next artdeco-button artdeco-button--muted artdeco-button--icon-right artdeco-button--1 artdeco-button--tertiary ember-view']"));
					if (nextPageElements.isEmpty()) {
						firm.setHasFinished(true);
						mapper.writeValue(huntingFirmFile, firmsSet);
						break;
					} else {
						nextPageElements.get(0).sendKeys(Keys.ENTER);
						obj.sleep(10000);
					}
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}

		}
		// finished
		// driver.close();

	}

}
