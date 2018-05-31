package com.linkedin.spider.processor;

import java.util.logging.Level;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.linkedin.spider.processor.copy.ExcelFilePipeLine;
import com.linkedin.spider.processor.copy.MyLinkedInSpider;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.QueueScheduler;

@Service
public class APSpiderHttpMain {
	private Logger logger = LoggerFactory.getLogger(getClass());

	public void startLinkedProfileSpider() {
		System.setProperty("webdriver.chrome.driver", "/Users/i071944/chromedriver/chromedriver.exe");
		
		WebDriver driver;
		System.setProperty("webdriver.chrome.driver", "/Users/i071944/chromedriver/chromedriver.exe");
		driver = new ChromeDriver();
		
		StaticUtil.set(driver);

		APDispatcherPageProcessor dispather = new APDispatcherPageProcessor();
		Spider spider = MyLinkedInSpider.create(dispather);

		java.util.logging.Logger.getLogger(PhantomJSDriverService.class.getName()).setLevel(Level.OFF);

		APOutputKeeper keeper = new APOutputKeeper();
		keeper.start();

		for (int iter = 1; iter <= 326; iter++) {
			String url = "https://people.wdf.sap.corp/search#/results?page=" + new Integer(iter).toString() + "&query=office:CN";
			spider.addUrl(url);
		}
		String chromeDriverPath = "/Users/i071944/chromedriver";
		APSeleniumDownloader seleniumDownloader = new APSeleniumDownloader(chromeDriverPath);

		spider.setDownloader(seleniumDownloader);
		spider.setScheduler(new QueueScheduler())
				// ����Pipeline���������json��ʽ���浽�ļ�
				.addPipeline(new ExcelFilePipeLine())
				// ����5���߳�ͬʱִ��
				.thread(1)
				// ��������
				.run();
	}

	public static void main(String[] args) {
		new APSpiderHttpMain().startLinkedProfileSpider();

	}
}