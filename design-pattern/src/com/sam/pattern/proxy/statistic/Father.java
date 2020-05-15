package com.sam.pattern.proxy.statistic;

public class Father {
    private Son son;

    Father(Son son){
        this.son = son;
    }

    public void findLove(){
        System.out.println("给儿子找对象");
        son.findLove();
        System.out.println("双方父母同意吗");
    }
}
