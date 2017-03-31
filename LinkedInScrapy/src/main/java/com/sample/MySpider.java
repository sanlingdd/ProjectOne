package com.sample;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class MySpider extends Spider {

	public MySpider(PageProcessor pageProcessor) {
		super(pageProcessor);
	}

	@Override
	protected void processRequest(Request request) {
		if (!SpiderConstants.terminate) {
			super.processRequest(request);
		} else {
			new SpecialKeeper().start();
			this.close();
		}

	}
}
