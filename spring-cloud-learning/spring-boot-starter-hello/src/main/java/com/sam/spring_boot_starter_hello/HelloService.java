package com.sam.spring_boot_starter_hello;

/**
 * ����Ĵ�������ݴ����Ƿ���ڣ��������Ƿ�������Ӧ��Bean
 *
 */
public class HelloService {

	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String sayHello() {
		return "hello " + msg;
	}
}
