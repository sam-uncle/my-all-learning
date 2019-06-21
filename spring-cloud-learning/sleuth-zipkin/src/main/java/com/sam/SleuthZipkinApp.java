package com.sam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import zipkin.server.EnableZipkinServer;

/**
 * @EnableZipkinServer
 * 
 * 用于开启Zipkin Server功能
 *
 */
@EnableZipkinServer
@SpringBootApplication
@EnableDiscoveryClient
public class SleuthZipkinApp {

//	@Bean
//    public MySQLStorage mySQLStorage(DataSource datasource) {
//        return MySQLStorage.builder().datasource(datasource).executor(Runnable::run).build();
//    }

	public static void main(String[] args) {
		SpringApplication.run(SleuthZipkinApp.class, args);
	}

}
