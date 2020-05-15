package com.sam.pattern.factory.func;

import com.sam.pattern.factory.model.Mengniu;
import com.sam.pattern.factory.model.Milk;

public class MengniuFactory implements Factory{
    @Override
    public Milk getMilk() {
        return new Mengniu();
    }
}
