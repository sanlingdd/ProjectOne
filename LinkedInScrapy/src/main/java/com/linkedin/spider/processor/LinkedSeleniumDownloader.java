package com.linkedin.spider.processor;

import java.io.Closeable;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.downloader.selenium.WebDriverPool;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.PlainText;
import us.codecraft.webmagic.utils.UrlUtils;

/**
 * 使用Selenium调用浏览器进行渲染�?�目前仅支持chrome�?<br>
 * �?要下载Selenium driver支持�?<br>
 *
 * @author code4crafter@gmail.com <br>
 *         Date: 13-7-26 <br>
 *         Time: 下午1:37 <br>
 */
public class LinkedSeleniumDownloader implements Downloader, Closeable {

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
	public LinkedSeleniumDownloader(String chromeDriverPath) {
		System.getProperties().setProperty("webdriver.chrome.driver", chromeDriverPath);
	}

	/**
	 * Constructor without any filed. Construct PhantomJS browser
	 * 
	 * @author bob.li.0718@gmail.com
	 */
	public LinkedSeleniumDownloader() {
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
	public LinkedSeleniumDownloader setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
		return this;
	}

	@Override
	public LinkedinPage download(Request request, Task task) {
		checkInit();
		WebDriver webDriver;
		try {
			webDriver = webDriverPool.get();
		} catch (InterruptedException e) {
			logger.warn("interrupted", e);
			return null;
		}
		logger.info("downloading page " + request.getUrl());
		webDriver.get(request.getUrl());
		webDriver.manage().window().maximize();
		
		WebDriver.Options manage = webDriver.manage();
		Site site = task.getSite();
		if (site.getCookies() != null) {
			for (Map.Entry<String, String> cookieEntry : site.getCookies().entrySet()) {
				Cookie cookie = new Cookie(cookieEntry.getKey(), cookieEntry.getValue());
				manage.addCookie(cookie);
			}
		}
		
		this.scrollThePage(webDriver);


		LinkedinPage page = new LinkedinPage();
		if (!request.getUrl().contains("facetNetwork") && request.getUrl().contains("SEARCH")) {
			WebElement webFirstCheckbookElement = null;
			try {
				webFirstCheckbookElement = webDriver.findElement(By.xpath("//input[@id='sf-facetNetwork-F']"));
				page.setLinkedInLimitStarted(webFirstCheckbookElement.isSelected());
			} catch (Exception e) {
				logger.info(e.getMessage());
			}
		} else {

			while (true) {
				WebElement moreSkills = null;
				try {
					moreSkills = webDriver.findElement(By.xpath(
							"//button[contains(@class,'pv-profile-section__card-action-bar artdeco-container-card-action-bar pv-skills-section__additional-skills')]"));
					moreSkills.click();
					break;
				} catch (Exception e) {
					webDriver.navigate().refresh();
					this.scrollThePage(webDriver);
				}

			}

			sleep(sleepTime);

			while (true) {

				WebElement seeMore = null;
				try {
					seeMore = webDriver.findElement(
							By.xpath("//button[contains(@class,'pv-profile-section__see-more-inline link')]"));
					seeMore.click();
					sleep(sleepTime);
				} catch (Exception e) {
					break;
				}
			}
		}

		WebElement webElement = webDriver.findElement(By.xpath("/html"));
		String content = webElement.getAttribute("outerHTML");

		page.setRawText(content);
		page.setWebDriver(webDriver);
		page.setWebDriverPool(webDriverPool);

		// PrintWriter printWriter = null;
		// try {
		// printWriter = new PrintWriter(new
		// FileWriter("C:/data/webmagic/www.linkedin.com/temp" + ".json"));
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// printWriter.write(content);
		// printWriter.flush();
		// printWriter.close();
		//
		// page.setHtml(new Html(UrlUtils.fixAllRelativeHrefs(content,
		// request.getUrl())));
		page.setUrl(new PlainText(request.getUrl()));
		page.setRequest(request);
		// webDriverPool.returnToPool(webDriver);
		return page;
	}

	private void checkInit() {
		if (webDriverPool == null) {
			synchronized (this) {
				webDriverPool = new WebDriverPool(poolSize);
			}
		}
	}
	
	private void scrollThePage(WebDriver webDriver)
	{
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		js.executeScript("window.scrollTo(0, (document.body.scrollHeight)/4)");
		sleep(sleepTime);
		
		js.executeScript("window.scrollTo(0, (document.body.scrollHeight)/3)");

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
