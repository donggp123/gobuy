package com.cndinuo.domain;

import com.cndinuo.annotation.Name;
import com.cndinuo.base.BaseEntity;

import java.util.List;

public class SysRole extends BaseEntity {

    @Name("角色名称")
    private String roleName;

    @Name("备注")
    private String content;

    @Name("菜单ID")
    private String menuIds;

    @Name("权限ID")
    private String btnIds;

    private List<SysMenu> menus;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds == null ? null : menuIds.trim();
    }

    public String getBtnIds() {
        return btnIds;
    }

    public void setBtnIds(String btnIds) {
        this.btnIds = btnIds;
    }

    public List<SysMenu> getMenus() {
        return menus;
    }

    public void setMenus(List<SysMenu> menus) {
        this.menus = menus;
    }
}
