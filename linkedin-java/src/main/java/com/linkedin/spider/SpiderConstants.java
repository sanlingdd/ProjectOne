package com.linkedin.spider;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import com.linkedin.jpa.entity.Profile;

import redis.clients.jedis.Jedis;

public class SpiderConstants {
	public static Vector<HashMap<String, Object>> profilesAccessedVector = new Vector<HashMap<String, Object>>();
	public static ConcurrentHashMap<String, Object> schools = new ConcurrentHashMap<String, Object>();
	public static ConcurrentHashMap<String, Object> companys = new ConcurrentHashMap<String, Object>();

	public static Map<String,SearchURL> downloadedSearchLinks = new ConcurrentHashMap<String,SearchURL>();
	public static Map<String, SearchURL> searchURLs = new ConcurrentHashMap<String, SearchURL>();
	
	public static Set<String> profilesWritten = new HashSet<String>();

	public static boolean stop = false;
	public static boolean terminate = false;
	public static Date startDate = new Date();
	
	public static Map<String,Boolean> allProfileURLsThisExcution = new ConcurrentHashMap<String,Boolean>();
	public static Set<String> downloadLinks = new HashSet<String>();
	
	public static Map<String,Profile> profiles = new HashMap<String,Profile>();
	public static Jedis jedis_master = new Jedis("127.0.0.1",6379);
	public static Set<String> allPublicIdentifiers = new HashSet<String>();
}
