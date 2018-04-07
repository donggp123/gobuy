package com.cndinuo.domain;

import com.cndinuo.annotation.Name;
import com.cndinuo.eunm.MrhtStatusEnum;
import com.cndinuo.eunm.MrhtTypeEnum;

import java.io.Serializable;
import java.util.Date;

public class Merchant implements Serializable {

    @Name("ID")
    private Integer id;

    @Name("商户名称")
    private String mrhtName;

    @Name("登录名")
    private String loginName;

    @Name("密码")
    private String password;

    @Name("商户类型")
    private Byte mrhtType;

    @Name("审核状态")
    private Byte status;

    private Byte deviceType;

    private String deviceToken;

    @Name("注册时间")
    private Date createTime;

    @Name("审核时间")
    private Date updateTime;

    @Name("盐密")
    private String salt;

    @Name("删除标识")
    private Byte deleted;

    @Name("备注")
    private String remark;

    private String mrhtTypeName;

    private String statusName;

    private String officeTime;

    public String getOfficeTime() {
        return officeTime;
    }

    public void setOfficeTime(String officeTime) {
        this.officeTime = officeTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMrhtName() {
        return mrhtName;
    }

    public void setMrhtName(String mrhtName) {
        this.mrhtName = mrhtName == null ? null : mrhtName.trim();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Byte getMrhtType() {
        return mrhtType;
    }

    public void setMrhtType(Byte mrhtType) {
        this.mrhtType = mrhtType;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Byte deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getMrhtTypeName() {
        if (getMrhtType() != null) {
            return MrhtTypeEnum.getName(getMrhtType().intValue());
        }
        return mrhtTypeName;
    }

    public void setMrhtTypeName(String mrhtTypeName) {
        this.mrhtTypeName = mrhtTypeName;
    }

    public String getStatusName() {
        if (getStatus() != null) {
            return MrhtStatusEnum.getName(getStatus().intValue());
        }
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}