package com.sam.secondKill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sam.secondKill.service.SecondKillService;
import com.sam.secondKill.utils.Utils;

@RestController
public class SecondKillController {

	@Autowired
	SecondKillService service;

	
	@RequestMapping(value = "/secondKill/{productId}")
	public synchronized String secondKill(@PathVariable("productId") Integer productId) throws Exception {
		/**
		 *防止少卖，采用重入机制
		 *重入机制有两种：
		 *		1.时间戳，比如0.1秒内失败都会重试
		 *		2.重试次数
		 *
		 *这里采用了重试次数的方式，重试3次
		 */
		for (int i = 0; i < 3; i++) {
			int result = service.execSecondKill(productId);
			if (result == Utils.SECOND_KILL_SUCCESS) {
				return "second kill success";
			} else if (result == Utils.SECOND_KILL_SELL_OUT) {
				return "sell out";
			}
		}
		return "second kill fail";
	}

}
