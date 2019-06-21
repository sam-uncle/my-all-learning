package com.sam.httpclient_learn;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhcacheTest {
	public static void main(String[] args) {
		CacheManager manager = CacheManager.create();
		Cache cache = manager.getCache("HelloWorldCache");
		cache.put(new Element("sam", "ahha"));
		cache.put(new Element("sam-uncle", "ahha"));
		System.out.println(cache.get("sam").getValue());
		cache.flush();
		manager.shutdown();
	}
}
