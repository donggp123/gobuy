package com.cndinuo.eunm;

/**
 * 结算单订单类型
 */
public enum SettleOrderTypeEnum {

    normal_order(1,"正常订单"),
    return_order(2, "退款单");

    private SettleOrderTypeEnum(Integer code, String name) {
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
        for (SettleOrderTypeEnum s : SettleOrderTypeEnum.values()) {
            if (s.getCode() == code) {
                return s.getName();
            }
        }
        return null;
    }
}
