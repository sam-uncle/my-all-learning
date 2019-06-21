package com.sam.httpclient_learn;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HtmlUnitTest {

	public static void main(String[] args) {
		//1.ʵ�����ͻ���
//		WebClient webClient = new WebClient();
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		try {
			//2.������ȡ����
			HtmlPage page = webClient.getPage("http://www.java1234.com");
			System.out.println("��ҳhtml:"+page.asXml());
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			webClient.close();//�رտͻ���,�ͷ��ڴ�
		}
	}
}
