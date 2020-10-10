package com.linkedin.automation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {

		// Jedis jedis_slave = new Jedis("127.0.0.1",6380);

		// 设置6379服务器为主节点,使得6380为从节点
		// jedis_slave.slaveof("127.0.0.1", 6379);

//		Integer[] industry = {1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148};		
//		List<Integer> nos = new ArrayList<Integer>();
//		for(int number : industry){
//			nos.add(number);
//		}
//		Collections.sort(nos);
//		
//		System.out.println(nos);

		String codesString = CommonSetting.cookieFilePrefix + "huntingfirmsPureCode.txt";

		File huntingFirmFile = new File(codesString);
		ObjectMapper mapper = new ObjectMapper();
		JavaType firmType = mapper.getTypeFactory().constructParametricType(Set.class, HuntingCompany.class);

		Set<HuntingCompany> huntingFirms = (Set<HuntingCompany>) mapper.readValue(huntingFirmFile, firmType);
		
		
		JavaType firmTypeList = mapper.getTypeFactory().constructParametricType(ArrayList.class, HuntingCompany.class);		
		List<HuntingCompany> huntingFirmList = (ArrayList<HuntingCompany>) mapper.readValue(huntingFirmFile, firmTypeList);
//		codesString = CommonSetting.cookieFilePrefix+"CFOPureURL.txt";
//		huntingFirmList.clear();
//		String[] keywords = { "HRD", "人力资源总监","HR BP", "HRBP", "HR AD", "HR Head", "HP VP", "HR GM ", "招聘负责人", "招聘总经理",
//				"招聘副总监", "招聘总监", "Associate HRD", "Human Resources Head", "GM Human Resources", "Human Resources VP",
//				"Human resources Associate Director", "Human resources Director", "Talent Acquisition VP",
//				"Talent Acquisition Director", "Talent Acquisition AD", "Talent Acquisition Associate Director" };
//		String[] industries = { "6", "7", "12", "15", "17", "18", "19", "20", "24", "25", "27", "41", "42", "43", "44",
//				"45", "46", "47", "106", "112", "116", "118", "119", "128", "129", "132" };
//		for (String keyword : keywords) {
//			for (String industry : industries) {
//				HuntingCompany hc = new HuntingCompany();
//				hc.setCode(keyword);
//				hc.setName(industry);
//				hc.setLink(false);
//				hc.setHasFinished(false);
//				huntingFirmList.add(hc);
//			}
//		}
//		mapper.writeValue(new File(codesString), huntingFirmList);

		String[] codes = { "32424387", "6659868", "33194776", "7985", "78268", "10841320", "19939", "6418642", "12585",
				"32298592", "3504639", "1274011", "14721294", "2572611", "3169190", "13423147", "77505", "18877505",
				"493928", "2095906", "277612", "667203", "14699735", "13366135", "1032632", "2818704", "24989",
				"3817624", "110968", "1043184", "29479614", "4858969", "13591926", "14508038", "14510220", "1594568",
				"1925539", "14430728", "1361424", "110968", "3538965", "667203", "513450", "2444036", "207796", "6254",
				"2562803", "14532625", "14653563", "14922", "10646867", "2875903", "3692219", "52091", "13612463",
				"14624617", "14384030", "122449", "48109", "298933", "690181", "2740428", "3047466", "3771696",
				"811718", "10339735", "4793801", "4612080", "10296922", "3292870", "250897", "13244203", "14706459",
				"12944517", "12925723", "14560394", "16204958", "13615526", "3720142", "1948419", "13610882", "3118347",
				"9244579", "13335440", "14497870", "6396864", "1245481", "32706590", "1590088", "2562745ID", "29379019",
				"13264332", "30294776", "13274243", "13352361", "35500224", "3079347", "55975", "22100", "999662",
				"14468318", "4850608", "3505194", "13323712", "6436157", "2835920", "165722", "165757", "879045",
				"256372", "164587", "28934", "3538965", "3538965", "12662333", "341192", "13742978", "10005088",
				"13340231", "3079347", "3515143", "3586308", "1707118", "3505194", "228650", "14699735", "13271464",
				"13463825", "30908419", "107990", "202782", "9461493", "10844327", "6616713", "3497068", "2882987" ,"31549139","32596357","1069520"
				,"1404985","14705855","31549139","305794","20548466","1274011"};
//		 String[]
//		 codes={"17977312","18273293","4826488","935469","4856382","13704739","9452261","32065130","13699091","3658476","18877505","134354","30060022","14432597","29962401","1620350","1069520","1069520","793329","29127437","332701","32451689","3530418","2695939","32202590","2095757","29832517","29422456","7934647","3326104","13713659","208554","29181483","13380267","13282868","308813","3282638","1695643","1695643","1386443","78268","10852261","4788574"};
		// String[]
		// codes={"3686466","10368249","10646867","2875903","2427732","277612","13713659","11051897","13271641","30025391","31636406","32513303","29719742","3787662","6447069","3346425","30307496","879333","5355380","2152","13231900","163441","5415","3975","5652","1799","2445407","14525729","3580297","31928326","13361968","13271266","1436369","13195075","9258614","2017837","28759","13403294","3109426","648493","13377305","1436369","1695643","2608524","121801","13195726","32260808","2790446","1028623","305250","2542096","3476","7802895","92528","1799","2312083","2203697","3958","628407","1416071","1043302","13199562","16174260","1912194","2714206","2883352","3271599","62435","1634465","14416","13194276","3682814","13623928","48109","486877","1156748","3486","383718","3007780","648493","12930337","21078","13250050","207172","1412840","3661528","10260899","2343","10702306","1865192","12918238","3658476","244408","2571141","2054236","13273170","235855","2904253","10128499","31917117","9414641","793329","2203697","10152780","3741","2170631","13205810","2307","1817855","2686492","13203421","12918338","992741","2841962","1883162","3647938","2153","436170","2903260","1094165","13186641","6279","13754163","2630395","9213353","3752417","16221181","1353051","2832506","702272","6431173","12925723","1016086","6412996","6436157","3265747","1925539","2154","10878873","2889","909824","3769934","51907","310611","1618335","208554","3504639","1346924","2969132","6636888","13615526","2608524","1389236","2695939","34843","218306","1992191","4788574","2688525","122449","10207056","10634239","39371","1281046","1659375","523589","16175","1891437","431437","2059733","4831665","3576631","366909","13395430","298933","13401427","343434","131707","3608552","14401883","2807309","10827281","2248867","69281","1028874","240403","2059733","157317","10367849","618961","3796446","9506598","13469266","3183250","676589","32513303","126452","10878873","3707210","2161304","34843","13253144","166244","1087283","13580979","3292041","134354","9436392","3853497","13244203","110968","29176768","893372","241173","6429708","13692522","2489623","1618687","2046853","2054236","919223","18171807","579523","13636478","493928","6448937","3682814","565624","18416204","3309395","1695643","3309395","7939748","4849899","7933457","2857988","7399","277318","2873827","1353051","3100476","356385","14450204","32820110","13306511","30871127","3799138","3755473","13306511","12948296","13636478","3673209","30570348","13401096","13281797","24091","2909651","18273293","1151061","488946","1652678","527297","3161771","6630140","1181294","2662232","3225824","29656123","13319068","4858969","16227746","3225824","29656123","13697977","32122965","1707118","32451689","240403"};
		for (String code : codes) {
			HuntingCompany hc = new HuntingCompany();
			hc.setCode(code);
			hc.setLink(false);
			hc.setHasFinished(false);
			huntingFirms.add(hc);
		}
		
		

		String[] urls = {
				// "https://www.linkedin.com/search/results/all/?keywords=ITerGet"
				// ,"https://www.linkedin.com/search/results/all/?keywords=浙江百易行人力资源服务有限公司"
				// ,https://www.linkedin.com/search/results/all/?keywords=上海泽方企业管理咨询有限公司
				// ,
		};
		// String[]
		// urls={"http://www.linkedin.com/search/results/people/?facetGeoRegion=%5B%22cn%3A8909%22%2C%22cn%3A8883%22%5D&keywords=AIMSEN&origin=FACETED_SEARCH","http://www.linkedin.com/search/results/index/?keywords=%E5%A4%A7%E7%80%9A&origin=GLOBAL_SEARCH_HEADER","http://www.linkedin.com/search/results/index/?keywords=%E9%AB%98%E5%87%A1&origin=GLOBAL_SEARCH_HEADER&page=1","http://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B%22840287%22%2C%222510861%22%2C%223226341%22%2C%223226342%22%5D&facetNetwork=%5B%22F%22%2C%22S%22%2C%22O%22%5D&keywords=job&origin=FACETED_SEARCH&page=1","http://www.linkedin.com/search/results/people/?facetGeoRegion=%5B%22cn%3A8909%22%2C%22cn%3A8883%22%5D&keywords=%E6%9F%90%E7%8C%8E%E5%A4%B4%E5%85%AC%E5%8F%B8&origin=GLOBAL_SEARCH_HEADER","http://www.linkedin.com/search/results/people/?facetGeoRegion=%5B%22cn%3A8909%22%2C%22cn%3A8883%22%5D&keywords=%E5%9F%83%E6%91%A9%E6%A3%AE&origin=FACETED_SEARCH","http://www.linkedin.com/search/results/people/?facetConnectionOf=%5B%22ACoAACXGEqkBgDYYPjyaD0OsgqGjgJOB1-Io6fE%22%5D&facetNetwork=%5B%22F%22%2C%22S%22%5D&keywords=%E7%8C%8E%E5%A4%B4&origin=GLOBAL_SEARCH_HEADER&page=17","http://www.linkedin.com/search/results/people/?facetConnectionOf=%5B\"ACoAABnEomkBE50ujVa1JEH87ZnOVkUfdjLLMXI\"%5D&facetNetwork=%5B\"S\"%2C\"O\"%5D&keywords=猎头&origin=GLOBAL_SEARCH_HEADER&page=9","http://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B%2212930337%22%5D&facetGeoRegion=%5B%22cn%3A8909%22%2C%22cn%3A8883%22%5D&facetNetwork=%5B%22S%22%2C%22O%22%5D&origin=FACETED_SEARCH&page=25","http://www.linkedin.com/search/results/index/?keywords=%E6%B8%A1%E8%8B%B1%E5%95%86%E5%8A%A1%E5%92%A8%E8%AF%A2","http://www.linkedin.com/search/results/index/?keywords=RESCODE%20Executive%20Search%20%E9%94%90%E5%BE%B7","http://www.linkedin.com/search/results/index/?keywords=%E8%80%80%E8%BF%9B","https://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B\"1028623\"%5D","http://www.linkedin.com/search/results/index/?keywords=北京睿和良木管理咨询有限公司&page=1","http://www.linkedin.com/search/results/index/?keywords=%E4%B8%8A%E6%B5%B7%E6%B1%87%E7%9D%BF%E4%BC%81%E4%B8%9A%E7%AE%A1%E7%90%86%E5%92%A8%E8%AF%A2%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8","http://www.linkedin.com/search/results/index/?keywords=%E4%B8%8A%E6%B5%B7%E6%99%BA%E7%94%9F%E9%81%93%E4%BA%BA%E6%89%8D%E5%92%A8%E8%AF%A2%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8","http://www.linkedin.com/search/results/index/?keywords=%E4%BC%AF%E5%91%A8%20%E7%8C%8E%E5%A4%B4&origin=GLOBAL_SEARCH_HEADER&page=1","http://www.linkedin.com/search/results/index/?keywords=startalents&page=1","https://www.linkedin.com/search/results/people/?keywords=奕普&origin=FACETED_SEARCH","http://www.linkedin.com/search/results/index/?keywords=%E6%8D%B7%E9%87%8C%E7%89%B9%E4%BC%81%E4%B8%9A%E7%AE%A1%E7%90%86%E5%92%A8%E8%AF%A2%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8","https://www.linkedin.com/search/results/index/?keywords=Yanboon&origin=GLOBAL_SEARCH_HEADER","https://www.linkedin.com/search/results/index/?keywords=Graceford","http://www.linkedin.com/search/results/people/?facetGeoRegion=%5B%22cn%3A8909%22%2C%22cn%3A8883%22%5D&keywords=%E4%B8%8E%E4%B8%BA%20%E7%8C%8E%E5%A4%B4&origin=GLOBAL_SEARCH_HEADER&page=1","http://www.linkedin.com/search/results/people/?facetGeoRegion=%5B%22cn%3A8909%22%2C%22cn%3A8883%22%5D&keywords=%E4%BC%AF%E7%89%99&origin=GLOBAL_SEARCH_HEADER","http://www.linkedin.com/search/results/index/?keywords=Collars%20Consultants&origin=GLOBAL_SEARCH_HEADER","https://www.linkedin.com/search/results/index/?keywords=苏州奥凡企业管理咨询有限公司","http://www.linkedin.com/search/results/index/?keywords=%E4%B8%8A%E6%B5%B7%E6%B3%BD%E6%96%B9%E4%BC%81%E4%B8%9A%E7%AE%A1%E7%90%86%E5%92%A8%E8%AF%A2%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8","http://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B%22310611%22%5D&facetGeoRegion=%5B%22cn%3A8909%22%2C%22cn%3A8883%22%5D&origin=FACETED_SEARCH&page=3","http://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B%22310611%22%5D&origin=FACETED_SEARCH","http://www.linkedin.com/search/results/people/?keywords=一合管理咨询有限公司&origin=GLOBAL_SEARCH_HEADER","http://www.linkedin.com/search/results/index/?keywords=Allegisbn&page=11","http://www.linkedin.com/search/results/people/?facetGeoRegion=%5B%22cn%3A0%22%5D&keywords=%E6%99%AE%E7%91%9E%E6%96%AF&origin=FACETED_SEARCH","http://www.linkedin.com/search/results/index/?keywords=%E8%89%BA%E5%AF%BB","http://www.linkedin.com/search/results/index/?keywords=Consultant%20at%20Devon-talent&origin=GLOBAL_SEARCH_HEADER","http://www.linkedin.com/search/results/people/?facetGeoRegion=%5B%22cn%3A8931%22%5D&keywords=%E9%AB%98%E7%BA%A7%20%E7%8C%8E%E5%A4%B4&origin=GLOBAL_SEARCH_HEADER","http://www.linkedin.com/search/results/index/?keywords=%E4%BB%95%E8%81%94%EF%BC%88%E4%B8%8A%E6%B5%B7%EF%BC%89%E4%BC%81%E4%B8%9A%E7%AE%A1%E7%90%86%E5%92%A8%E8%AF%A2%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8","http://www.linkedin.com/search/results/index/?keywords=%E4%B8%8A%E6%B5%B7%E6%89%8D%E6%98%93&page=1","http://www.linkedin.com/search/results/index/?keywords=%E4%B8%8A%E6%B5%B7%E6%8B%A9%E4%BB%95%E4%BC%81%E4%B8%9A%E7%AE%A1%E7%90%86%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8","http://www.linkedin.com/search/results/people/?facetGeoRegion=%5B%22cn%3A8909%22%5D&keywords=%E5%8A%A9%E7%90%86%E7%8C%8E%E5%A4%B4%E9%A1%BE%E9%97%AE&origin=FACETED_SEARCH&page=9","http://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B%22%22%5D&facetGeoRegion=%5B%22cn%3A8909%22%2C%22cn%3A8883%22%5D&facetNetwork=%5B%22S%22%2C%22O%22%5D&keywords=%E7%8C%8E%E5%A4%B4&origin=GLOBAL_SEARCH_HEADER&page=101","http://www.linkedin.com/search/results/people/?facetGeoRegion=%5B\"cn%3A8909\"%2C\"cn%3A8883\"%5D&facetNetwork=%5B\"O\"%5D&keywords=猎头&origin=FACETED_SEARCH&page=63","http://www.linkedin.com/search/results/index/?keywords=%E6%9F%AF%E7%BB%B4%E6%AD%A5%E6%A1%91","http://www.linkedin.com/search/results/index/?keywords=girro-consulting","http://www.linkedin.com/search/results/index/?keywords=%E4%B8%8A%E6%B5%B7%E4%BC%98%E7%8C%8E","https://www.linkedin.com/search/results/index/?keywords=TophunterHR&origin=GLOBAL_SEARCH_HEADER","http://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B%22143648%22%2C%223118347%22%2C%229748%22%2C%229506953%22%2C%22141419%22%2C%221209962%22%2C%221611103%22%2C%222632634%22%2C%222868300%22%2C%222880773%22%2C%223029983%22%2C%223667893%22%2C%22544765%22%2C%2254507%22%2C%22574466%22%5D&facetGeoRegion=%5B%22cn%3A8909%22%2C%22cn%3A8883%22%5D&origin=FACETED_SEARCH&page=1","http://www.linkedin.com/search/results/people/?facetGeoRegion=%5B%22cn%3A8909%22%5D&keywords=%E6%9D%AD%E5%B7%9E%E8%AF%97%E8%BF%88%E5%8C%BB%E8%8D%AF%E7%A7%91%E6%8A%80%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8&origin=FACETED_SEARCH&page=1","http://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B%22935469%22%5D&facetGeoRegion=%5B%22cn%3A8909%22%5D&origin=FACETED_SEARCH","http://www.linkedin.com/search/results/index/?keywords=%E5%86%80%E8%B6%8A%E5%92%A8%E8%AF%A2&lipi=urn%3Ali%3Apage%3Ad_flagship3_profile_view_base%3BmC9ORGBASSaDafz2CBWrsA%3D%3D&licu=urn%3Ali%3Acontrol%3Ad_flagship3_profile_view_base-background_details_company","http://www.linkedin.com/search/results/people/?facetGeoRegion=%5B%22cn%3A8909%22%5D&keywords=Nstarts%20Consultants%20Co.%2CLtd.&origin=FACETED_SEARCH","http://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B\"39371\"%5D&facetGeoRegion=%5B\"cn%3A8909\"%5D&origin=FACETED_SEARCH","http://www.linkedin.com/search/results/index/?keywords=示优企业管理","http://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B\"4856382\"%5D&facetGeoRegion=%5B\"cn%3A0\"%5D&keywords=Consultant&origin=FACETED_SEARCH","http://www.linkedin.com/search/results/index/?keywords=上海脉谷","https://www.linkedin.com/search/results/index/?keywords=寻英咨询","http://www.linkedin.com/search/results/index/?keywords=CareerElite","http://www.linkedin.com/search/results/index/?keywords=上海渡新","http://www.linkedin.com/search/results/index/?keywords=基石（上海）人才服务有限公司","http://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B%22126452%22%5D&facetGeoRegion=%5B%22cn%3A0%22%5D&facetNetwork=%5B%22S%22%2C%22O%22%5D&keywords=%E6%8B%9B%E8%81%98&origin=GLOBAL_SEARCH_HEADER&page=1","http://www.linkedin.com/search/results/people/?facetGeoRegion=%5B\"cn%3A8909\"%5D&keywords=猎益&origin=FACETED_SEARCH","http://www.linkedin.com/search/results/index/?keywords=对点咨询&origin=GLOBAL_SEARCH_HEADER","http://www.linkedin.com/search/results/people/?facetGeoRegion=%5B%22cn%3A0%22%5D&keywords=KNX-&origin=GLOBAL_SEARCH_HEADER","https://www.linkedin.com/search/results/people/?facetGeoRegion=%5B\"cn%3A0\"%5D&keywords=OPT%20Consultancy&origin=FACETED_SEARCH&page=1","https://www.linkedin.com/search/results/index/?keywords=深圳市高邦企业咨询管理有限公司","https://www.linkedin.com/search/results/index/?keywords=上海千汇","https://www.linkedin.com/search/results/index/?keywords=上海谷正咨询","https://www.linkedin.com/search/results/people/?facetGeoRegion=%5B\"cn%3A8909\"%5D&keywords=head%20hunting&origin=FACETED_SEARCH&page=45","https://www.linkedin.com/search/results/index/?keywords=金戈猎头&page=1","https://www.linkedin.com/search/results/index/?keywords=上海好伯人力资源有限公司","https://www.linkedin.com/search/results/people/?facetGeoRegion=%5B\"cn%3A8909\"%5D&keywords=Kuta&origin=FACETED_SEARCH","https://www.linkedin.com/search/results/people/?facetGeoRegion=%5B\"cn%3A8909\"%5D&keywords=协骏咨询&origin=FACETED_SEARCH","https://www.linkedin.com/search/results/index/?keywords=上海泽陈企业管理咨询有限公司","https://www.linkedin.com/search/results/index/?keywords=大连智博人才顾问有限公司","https://www.linkedin.com/search/results/index/?keywords=上海杰艾企业管理咨询有限公司","https://www.linkedin.com/search/results/index/?keywords=上海猎丰企业管理咨询有限公司","http://www.linkedin.com/search/results/index/?keywords=上海飞仕管理咨询有限公司","https://www.linkedin.com/search/results/index/?keywords=上海远志人力资源管理有限公司","http://www.linkedin.com/search/results/index/?keywords=博才世杰","http://www.linkedin.com/search/results/index/?keywords=百易行","http://www.linkedin.com/search/results/index/?keywords=上海复讯信息咨询有限公司","https://www.linkedin.com/search/results/people/v2/?keywords=%E4%BB%95%E8%81%94&origin=FACETED_SEARCH","https://www.linkedin.com/search/results/people/?facetGeoRegion=%5B\"cn%3A8939\"%5D&keywords=猎头&origin=FACETED_SEARCH&page=75","https://www.linkedin.com/search/results/people/?keywords=杜为尔&origin=FACETED_SEARCH","https://www.linkedin.com/search/results/people/?facetGeoRegion=%5B\"cn%3A0\"%5D&keywords=讯升&origin=FACETED_SEARCH","https://www.linkedin.com/search/results/index/?keywords=%E6%B1%9F%E8%8B%8F%E9%A2%86%E8%88%AA%E4%BA%BA%E6%89%8D%E5%BC%80%E5%8F%91%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8&page=1","https://www.linkedin.com/search/results/index/?keywords=Njorth%20consulting","https://www.linkedin.com/search/results/people/?facetGeoRegion=%5B\"cn%3A8909\"%5D&facetNetwork=%5B\"S\"%5D&keywords=招聘顾问&origin=FACETED_SEARCH&page=36","https://www.linkedin.com/search/results/people/?facetGeoRegion=%5B\"cn%3A8909\"%5D&facetNetwork=%5B\"O\"%5D&keywords=招聘顾问&origin=FACETED_SEARCH&page=1","https://www.linkedin.com/search/results/people/v2/?facetCurrentCompany=%5B%222327%22%2C%22383718%22%5D&facetGeoRegion=%5B%22cn%3A0%22%5D&facetNetwork=%5B%22S%22%5D&origin=FACETED_SEARCH&page=36","https://www.linkedin.com/search/results/people/v2/?facetCurrentCompany=%5B%222327%22%2C%22383718%22%5D&facetGeoRegion=%5B%22cn%3A0%22%5D&facetNetwork=%5B%22O%22%5D&origin=FACETED_SEARCH","http://www.linkedin.com/search/results/all/?keywords=Youroads%20Consulting%20Co.%2C%20Ltd"};
		for (String url : urls) {
			HuntingCompany hc = new HuntingCompany();
			hc.setUrl(url);
			hc.setLink(true);
			hc.setHasFinished(false);
			huntingFirms.add(hc);
		}

		// for(HuntingCompany firm:huntingFirms) {
		// firm.setHasFinished(false);
		// if(!StringUtils.isEmpty(firm.getUrl()))
		// {
		// firm.setLink(true);
		// }else {
		// firm.setLink(false);
		// }
		// }

		// Iterator<HuntingCompany> it = huntingFirms.iterator();
		// while (it.hasNext()) {
		// HuntingCompany firm = it.next();
		// System.out.println(firm.getUrl());
		// if (!firm.isLink()) {
		// it.remove();
		// }
		// }

		List<HuntingCompany> orderList = new ArrayList<HuntingCompany>();
		orderList.addAll(huntingFirms);
		Collections.sort(orderList,
				(final HuntingCompany firm1, final HuntingCompany firm2) -> firm1.getCode().compareTo(firm2.getCode()));

		mapper.writeValue(new File(codesString), huntingFirms);
		// mapper.writeValue(huntingFirmFile, huntingFirms);
	}

}
