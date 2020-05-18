package com.sam.pattern.strategy.paymode;

/**
 * 把枚举当成常亮来维护
 */
public enum PayMode {
    ALI_PAY(new AliPay()),
    WECHAT_PAY(new WechatPay()),
    JD_PAY(new JDPay()),
    UNION_PAY(new UnionPay());

    private Payment payment;
    PayMode(Payment payment) {
        this.payment = payment;
    }

    public Payment getPayment() {
        return payment;
    }
}
