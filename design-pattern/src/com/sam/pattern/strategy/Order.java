package com.sam.pattern.strategy;

import com.sam.pattern.strategy.paymode.PayMode;
import com.sam.pattern.strategy.paymode.Payment;

import java.math.BigDecimal;

public class Order {
    private String uid;
    private String orderId;
    private BigDecimal amount;

    public Order(String uid, BigDecimal amount, String orderId) {
        this.uid = uid;
        this.orderId = orderId;
        this.amount = amount;
    }

    //完全可以用接口Payment来代替
    //但是为什么要用策略模式？
    //完美的解决了switch的过程，不需要在代码逻辑中写switch
    //更不需要写if else if
    public PayState pay(PayMode payMode){

        return payMode.getPayment().pay(this.uid, this.amount);
    }


}
