package com.cndinuo.eunm;

/**
 * 订单类型
 */
public enum OrderTypeEnum {

    ORDER_SHOPPING(1,"购物"),
    ORDER_RECHARGE(2,"充值");

    private OrderTypeEnum(Integer code, String name) {
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

    public static String getName(Integer code) {
        for (OrderTypeEnum o : OrderTypeEnum.values()) {
            if (o.getCode() == code) {
                return o.getName();
            }
        }
        return null;
    }
}
