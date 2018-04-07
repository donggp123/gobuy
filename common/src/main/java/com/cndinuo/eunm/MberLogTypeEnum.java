package com.cndinuo.eunm;

/**
 * 用户登录类型
 */
public enum MberLogTypeEnum {

    MOBILE(1, "手机号"),
    WEIXIN(2, "微信"),
    QQ(3, "QQ"),
    WEIBO(4,"微博");

    private MberLogTypeEnum(Integer code, String name) {
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
        for (MberLogTypeEnum m : MberLogTypeEnum.values()) {
            if (m.getCode() == code) {
                return m.getName();
            }
        }
        return null;
    }
}
