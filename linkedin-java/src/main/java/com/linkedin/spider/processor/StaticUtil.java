package com.linkedin.spider.processor;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;

public class StaticUtil {
	public static WebDriver webDriver = null;
	public static List<APProfile> profiles = new ArrayList<APProfile>();
	public static Integer pages = new Integer(0);
	public static void set(WebDriver webDriver){
		StaticUtil.webDriver = webDriver;
	}
}
