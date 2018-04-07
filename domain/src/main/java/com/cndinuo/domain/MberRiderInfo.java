package com.cndinuo.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class MberRiderInfo implements Serializable {
    private Integer id;

    private Integer mberId;

    private BigDecimal balance;

    private BigDecimal deposit;

    private String alipay;

    private String authName;

    private String authPhone;

    private String authCertNo;

    private String authCertFront;

    private String authCertBack;

    private String authCertHand;

    private Byte status;

    private String remark;

    private String headIcon;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMberId() {
        return mberId;
    }

    public void setMberId(Integer mberId) {
        this.mberId = mberId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay == null ? null : alipay.trim();
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName == null ? null : authName.trim();
    }

    public String getAuthPhone() {
        return authPhone;
    }

    public void setAuthPhone(String authPhone) {
        this.authPhone = authPhone == null ? null : authPhone.trim();
    }

    public String getAuthCertNo() {
        return authCertNo;
    }

    public void setAuthCertNo(String authCertNo) {
        this.authCertNo = authCertNo == null ? null : authCertNo.trim();
    }

    public String getAuthCertFront() {
        return authCertFront;
    }

    public void setAuthCertFront(String authCertFront) {
        this.authCertFront = authCertFront == null ? null : authCertFront.trim();
    }

    public String getAuthCertBack() {
        return authCertBack;
    }

    public void setAuthCertBack(String authCertBack) {
        this.authCertBack = authCertBack == null ? null : authCertBack.trim();
    }

    public String getAuthCertHand() {
        return authCertHand;
    }

    public void setAuthCertHand(String authCertHand) {
        this.authCertHand = authCertHand == null ? null : authCertHand.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }
}