package com.sam.httpclient_learn;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * 
 * 使用代理ip
 *
 */
public class Demo4 {

	public static void main(String[] args) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpGet httpGet = new HttpGet("https://www.tuicool.com/");
		
		try {
			httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36");
			System.out.println("请求信息：" +httpGet.toString());
			
			//core code
			HttpHost proxy = new HttpHost("112.87.68.62", 9999);
			RequestConfig config = RequestConfig.custom().setProxy(proxy).setSocketTimeout(1000).setConnectTimeout(1000).build();
			httpGet.setConfig(config);
			
			
			CloseableHttpResponse response = httpClient.execute(httpGet);
			Header[] allHeaders = response.getAllHeaders();
			for (Header header : allHeaders) {
				System.out.println("响应头:==>" + header);
			}
			
			HttpEntity httpEntity = response.getEntity();
			
			System.out.println(response.getFirstHeader("Content-type"));
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				System.out.println("===========success%%%%%%%%%%%%");
			}
			
			httpClient.close();
			response.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
