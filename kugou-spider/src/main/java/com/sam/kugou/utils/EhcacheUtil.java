package com.sam.kugou.utils;

import org.apache.log4j.Logger;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhcacheUtil {

	static final Logger logger = Logger.getLogger(EhcacheUtil.class);
	
	private static CacheManager manager = CacheManager.create();
	
	public static void setCache(String hash, Integer albumId) {
		Cache cache = manager.getCache("HashIdCache");
		cache.put(new Element(hash, albumId));
		logger.debug("setCache >>> " + cache.get(hash));
		cache.flush();
	}
	
	public static Element getCache(String hash) {
		Cache cache = manager.getCache("HashIdCache");
		return cache.get(hash);
	}
	
	public static void setFinishedCache(String url, String title) {
		Cache cache = manager.getCache("FinishedCache");
		cache.put(new Element(title, url));
		logger.info("setFinishedCache >>> " + cache.get(title));
		cache.flush();
	}
	
	public static Element getFinishedCache(String hash) {
		Cache cache = manager.getCache("FinishedCache");
		return cache.get(hash);
	}
	
	public static void setProxyIpsCache(String host, int port) {
		Cache cache = manager.getCache("proxyIpsCache");
		cache.put(new Element(host+":"+port, "used"));
		logger.info("proxyIpsCache >>> " + cache.get(host+":"+port));
		cache.flush();
	}
	
	public static Element getProxyIpsCache(String host, int port) {
		Cache cache = manager.getCache("proxyIpsCache");
		return cache.get(host+":"+port);
	}
	
	public static void shutDownManager() {
		manager.shutdown();
	}
	
//	public static void main(String[] args) {
//		Cache cache = manager.getCache("FinishedCache");
//		for (String key : list) {
//			Element element = getFinishedCache(key);
//			logger.info("歌名:>>> " + element.getObjectValue() + ": url:>>> " + key);
//		}
//		shutDownManager();
//	}
}