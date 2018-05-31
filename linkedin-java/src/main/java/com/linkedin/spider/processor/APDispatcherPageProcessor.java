package com.linkedin.spider.processor;

import org.apache.http.HttpHost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

@Service
public class APDispatcherPageProcessor implements PageProcessor {

	private APPageProcessor profileProcessor;


	private Site site = Site.me().setRetryTimes(3).setSleepTime(15000).setTimeOut(10000);

	public APDispatcherPageProcessor() {
		site.setCharset("UTF-8");
		site.addHeader("accept-encoding", "gzip, deflate, sdch, br");
		site.addHeader("accept-language", "zh-CN,zh;q=0.8");
		site.addHeader("upgrade-insecure-requests", "1");
		site.addHeader("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
		site.addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		site.addHeader("cache-control", "max-age=0");
		site.addHeader("cookie",
				"shpuvid=\"CmEHO1sOdoexGQm5BYOjAg==\"; BIGipServer~sap_it_corp_webapps-people_prod~lb-4c7322be4-e721=\"rd5o00000000000000000000ffff0a61073bo4000\"; _expertondemand_session=\"BAh7CkkiD3Nlc3Npb25faWQGOgZFVEkiJTU2NWIyNGFkYmQ0YTdjYmFlYzQ1NzQ0ZjYxMGYxZmY5BjsAVEkiCHVpZAY7AEZJIgxJMDcxOTQ0BjsAVEkiDnJldHVybl90bwY7AEYiDC9zZWFyY2hJIhhhdmFpbGFiaWxpdHlfYmFza2V0BjsARnsGOgl1aWRzWwBJIhBfY3NyZl90b2tlbgY7AEZJIjEvYUI0ajBod2U0NVBwQWJ4TXhKU0l2bkV1alhGUytIZEo2Y2lIQjM1aGJnPQY7AEY%3D--ddce5eb432f0ca3a9634f55a13a6936f0c53634f\"");

		// site.addCookie("bcookie","v=2^&9fbee1ca-79a5-47cc-8810-eb18e753ff44");
		// site.addCookie("bscookie","v=1^&201610010831302ee66e54-22ff-4608-84ac-0f2f7511eea0AQHT-9IP6dXurZuyOFX72-6doqjLS_kN");
		// site.addCookie("visit","v=1^&M");
		// site.addCookie("SID","4b5771bc-b4f9-4836-a8f4-74bcc65d9517");
		// site.addCookie("VID","V_2016_12_12_01_217755");
		// site.addCookie("share_setting","PUBLIC");
		// site.addCookie("hc_survey","true");
		// site.addCookie("_cb_ls","1");
		// site.addCookie("_chartbeat2","B4SLZSCbyTk6B5AWo_.1479287732844.1491303514668.0000000000000001");
		// site.addCookie("ELOQUA","GUID=F341FA27D0614A4DAA111812E8DBBAF9");
		// site.addCookie("transaction_state","AgHB5iQXCxfKfAAAAVtbf0UL6j9y9bB2zlhMbJIZoKwQN5PdJRdW5QElhMD1z0XxEjO85zKBmAJxOCrB-Dc-28EnUm4ONNp5_sFUvqrZjfFEMs1ElZYKuQbLgmuao___C7gYBv4ewI8I8I4PdsPZwW37DuiQ6H0LmrVEJYMb3_FPSffynfj1a9UatbldutM5J8RPGlZr9w");
		// site.addCookie("PLAY_SESSION","0eece363f8cb0a08775a5646951edb5c4688bf67-chsInfo=102a4255-43be-4432-a488-2783e5ca313c+premium_inmail_profile_upsell");
		// site.addCookie("lang","v=2^&lang=en-us");
		// site.addCookie("JSESSIONID","ajax:5571266308247153066");
		// site.addCookie("liap","true");
		// site.addCookie("li_at","AQEDARxstRYE3gasAAABW1xjvW4AAAFbXhsxbk4Al0jsqQBOYrgyCF0L3MQ0A6RD3o9d5ZcEKniwdK9lyoNs6PiZwtFBE9Q9U4rAaaJyqfiRS7B8w02lgLNFqJuOCL0yHxLc9FZKKNTtZP32a73pKR9Q");
		// site.addCookie("lidc","b=SB94:g=26:u=93:i=1491903692:t=1491959898:s=AQHfPtvzwT9wr-aAz2uvlBKSst9Fn3hp");
		// site.addCookie("RT","s=1491903693577^&r=https^%^3A^%^2F^%^2Fwww.linkedin.com^%^2Fuas^%^2Flogin^%^3FformSignIn^%^3Dtrue^%^26session_redirect^%^3D^%^252Fvoyager^%^252FloginRedirect.html");
		// site.addCookie("sdsc","22^%^3A1^%^2C1491903720907^%^7ECONN^%^2C0BA^%^2FPCC6aQudYGk1ykojXYHeXKR0^%^3D");
		// site.addCookie("_ga","GA1.2.1251577009.1475482809");
		// site.addCookie("_gat","1");
		// site.addCookie("_lipt","CwEAAAFbXGVECaXZHkOnD84CqQIer2KE9Hgcl88GfHx4zPKz4-Vg8f9QDzvtv34kSjnOJqL5JB7HNWMenSap9xx0QbRNUfKiaeTZ7YHjBvwraY5RbsVPfYD-H-TjKHged8vJhHjg3UTv0T8h2jUxzVlKsFgBdbDG4rRGdIh_D4CD9ZehHFwIXxxZkxL11vucYd4LLr9AwoUHhSGKw6Q-WKYKYOfLBfl6SPsaIPVaudQbCcYsemUnPaHpyKNjfoEBkz_ApHwV-z-ZYOSLAa47gsSI3RZw8byRirNFhQYDXrLhHirGJv0ULR8wl6Etl2WSRaj4TtsdRKWasbDlmcKWzK03rDFSrDa4gVn-AgWAhnB7QEsWuEbwJkyfI4mNoAvUyLR3jEMkQzwbLcikgH4gMDttp-fqfa8dm4NuE7rTsFbHVsWb6aFf4PUYnE9fZeTz91MgnSJlt_nvVa3HfILmIsnaOvKlZezItuwMdYikfEMIz3xuWGeauBQfO5h9wSsRvebxqo5qzDI_ALdy49rfX2GPrQGs8XbwgALhoj5fWOXZZxJG-SV_kjkPN4ewrSTClJC6-fmRFUugezDWzRffHB6rVGN98zja4o5LpSV8tDyPMzCOczMj-3OVv2dBuZ0");

		site.addHeader("referer", "https://people.wdf.sap.corp/search");
		site.setHttpProxy(new HttpHost("proxy.pal.sap.corp", 8080));
		// site.setHttpProxy(new HttpHost("proxy.sin.sap.corp", 8080));
		// site.setHttpProxy(new HttpHost("proxy.sha.sap.corp", 8080));
		// site.setHttpProxy(new HttpHost("proxy.fra.sap.corp", 8080));
	}

	@Override
	public void process(Page page) {
		if (profileProcessor == null) {
			profileProcessor = new APPageProcessor();
		}
		
		profileProcessor.process(page);
	}

	@Override
	public Site getSite() {
		return site;
	}

}
