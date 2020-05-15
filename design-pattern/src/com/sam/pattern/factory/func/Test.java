package com.sam.pattern.factory.func;

import com.sam.pattern.factory.model.Yili;

/**
 * 工厂（方法）模式
 */
public class Test {
    public static void main(String[] args) {

        //每种产品都有自己的工程
        //每次用户需要自己选择使用哪个工厂，增加了用户的复杂度
        //用户可能会选错factory
        Factory factory = new MengniuFactory();
        factory.getMilk().getName();

        Factory factory1 = new YiliFactory();
        factory1.getMilk().getName();
    }
}
