package com.linkedin.spider.processor;

import com.linkedin.spider.SpiderConstants;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.PriorityScheduler;

public class LinkedinPriorityScheduler extends PriorityScheduler {
	private static boolean firstStatus = true;

	@Override
	public synchronized Request poll(Task task) {
		Request poll = super.poll(task);
		if (poll == null) {
			if (firstStatus) {
				firstStatus = !firstStatus;
				return poll;
			} else {
				// SpiderConstants.allProfileURLsThisExcution.put(newURL,
				// false);
				for (String str : SpiderConstants.allProfileURLsThisExcution.keySet()) {
					if (!SpiderConstants.allProfileURLsThisExcution.get(str)) {
						SpiderConstants.allProfileURLsThisExcution.put(str, true);
						super.pushWhenNoDuplicate(new Request(str), task);
					}
				}
			}
		}
		return poll;
	}
}
