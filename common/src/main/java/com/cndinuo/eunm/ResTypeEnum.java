package com.cndinuo.eunm;

/**
 * 资源类型
 */
public enum ResTypeEnum {

    RES_TYPE_MENU(1,"菜单"),
    RES_TYPE_BTN(2,"按钮");

    private ResTypeEnum(Integer code, String name) {
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

    public static String getName(int code) {
        for(ResTypeEnum r : ResTypeEnum.values()){
            if (r.getCode() == code) {
                return r.getName();
            }
        }
        return null;
    }
}