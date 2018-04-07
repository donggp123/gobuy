package com.cndinuo.domain;

import com.cndinuo.annotation.Name;

import java.io.Serializable;

public class MrhtMenu implements Serializable{

    @Name("ID")
    private Integer id;

    @Name("父ID")
    private Integer parentId;

    @Name("菜单名称")
    private String menuName;

    @Name("URL")
    private String menuUrl;

    @Name("商户类型")
    private Byte mrhtType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl == null ? null : menuUrl.trim();
    }

    public Byte getMrhtType() {
        return mrhtType;
    }

    public void setMrhtType(Byte mrhtType) {
        this.mrhtType = mrhtType;
    }
}