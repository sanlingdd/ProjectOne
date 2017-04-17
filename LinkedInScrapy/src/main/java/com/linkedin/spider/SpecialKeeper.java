package com.linkedin.spider;

import com.linkedin.spider.processor.LinkedInOutputKeeper;

public class SpecialKeeper extends LinkedInOutputKeeper {
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try {
			this.writeToExcelFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
