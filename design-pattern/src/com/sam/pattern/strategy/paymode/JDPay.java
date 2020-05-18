package com.sam.pattern.strategy.paymode;

import com.sam.pattern.strategy.PayState;

import java.math.BigDecimal;

public class JDPay implements Payment{
    @Override
    public PayState pay(String uid, BigDecimal amount) {
        System.out.println("欢迎使用京东白条支付");
        System.out.println("查询账户余额，开始扣款");
        return new PayState(200,"支付成功", amount);
    }
}
