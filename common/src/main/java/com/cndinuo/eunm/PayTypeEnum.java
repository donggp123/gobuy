package com.cndinuo.eunm;

/**
 * 支付方式
 */
public enum PayTypeEnum {

    WECHAT(1,"微信支付"),
    ALIPAY(2,"支付宝支付");

    private Integer code;
    private String name;

    private PayTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

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

    public String getName(Integer code) {
        for (PayTypeEnum p : PayTypeEnum.values()) {
            if (p.getCode() == code) {
                return p.getName();
            }
        }
        return null;
    }
}
