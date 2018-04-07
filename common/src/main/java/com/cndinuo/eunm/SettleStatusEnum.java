package com.cndinuo.eunm;

/**
 * 结算状态
 */
public enum SettleStatusEnum {

    STAY_SETTLE(1, "待结算"),
    SETTLE_ING(2, "结算中"),
    SETTLE_COMPLATE(3,"结算完成");

    private SettleStatusEnum(Integer code, String name) {
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
