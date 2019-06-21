package com.sam.secondKill.utils;

public class Utils {

	
	/**
	 * 秒杀成功
	 */
	public static final int SECOND_KILL_SUCCESS = 1;
	/**
	 * 秒杀失败，在重试机制性重试
	 */
	public static final int SECOND_KILL_VERSION_OLD = 2;
	/**
	 * 商品售罄
	 */
	public static final int SECOND_KILL_SELL_OUT = 3;
}
