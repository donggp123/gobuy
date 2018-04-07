package com.cndinuo.domain;

import java.io.Serializable;

public class SysAuthor implements Serializable{

    private Integer roleId;

    private Integer resId;

    private Byte resType;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public Byte getResType() {
        return resType;
    }

    public void setResType(Byte resType) {
        this.resType = resType;
    }
}