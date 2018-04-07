package com.cndinuo.eunm;

/**
 * 用户类型
 */
public enum MberTypeEnum {

    NORMAL_MBER(1, "普通会员"),
    RIDER(2, "骑手");

    private MberTypeEnum(Integer code, String name) {
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
        for (MberTypeEnum m : MberTypeEnum.values()) {
            if (m.getCode() == code) {
                return m.getName();
            }
        }
        return null;
    }
}
