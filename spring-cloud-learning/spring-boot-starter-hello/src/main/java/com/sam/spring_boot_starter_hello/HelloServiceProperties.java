package com.sam.spring_boot_starter_hello;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ConfigurationProperties
 * �Զ�ƥ��application.properties�ļ�����hello.��Ϊǰ׺�������Զ���ֵ��������
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
