package com.sam.pattern.factory.abstractfactory;

/**
 * 抽象工厂模式
 */
public class Test {
    public static void main(String[] args) {

        //只有一个factor，防止选错；在factory里面挑自己需要的方法
        //对于拓展，只需要在工厂中添加新的方法，不需要修改客户端代码
        AbstractFactory factory = new MilkFactory();
        factory.getMengniu().getName();
        factory.getYili().getName();
    }
}
