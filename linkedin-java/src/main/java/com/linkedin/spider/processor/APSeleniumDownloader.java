package com.linkedin.spider.processor;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.downloader.selenium.WebDriverPool;
import us.codecraft.webmagic.selector.PlainText;

/**
 * 使用Selenium调用浏览器进行渲染�?�目前仅支持chrome�?<br>
 * �?要下载Selenium driver支持�?<br>
 *
 * @author code4crafter@gmail.com <br>
 *         Date: 13-7-26 <br>
 *         Time: 下午1:37 <br>
 */
public class APSeleniumDownloader implements Downloader, Closeable {

	private volatile WebDriverPool webDriverPool;

	private Logger logger = Logger.getLogger(getClass());

	private int sleepTime = 50;

	private int poolSize = 5;

	private static final String DRIVER_PHANTOMJS = "phantomjs";

	/**
	 * 新建
	 *
	 * @param chromeDriverPath
	 *            chromeDriverPath
	 */
	public APSeleniumDownloader(String chromeDriverPath) {
		System.getProperties().setProperty("webdriver.chrome.driver", chromeDriverPath);
	}

	/**
	 * Constructor without any filed. Construct PhantomJS browser
	 * 
	 * @author bob.li.0718@gmail.com
	 */
	public APSeleniumDownloader() {
		// System.setProperty("phantomjs.binary.path",
		// "/Users/Bingo/Downloads/phantomjs-1.9.7-macosx/bin/phantomjs");
	}

	/**
	 * set sleep time to wait until load success
	 *
	 * @param sleepTime
	 *            sleepTime
	 * @return this
	 */
	public APSeleniumDownloader setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
		return this;
	}

	@Override
	public LinkedinPage download(Request request, Task task) {
		checkInit();
		WebDriver webDriver = StaticUtil.webDriver;
		logger.info("downloading page " + request.getUrl());
		webDriver.get(request.getUrl());
		// WebDriver.Options manage = webDriver.manage();
		//
		// try {
		// File file = new File("/Users/i071944/chromedriver/Cookies.data");
		// FileReader fileReader = new FileReader(file);
		// BufferedReader Buffreader = new BufferedReader(fileReader);
		// String strline;
		// while ((strline = Buffreader.readLine()) != null) {
		// StringTokenizer token = new StringTokenizer(strline, ";");
		// while (token.hasMoreTokens()) {
		// String name = token.nextToken();
		// String value = token.nextToken();
		// String domain = token.nextToken();
		// String path = token.nextToken();
		// Date expiry = null;
		//
		// String val;
		// if (!(val = token.nextToken()).equals("null")) {
		// expiry = new Date(val);
		// }
		// Boolean isSecure = new Boolean(token.nextToken()).booleanValue();
		// Cookie ck = new Cookie(name, value, domain, path, expiry, isSecure);
		// System.out.println(ck);
		// webDriver.manage().addCookie(ck); // This will add the stored //
		// cookie to your current
		// // session
		// }
		// }
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// }

		LinkedinPage page = new LinkedinPage();
		this.sleep(500);

		WebElement webElement = webDriver.findElement(By.xpath("/html"));
		String content = webElement.getAttribute("outerHTML");

		page.setRawText(content);
		page.setWebDriver(webDriver);
		page.setWebDriverPool(webDriverPool);

//		PrintWriter printWriter = null;
//		try {
//			printWriter = new PrintWriter(new FileWriter("C:/data/webmagic/www.linkedin.com/temp" + ".html"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		printWriter.write(content);
//		printWriter.flush();
//		printWriter.close();
		//
		// page.setHtml(new Html(UrlUtils.fixAllRelativeHrefs(content,
		// request.getUrl())));
		page.setUrl(new PlainText(request.getUrl()));
		page.setRequest(request);
		// webDriverPool.returnToPool(webDriver);
		// this.print(((LinkedinPage)page).getWebDriver());
		// this.takescreenShot(((LinkedinPage)page).getWebDriver());

		return page;
	}

	private void print(WebDriver webDriver) {
		WebElement webElement = webDriver.findElement(By.xpath("/html"));
		String content = webElement.getAttribute("outerHTML");
		PrintWriter printWriter = null;
		try {
			printWriter = new PrintWriter(
					new FileWriter("C:/data/webmagic/www.linkedin.com/" + UUID.randomUUID().toString() + ".html"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		printWriter.write(content);
		printWriter.flush();
		printWriter.close();
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

	private void checkInit() {
		if (webDriverPool == null) {
			synchronized (this) {
				webDriverPool = new WebDriverPool(poolSize);
			}
		}
	}

	private void scrollThePage(WebDriver webDriver) {
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		js.executeScript("window.scrollTo(0, (document.body.scrollHeight)/5)");
		sleep(sleepTime);

		js.executeScript("window.scrollTo(0, (document.body.scrollHeight)/4)");
		sleep(sleepTime);

		js.executeScript("window.scrollTo(0, (document.body.scrollHeight)/3)");
		sleep(sleepTime);

		js.executeScript("window.scrollTo(0, (document.body.scrollHeight)/2)");
		sleep(sleepTime);

		js.executeScript("window.scrollTo(0, 2*document.body.scrollHeight/3)");
		sleep(sleepTime);

		js.executeScript("window.scrollTo(0, 3*document.body.scrollHeight/4)");
		sleep(sleepTime);
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		sleep(200);
	}

	private void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setThread(int thread) {
		this.poolSize = thread;
	}

	@Override
	public void close() throws IOException {
		webDriverPool.closeAll();
	}
}
