package com.cndinuo.eunm;

/**
 * 商户审核状态
 */
public enum MrhtStatusEnum {

    PENDING_AUDIT(1,"待审核"),
    AUDITED(2,"审核通过"),
    AUDIT_REJECT(3,"审核拒绝");

    private MrhtStatusEnum(Integer code, String name) {
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

        for (MrhtStatusEnum c : MrhtStatusEnum.values()) {
            if (c.getCode() == code) {
                return c.getName();
            }
        }
        return null;
    }
}
