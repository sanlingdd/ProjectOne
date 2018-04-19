package com.linkedin.spider.processor;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linkedin.spider.SpecialKeeper;
import com.linkedin.spider.SpiderConstants;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

public class ExcelFilePipeLine extends FilePersistentBase implements Pipeline {

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * new JsonFilePageModelPipeline with default path "/data/webmagic/"
	 */
	public ExcelFilePipeLine() {
	}

	public void process(ResultItems resultItems, Task task) {
		if (resultItems.get("publicIdentifier") != null && resultItems.get("lastName") == null
				&& resultItems.get("firstName") == null) {
			if (!resultItems.get("publicIdentifier").toString().contains("UNKNOW")) {
				new SpecialKeeper().start();
			}
		}

		if (resultItems.get("publicIdentifier") != null && resultItems.get("lastName") != null) {
			SpiderConstants.allProfileURLsThisExcution.put(resultItems.get("publicIdentifier"), true);
			SpiderConstants.profilesAccessedVector.addElement((HashMap<String, Object>) resultItems.getAll());
		}
	}

}