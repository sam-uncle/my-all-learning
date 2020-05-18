package com.sam.pattern.strategy;

import com.sam.pattern.strategy.paymode.AliPay;
import com.sam.pattern.strategy.paymode.PayMode;
import com.sam.pattern.strategy.paymode.Payment;
import com.sam.pattern.strategy.paymode.WechatPay;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * 策略模式---行为型模式
 *      比较器、旅游路线、固定算法、买东西结算支付、爬虫
 *
 * 场景：
 * 根据用户的需求处理数据的时候需要对算法做出选择，固定的一些算法（不再发生变化的算法），扩展
 *
 * 客户有选择， 但是不知道具体的细节
 *
 *
 * 网购付款的时候有多种选择：
 *      支付宝、微信支付、京东白条、银联卡
 *      可以使用策略模式
 *
 *
 * 用设计模式是为了解决复杂问题的，把复杂的问题简单化
 * 不要去生搬硬套，容易把简单的事情复杂化
 *
 * 把简单的事情搞复杂，谁都会
 * 但是要把复杂的事情变简单，那就要技术含量（借鉴经验）
 *
 */
public class StrategyTest {

    public static void main(String[] args) {
        //省略把商品添加到购物车，再从购物车下单
        //直接从订单开始
        Order order = new Order("1", new BigDecimal("25.50"),"2020050700001");
        //开始支付，选择微信、支付宝、京东、银联卡
        //基本算法固定的
        PayState payState = order.pay(PayMode.JD_PAY);
        System.out.println(payState);


        new ArrayList<Object>().sort(new Comparator<Object>() {
            @Override
            //这里的比较也是一种策略
            public int compare(Object o1, Object o2) {
                return 0;
            }
        });


        //Spring中用到策略模式的地方
        //BeanFactory
        //ListableFactory...
        //通常和抽象工厂模式混合使用




        //爬虫==根据url自动选择
        //爬取百度的数据  BaiduParse
        //新浪的       SinaParse
        //搜狗的       SougouParse
        //返回一个解析好的json格式，统一保存入库
    }

}
