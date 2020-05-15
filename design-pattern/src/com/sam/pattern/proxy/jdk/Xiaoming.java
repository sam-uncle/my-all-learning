package com.sam.pattern.proxy.jdk;

public class Xiaoming implements Person {
    @Override
    public void findLove() {
        System.out.println("性格合适的");
    }

    @Override
    public void findJob() {

        System.out.println("合适的工作");
    }

    @Override
    public void findHouse() {
        System.out.println("性价比高的");
    }
}
