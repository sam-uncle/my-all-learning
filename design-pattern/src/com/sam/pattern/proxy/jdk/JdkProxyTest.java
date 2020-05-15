package com.sam.pattern.proxy.jdk;

public class JdkProxyTest {

    public static void main(String[] args) {

        Person obj = (Person) new JDKMeipo().getInstance(new Xiaoming());
        obj.findLove();
        System.out.println(obj.getClass());


        /**
         * 原理：
         * 1.拿到被代理对象的引用，并且获取到它的接口，反射获取
         * 2.JDK proxy类重新生成一个新的类，同时新的类要实现被代理类所有实现所有的
         * 3.动态的生产java代码，把新加的业务逻辑方法由一定的逻辑代码取调用（在代码中体现）
         * 4.编译新生成的java代码
         * 5.再重新加载到JVM中运行
         * 以上这个过程就叫字节码重组
         */
        Person person = (Person) new JDK58().getInstance(new Xiaoming());
        person.findJob();
    }
}
