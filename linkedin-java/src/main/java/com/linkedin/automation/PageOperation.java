package com.linkedin.automation;

import java.util.Random;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PageOperation {

	public void scrollThePageWithPercent(WebDriver webDriver, Double percent) {
		int sleepTime = 100;
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		js.executeScript(String.format("window.scrollTo(0, (document.body.scrollHeight)* %s)", percent));
		sleep(sleepTime);
	}

	public void scrollThePage(WebDriver webDriver) {
		int sleepTime = 100;
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		js.executeScript("window.scrollTo(0, (document.body.scrollHeight)/2)");
		sleep(sleepTime);

		js.executeScript("window.scrollTo(0, (document.body.scrollHeight)/6)");
		sleep(sleepTime);
	}

	public void scrollThePage(WebDriver webDriver, WebElement element) {
		int sleepTime = 100;
		// document.getElementById(“test”).scrollIntoView();

		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		// roll down and keep the element to the center of browser
		js.executeScript("arguments[0].scrollIntoViewIfNeeded(true)", element);
		sleep(sleepTime);

		// element.c
	}

	public void sleep(int time) {
		try {
			Random rand = new Random();
			int randNum = rand.nextInt(1000);
			Thread.sleep(time + randNum);
		} catch (InterruptedException e) {			e.printStackTrace();
		}
	}
}
