package com.sam.pattern.factory.simplefactory;

import com.sam.pattern.factory.model.Milk;

public class Test {
    public static void main(String[] args) {

        //需要传入参数，决定要哪个对象
        //参数有可能会传错
        Milk milk = SimpleFactory.getMilk("伊利");
        milk.getName();

        milk = SimpleFactory.getMilk("蒙牛");
        milk.getName();

    }
}
