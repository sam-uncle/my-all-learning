package com.sam.httpclient_learn;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * 
 * 将图片保存到本地
 *
 */
public class Demo3 {

	public static void main(String[] args) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpGet httpGet = new HttpGet("http://www.java1234.com");
		
		try {
			httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36");
			System.out.println("请求信息：" +httpGet.toString());
			CloseableHttpResponse response = httpClient.execute(httpGet);
			Header[] allHeaders = response.getAllHeaders();
			for (Header header : allHeaders) {
				System.out.println("响应头:==>" + header);
			}
			
			HttpEntity httpEntity = response.getEntity();
			
			System.out.println(response.getFirstHeader("Content-type"));
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				InputStream inputStream = httpEntity.getContent();
				FileUtils.copyToFile(inputStream, new File("D://1.txt"));
				inputStream.close();
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
