package com.cndinuo.eunm;

public enum BalanceTypeEnum {

    income(1,"收入"),
    Withdrawals(2,"提现"),
    Recharge(3,"充值"),
    Debit(4,"扣款"),
    refund(5,"退佣金");

    private BalanceTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    private Integer code;
    private String name;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
