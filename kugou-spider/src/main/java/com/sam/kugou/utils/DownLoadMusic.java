package com.sam.kugou.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.sam.kugou.main.KugouSpiderMain;

import net.sf.ehcache.Element;

public class DownLoadMusic {
	static final Logger logger = Logger.getLogger(DownLoadMusic.class);
	
	static {
		String[] list = (new File(KugouSpiderMain.DIR_NAME)).list();
		if(list != null && list.length > 0) {
			for (String string : list) {
				String title = string.substring(0, string.indexOf("."));
				EhcacheUtil.setFinishedCache(KugouSpiderMain.DIR_NAME + string, title);
			}
		}
		
	}
	
	public static void requestMusic(String title, String url) {
		/**
		 * 1.根据列表中的链接访问，获取播放页面内容
		 */
		String html = HttpClientUtil.getHtml(url);
		logger.debug(">>>>>"+html);
		String regEx = "\"hash\":\"[0-9A-Z]+\"";
		 String hash = "";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            hash = matcher.group();
            hash = hash.replace("\"hash\":\"", "");
            hash = hash.replace("\"", "");
        }
        logger.info("hash >>> " + hash);
		/**
		 * 2.获取请求mp3的地址
		 */
        String requestMP3 = "https://wwwapi.kugou.com/yy/index.php?r=play/getdata&callback=jQuery19108318842169864549_1558160178250&hash=HASHCODE&album_id=ALBUMID&dfid=4Vyhka0JsPzT0DLMy10TfJPj&mid=122dc1e8e26152d6ec1aca669ca448d3&platid=4&_=TIME";
        requestMP3 = requestMP3.replace("HASHCODE", hash);
        if(EhcacheUtil.getCache(hash) == null)
        	return;
		requestMP3 = requestMP3.replace("ALBUMID", EhcacheUtil.getCache(hash).getObjectValue().toString());
		requestMP3 = requestMP3.replace("TIME",System.currentTimeMillis() + "");
		logger.info("获取请求mp3的地址 >>> " + requestMP3);
		
		/**
		 * 3.获取真实歌曲地址
		 * 
		 */
		String mp3Html = HttpClientUtil.getHtml(requestMP3);
		logger.info("mp3Html==>" +mp3Html);
		if(mp3Html == null)
			return;
		String jsonStr = mp3Html.substring(mp3Html.indexOf("{"), mp3Html.length()-2);
		logger.info(jsonStr);
		JSONObject jsonObject = JSONObject.parseObject(jsonStr);
		if(!"0".equals(jsonObject.getString("err_code"))) {
			logger.info("***爬虫已被网站识别，切换代理ip***");
			boolean setProxyFlag = HttpClientUtil.setProxy();
			if(!setProxyFlag) {
				logger.info("切换代理失败，即将睡眠。。。。。。。");
				try {
					Thread.sleep(KugouSpiderMain.SLEEP_TIME_WHEN_DENY);
				} catch (InterruptedException e) {
					logger.error(e.getMessage(), e);
				}
				logger.info("睡眠结束，继续爬取。。。。。。。");
				return;
			}
			//切换代理ip后，将刚才失败的歌曲重新请求一遍
			mp3Html = HttpClientUtil.getHtml(requestMP3);
			logger.info("mp3Html==>" +mp3Html);
			if(mp3Html == null || "".equals(mp3Html))
				return;
			jsonStr = mp3Html.substring(mp3Html.indexOf("{"), mp3Html.length()-2);
			logger.info("切换代理ip后，将刚才失败的歌曲重新请求一遍 >>> " + jsonStr);
			jsonObject = JSONObject.parseObject(jsonStr);
			
		}
		String realUrl = jsonObject.getJSONObject("data").getString("play_url");
		if("".equals(realUrl)) {
			logger.info("***歌曲真实地址未获取到，可能属于付费歌曲***");
			return;
		}
		logger.info("realUrl ==>" + realUrl);
		
		
		/**
		 * 4.下载歌曲
		 */
		downLoad(title, realUrl);
	}
	
	public static void downLoad(String title, String url) {
		if(url == null || url.equals("")) {
			return ;
		}
		//已经完成的就不再重新下载
		Element finishedCache = EhcacheUtil.getFinishedCache(title);
		logger.debug("finishedCache >>>>> " + finishedCache);
		if(finishedCache != null) {
			logger.info("歌曲已经存在！！！");
			return;
		}
		String suffix = url.substring(url.lastIndexOf("."));
		try {
			HttpEntity httpEntity = HttpClientUtil.getHttpEntity(url);
			InputStream inputStream = httpEntity.getContent();
			String filePath = KugouSpiderMain.DIR_NAME+title+suffix;
			FileUtils.copyToFile(inputStream, new File(filePath));
			logger.info("***完成下载：***"+title+suffix);
			logger.info("***总歌曲数量：***"+(new File(KugouSpiderMain.DIR_NAME)).list().length);
			EhcacheUtil.setFinishedCache(url, title);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}