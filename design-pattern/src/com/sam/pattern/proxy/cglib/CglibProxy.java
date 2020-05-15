package com.sam.pattern.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {


    public Object getInstance(Class<?> clazz) throws Exception{

        Enhancer enhancer = new Enhancer();

        //要把哪个类设置为即将生成的类的父类
        enhancer.setSuperclass(clazz);

        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("我是媒婆，我己经拿到你的要求");
        System.out.println("准备匹配");

        methodProxy.invokeSuper(o, objects);

        System.out.println("合适的话，就见面聊聊吧");
        return null;
    }
}
