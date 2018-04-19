package com.linkedin.spider;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class SpiderConstants {
	public static Vector<HashMap<String, Object>> profilesAccessedVector = new Vector<HashMap<String, Object>>();
	public static ConcurrentHashMap<String, Object> schools = new ConcurrentHashMap<String, Object>();
	public static ConcurrentHashMap<String, Object> companys = new ConcurrentHashMap<String, Object>();

	public static Map<String,SearchURL> downloadedSearchLinks = new HashMap<String,SearchURL>();
	public static Map<String, SearchURL> searchURLs = new HashMap<String, SearchURL>();
	
	public static Set<String> profilesWritten = new HashSet<String>();

	public static boolean stop = false;
	public static boolean terminate = false;
	public static Date startDate = new Date();
	
	public static Map<String,Boolean> allProfileURLsThisExcution = new HashMap<String,Boolean>();
	public static Set<String> downloadLinks = new HashSet<String>();
}
