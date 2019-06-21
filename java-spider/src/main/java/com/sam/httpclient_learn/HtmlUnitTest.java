package com.sam.httpclient_learn;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HtmlUnitTest {

	public static void main(String[] args) {
		//1.实例化客户端
//		WebClient webClient = new WebClient();
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		try {
			//2.解析获取画面
			HtmlPage page = webClient.getPage("http://www.java1234.com");
			System.out.println("网页html:"+page.asXml());
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			webClient.close();//关闭客户端,释放内存
		}
	}
}
