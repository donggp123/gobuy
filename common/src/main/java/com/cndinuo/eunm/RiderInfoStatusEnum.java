package com.cndinuo.eunm;

/**
 * 骑手审核状态
 */
public enum RiderInfoStatusEnum {

    APPROVINIG(1,"审核中"),
    REJECT(2, "审核拒绝"),
    COMPLATE(3,"审核通过");

    private RiderInfoStatusEnum(Integer code, String name) {
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
        for (RiderInfoStatusEnum r : RiderInfoStatusEnum.values()) {
            if (r.getCode() == code) {
                return r.getName();
            }
        }
        return null;
    }
}
