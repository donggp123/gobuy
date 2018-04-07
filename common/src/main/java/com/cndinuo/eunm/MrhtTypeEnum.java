package com.cndinuo.eunm;

/**
 * 商户类型
 */
public enum MrhtTypeEnum {

    SUPPLIER(1,"供应商"),
    MERCHANT(2,"商店");

    private MrhtTypeEnum(Integer code, String name) {
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
        for (MrhtTypeEnum m : MrhtTypeEnum.values()) {
            if (m.getCode() == code) {
                return m.getName();
            }
        }
        return null;
    }
}
