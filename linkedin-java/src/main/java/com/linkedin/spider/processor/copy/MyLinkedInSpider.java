package com.linkedin.spider.processor.copy;

import com.linkedin.spider.SpecialKeeper;
import com.linkedin.spider.SpiderConstants;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class MyLinkedInSpider extends Spider {

	public MyLinkedInSpider(PageProcessor pageProcessor) {
		super(pageProcessor);
	}

	@Override
	protected void processRequest(Request request) {
		if (!SpiderConstants.terminate) {
			super.processRequest(request);
		} else {
			//new SpecialKeeper().writeToExcelFile();
			this.close();
		}

	}
}
