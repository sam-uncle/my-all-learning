package com.sam.pattern.strategy;

/**
 * 支付状态
 */
public class PayState {
    private int code;
    private Object data;
    private String msg;

    public PayState(int code,String msg, Object data) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public String toString(){
        return ("支付状态:[" + this.code +  "]" + data + "; msg = " + msg);
    }
}
