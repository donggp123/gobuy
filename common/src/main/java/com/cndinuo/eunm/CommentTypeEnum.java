package com.cndinuo.eunm;

/**
 * 评价类型
 */
public enum CommentTypeEnum {

    MERCHENT(1, "对商家评价"),
    GOODS(2, "对商品评价"),
    RIDER(3, "对骑手评价");

    private CommentTypeEnum(Integer code, String name) {
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
        for (CommentTypeEnum c : CommentTypeEnum.values()) {
            if (c.getCode() == code) {
                return c.getName();
            }
        }
        return null;
    }
}
