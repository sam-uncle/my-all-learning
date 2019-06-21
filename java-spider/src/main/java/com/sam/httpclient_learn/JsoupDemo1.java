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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupDemo1 {

	public static void main(String[] args) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpGet httpGet = new HttpGet("https://www.cnblogs.com");
		
		try {
//			httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36");
			System.out.println("请求信息：" +httpGet.toString());
			
			//core code
			RequestConfig config = RequestConfig.custom().
						setSocketTimeout(1000).//读取超时
						setConnectTimeout(1000).//连接超时
						build();
			httpGet.setConfig(config);
			
			
			CloseableHttpResponse response = httpClient.execute(httpGet);
			HttpEntity httpEntity = response.getEntity();
			
			System.out.println(response.getFirstHeader("Content-type"));
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String context = EntityUtils.toString(httpEntity, "utf-8");
				
				Document document = Jsoup.parse(context);
				Element title = document.getElementsByTag("title").get(0);
				System.out.println("标题是："+title.html());
				System.out.println("口号是："+document.select("#site_nav_top").html());
				
				Elements elements = document.select(".post_item");
				System.out.println("标题----作者----文章链接");
				for (Element element : elements) {
//					System.out.println(element);
					Elements elements2 = element.select(".titlelnk");
					System.out.println(elements2.html() + "----" + element.select(".post_item_foot .lightblue").html() + "----" + elements2.attr("href"));
				}
				
				Elements elements2 = document.select("img[src$=.png]");
				for (Element element : elements2) {
					System.out.println(element);
				}
				System.out.println(elements2.first());//第一个元素
				System.out.println(elements2.last());//最后一个元素
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
