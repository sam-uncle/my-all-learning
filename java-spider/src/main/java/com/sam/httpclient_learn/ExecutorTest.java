package com.sam.httpclient_learn;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorTest {
	static int pages = 1;
	static boolean flag = true;
	static ExecutorService executorService = Executors.newFixedThreadPool(10);
	public static void main(String[] args) {
		
		
		while(flag) {
			
			synchronized (executorService) {
				executorService.execute(new Runnable() {
					public void run() {
						System.out.println(Thread.currentThread() + "执行"
								+ pages + "第个页面");
						pages ++;
						if(pages >= 100) {
							flag = false;
							executorService.shutdownNow();
						}
					}
					
				});
			}
			
		}
		
	}
}
