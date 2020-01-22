package com.linkedin.automation;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jadira.usertype.spi.utils.lang.StringUtils;
import org.joda.time.DateTime;
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
import com.fasterxml.jackson.databind.type.MapType;

public class CompanyMessageSending {

	public static void HandleAPage(PageOperation obj, WebDriver driver, HuntingCompany firm,
			Map<String, MessageSendRecord> records, File messageRecordFile) {
		ObjectMapper mapper = new ObjectMapper();
		obj.scrollThePageWithPercent(driver, Double.valueOf(0.75));
		List<WebElement> elementsSendMessage = driver.findElements(By.xpath(".//button[text()='发消息']"));
		for (WebElement elementSentButton : elementsSendMessage) {
			WebElement linkedProfileElement = elementSentButton.findElement(By.xpath("../../../../div[2]/a"));
			String linkedProfile = linkedProfileElement.getAttribute("href");
			MessageSendRecord rec = records.get(linkedProfile);
			if (rec != null) {
				// less than a month, don't send message again
				if (DateTime.now().getMillis() - rec.getLastMessageSendTime() < 30 * 24 * 60 * 60 * 1000L) {
					continue;
				}
			}

			String name = linkedProfileElement.findElement(By.xpath(".//h3/span/span/span")).getText().split(" ")[0];
			try {
				obj.scrollThePage(driver, elementSentButton);
				// send exist message
				List<WebElement> sendMessageElements = driver.findElements(By.xpath(".//button[text()='发送']"));
				if (!sendMessageElements.isEmpty()) {
					WebElement ele = sendMessageElements.get(0);
					obj.scrollThePage(driver, ele);
					obj.sleep(1000);
					if (ele.isEnabled()) {
						ele.sendKeys(Keys.ENTER);
					}
					obj.sleep(3000);
				}

				// close all message window
				closeAllMessageWindow(driver);
				elementSentButton.sendKeys(Keys.ENTER);

//				List<WebElement> namespans = driver.findElements(By.xpath(".//span[@class='artdeco-pill__text']"));
//				if (!namespans.isEmpty()) {
//					name = namespans.get(0).getText().split(" ")[0];
//				}

				String hintMessage[] = { "Hi " + name + ",\r\n" + "我是William,工程师出身的R2R Consultant。\r\n"
						+ "我在帮一些专业的猎头公司招聘猎头顾问。\r\n" + "最近怎么样呀？\r\n" + "我的手机18601793121（微信同号）,可以认识一下，互帮互助。\r\n" };

				WebElement messageElement = driver.findElements(By.xpath(".//div[@aria-label='写消息…']/p/..")).get(0);
				WebElement sendMessageElement = driver.findElements(By.xpath(".//button[text()='发送']")).get(0);

				for (String hint : hintMessage) {
					obj.sleep(10000);					
					messageElement.sendKeys(hint);
					sendMessageElement.sendKeys(Keys.ENTER);

					Long nowLong = DateTime.now().getMillis();
					if (rec == null) {
						rec = new MessageSendRecord();
						rec.setProfile(linkedProfile);
						rec.setLastMessageSendTime(nowLong);
						records.put(linkedProfile, rec);
					} else {
						rec.setLastMessageSendTime(nowLong);
					}
					mapper.writeValue(messageRecordFile, records);
					obj.sleep(5000);
				}
				closeAllMessageWindow(driver);
			} catch (Exception e) {
				e.printStackTrace();
				// obj.sleep(1000 * 60 * 60 * 1);
				String currentURL = driver.getCurrentUrl();
				driver.get(currentURL);
				HandleAPage(obj, driver, firm, records, messageRecordFile);
			}
		}
	}

	public static void closeAllMessageWindow(WebDriver driver) {
		List<WebElement> msgCloseBtn = driver
				.findElements(By.xpath(".//button[@data-control-name='overlay.close_conversation_window']"));
		if (!msgCloseBtn.isEmpty()) {
			for (WebElement element : msgCloseBtn) {
				element.sendKeys(Keys.ENTER);
			}
		}

		List<WebElement> nextPageElements = driver.findElements(By.xpath(".//span[text()='删除']/.."));
		if (!nextPageElements.isEmpty()) {
			for (WebElement element : nextPageElements) {
				element.sendKeys(Keys.ENTER);
			}
		}
	}

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {

		File huntingFirmFile = new File(CommonSetting.cookieFilePrefix + "huntingfirmsPureCode.txt");
		File messageRecordFile = new File(CommonSetting.cookieFilePrefix + "messageRecord.txt");
		ObjectMapper mapper = new ObjectMapper();
		JavaType firmType = mapper.getTypeFactory().constructParametricType(List.class, HuntingCompany.class);
		MapType recordType = mapper.getTypeFactory().constructMapType(Map.class, String.class, MessageSendRecord.class);
		// new TypeReference<List<Cookie>>() {}
		List<HuntingCompany> firmsSet = (List<HuntingCompany>) mapper.readValue(huntingFirmFile, firmType);

		HashMap<String, MessageSendRecord> records = (HashMap<String, MessageSendRecord>) mapper
				.readValue(messageRecordFile, recordType);

		// for (HuntingCompany firm : firmsSet) {
		// firm.setHasFinished(false);
		// }

		PageOperation obj = new PageOperation();
		WebDriver driver;
		// chrome
		System.setProperty("webdriver.chrome.driver", CommonSetting.chromeDrivePath);
		driver = new ChromeDriver();

		driver.get("https://www.linkedin.com");
		driver.manage().window().maximize();

		driver.manage().deleteAllCookies();

		File cookieFile = new File(CommonSetting.cookieFilePrefix + "WilliamCookie.txt");
		JavaType linkedinCookieType = mapper.getTypeFactory().constructParametricType(List.class, LinkedInCookie.class);
		List<LinkedInCookie> cookieSet = (List<LinkedInCookie>) mapper.readValue(cookieFile, linkedinCookieType);
		obj.sleep(1000);
		for (LinkedInCookie cook : cookieSet) {
			Cookie coo = new Cookie(cook.getName(), cook.getValue(), cook.getDomain(), cook.getPath(), cook.getExpiry(),
					cook.isSecure(), cook.isHttpOnly());
			driver.manage().addCookie(coo);
		}

		for (HuntingCompany firm : firmsSet) {
			if (firm.isCustomer()) {
				continue;
			}
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

					HandleAPage(obj, driver, firm, records, messageRecordFile);
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
						WebElement ele = nextPageElements.get(0);
						if (ele.isEnabled()) {
							nextPageElements.get(0).sendKeys(Keys.ENTER);
							obj.sleep(10000);
						} else {
							firm.setHasFinished(true);
							mapper.writeValue(huntingFirmFile, firmsSet);
							break;
						}
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
