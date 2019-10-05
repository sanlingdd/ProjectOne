package com.linkedin.carol;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkedin.automation.LinkedinOperation;
import com.linkedin.automation.PageOperation;

public class LinkedinCookie {

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		// TODO Auto-generated method stub
		PageOperation obj = new PageOperation();
		WebDriver driver;
		// chrome
		System.setProperty("webdriver.chrome.driver", "/temp/chromedriver_win32/chromedriver.exe");
		driver = new ChromeDriver();


		LinkedinOperation lop = new LinkedinOperation();
		driver.manage().window().maximize();

		//Daisy Dong
		//lop.login(driver,"1574580422@qq.com","woaini123123");

		//Yaqi
		//lop.login(driver,"yaqichenhappy@163.com","AaBCD525");
		
		//Carol Xu
		lop.login(driver,"478267107@qq.com","Xu234567");
		
		//Lily Rao
		//lop.login(driver,"15000729310","rt135790");
		

		//Yulia ===Email Yulia170814
		//lop.login(driver,"13774278832@163.com","hx123888");
		
//		https://www.linkedin.com/search/results/people/?facetGeoRegion=%5B%22cn%3A8911%22%5D&facetIndustry=%5B%226%22%5D&keywords=HRBP&origin=FACETED_SEARCH
		
		Set<org.openqa.selenium.Cookie> cookies = driver.manage().getCookies();
		//File cookieFile = new File("C://temp/YaqiCookie.txt");
		//File cookieFile = new File("C://temp/LilyCookie.txt");
		File cookieFile = new File("C://temp/CarolCookie.txt");
		ObjectMapper mapper = new ObjectMapper(); 
		mapper.writeValue(cookieFile, cookies);
	}

}
