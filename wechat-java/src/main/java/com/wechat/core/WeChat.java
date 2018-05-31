package com.wechat.core;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.appium.java_client.android.AndroidDriver;

public class WeChat {

	public WebDriver driver;
	private Logger LOGGER = LoggerFactory.getLogger(WeChat.class);
	private Random random = new Random();

	public WeChat() throws MalformedURLException, InterruptedException {
		setup();
	}

	
	private void setup() throws MalformedURLException, InterruptedException {

		// adb connect 127.0.0.1:7555 to enable appium connection to mumu
		// adb shell
		//
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("platformVersion", "7.0");
		// desired_caps['deviceName'] = 'Android Emulator'
		// miu miu installed port
		capabilities.setCapability("deviceName", "SAL0217A28001753");
		//capabilities.setCapability("deviceName", "localhost:7555");
		capabilities.setCapability("appPackage", "com.tencent.mm");
		capabilities.setCapability("appActivity", "com.tencent.mm.ui.LauncherUI");
		capabilities.setCapability("noReset", true);
		driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), capabilities);
		TimeUnit.SECONDS.sleep(random.nextInt(20));
	}

	public AddFriendStatus addFriendUsingQRCode(String message, String comment) throws InterruptedException {

		try {
			TimeUnit.MICROSECONDS.sleep(random.nextInt(3000));
			driver.findElement(By.xpath(
					"//android.support.v7.widget.LinearLayoutCompat/android.widget.RelativeLayout/android.widget.ImageView"))
					.click();
			TimeUnit.MICROSECONDS.sleep(random.nextInt(3000));
			driver.findElements(By.xpath("//android.widget.LinearLayout/android.widget.LinearLayout")).get(2).click();
			TimeUnit.MICROSECONDS.sleep(random.nextInt(3000));
			driver.findElement(By.xpath(
					"//android.support.v7.widget.LinearLayoutCompat/android.widget.LinearLayout/android.widget.RelativeLayout/android.widget.ImageButton"))
					.click();
			TimeUnit.MICROSECONDS.sleep(random.nextInt(3000));

			driver.findElements(
					By.xpath("//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView"))
					.get(1).click();
			TimeUnit.MICROSECONDS.sleep(random.nextInt(3000));

			driver.findElements(
					By.xpath("//android.widget.FrameLayout/android.widget.GridView/android.widget.RelativeLayout"))
					.get(1).click();
			TimeUnit.MICROSECONDS.sleep(random.nextInt(3000));

			TimeUnit.MICROSECONDS.sleep(random.nextInt(3000));
			driver.findElement(By.className("android.widget.Button")).click();
			TimeUnit.MICROSECONDS.sleep(random.nextInt(3000));

			WebElement messageElement = driver
					.findElements(By
							.className("android.widget.EditText"))
					.get(0);
			TimeUnit.MICROSECONDS.sleep(random.nextInt(3000));
			messageElement.clear();
			messageElement.sendKeys(message);

			TimeUnit.MICROSECONDS.sleep(random.nextInt(3000));
			WebElement memoElement = driver.findElements(By.className("android.widget.EditText")).get(1);
			TimeUnit.MICROSECONDS.sleep(random.nextInt(3000));
			memoElement.clear();
			memoElement.sendKeys(message);

			TimeUnit.MICROSECONDS.sleep(random.nextInt(3000));
			driver.findElement(By.xpath(
					"//android.support.v7.widget.LinearLayoutCompat/android.widget.LinearLayout/android.widget.TextView"))
					.click();
			TimeUnit.MICROSECONDS.sleep(random.nextInt(3000));
			return AddFriendStatus.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("not legal friends account");
		}

		return AddFriendStatus.ACCOUNT_NOT_FOUND;
	}

	public void takescreenShot(WebDriver webDriver) {
		try {
			TakesScreenshot ts = (TakesScreenshot) webDriver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(source, new File("C:\\Selenium_Shubham\\" + UUID.randomUUID().toString() + ".jpg"));

			System.out.println("Screenshot is printed");
		} catch (Exception e) {
			System.out.println("Exception is handled");
			e.getMessage();
		}
	}

	public void backtoIntial() {
		for (int i = 0; i < 4; i++) {
			driver.navigate().back();
		}
	}

	public AddFriendStatus addFriendUsingAccount(String account, String message, String comment)
			throws InterruptedException {
		TimeUnit.MICROSECONDS.sleep(random.nextInt(3000));
		driver.findElement(By.xpath(
				"//android.support.v7.widget.LinearLayoutCompat/android.widget.RelativeLayout/android.widget.ImageView"))
				.click();
		TimeUnit.MICROSECONDS.sleep(random.nextInt(3000));
		driver.findElements(By.xpath("//android.widget.LinearLayout/android.widget.LinearLayout")).get(1).click();
		TimeUnit.MICROSECONDS.sleep(random.nextInt(3000));
		driver.findElements(
				By.xpath("//android.widget.LinearLayout/android.widget.RelativeLayout/android.widget.TextView")).get(1)
				.click();
		TimeUnit.MICROSECONDS.sleep(random.nextInt(3000));

		WebElement acct_inputs = driver.findElement(
				By.xpath("//android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.EditText"));
		acct_inputs.clear();
		acct_inputs.sendKeys(account);

		TimeUnit.MICROSECONDS.sleep(random.nextInt(3000));
		driver.findElement(
				By.xpath("//android.widget.RelativeLayout/android.widget.LinearLayout"))
				.click();
		TimeUnit.MICROSECONDS.sleep(random.nextInt(3000));

		try {
			WebElement messageElement = driver.findElement(By.id("com.tencent.mm:id/cz9"));//.get(0);
			TimeUnit.MICROSECONDS.sleep(random.nextInt(3000));
			messageElement.clear();
			messageElement.sendKeys(message);

			TimeUnit.MICROSECONDS.sleep(random.nextInt(3000));
			WebElement memoElement = driver.findElements(By.className("android.widget.EditText")).get(1);
			TimeUnit.MICROSECONDS.sleep(random.nextInt(3000));
			memoElement.clear();
			memoElement.sendKeys(message);

			TimeUnit.MICROSECONDS.sleep(random.nextInt(3000));
			driver.findElement(By.className("android.widget.Button")).click();
			TimeUnit.MICROSECONDS.sleep(random.nextInt(3000));
			return AddFriendStatus.SUCCESS;
		} catch (Exception e) {
			LOGGER.info("not legal friends account");
			e.printStackTrace();
			// can not find account successfully
		}

		try {
			// ckeck if already your friend,
			WebElement sendmessagebutton = driver.findElement(By.className("android.widget.Button"));
			if (sendmessagebutton != null) {
				LOGGER.info("already your friend, can send message");
				return AddFriendStatus.SUCCESS;
			}
		} catch (Exception e) {
			// not account not found, continue check
			LOGGER.info("acount found continue");
		}

		try {

			TimeUnit.MICROSECONDS.sleep(random.nextInt(3000));
			driver.findElement(By.xpath(
					"//android.widget.LinearLayout[contains(@class,'android.widget.TextView') and contains(@class,'android.widget.TextView')]"));
			TimeUnit.MICROSECONDS.sleep(random.nextInt(3000));
			return AddFriendStatus.ACCOUNT_NOT_FOUND;
			// account not found
		} catch (Exception e) {
			return AddFriendStatus.FAIL;
		}
	}

	public static void main(String[] args) throws MalformedURLException, InterruptedException {
		
		WeChat wechat1 = new WeChat();
		wechat1.addFriendUsingAccount("wendydww", "This is from Robot", "Yulia");
		wechat1.driver.quit();
		
//		WeChat wechat = new WeChat();
//		wechat.addFriendUsingQRCode("Hello", "William");
//		wechat.driver.quit();
		

	}

}
