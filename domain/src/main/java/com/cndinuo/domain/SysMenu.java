package com.cndinuo.domain;

import com.cndinuo.annotation.Name;
import com.cndinuo.base.BaseEntity;

import java.util.List;

public class SysMenu extends BaseEntity{

    @Name("父菜单")
    private Integer parentId;

    @Name("菜单名称")
    private String menuName;

    @Name("菜单路径")
    private String menuUrl;

    private List<SysMenu> childs;

    private List<SysBtn> btns;

    private boolean checked;

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

    public List<SysMenu> getChilds() {
        return childs;
    }

    public void setChilds(List<SysMenu> childs) {
        this.childs = childs;
    }

    public List<SysBtn> getBtns() {
        return btns;
    }

    public void setBtns(List<SysBtn> btns) {
        this.btns = btns;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}