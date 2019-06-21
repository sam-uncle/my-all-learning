package com.sam.kugou.main;

import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONObject;
import com.sam.kugou.utils.DownLoadMusic;
import com.sam.kugou.utils.EhcacheUtil;
import com.sam.kugou.utils.HttpClientUtil;

public class KugouSpiderMain {

	static final Logger logger = Logger.getLogger(KugouSpiderMain.class);
	
	static String URL_TEMP = "https://www.kugou.com/yy/rank/home/PAGE_NUM-8888.html?from=homepage";
	public static final int SLEEP_TIME_WHEN_DENY = 1000*60*60;//被网站识别后睡眠时间
	public static final int SPIDER_DURING = 1;//隔多久爬取下一首，单位：ms
	public static final String DIR_NAME = "E:\\personal\\音乐\\酷狗\\";//音乐下载地址
	
	public static void main(String[] args) {
		
		//酷狗TOP500页面
		try {
			for (int i = 1; i <= 23; i++) {
				String url = URL_TEMP;
				url = url.replace("PAGE_NUM", i + "");
				/**
				 * 1.请求歌曲列表
				 */
				logger.info(url);
				String html = HttpClientUtil.getHtml(url);
				logger.debug(html);
				
				
				/**
				 * 2.获取该页的hash和id 放到缓存
				 */
				
				int beginIdx = html.indexOf("global.features = ");
				int endIdx = html.indexOf("];", beginIdx);
				String features = html.substring(beginIdx, endIdx + 1).replace("global.features = ", "");
				logger.info("containingOwnText >>>>>> " + features);
				
				List<JSONObject> list = JSONObject.parseArray(features, JSONObject.class);
				for (JSONObject jsonObject : list) {
					String hash = (String) jsonObject.get("Hash");
					Integer albumId = (Integer) jsonObject.get("album_id");
					EhcacheUtil.setCache(hash, albumId);
				}
				
				/**
				 * 3.解析列表内容
				 */
				Document doc = Jsoup.parse(html);
				Elements songList = doc.select(".pc_temp_songlist ul li a");
//				int count = 0;
				for (Element element : songList) {
					
					String title = element.attr("title");
					String href = element.attr("href");
					
					if(href.contains("https")) {
						try {
//							count++;
//							if(count > 2) {
//								break;
//							}
							Thread.sleep(SPIDER_DURING);
						} catch (InterruptedException e) {
							logger.error(e.getMessage());
						}
						logger.info("title " + title +" >>> href " + href);
						DownLoadMusic.requestMusic(title, href);
					}
				}

			}
		} catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			/***
			 * 4.关闭
			 */
			EhcacheUtil.shutDownManager();
		}
		
	}

}