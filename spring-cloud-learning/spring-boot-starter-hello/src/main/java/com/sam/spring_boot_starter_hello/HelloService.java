package com.sam.spring_boot_starter_hello;

/**
 * 后面的代码会依据此类是否存在，来决定是否生产对应的Bean
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
