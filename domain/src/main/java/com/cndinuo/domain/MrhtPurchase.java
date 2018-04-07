package com.cndinuo.domain;

import com.cndinuo.annotation.Name;

import java.io.Serializable;
import java.math.BigDecimal;

public class MrhtPurchase implements Serializable {
    @Name("ID")
    private Integer id;

    @Name("商户ID")
    private Integer mrhtId;

    private String mrhtName;

    @Name("商品名称")
    private String goodsName;

    @Name("商品规格")
    private String goodsSpec;

    @Name("商品分类")
    private String goodsType;

    @Name("采购数量")
    private Integer num;

    @Name("原价")
    private BigDecimal originalPrice;

    @Name("是否可退")
    private String isReturn;

    @Name("采购单状态")
    private Byte status;

    @Name("供应商ID")
    private Integer supplierId;

    @Name("供应商名称")
    private String supplierName;

    @Name("支付方式")
    private Byte payType;

    @Name("总价")
    private BigDecimal totalPrice;

    @Name("商品条形码")
    private String barCode;
    @Name("分类名称")
    private String typeName;
    @Name("退货理由")
    private String returnName;

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
        this.mrhtName = mrhtName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(String isReturn) {
        this.isReturn = isReturn == null ? null : isReturn.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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

    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getReturnName() {
        return returnName;
    }

    public void setReturnName(String returnName) {
        this.returnName = returnName;
    }
}