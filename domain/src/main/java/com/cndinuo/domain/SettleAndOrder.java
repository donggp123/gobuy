package com.cndinuo.domain;

import java.io.Serializable;

public class SettleAndOrder implements Serializable {
    private String orderNo;

    private String retNo;

    private String settleNo;

    private Byte type;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getRetNo() {
        return retNo;
    }

    public void setRetNo(String retNo) {
        this.retNo = retNo == null ? null : retNo.trim();
    }

    public String getSettleNo() {
        return settleNo;
    }

    public void setSettleNo(String settleNo) {
        this.settleNo = settleNo == null ? null : settleNo.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}