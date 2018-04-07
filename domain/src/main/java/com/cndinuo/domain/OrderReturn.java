package com.cndinuo.domain;

import com.cndinuo.eunm.OrderStatusEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderReturn implements Serializable {
    private Integer id;

    private Integer mberId;

    private String retNo;

    private String orderNo;

    private BigDecimal retPrice;

    private BigDecimal actualAmount;

    private Byte retStatus;

    private Byte settleStatus;

    private Integer retNum;

    private String retReason;

    private Date retTime;

    private Byte retType;

    private String remark;

    private List<OrderItem> items;

    private OrderItem item;

    private Integer mrhtId;

    private Integer goodsId;

    private String goodsName;

    private String goodsSpec;

    private String imgServer;

    private String nickName;

    private String mrhtName;

    private String statusName;

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

    public String getRetNo() {
        return retNo;
    }

    public void setRetNo(String retNo) {
        this.retNo = retNo == null ? null : retNo.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public BigDecimal getRetPrice() {
        return retPrice;
    }

    public void setRetPrice(BigDecimal retPrice) {
        this.retPrice = retPrice;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public Byte getRetStatus() {
        return retStatus;
    }

    public void setRetStatus(Byte retStatus) {
        this.retStatus = retStatus;
    }

    public Byte getSettleStatus() {
        return settleStatus;
    }

    public void setSettleStatus(Byte settleStatus) {
        this.settleStatus = settleStatus;
    }

    public Integer getRetNum() {
        return retNum;
    }

    public void setRetNum(Integer retNum) {
        this.retNum = retNum;
    }

    public String getRetReason() {
        return retReason;
    }

    public void setRetReason(String retReason) {
        this.retReason = retReason == null ? null : retReason.trim();
    }

    public Date getRetTime() {
        return retTime;
    }

    public void setRetTime(Date retTime) {
        this.retTime = retTime;
    }

    public Byte getRetType() {
        return retType;
    }

    public void setRetType(Byte retType) {
        this.retType = retType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public OrderItem getItem() {
        return item;
    }

    public void setItem(OrderItem item) {
        this.item = item;
    }

    public Integer getMrhtId() {
        return mrhtId;
    }

    public void setMrhtId(Integer mrhtId) {
        this.mrhtId = mrhtId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public String getImgServer() {
        return imgServer;
    }

    public void setImgServer(String imgServer) {
        this.imgServer = imgServer;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMrhtName() {
        return mrhtName;
    }

    public void setMrhtName(String mrhtName) {
        this.mrhtName = mrhtName;
    }

    public String getStatusName() {
        if (getRetStatus() != null) {
            return OrderStatusEnum.getName(getRetStatus().intValue());
        }
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}