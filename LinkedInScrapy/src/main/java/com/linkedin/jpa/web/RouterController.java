package com.linkedin.jpa.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linkedin.jpa.entity.Company;
import com.linkedin.jpa.service.CompanyService;

@Controller
public class RouterController {

	@Autowired
	private CompanyService companyService;

	@RequestMapping("/")
	@ResponseBody
	@Transactional(readOnly = true)
	public String helloWorld() {
		Company company = new Company();
		company.setCompanyId(1000L);
		company.setCompanyName("Names");
		this.companyService.saveOrUpdate(company);
		return "Company";
	}

}
