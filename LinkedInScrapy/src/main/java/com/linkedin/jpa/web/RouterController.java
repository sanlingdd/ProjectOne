package com.linkedin.jpa.web;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linkedin.jpa.entity.Company;
import com.linkedin.jpa.service.CompanyService;
import com.linkedin.spider.processor.LinkedinSpiderHttpMain;

@Controller
public class RouterController {

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private LinkedinSpiderHttpMain linkedInSpider;
	
	@RequestMapping("/")
	@ResponseBody
	@Transactional(readOnly = true)
	public String helloWorld() {
		Company company = new Company();
		company.setCompanyId(1000L);
		company.setCompanyName("Names");
		company.setUpdateTime(new DateTime());
		this.companyService.saveOrUpdate(company);
		return "Company";
	}
	
	@RequestMapping("/httpspider")
	@ResponseBody
	@Transactional(readOnly = true)
	public String httpSpider() {
		Company company = new Company();
		company.setCompanyId(1000L);
		company.setCompanyName("Names");
		company.setUpdateTime(new DateTime());
		this.companyService.saveOrUpdate(company);
		linkedInSpider.startLinkedProfileSpider();
		return "Company";
	}
	
	@RequestMapping("/plantomspider")
	@ResponseBody
	@Transactional(readOnly = true)
	public String plantomSpider() {
		Company company = new Company();
		company.setCompanyId(1000L);
		company.setCompanyName("Names");
		company.setUpdateTime(new DateTime());
		this.companyService.saveOrUpdate(company);
		return "Company";
	}

}
