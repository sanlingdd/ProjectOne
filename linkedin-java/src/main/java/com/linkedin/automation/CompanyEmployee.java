package com.linkedin.automation;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CompanyEmployee {

	public static void main(String[] args) throws InterruptedException, JsonParseException, JsonMappingException, IOException {
		//展动力
		//String[] abcs = { "305250" };
		// 锐仕方达
		//String[] abcs = { "2542096" };
		// micheal pa
		//String[] abcs = { "3476" };
		// 英迈
		// String[] abcs = { "7802895" };
		// manpower	
		//String[] abcs = { "92528","1799","2312083","2203697","3958","628407","1416071","1043302","13199562","16174260","1912194","2714206","2883352","3271599"};
		// 科锐国际
		//String[] abcs = { "62435" };
		// 外企德科fesco
		//String[] abcs = { "1634465" };
		
		// 外企K2
		// String[] abcs = { "14416" };
		// 上海卿百
		// String[] abcs = { "13194276" };
		// 上海Best Gear，博炎猎头
		// String[] abcs = { "3682814" };
		// CGL
		// String[] abcs = { "13623928" };
		// 科瑞福克斯
		// String[] abcs = { "48109" };
		// 伯乐
		// String[] abcs = {"486877"};
		// new ferry 1412840
		// String[] abcs = {"1412840"};
		// mongo
		// String[] abcs = { "1156748" };
		// Hays
		// String[] abcs = { "3484","3486" };
		// ranstand 任仕达
		//String[] abcs = { "2327","383718" };
		// jobwell
		// String[] abcs = {"3007780"};
		// BRecuit英智 648493
		// String[] abcs = {"648493"};
		// 铜雀
		// String[] abcs = {"12930337"};
		// lloyd morgan
		// String[] abcs= {"2572611","21078"};
		// 13250050 eHire
		// String[] abcs= {"13250050"};
		// seeker
		// String[] abcs= {"207172"};
		// newFerry
		// String[] abcs= {"1412840"};
		// newFerry
		// String[] abcs= {"3661528"};
		// 麦星
		// String[] abcs= {"10260899"};
		// Hundson
		// String[] abcs= {"2343"};
		// 10702306 沃銳
		// String[] abcs = {"10702306"};
		// talent engine
		// String[] abcs = {"1865192"};
		// 慧恩
		// String[] abcs = {"12918238"};
		// ushi talent
		// String[] abcs = {"3658476"};
		// onxion
		// String[] abcs = {"244408"};
		// proMatrix & CO. 2571141
		// String[] abcs = {"2571141"};
		// Sillinfo 2054236
		// String[] abcs = {"2054236"};
		// 瀚雨人力资源 13273170
		// String[] abcs = {"13273170"};
		// Profile
		// String[] abcs = {"35312","121801","235855"};
		// talent bank
		// String[] abcs = {"2904253"};
		// MooreElite
		// String[] abcs = {"10128499"};
		// 兴星人才
		// String[] abcs = {"31917117"};
		// GraceFold
		// String[] abcs = {"9414641"};
		// Experies
		// String[] abcs = {"2203697"};
		// people key 793329
		// String[] abcs = {"2203697"};
		// 奕普
		// String[] abcs = {"10152780"};
		// FutureStep 光辉国际
		// String[] abcs = {"3741"};
		// innobe
		// String[] abcs = {"272972"};
		// Great Career
		// String[] abcs = {"2170631"};
		// Fyte Morgan philiphs
		// String[] abcs = {"13205810"};
		// Kelly Service
		// String[] abcs = {"2307","1817855"};
		// DTJ International
		// String[] abcs = {"2686492"};
		// MARS2
		// String[] abcs = {"13203421"};
		// String[] abcs = {"12918338"};
		// bird
		// String[] abcs = {"992741"};
		// Falcon
		// String[] abcs = {"2841962"};
		// urCareer
		// String[] abcs = {"1883162","3647938"};
		// Allegis Group
		// String[] abcs = {"2153"};
		// G&E
		// String[] abcs = {"436170"};
		// WITS
		// String[] abcs = {"2903260"};
		// dowwell
		// String[] abcs = {"1094165"};
		// FA Talent
		// String[] abcs = {"13186641"};
		// Antal
		// String[] abcs = {"6279","13754163","2630395","9213353","3752417","16221181"};
		// faster consultanting
		// String[] abcs = {"1353051"};
		// ZW 2832506
		// String[] abcs = {"2832506"};
		// FMC 702272
		// String[] abcs = {"702272"};
		// Reforce 6431173
		// String[] abcs = {"6431173"};
		// 亨特尔 12925723
		// String[] abcs = {"12925723"};
		// 顶才猎头 1016086
		// String[] abcs = {"1016086"};
		// Standard management
		// String[] abcs = {"6412996"};
		// Morning management
		// String[] abcs = {"6436157"};
		// RPOcn HR
		// String[] abcs = {"3265747"};
		// Versal 1925539
		// String[] abcs = {"1925539"};
		// Aston Carter
		// String[] abcs = {"13153","2154"};
		// Comrise
		// String[] abcs = {"14651","10878873"};
		// Aerotek
		// String[] abcs = {"2889"};
		// Talent Spot
		// String[] abcs = {"909824"};
		// Pioneer 3769934
		// String[] abcs = {"3769934"};
		// Global Associate
		// String[] abcs = {"51907"};
		// KingFisher
		// String[] abcs = {"310611"};
		// JWS 223265747
		// Magic 1618335
		// String[] abcs = {"1618335"};
		// DaCare
		// String[] abcs = {"208554"};
		// forward
		// String[] abcs = {"3504639"};
		// Spring 1346924
		// String[] abcs = {"1346924"};
		// Chapter
		// String[] abcs = {"2969132"};
		// 智邦集团
		// String[] abcs = {"6636888"};
		// LiNK
		// String[] abcs = {"13615526"};
		// talent point
		// String[] abcs = {"2608524"};
		// VIP Hunter 1389236
		// String[] abcs = {"1389236"};
		// Career Fog 2695939
		// String[] abcs = {"2695939"};
		// HRNetOne
		// String[] abcs = {"34843"};
		// Faro 218306
		// String[] abcs = {"218306"};
		// GeSeeker 1992191
		// String[] abcs = {"1992191"};
		// Table Base 4788574
		// String[] abcs = {"4788574"};
		// We Can 2688525
		// String[] abcs = {"2688525"};
		// Talent Power 122449
		// String[] abcs = {"122449"};
		// Partner One 10207056
		// String[] abcs = {"10207056"};
		// Jobmet
		// String[] abcs = {"10634239"};
		// PeopleSearch 39371
		// 39371
		// Talent center 1281046
		// String[] abcs = {"1281046"};
		// Executive Search Firm 1659375
		// sonder 523589
		// String[] abcs = {"523589"};
		// Morgan McKinley 16175
		// String[] abcs = {"16175"};
		// 目前就职: Isaac&Kenneth 1891437
		// String[] abcs = {"1891437"};
		// Belink Partner 431437
		// String[] abcs = {"431437"};
		// Horbo Talent
		// String[] abcs = {"2059733"};
		// IVS 4831665
		// String[] abcs = {"4831665"};
		// Mijobs
		// String[] abcs = {"3576631"};
		// Angstrom International
		// String[] abcs = {"366909"};
		// 猎诺 13395430
		// String[] abcs = {"13395430"};
		// New Haven 298933
		// String[] abcs = {"298933"};
		// 与为 猎头 13401427
		// String[] abcs = {"13401427"};
		// Talent One
		// String[] abcs = {"343434"};
		// MTS 131707
		// String[] abcs = {"131707"};
		// Accurman
		// String[] abcs = {"3608552"};
		// Zenithope Consulting
		// String[] abcs = {"14401883"};
		// Bridge International Associates
		// String[] abcs = {"2807309"};
		// Consultant of Wisbond Human Resource
		// String[] abcs = {"10827281"};
		// JAC Recruitment
		// String[] abcs = {"2248867","69281"};
		// Allways Consulting
		// String[] abcs = {"1028874"};
		// KTHR
		// String[] abcs = {"240403"};
		// Horbo Talent Solutions
		// String[] abcs = {"2059733"};
		// Spencer Stuart
		// String[] abcs = {"157317"};
		// Stanley Executive Search
		// String[] abcs = {"10367849"};
		// JWard Group
		// String[] abcs = {"618961"};
		// Bettermore Consulting
		// String[] abcs = {"3796446"};
		// COPETUS
		// String[] abcs = {"9506598"};
		// Kuta Hunting
		// String[] abcs = {"13469266"};
		// MNC-Management
		// String[] abcs = {"3183250"};
		// Nstarts Consultants
		//String[] abcs = { "676589" };
		// 深圳市一览网络股份有限公司
		//String[] abcs = {"32513303"};
		// 软通动力
		//String[] abcs = {"126452"};
		//Comrise Asia
		//String[] abcs = {"10878873"};	
		//肯耐珂萨
		//String[] abcs = {"3707210"};
		//PLINK CONSULTING
		//String[] abcs = {"2161304"};
		//目前就职: HRnet One - HRnetOne Associate Consultant
		//String[] abcs = {"34843"};
		//rui fan
		//String[] abcs = {"13253144"};
		
		//Talent2 - Researcher
		//String[] abcs = {"166244"};	
		//Cesna 
		//String[] abcs = {"1087283"};	
		//目前就职: BetterThink
		//String[] abcs = {"13580979"};
		//open hunt
		//String[] abcs = {"3292041"};
		//Global Executive Consultants Ltd
		//String[] abcs = {"134354"};
		//J.H. Partners (Asia) Company Limited 
		//String[] abcs = {"9436392"};
		//CarmelTop Limited
		//String[] abcs = {"3853497"};
		//Leader Associates
		//String[] abcs = {"13244203"};
		//目前就职: Access People - Associate Consultant
		//
		//String[] abcs = {"110968"};
		//上海阳夏企业管理咨询有限公司 
		//String[] abcs = {"29176768"};
		//Links International - HR & Finance Manager
		//String[] abcs = {"893372"};
		//Career Mentors 
		//String[] abcs = {"241173"};
		//WORK Consulting 
		//String[] abcs = {"2909651","6429708"};
		//DOX
		//String[] abcs = {"881911","13692522"};
		//YiYong Executive Search 
		//String[] abcs = {"2489623"};		
		//Terranss Consulting 
		//String[] abcs = {"1618687"};	
		//JPlusHR(杰普乐仕) - CMO
		//String[] abcs = {"2046853"};	
		//Sillinfo 
		//String[] abcs = {"2054236"};
		//Wide-Wise HR Consulting 919223
		//String[] abcs = {"919223","18171807"};
		//Career Magic Strategic Recruitment Solutions
		//String[] abcs = {"579523"};
		//上海仁联人力资源有限公司
		//String[] abcs = {"18243697","13636478"};
		//ProKing Management consulting 
		//String[] abcs = {"493928"};
		//Huntsor International 
		//String[] abcs = {"6448937"};		
		//Bestgear Consulting 
		//String[] abcs = {"3682814"};
		//Eazycome
		//String[] abcs = {"7934647","9375664","565624"};
		//Talent Lead 
		//String[] abcs = {"2605211","18416204"};
		//Manfield 
		//String[] abcs = {"3309395"};	
		//Selegate Consulting Corp 1695643
		//String[] abcs = {"3309395"};	
		//Kupin Talent 
		//String[] abcs = {"7939748","4849899","7933457"};	
		//CGP 
		//String[] abcs = {"2857988"};	
		//PSD 
		//String[] abcs = {"7399"};
		//Jobmet 
		//String[] abcs = {"277318"};
		//CIIC Talent Consulting Ltd 
		//String[] abcs = {"2873827"};	
		//FASTER Consulting 
		//String[] abcs = {"1353051"};	
		//CareerBays 
		//String[] abcs = {"3100476"};
		//MPS China
		//String[] abcs = {"356385","14450204"};
		//上海科芮企业管理咨询有限公司 
		//String[] abcs = {"32820110","13306511"};
		// 南京万宝跃华企业管理咨询有限公司
		//String[] abcs = {"30871127","3799138","3755473"};
		//上海环搜投资咨询有限公司
		//String[] abcs = {"13306511","12948296"};
		//上海聚仕人才咨询有限公司
		//String[] abcs={"13636478"};
		
		//才集猎头
        //String[] abcs={"3673209","30570348","13401096","13281797","24091"};
		//WORK HRS China
		//String[] abcs={"2909651","18273293","1151061"};	
		//String[] abcs={"488946","1652678","527297"};	
		//Oasis Recruiting Consultancy
		//String[] abcs={"3161771","6630140","1181294","2662232","3225824"};
		//上海聚仕人才咨询有限公司
		//String[] abcs={"29656123","13319068"};
		//String[] abcs={"4858969","16227746","3225824"};

		//GlobalCareerPath
		//String[] abcs={"29656123","13697977","32122965"};
		
		String[] abcs={"1707118","32451689","240403"};
		
		if (args.length == 0) {
			args = abcs;
		}

		StringBuilder argsStr = new StringBuilder("");
		for (int iter = 0; iter < args.length - 1; iter++) {
			argsStr.append("\"").append(args[iter]).append("\"").append("%2C");
		}
		
		argsStr.append("\"").append(args[args.length - 1]).append("\"");
		
				
		String company = "http://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B" + argsStr.toString()
		+ "%5D&facetGeoRegion=%5B\"cn%3A0\"%5D&facetNetwork=%5B%22S%22%2C%22O%22%5D&origin=FACETED_SEARCH&page=1";
		
//		String company = "http://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B" + argsStr.toString()
//				+ "%5D&facetGeoRegion=%5B\"cn%3A8911\"%2C\"cn%3A8905\"%5D&facetNetwork=%5B%22S%22%2C%22O%22%5D&origin=FACETED_SEARCH&page=1";
		// AIMSEN
		// company =
		// "http://www.linkedin.com/search/results/people/?facetGeoRegion=%5B%22cn%3A8909%22%2C%22cn%3A8883%22%5D&keywords=AIMSEN&origin=FACETED_SEARCH";
		// 大翰
		// company =
		// "http://www.linkedin.com/search/results/index/?keywords=%E5%A4%A7%E7%80%9A&origin=GLOBAL_SEARCH_HEADER";
		// 高凡
		// company =
		// "http://www.linkedin.com/search/results/index/?keywords=%E9%AB%98%E5%87%A1&origin=GLOBAL_SEARCH_HEADER&page=1";
		// morgan phillips
		// company =
		// "http://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B%22840287%22%2C%222510861%22%2C%223226341%22%2C%223226342%22%5D&facetNetwork=%5B%22F%22%2C%22S%22%2C%22O%22%5D&keywords=job&origin=FACETED_SEARCH&page=1";
		// 某猎头公司
		// company =
		// "http://www.linkedin.com/search/results/people/?facetGeoRegion=%5B%22cn%3A8909%22%2C%22cn%3A8883%22%5D&keywords=%E6%9F%90%E7%8C%8E%E5%A4%B4%E5%85%AC%E5%8F%B8&origin=GLOBAL_SEARCH_HEADER";
		// 埃莫森
		// company =
		// "http://www.linkedin.com/search/results/people/?facetGeoRegion=%5B%22cn%3A8909%22%2C%22cn%3A8883%22%5D&keywords=%E5%9F%83%E6%91%A9%E6%A3%AE&origin=FACETED_SEARCH";
		// Singing zhou 好友
		// company =
		// "http://www.linkedin.com/search/results/people/?facetConnectionOf=%5B%22ACoAACXGEqkBgDYYPjyaD0OsgqGjgJOB1-Io6fE%22%5D&facetNetwork=%5B%22F%22%2C%22S%22%5D&keywords=%E7%8C%8E%E5%A4%B4&origin=GLOBAL_SEARCH_HEADER&page=17";
		// Lily 好友
		// company =
		// "http://www.linkedin.com/search/results/people/?facetConnectionOf=%5B\"ACoAABnEomkBE50ujVa1JEH87ZnOVkUfdjLLMXI\"%5D&facetNetwork=%5B\"S\"%2C\"O\"%5D&keywords=猎头&origin=GLOBAL_SEARCH_HEADER&page=9";
		// company =
		// "http://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B%2212930337%22%5D&facetGeoRegion=%5B%22cn%3A8909%22%2C%22cn%3A8883%22%5D&facetNetwork=%5B%22S%22%2C%22O%22%5D&origin=FACETED_SEARCH&page=25";
		// 杜英
		// company =
		// "http://www.linkedin.com/search/results/index/?keywords=%E6%B8%A1%E8%8B%B1%E5%95%86%E5%8A%A1%E5%92%A8%E8%AF%A2";
		// Rui De
		// company =
		// "http://www.linkedin.com/search/results/index/?keywords=RESCODE%20Executive%20Search%20%E9%94%90%E5%BE%B7";
		// 跃进
		// company =
		// "http://www.linkedin.com/search/results/index/?keywords=%E8%80%80%E8%BF%9B";
		// Fortune Career
		// company =
		// "https://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B\"1028623\"%5D";
		// 背景瑞和
		// company =
		// "http://www.linkedin.com/search/results/index/?keywords=北京睿和良木管理咨询有限公司&page=1";
		// 上海汇锐
		// company =
		// "http://www.linkedin.com/search/results/index/?keywords=%E4%B8%8A%E6%B5%B7%E6%B1%87%E7%9D%BF%E4%BC%81%E4%B8%9A%E7%AE%A1%E7%90%86%E5%92%A8%E8%AF%A2%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8";
		// 上海智生
		// company =
		// "http://www.linkedin.com/search/results/index/?keywords=%E4%B8%8A%E6%B5%B7%E6%99%BA%E7%94%9F%E9%81%93%E4%BA%BA%E6%89%8D%E5%92%A8%E8%AF%A2%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8";
		// 伯周
		// company =
		// "http://www.linkedin.com/search/results/index/?keywords=%E4%BC%AF%E5%91%A8%20%E7%8C%8E%E5%A4%B4&origin=GLOBAL_SEARCH_HEADER&page=1";
		// star talent
		// company =
		// "http://www.linkedin.com/search/results/index/?keywords=startalents&page=1";
		// 奕普
		// company =
		// "https://www.linkedin.com/search/results/people/?keywords=奕普&origin=FACETED_SEARCH";
		// company =
		// "http://www.linkedin.com/search/results/index/?keywords=%E6%8D%B7%E9%87%8C%E7%89%B9%E4%BC%81%E4%B8%9A%E7%AE%A1%E7%90%86%E5%92%A8%E8%AF%A2%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8";
		// company =
		// "https://www.linkedin.com/search/results/index/?keywords=Yanboon&origin=GLOBAL_SEARCH_HEADER";
		// graceford
		// company =
		// "https://www.linkedin.com/search/results/index/?keywords=Graceford";
		// 与为
		// company =
		// "http://www.linkedin.com/search/results/people/?facetGeoRegion=%5B%22cn%3A8909%22%2C%22cn%3A8883%22%5D&keywords=%E4%B8%8E%E4%B8%BA%20%E7%8C%8E%E5%A4%B4&origin=GLOBAL_SEARCH_HEADER&page=1";
		// 伯牙
		// company =
		// "http://www.linkedin.com/search/results/people/?facetGeoRegion=%5B%22cn%3A8909%22%2C%22cn%3A8883%22%5D&keywords=%E4%BC%AF%E7%89%99&origin=GLOBAL_SEARCH_HEADER";
		// collars
		// company =
		// "http://www.linkedin.com/search/results/index/?keywords=Collars%20Consultants&origin=GLOBAL_SEARCH_HEADER";
		// 奥凡
		// company =
		// "https://www.linkedin.com/search/results/index/?keywords=苏州奥凡企业管理咨询有限公司";
		// company =
		// "http://www.linkedin.com/search/results/index/?keywords=%E4%B8%8A%E6%B5%B7%E6%B3%BD%E6%96%B9%E4%BC%81%E4%B8%9A%E7%AE%A1%E7%90%86%E5%92%A8%E8%AF%A2%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8";
		// company =
		// "http://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B%22310611%22%5D&facetGeoRegion=%5B%22cn%3A8909%22%2C%22cn%3A8883%22%5D&origin=FACETED_SEARCH&page=3";
		// company =
		// "http://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B%22310611%22%5D&origin=FACETED_SEARCH";
		// company =
		// "http://www.linkedin.com/search/results/people/?keywords=一合管理咨询有限公司&origin=GLOBAL_SEARCH_HEADER";
		// company =
		// "http://www.linkedin.com/search/results/index/?keywords=Allegisbn&page=11";
		// company =
		// "http://www.linkedin.com/search/results/people/?facetGeoRegion=%5B%22cn%3A0%22%5D&keywords=%E6%99%AE%E7%91%9E%E6%96%AF&origin=FACETED_SEARCH";
		// company =
		// "http://www.linkedin.com/search/results/index/?keywords=%E8%89%BA%E5%AF%BB";
		// company =
		// "http://www.linkedin.com/search/results/index/?keywords=Consultant%20at%20Devon-talent&origin=GLOBAL_SEARCH_HEADER";
		// company =
		// "http://www.linkedin.com/search/results/people/?facetGeoRegion=%5B%22cn%3A8931%22%5D&keywords=%E9%AB%98%E7%BA%A7%20%E7%8C%8E%E5%A4%B4&origin=GLOBAL_SEARCH_HEADER";
		// company =
		// "http://www.linkedin.com/search/results/index/?keywords=%E4%BB%95%E8%81%94%EF%BC%88%E4%B8%8A%E6%B5%B7%EF%BC%89%E4%BC%81%E4%B8%9A%E7%AE%A1%E7%90%86%E5%92%A8%E8%AF%A2%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8";
		// company =
		// "http://www.linkedin.com/search/results/index/?keywords=%E4%B8%8A%E6%B5%B7%E6%89%8D%E6%98%93&page=1";
		// company =
		// "http://www.linkedin.com/search/results/index/?keywords=%E4%B8%8A%E6%B5%B7%E6%8B%A9%E4%BB%95%E4%BC%81%E4%B8%9A%E7%AE%A1%E7%90%86%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8";
		// company =
		// "http://www.linkedin.com/search/results/people/?facetGeoRegion=%5B%22cn%3A8909%22%5D&keywords=%E5%8A%A9%E7%90%86%E7%8C%8E%E5%A4%B4%E9%A1%BE%E9%97%AE&origin=FACETED_SEARCH&page=9";

		// company =
		// "http://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B%22%22%5D&facetGeoRegion=%5B%22cn%3A8909%22%2C%22cn%3A8883%22%5D&facetNetwork=%5B%22S%22%2C%22O%22%5D&keywords=%E7%8C%8E%E5%A4%B4&origin=GLOBAL_SEARCH_HEADER&page=101";
		// company =
		// "http://www.linkedin.com/search/results/people/?facetGeoRegion=%5B\"cn%3A8909\"%2C\"cn%3A8883\"%5D&facetNetwork=%5B\"O\"%5D&keywords=猎头&origin=FACETED_SEARCH&page=63";
		// company =
		// "http://www.linkedin.com/search/results/index/?keywords=%E6%9F%AF%E7%BB%B4%E6%AD%A5%E6%A1%91";

		// company =
		// "http://www.linkedin.com/search/results/index/?keywords=girro-consulting";
		// company =
		// "http://www.linkedin.com/search/results/index/?keywords=%E4%B8%8A%E6%B5%B7%E4%BC%98%E7%8C%8E";

		// company =
		// "https://www.linkedin.com/search/results/index/?keywords=TophunterHR&origin=GLOBAL_SEARCH_HEADER";
		// company =
		// "http://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B%22143648%22%2C%223118347%22%2C%229748%22%2C%229506953%22%2C%22141419%22%2C%221209962%22%2C%221611103%22%2C%222632634%22%2C%222868300%22%2C%222880773%22%2C%223029983%22%2C%223667893%22%2C%22544765%22%2C%2254507%22%2C%22574466%22%5D&facetGeoRegion=%5B%22cn%3A8909%22%2C%22cn%3A8883%22%5D&origin=FACETED_SEARCH&page=1";

		// company =
		// "http://www.linkedin.com/search/results/people/?facetGeoRegion=%5B%22cn%3A8909%22%5D&keywords=%E6%9D%AD%E5%B7%9E%E8%AF%97%E8%BF%88%E5%8C%BB%E8%8D%AF%E7%A7%91%E6%8A%80%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8&origin=FACETED_SEARCH&page=1";

		// company =
		// "http://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B%22935469%22%5D&facetGeoRegion=%5B%22cn%3A8909%22%5D&origin=FACETED_SEARCH";

		// company =
		// "http://www.linkedin.com/search/results/index/?keywords=%E5%86%80%E8%B6%8A%E5%92%A8%E8%AF%A2&lipi=urn%3Ali%3Apage%3Ad_flagship3_profile_view_base%3BmC9ORGBASSaDafz2CBWrsA%3D%3D&licu=urn%3Ali%3Acontrol%3Ad_flagship3_profile_view_base-background_details_company";

		// company =
		// "http://www.linkedin.com/search/results/people/?facetGeoRegion=%5B%22cn%3A8909%22%5D&keywords=Nstarts%20Consultants%20Co.%2CLtd.&origin=FACETED_SEARCH";
		// company =
		// "http://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B\"39371\"%5D&facetGeoRegion=%5B\"cn%3A8909\"%5D&origin=FACETED_SEARCH";

		// company = "http://www.linkedin.com/search/results/index/?keywords=示优企业管理";
		// company =
		// "http://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B\"4856382\"%5D&facetGeoRegion=%5B\"cn%3A0\"%5D&keywords=Consultant&origin=FACETED_SEARCH";
		// company = "http://www.linkedin.com/search/results/index/?keywords=上海脉谷";
		// company = "https://www.linkedin.com/search/results/index/?keywords=寻英咨询";
		// company =
		// "http://www.linkedin.com/search/results/index/?keywords=CareerElite";
		// company = "http://www.linkedin.com/search/results/index/?keywords=上海渡新";
		//company = "http://www.linkedin.com/search/results/index/?keywords=基石（上海）人才服务有限公司";
		//company = "http://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B%22126452%22%5D&facetGeoRegion=%5B%22cn%3A0%22%5D&facetNetwork=%5B%22S%22%2C%22O%22%5D&keywords=%E6%8B%9B%E8%81%98&origin=GLOBAL_SEARCH_HEADER&page=1";
		//company = "http://www.linkedin.com/search/results/people/?facetGeoRegion=%5B\"cn%3A8909\"%5D&keywords=猎益&origin=FACETED_SEARCH";
		//company = "http://www.linkedin.com/search/results/index/?keywords=对点咨询&origin=GLOBAL_SEARCH_HEADER";
		//company = "http://www.linkedin.com/search/results/people/?facetGeoRegion=%5B%22cn%3A0%22%5D&keywords=KNX-&origin=GLOBAL_SEARCH_HEADER";
		//company = "https://www.linkedin.com/search/results/people/?facetGeoRegion=%5B\"cn%3A0\"%5D&keywords=OPT%20Consultancy&origin=FACETED_SEARCH&page=1";
		//company = "https://www.linkedin.com/search/results/index/?keywords=深圳市高邦企业咨询管理有限公司";
		//company = "https://www.linkedin.com/search/results/index/?keywords=上海千汇";
		//company = "https://www.linkedin.com/search/results/index/?keywords=上海谷正咨询";
		//company = "https://www.linkedin.com/search/results/people/?facetGeoRegion=%5B\"cn%3A8909\"%5D&keywords=head%20hunting&origin=FACETED_SEARCH&page=45";
		//company = "https://www.linkedin.com/search/results/index/?keywords=金戈猎头&page=1";
		
		//company = "https://www.linkedin.com/search/results/index/?keywords=上海好伯人力资源有限公司";
		//company = "https://www.linkedin.com/search/results/people/?facetGeoRegion=%5B\"cn%3A8909\"%5D&keywords=Kuta&origin=FACETED_SEARCH";
		//company = "https://www.linkedin.com/search/results/people/?facetGeoRegion=%5B\"cn%3A8909\"%5D&keywords=协骏咨询&origin=FACETED_SEARCH";
		//company = "https://www.linkedin.com/search/results/index/?keywords=上海泽陈企业管理咨询有限公司";
		//company = "https://www.linkedin.com/search/results/index/?keywords=大连智博人才顾问有限公司";
		//company = "https://www.linkedin.com/search/results/index/?keywords=上海杰艾企业管理咨询有限公司";
		//company = "https://www.linkedin.com/search/results/index/?keywords=上海猎丰企业管理咨询有限公司";
		//company = "http://www.linkedin.com/search/results/index/?keywords=上海飞仕管理咨询有限公司";
		//company = "https://www.linkedin.com/search/results/index/?keywords=上海远志人力资源管理有限公司";
		//company = "http://www.linkedin.com/search/results/index/?keywords=博才世杰";
		//company = "http://www.linkedin.com/search/results/index/?keywords=百易行";
		//company = "http://www.linkedin.com/search/results/index/?keywords=上海复讯信息咨询有限公司";
		//company = "https://www.linkedin.com/search/results/people/v2/?keywords=%E4%BB%95%E8%81%94&origin=FACETED_SEARCH";
		
		
		//company = "https://www.linkedin.com/search/results/people/?facetGeoRegion=%5B\"cn%3A8939\"%5D&keywords=猎头&origin=FACETED_SEARCH&page=75";
				
		//company = "https://www.linkedin.com/search/results/people/?keywords=杜为尔&origin=FACETED_SEARCH";
		
		//company = "https://www.linkedin.com/search/results/people/?facetGeoRegion=%5B\"cn%3A0\"%5D&keywords=讯升&origin=FACETED_SEARCH";
		//company = "https://www.linkedin.com/search/results/index/?keywords=%E6%B1%9F%E8%8B%8F%E9%A2%86%E8%88%AA%E4%BA%BA%E6%89%8D%E5%BC%80%E5%8F%91%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8&page=1";
		//company = "https://www.linkedin.com/search/results/index/?keywords=Njorth%20consulting";
		
		//company = "https://www.linkedin.com/search/results/people/?facetGeoRegion=%5B\"cn%3A8909\"%5D&facetNetwork=%5B\"S\"%5D&keywords=招聘顾问&origin=FACETED_SEARCH&page=36";
		
		//company = "https://www.linkedin.com/search/results/people/?facetGeoRegion=%5B\"cn%3A8909\"%5D&facetNetwork=%5B\"O\"%5D&keywords=招聘顾问&origin=FACETED_SEARCH&page=1";
		
		//company = "https://www.linkedin.com/search/results/people/v2/?facetCurrentCompany=%5B%222327%22%2C%22383718%22%5D&facetGeoRegion=%5B%22cn%3A0%22%5D&facetNetwork=%5B%22S%22%5D&origin=FACETED_SEARCH&page=36";
		//company = "https://www.linkedin.com/search/results/people/v2/?facetCurrentCompany=%5B%222327%22%2C%22383718%22%5D&facetGeoRegion=%5B%22cn%3A0%22%5D&facetNetwork=%5B%22O%22%5D&origin=FACETED_SEARCH";
		//company = "http://www.linkedin.com/search/results/all/?keywords=Youroads%20Consulting%20Co.%2C%20Ltd";
		
		company = "http://www.linkedin.com/search/results/people/v2/?facetGeoRegion=%5B%22cn%3A8911%22%5D&facetNetwork=%5B%22S%22%2C%22O%22%5D&keywords=headhunting&origin=FACETED_SEARCH";
		PageOperation obj = new PageOperation();
		WebDriver driver;
		// chrome
		System.setProperty("webdriver.chrome.driver", "/temp/chromedriver_win32/chromedriver.exe");
		driver = new ChromeDriver();
		// //firefox
		// //要调起新版本的firefox，需要geckodriver驱动（未设置时java.lang.IllegalStateException报错）
		// System.setProperty("webdriver.gecko.driver",
		// "D:\\temp\\geckodriver-v0.21.0-win64\\geckodriver.exe");
		// //若无法打开Firefox浏览器，可设定Firefox浏览器的安装路径(未设置路径时path报错)
		// System.setProperty("webdriver.firefox.bin", "C:\\Program Files\\Mozilla
		// Firefox\\firefox.exe");
		// //打开Firefox浏览器
		// driver = new FirefoxDriver();

		/*
		 * 如何使用IE浏览器 如果没有将IEDriverServer放入系统的环境变量中，那么必须在程序中设置 例如：
		 */

		// DesiredCapabilities dc = DesiredCapabilities.internetExplorer();
		// dc.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
		// true);
		// dc.setCapability("ignoreZoomSetting", true);
		// System.setProperty("webdriver.ie.driver",
		// "D:\\temp\\IEDriver\\IEDriverServer.exe");
		// driver = new InternetExplorerDriver(dc);

		LinkedinOperation lop = new LinkedinOperation();
		driver.get("https://www.linkedin.com");

		//lop.login(driver);

//		Set<org.openqa.selenium.Cookie> cookies = driver.manage().getCookies();17612165703
//		cookies.clear();
        driver.manage().deleteAllCookies();
        Thread.sleep(3000);  

		
		File cookieFile = new File("C://temp/cookie.txt");
		ObjectMapper mapper = new ObjectMapper(); 
		//mapper.writeValue(cookieFile, cookies);
		JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, LinkedInCookie.class);
		//new TypeReference<List<Cookie>>() {}
		List<LinkedInCookie> cookieSet = (List<LinkedInCookie>)mapper.readValue(cookieFile, javaType);
		//mapper.writeValue(cookieFile, javaType);

		driver.get(company);
		obj.sleep(1000);
		for(LinkedInCookie cook : cookieSet)
		{
			Cookie coo = new Cookie(cook.getName(), cook.getValue(), cook.getDomain(), cook.getPath(), cook.getExpiry(),cook.isSecure(), cook.isHttpOnly());
			driver.manage().addCookie(coo);
		}
		
		driver.navigate().refresh();  
		obj.sleep(1000);
		driver.get(company);
		obj.sleep(3000);

		while (true) {

			try {
				int iter = 0;
				double current = 0.0;
				obj.scrollThePageWithPercent(driver, Double.valueOf(0.75));
				List<WebElement> elements = driver.findElements(By.xpath(".//button[text()='加为好友']"));
				for (WebElement element : elements) {
					// scroll to get all the candidate in the page
					// obj.scrollThePageWithPercent(driver, Double.valueOf(iter / elements.size()));

					try {
						obj.scrollThePage(driver, element);
						element.click();
						obj.sleep(5000);

						List<WebElement> emails = driver.findElements(By.xpath(".//input[@id='email']"));
						if (!emails.isEmpty()) {
							
							List<WebElement> sendbuttons = driver.findElements(By.xpath(".//button[@name='cancel']"));
							if (!sendbuttons.isEmpty()) {
								sendbuttons.get(0).click();
							}
							continue;
						}

						List<WebElement> sendbuttons = driver.findElements(By.xpath(".//button[text()='添加消息']"));
						if (!sendbuttons.isEmpty()) {
							sendbuttons.get(0).click();

							WebElement messageElement = driver
									.findElements(By.xpath(".//textarea[@id='custom-message']")).get(0);
							messageElement.sendKeys(
									"我是William，是猎头的猎头，挖猎头，另外也免费做猎头的职业规划，请问最近会open看机会吗？我这边从AC，C到Partner的机会都有，欢迎一起探讨，希望可以与您建立联系，我的微信号rtrwilliam,手机号18601793121，欢迎您分享您的联系方式，期待您的回复！");
							obj.sleep(3000);

							List<WebElement> sendinvitationElements = driver
									.findElements(By.xpath(".//button[text()='发邀请']"));
							if (!sendinvitationElements.isEmpty()) {
								sendinvitationElements.get(0).click();
								obj.sleep(3000);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();

						String currentURL = driver.getCurrentUrl();
						driver.get(currentURL);
						break;
					}
					obj.sleep(10000);
				}

				// elements.forEach((element) -> {
				// element.click();
				// obj.sleep(100);
				// });

				List<WebElement> nextPageElements = driver.findElements(By.xpath(".//div[@class='next-text']"));
				if (nextPageElements.isEmpty()) {
					break;
				} else {
					nextPageElements.get(0).click();
					obj.sleep(10000);
				}

			} catch (Exception e) {
				break;
			}
		}
		
		//driver.close();

	}

}
