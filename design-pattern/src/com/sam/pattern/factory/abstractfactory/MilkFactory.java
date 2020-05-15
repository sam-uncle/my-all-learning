package com.sam.pattern.factory.abstractfactory;

import com.sam.pattern.factory.func.YiliFactory;
import com.sam.pattern.factory.model.Mengniu;
import com.sam.pattern.factory.model.Milk;

public class MilkFactory extends AbstractFactory{
    @Override
    Milk getMengniu() {
        return new Mengniu();
    }

    @Override
    Milk getYili() {
        return new YiliFactory().getMilk();
    }
}
