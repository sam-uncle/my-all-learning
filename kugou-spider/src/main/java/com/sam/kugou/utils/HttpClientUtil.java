package com.sam.kugou.utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.sf.ehcache.Element;

public class HttpClientUtil {

	static final Logger logger = Logger.getLogger(HttpClientUtil.class);
	static String proxyIp = null;
	static int proxyPort = 0;
	
	public static String getHtml(String url) {
		
		// 1.创建一个httpClient
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String html = "";

		try {
			response = doRequest(httpClient, url);

			logger.debug("getHtml " + url + "**处理结果：**" + response.getStatusLine());
			// 5.判断返回结果，200， 成功
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				HttpEntity httpEntity = response.getEntity();
				html = EntityUtils.toString(httpEntity,"utf-8");
			} else if (HttpStatus.SC_FORBIDDEN == response.getStatusLine().getStatusCode()) {
				logger.info("请求返回 403 ，切换代理ip");
				HttpClientUtil.setProxy();
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			// 关闭
			HttpClientUtils.closeQuietly(response);
			HttpClientUtils.closeQuietly(httpClient);
		}
		return html;
	}

	public static HttpEntity getHttpEntity(String url) {
		// 1.创建一个httpClient
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		HttpEntity httpEntity = null;

		
		try {
			response = doRequest(httpClient, url);
			logger.debug("getHtml " + url + "**处理结果：**" + response.getStatusLine());
			// 5.判断返回结果，200， 成功
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				httpEntity = response.getEntity();
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		} 

		return httpEntity;
	}
	
	public static CloseableHttpResponse doRequest(CloseableHttpClient httpClient, String url) throws ClientProtocolException, IOException {
		CloseableHttpResponse response = null;
		// 1.创建get请求
		HttpGet request = new HttpGet(url);
		// 2.伪装浏览器
		request.setHeader("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36");
		if (proxyIp != null) {
			HttpHost proxy = new HttpHost(proxyIp, proxyPort);
			RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
			request.setConfig(config);
		}

		// 4.执行请求
		response = httpClient.execute(request);
		return response;
	}
	
	
	public static boolean setProxy() {
		// 1.创建一个httpClient
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String url = "https://raw.githubusercontent.com/fate0/proxylist/master/proxy.list";
		try {
			response = doRequest(httpClient, url);

			logger.debug("getHtml " + url + "**处理结果：**" + response.getStatusLine());
			// 5.判断返回结果，200， 成功
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				HttpEntity httpEntity = response.getEntity();
				String html = EntityUtils.toString(httpEntity, "utf-8");
				html = "["+html+"]";
				List<JSONObject> list = JSONArray.parseArray(html, JSONObject.class);
				for (JSONObject jsonObject : list) {
					int port = Integer.valueOf(jsonObject.get("port").toString());
					String host = jsonObject.get("host").toString();
					logger.info(host + ":"+port);
					if(isHostConnectable(host, port)) {//代理ip可以连接
						Element ipsCache = EhcacheUtil.getProxyIpsCache(host, port);//代理ip未使用过
						if(ipsCache == null) {
							proxyIp = host;
							proxyPort = port;
							EhcacheUtil.setProxyIpsCache(host, port);
							break;
						} else {
							logger.info("该代理ip已经使用过，切换下一个");
						}
					}
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return false;
		} finally {
			// 关闭
			HttpClientUtils.closeQuietly(response);
			HttpClientUtils.closeQuietly(httpClient);
		}
		logger.info("切换代理ip成功：>>>" + proxyIp + ":" + proxyPort);
		return true;
	}
	
	public static boolean isHostConnectable(String host, int port) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port));
        } catch (IOException e) {
        	logger.error(e.getMessage(),e);
            return false;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
            	logger.error(e.getMessage(),e);
            }
        }
        return true;
    }
	
	public static void main(String[] args) {
		setProxy();
	}
}