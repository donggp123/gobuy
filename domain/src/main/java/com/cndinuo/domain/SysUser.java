package com.cndinuo.domain;

import com.cndinuo.annotation.Name;
import com.cndinuo.base.BaseEntity;

import java.util.List;

public class SysUser extends BaseEntity{

    @Name("登录名")
    private String loginName;

    @Name("真实名称")
    private String realName;

    @Name("密码")
    private String password;

    @Name("角色")
    private String roleIds;

    @Name("盐密")
    private String salt;

    @Name("备注")
    private String remark;

    private List<SysRole> roles;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds == null ? null : roleIds.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }
}