package com.linkedin.automation;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class PeopleYouMayKnow {

	public void scrollThePage(WebDriver webDriver) {
		int sleepTime = 100;
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		js.executeScript("window.scrollTo(0, (document.body.scrollHeight)/2)");
		sleep(sleepTime);

		js.executeScript("window.scrollTo(0, (document.body.scrollHeight)/6)");
		sleep(sleepTime);
	}

	public void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InterruptedException {

		PeopleYouMayKnow obj = new PeopleYouMayKnow();
		WebDriver driver;
		System.setProperty("webdriver.chrome.driver", "/temp/chromedriver_win32/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://www.linkedin.com");
		driver.manage().window().maximize();

		WebElement account = driver.findElements(By.xpath(".//input[@id='login-email']")).get(0);
		account.sendKeys("17091275816");
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

		WebElement pass = driver.findElement(By.xpath(".//input[@id='login-password']"));
		pass.sendKeys("hiro12345");
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

		WebElement button = driver.findElement(By.xpath(".//input[@id='login-submit']"));
		button.click();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

		for (int i = 0; i < 50; i++) {
			driver.get("http://www.linkedin.com/mynetwork/");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			int count = 0;
			while (true) {
				try {
					driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
					List<WebElement> elements = driver
							.findElements(By.xpath(".//button[@class='button-secondary-small']/span[text()='加为好友']"));
					if (!elements.isEmpty()) {
						elements.get(0).click();
						driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
						Thread.sleep(10000);
						count++;
					} else {
						break;
					}
				} catch (Exception e) {
					break;
				}

				if (count % 6 == 0) {
					obj.scrollThePage(driver);
				}
			}
		}
	}

}
