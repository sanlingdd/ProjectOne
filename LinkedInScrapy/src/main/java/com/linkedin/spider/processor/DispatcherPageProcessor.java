package com.linkedin.spider.processor;

import org.apache.http.HttpHost;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class DispatcherPageProcessor implements PageProcessor {

	private LinkedinPeopleProfilePageProcessor profileProcessor = new LinkedinPeopleProfilePageProcessor();
	private LinkedInPeopleSearchPageProcessor searchProcessor = new LinkedInPeopleSearchPageProcessor();

	private Site site = Site.me().setRetryTimes(3).setSleepTime(15000).setTimeOut(10000);

	public DispatcherPageProcessor() {
		site.setCharset("UTF-8");
		site.addHeader("accept-encoding", "gzip, deflate, sdch, br");
		site.addHeader("accept-language", "zh-CN,zh;q=0.8");
		site.addHeader("upgrade-insecure-requests", "1");
		site.addHeader("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
		site.addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		site.addHeader("cache-control", "max-age=0");
		site.addHeader("avail-dictionary", "YFA7RCTS");
		site.addHeader("authority", "www.linkedin.com");
		site.addHeader("cookie",
				"bcookie=\"v=2^&9fbee1ca-79a5-47cc-8810-eb18e753ff44\"; bscookie=\"v=1^&201610010831302ee66e54-22ff-4608-84ac-0f2f7511eea0AQHT-9IP6dXurZuyOFX72-6doqjLS_kN\"; visit=\"v=1^&M\"; SID=4b5771bc-b4f9-4836-a8f4-74bcc65d9517; VID=V_2016_12_12_01_217755; share_setting=PUBLIC; hc_survey=true; _cb_ls=1; _chartbeat2=B4SLZSCbyTk6B5AWo_.1479287732844.1491303514668.0000000000000001; ELOQUA=GUID=F341FA27D0614A4DAA111812E8DBBAF9; transaction_state=AgHB5iQXCxfKfAAAAVtbf0UL6j9y9bB2zlhMbJIZoKwQN5PdJRdW5QElhMD1z0XxEjO85zKBmAJxOCrB-Dc-28EnUm4ONNp5_sFUvqrZjfFEMs1ElZYKuQbLgmuao___C7gYBv4ewI8I8I4PdsPZwW37DuiQ6H0LmrVEJYMb3_FPSffynfj1a9UatbldutM5J8RPGlZr9w; PLAY_SESSION=0eece363f8cb0a08775a5646951edb5c4688bf67-chsInfo=102a4255-43be-4432-a488-2783e5ca313c+premium_inmail_profile_upsell; _ga=GA1.2.1251577009.1475482809; lang=\"v=2^&lang=en-us\"; lidc=\"b=SB94:g=27:u=102:i=1493100038:t=1493179810:s=AQHDdNGRN2_etOXqm9ILIBphdIpXcUg1\"; li_at=AQEDARxstRYFzFlsAAABW6Oyhz0AAAFbqXGUGFYAcV3onzQNUUPk8IFyXojVRU9h3kXCbt1ocM4UP5-KQ1omPRoq_Bi8wRpOHjzn89bfvE4ACfS69YaFlJu1S_0SIcR079sBaIYFhpwwCt07cw8EZnMD; liap=true; sl=\"v=1^&8w-DY\"; JSESSIONID=\"ajax:6444178337454793968\"; sdsc=22^%^3A1^%^2C1493171797465^%^7ECONN^%^2C0Jzvdxf42RAA^%^2BmQAc8aeUsmhWiaw^%^3D; _lipt=CwEAAAFbqB-Skmxo1PdlEJx5D2Mcon-oafhfxXwGi46xWTm0KuiTFHLUApzeSFb9n2xk5oXF-QhwrRl50h3NII8zFOe6ZXp2Rsn5uUWNip4SxsQwgCK7cz76iKTdtsRXYgCBNFLsAnuBRTaiWJFX4891OkrWFJoj2AUK3fnYlIsg6AfaL1JGS_LUR2x6KdTBiI7oPU6i0IXuctXv2OCz5ClNfuCR_IoMuVhosWh5ln_ZQbDJcsZBplgOirey4lcwae3TWog4XZGhXU0M_f1fy1YxOCV8LLqkBgCHJYHin-z2wtriuSN69z8YLGbWHkKFxBm0_j4DujteJHuyo0KCVWkxgZtslEccPT1yMtCkdvKoKOO9ME2-tRf8V8blONr2FB2Zo7Pb5l3TS0tw9W_4Iwf_6ix4klo71ITay72qYx87EoBKhg9FadeUaeX4eeHVwZ-TDyuDF39fmbqouQDuCE-7m6TJYOBl1p_3OXq94bG1q7M969YJ7QKk9rUraLRb0doEojljxMADvjweq43MZRSfydVsxe8u2I9yycq4M2L6YSY1CNDZBm2xgHvUvMcsK0Q83cE96V4wdF_cgIRCseXBxpvQ5pPJGUZ2Kzc6sxPcbWKe");

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

		site.addHeader("referer", "https://www.linkedin.com/");
		site.addHeader("origin", "https://www.linkedin.com");
		site.setHttpProxy(new HttpHost("proxy.pal.sap.corp", 8080));
		// site.setHttpProxy(new HttpHost("proxy.sin.sap.corp", 8080));
		// site.setHttpProxy(new HttpHost("proxy.sha.sap.corp", 8080));
		// site.setHttpProxy(new HttpHost("proxy.fra.sap.corp", 8080));
	}

	@Override
	public void process(Page page) {
		if (page.getUrl().toString().matches("\\w+://www.linkedin.com/in/[^/]+/")
				|| page.getUrl().toString().matches("\\w+://www.linkedin.com/in/[^/]+")) {
			profileProcessor.process(page);
		} else {
			searchProcessor.process(page);
		}
	}

	@Override
	public Site getSite() {
		return site;
	}

}
