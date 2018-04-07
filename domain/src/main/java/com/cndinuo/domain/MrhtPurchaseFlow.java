package com.cndinuo.domain;

import com.cndinuo.annotation.Name;

import java.io.Serializable;
import java.math.BigDecimal;

public class MrhtPurchaseFlow implements Serializable {
    @Name("ID")
    private Integer id;

    @Name("采购单ID")
    private Integer purId;

    @Name("报价")
    private BigDecimal quote;

    @Name("供应商ID")
    private Integer supplierId;

    @Name("供应商名称")
    private String supplierName;

    @Name("是否接受")
    private Byte isAccept;

    @Name("备注")
    private String remark;

    @Name("支付方式")
    private Byte payType;

    @Name("是否可退")
    private String isReturn;

    private String returnName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPurId() {
        return purId;
    }

    public void setPurId(Integer purId) {
        this.purId = purId;
    }

    public BigDecimal getQuote() {
        return quote;
    }

    public void setQuote(BigDecimal quote) {
        this.quote = quote;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public Byte getIsAccept() {
        return isAccept;
    }

    public void setIsAccept(Byte isAccept) {
        this.isAccept = isAccept;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    public String getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(String isReturn) {
        this.isReturn = isReturn;
    }

    public String getReturnName() {
        return returnName;
    }

    public void setReturnName(String returnName) {
        this.returnName = returnName;
    }
}