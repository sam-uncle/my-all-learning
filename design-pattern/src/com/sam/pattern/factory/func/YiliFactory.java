package com.sam.pattern.factory.func;

import com.sam.pattern.factory.model.Milk;
import com.sam.pattern.factory.model.Yili;

public class YiliFactory implements Factory {
    @Override
    public Milk getMilk() {
        return new Yili();
    }
}
