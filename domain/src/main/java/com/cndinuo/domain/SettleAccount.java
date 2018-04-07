package com.cndinuo.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SettleAccount implements Serializable {
    private Integer id;

    private Integer mrhtId;

    private String mrhtName;

    private String settleNo;

    private BigDecimal salesAmount;

    private BigDecimal retAmount;

    private BigDecimal empAmount;

    private BigDecimal settleAmount;

    private BigDecimal deliveryFee;

    private Byte settleStatus;

    private Date settlePeriod;

    private BigDecimal rate;

    private Date createTime;

    private Date settleTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMrhtId() {
        return mrhtId;
    }

    public void setMrhtId(Integer mrhtId) {
        this.mrhtId = mrhtId;
    }

    public String getMrhtName() {
        return mrhtName;
    }

    public void setMrhtName(String mrhtName) {
        this.mrhtName = mrhtName == null ? null : mrhtName.trim();
    }

    public String getSettleNo() {
        return settleNo;
    }

    public void setSettleNo(String settleNo) {
        this.settleNo = settleNo == null ? null : settleNo.trim();
    }

    public BigDecimal getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(BigDecimal salesAmount) {
        this.salesAmount = salesAmount;
    }

    public BigDecimal getRetAmount() {
        return retAmount;
    }

    public void setRetAmount(BigDecimal retAmount) {
        this.retAmount = retAmount;
    }

    public BigDecimal getEmpAmount() {
        return empAmount;
    }

    public void setEmpAmount(BigDecimal empAmount) {
        this.empAmount = empAmount;
    }

    public BigDecimal getSettleAmount() {
        return settleAmount;
    }

    public void setSettleAmount(BigDecimal settleAmount) {
        this.settleAmount = settleAmount;
    }

    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(BigDecimal deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public Byte getSettleStatus() {
        return settleStatus;
    }

    public void setSettleStatus(Byte settleStatus) {
        this.settleStatus = settleStatus;
    }

    public Date getSettlePeriod() {
        return settlePeriod;
    }

    public void setSettlePeriod(Date settlePeriod) {
        this.settlePeriod = settlePeriod;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(Date settleTime) {
        this.settleTime = settleTime;
    }
}