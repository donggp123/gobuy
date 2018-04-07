package com.cndinuo.domain;

import com.cndinuo.annotation.Name;

import java.io.Serializable;

public class SysBtn implements Serializable{

    @Name("ID")
    private Integer id;

    @Name("权限名称")
    private String btnName;

    @Name("权限编码")
    private String btnCode;

    @Name("权限路径")
    private String btnUrl;

    @Name("菜单ID")
    private Integer menuId;

    @Name("菜单名称")
    private String menuName;

    private boolean checked;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBtnName() {
        return btnName;
    }

    public void setBtnName(String btnName) {
        this.btnName = btnName == null ? null : btnName.trim();
    }

    public String getBtnCode() {
        return btnCode;
    }

    public void setBtnCode(String btnCode) {
        this.btnCode = btnCode == null ? null : btnCode.trim();
    }

    public String getBtnUrl() {
        return btnUrl;
    }

    public void setBtnUrl(String btnUrl) {
        this.btnUrl = btnUrl == null ? null : btnUrl.trim();
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}