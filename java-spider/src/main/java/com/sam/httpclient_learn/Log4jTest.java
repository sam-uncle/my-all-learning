package com.sam.httpclient_learn;

import org.apache.log4j.Logger;

public class Log4jTest {

	static Logger logger = Logger.getLogger(Log4jTest.class);
	
	public static void main(String[] args) {
		
		logger.debug("debugger");
		logger.info("info");
		logger.warn("warn");
		logger.error("error");
		logger.fatal("fatal");
		
		logger.error("�׳��쳣", new IllegalAccessError("hahah"));
	}
}
