package com.sam.httpclient_learn;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class Demo1 {

	public static void main(String[] args) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpGet httpGet = new HttpGet("http://central.maven.org/maven2/HTTPClient/HTTPClient/0.3-3/HTTPClient-0.3-3.jar");
		
		try {
			httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36");
			System.out.println("请求信息：" +httpGet.toString());
			CloseableHttpResponse response = httpClient.execute(httpGet);
			Header[] allHeaders = response.getAllHeaders();
			for (Header header : allHeaders) {
				System.out.println("响应头:==>" + header);
			}
			
			HttpEntity httpEntity = response.getEntity();
//			System.out.println("响应体:"+EntityUtils.toString(httpEntity,"utf-8"));
			
			
			System.out.println(response.getFirstHeader("Content-type"));
			System.out.println(httpEntity.getContentType().getValue());
			
			System.out.println(response.getStatusLine().getStatusCode());
			
			httpClient.close();
			response.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
