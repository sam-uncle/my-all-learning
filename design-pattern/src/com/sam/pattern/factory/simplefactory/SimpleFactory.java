package com.sam.pattern.factory.simplefactory;

import com.sam.pattern.factory.model.Mengniu;
import com.sam.pattern.factory.model.Milk;
import com.sam.pattern.factory.model.Yili;

/**
 * 简单工厂模式
 */
public class SimpleFactory {
    public static Milk getMilk(String milk){
        if("蒙牛".equals(milk)){
            return new Mengniu();
        } else if("伊利".equals(milk)){
            return new Yili();
        }
        return null;
    }
}
