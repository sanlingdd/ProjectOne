package com.linkedin.spider.processor;

import com.linkedin.spider.SpiderConstants;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.PriorityScheduler;

public class LinkedinPriorityScheduler extends PriorityScheduler {

	@Override
	public synchronized Request poll(Task task) {
		Request poll = super.poll(task);

		if (poll == null) {
			for (String str : SpiderConstants.allProfileURLsThisExcution.keySet()) {
				if (!SpiderConstants.allProfileURLsThisExcution.get(str)) {
					SpiderConstants.allProfileURLsThisExcution.put(str, true);
					super.pushWhenNoDuplicate(new Request(str), task);
				}
			}
		} else {
			return poll;
		}

		return super.poll(task);
	}
}
