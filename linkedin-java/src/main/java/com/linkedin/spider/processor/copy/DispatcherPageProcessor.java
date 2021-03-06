package com.linkedin.spider.processor.copy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

@Service
public class DispatcherPageProcessor implements PageProcessor {

	@Autowired
	private LinkedinPeopleProfilePageProcessorWithoutPersist profileProcessor=new LinkedinPeopleProfilePageProcessorWithoutPersist();
	@Autowired
	private LinkedInPeopleSearchPageProcessor searchProcessor=new LinkedInPeopleSearchPageProcessor();

	private Site site = Site.me().setRetryTimes(3).setSleepTime(15000).setTimeOut(10000);

	public DispatcherPageProcessor() {
		site.setCharset("UTF-8");
		site.addHeader("accept-encoding", "gzip, deflate, sdch, br");
		site.addHeader("accept-language", "zh-CN,zh;q=0.8");
		site.addHeader("upgrade-insecure-requests", "1");
		site.addHeader("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36");
		site.addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		site.addHeader("cache-control", "max-age=0");
		site.addHeader("avail-dictionary", "YFA7RCTS");
		site.addHeader("authority", "www.linkedin.com");
		site.addHeader("cookie",
				"bcookie=\"v=2^&9fbee1ca-79a5-47cc-8810-eb18e753ff44\"; bscookie=\"v=1^&201610010831302ee66e54-22ff-4608-84ac-0f2f7511eea0AQHT-9IP6dXurZuyOFX72-6doqjLS_kN\"; visit=\"v=1^&M\"; SID=4b5771bc-b4f9-4836-a8f4-74bcc65d9517; VID=V_2016_12_12_01_217755; share_setting=PUBLIC; hc_survey=true; _cb_ls=1; _chartbeat2=B4SLZSCbyTk6B5AWo_.1479287732844.1491303514668.0000000000000001; ELOQUA=GUID=F341FA27D0614A4DAA111812E8DBBAF9; transaction_state=AgHB5iQXCxfKfAAAAVtbf0UL6j9y9bB2zlhMbJIZoKwQN5PdJRdW5QElhMD1z0XxEjO85zKBmAJxOCrB-Dc-28EnUm4ONNp5_sFUvqrZjfFEMs1ElZYKuQbLgmuao___C7gYBv4ewI8I8I4PdsPZwW37DuiQ6H0LmrVEJYMb3_FPSffynfj1a9UatbldutM5J8RPGlZr9w; PLAY_SESSION=0eece363f8cb0a08775a5646951edb5c4688bf67-chsInfo=102a4255-43be-4432-a488-2783e5ca313c+premium_inmail_profile_upsell; _gat=1; sl=\"v=1^&CGPcs\"; lang=\"v=2^&lang=en-us\"; li_at=AQEDARxstRYB_bQgAAABW64zPf4AAAFbr-qx_lYAT6fp37oEhLsOAZRMmlYzb_BvcuA1XBSBEH4ry5DhYHAqOkfBw1T36fKFIfM0swpW6YUsVokVa4i0LLFnBh8tf59bzTqHEZFfAHLsZuzHRkI8pcaE; liap=true; JSESSIONID=\"ajax:9214715271338863689\"; lidc=\"b=SB94:g=27:u=102:i=1493276245:t=1493352649:s=AQFehbqYfFpe9uHtNODV7GJyhXlK9dsU\"; RT=s=1493276246509^&r=https^%^3A^%^2F^%^2Fwww.linkedin.com^%^2Fuas^%^2Flogin-submit; _ga=GA1.2.1251577009.1475482809; _lipt=CwEAAAFbrjNJXBkoV-AU86KkribsNBHvgdrxNqyiIk1OL6p4IcMBso_QQB67ZPaEvzpYFYVfkKN8KonDm_sOLnEeUGc6DQQWImYEneYUF0u_DgRvt3SPBlP4-s8lVS9SPhyJVWiM77WQXSPVmoWxGX7TwGi2jWNydvnPwXwM5APoP1e7o3P9p4NozxYOVSw60jgCDEQ32kjm4eSMiQ1kd9rdYsOgCuQbbYunpuyjiSSyfnCwjeNQLvGAeClbhJNZ5tW76XdxrENOFHnMk5R7eSaWGNyBXD5LqI7FIXqZy5xJEbnf48NxVgAjKZDfyaUfz7CodQGEVXZ5BG7AKebuJqcuK4kttHF4AiZxUEb_dX9Bn2nsrOPvVWRpLOqzya5JUeX2fFKwYNRSdqzIc4mNCKW2Ayor-I8IoxF38AWSpZwUZqQ8DGeb5qqW8dj_TyH2rmC1fqqfMs5h-UhRWuxGzHlNduDNnoI2z_KUnLjpP5J4qpaiMO2ItdRckCHA1g6S1yHu4i_M4RUxWkHbxyoNNnS5IhGwEGu8et4qXvUX4VJgfLfo62okrFWtxRLQTUb4NxAvq9xIoPdttDYXgqrVrU7jMayEM9tm5atNLrqsF5EXK0uWdYwXuKAH-mQDaTquWKs; sdsc=1^%^3A1SZM1shxDNbLt36wZwCgPgvN58iw^%^3D");
		site.addHeader("referer", "https://www.linkedin.com/");
		site.addHeader("origin", "https://www.linkedin.com");
		
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
		// site.setHttpProxy(new HttpHost("proxy.pal.sap.corp", 8080));
		// site.setHttpProxy(new HttpHost("proxy.sin.sap.corp", 8080));
		// site.setHttpProxy(new HttpHost("proxy.sha.sap.corp", 8080));
		// site.setHttpProxy(new HttpHost("proxy.fra.sap.corp", 8080));
	}

	@Override
	public void process(Page page) {
		if (profileProcessor == null) {
			profileProcessor = new LinkedinPeopleProfilePageProcessorWithoutPersist();
			searchProcessor = new LinkedInPeopleSearchPageProcessor();
		}
		
		if (page.getUrl().toString().matches("\\w+://www.linkedin.com/in/[^/]+/")
				|| page.getUrl().toString().matches("\\w+://www.linkedin.com/in/[^/]+")) {
			profileProcessor.process(page);
		} else {
			searchProcessor.process(page);
		}
		
		LinkedinPage lpage = (LinkedinPage) page;
		lpage.getWebDriverPool().returnToPool(lpage.getWebDriver());
	}

	@Override
	public Site getSite() {
		return site;
	}

}
