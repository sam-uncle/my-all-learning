package com.sam.pattern.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDK58 implements InvocationHandler {

    Person person;

    public Object getInstance(Person person){
        this.person = person;

        Class<?> clazz = person.getClass();

        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("我是58同城，我己经拿到你的简历");
        System.out.println("准备投递");
        method.invoke(person , args);
        System.out.println("合适的话，就面试入职");
        return null;
    }
}
