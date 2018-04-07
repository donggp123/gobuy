package com.cndinuo.domain;

import com.cndinuo.eunm.OrderStatusEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {
    private Integer id;

    private String orderNo;

    private Byte orderType;

    private Integer mberId;

    private Integer mrhtId;

    private String mrhtName;

    private Byte orderStatus;

    private Byte deliveryType;

    private BigDecimal deliveryFee;

    private BigDecimal salesPrice;

    private BigDecimal totalPrice;

    private Date orderTime;

    private Byte payType;

    private Date payTime;

    private Date deliveryTime;

    private String receiptName;

    private String receiptMobile;

    private String receiptAddr;

    private Integer number;

    private BigDecimal unitPrice;

    private BigDecimal payPrice;

    private String goodsName;

    private String goodsImage;

    private String imgServer;

    private String mobile;

    private String remark;

    private Integer version;

    private Byte settleStatus;

    private String distance;

    private List<OrderItem> items;

    private List<MberRiderInfo> riderInfos;

    private OrderItem item;

    private Integer totalNum;

    private String storeImage;

    private String statusName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Byte getOrderType() {
        return orderType;
    }

    public void setOrderType(Byte orderType) {
        this.orderType = orderType;
    }

    public Integer getMberId() {
        return mberId;
    }

    public void setMberId(Integer mberId) {
        this.mberId = mberId;
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

    public Byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Byte getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Byte deliveryType) {
        this.deliveryType = deliveryType;
    }

    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(BigDecimal deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getReceiptName() {
        return receiptName;
    }

    public void setReceiptName(String receiptName) {
        this.receiptName = receiptName == null ? null : receiptName.trim();
    }

    public String getReceiptMobile() {
        return receiptMobile;
    }

    public void setReceiptMobile(String receiptMobile) {
        this.receiptMobile = receiptMobile == null ? null : receiptMobile.trim();
    }

    public String getReceiptAddr() {
        return receiptAddr;
    }

    public void setReceiptAddr(String receiptAddr) {
        this.receiptAddr = receiptAddr == null ? null : receiptAddr.trim();
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(BigDecimal payPrice) {
        this.payPrice = payPrice;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public String getImgServer() {
        return imgServer;
    }

    public void setImgServer(String imgServer) {
        this.imgServer = imgServer;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Byte getSettleStatus() {
        return settleStatus;
    }

    public void setSettleStatus(Byte settleStatus) {
        this.settleStatus = settleStatus;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public List<MberRiderInfo> getRiderInfos() {
        return riderInfos;
    }

    public void setRiderInfos(List<MberRiderInfo> riderInfos) {
        this.riderInfos = riderInfos;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public String getStoreImage() {
        return storeImage;
    }

    public void setStoreImage(String storeImage) {
        this.storeImage = storeImage;
    }

    public OrderItem getItem() {
        return item;
    }

    public void setItem(OrderItem item) {
        this.item = item;
    }

    public String getStatusName() {
        if (getOrderStatus() != null) {
            return OrderStatusEnum.getName(getOrderStatus().intValue());
        }
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}