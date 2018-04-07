package com.cndinuo.eunm;

/**
 * 配送方式
 */
public enum DeliveryTypeEnum {

    mrht_delivery(1, "商家自送"),
    rider_delivery(2, "骑手配送");

    private DeliveryTypeEnum(Integer code, String name) {
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
