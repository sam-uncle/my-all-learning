package com.sam.spring_boot_starter_hello;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ConfigurationProperties
 * 自动匹配application.properties文件中以hello.作为前缀的属性自动赋值给类属性
 *
 */
@ConfigurationProperties(prefix="hello")
public class HelloServiceProperties {

	private static final String MSG = "spring boot";
	
	private String msg = MSG;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
