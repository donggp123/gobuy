package com.cndinuo.eunm;

/**
 * 设备类型
 */
public enum DeviceTypeEnum {

    ANDROID(1, "安卓"),
    IOS(2,"苹果");

    private DeviceTypeEnum(Integer code, String name) {
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
        for (DeviceTypeEnum d : DeviceTypeEnum.values()) {
            if (d.getCode() == code) {
                return d.getName();
            }
        }
        return null;
    }
}
