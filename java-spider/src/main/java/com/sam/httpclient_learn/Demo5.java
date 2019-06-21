package com.sam.httpclient_learn;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 
 * ���ó�ʱʱ��Ͷ�ȡ��ʱ
 *
 */
public class Demo5 {

	public static void main(String[] args) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpGet httpGet = new HttpGet("https://www.baidu.com");
		
		try {
//			httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36");
			System.out.println("������Ϣ��" +httpGet.toString());
			
			//core code
			RequestConfig config = RequestConfig.custom().
						setSocketTimeout(1000).//��ȡ��ʱ
						setConnectTimeout(1000).//���ӳ�ʱ
						build();
			httpGet.setConfig(config);
			
			
			CloseableHttpResponse response = httpClient.execute(httpGet);
			HttpEntity httpEntity = response.getEntity();
			
			System.out.println(response.getFirstHeader("Content-type"));
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				System.out.println("===========success%%%%%%%%%%%%");
				System.out.println(EntityUtils.toString(httpEntity,"utf-8"));
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
