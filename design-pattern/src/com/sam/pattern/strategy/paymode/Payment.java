package com.sam.pattern.strategy.paymode;

import com.sam.pattern.strategy.PayState;

import java.math.BigDecimal;

public interface Payment {

    public PayState pay(String uid, BigDecimal amount);

}
