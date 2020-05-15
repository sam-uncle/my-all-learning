package com.sam.pattern.singleton;

/**
 * 初衷是为了实现资源共享，只需要赋值或初始化一次，大家能够重复利用
 *
 * 应用场景：Listener本身单例，日历calender、IOC容器、配置信息Config
 *
 * 技术方案：保证整个运行过程只有一份
 *          饿汉式  懒汉式  注册式（枚举）  反序列
 *
 * 解决问题：恶劣环境（程序的健全性）
 */




/**
 * 饿汉式
 * 不管用不用，直接初始化
 *      优点：没有加锁，执行效率高
 *           在用户体验上比懒汉式好
 *      缺点：在类加载的时候就初始化了，不管用不用都占用着空间，一定程度上浪费了内存
 *
 * 懒汉式
 * 用到的时候才实例化，延迟加载
 */
public class Hungary {
    public static Hungary singleton = new Hungary();

    private Hungary(){}

    public static Hungary getInstance(){
        return singleton;
    }
}
