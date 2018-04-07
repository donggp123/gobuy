package com.cndinuo.domain;

import java.io.Serializable;
import java.util.Date;

public class MberComment implements Serializable {
    private Integer id;

    private String orderNo;

    private Integer mberId;

    private Integer mrhtId;

    private String goodsId;

    private Integer deliClerkId;

    private Byte comType;

    private Byte comLevel;

    private String comImage;

    private String content;

    private Date comTime;

    private Byte reconsider;

    private String nickName;//用户昵称

    private String replyContent;//回复内容

    private Date replyTime;//回复时间

    private String goodsName;

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
        this.orderNo = orderNo;
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

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getDeliClerkId() {
        return deliClerkId;
    }

    public void setDeliClerkId(Integer deliClerkId) {
        this.deliClerkId = deliClerkId;
    }

    public Byte getComType() {
        return comType;
    }

    public void setComType(Byte comType) {
        this.comType = comType;
    }

    public Byte getComLevel() {
        return comLevel;
    }

    public void setComLevel(Byte comLevel) {
        this.comLevel = comLevel;
    }

    public String getComImage() {
        return comImage;
    }

    public void setComImage(String comImage) {
        this.comImage = comImage == null ? null : comImage.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getComTime() {
        return comTime;
    }

    public void setComTime(Date comTime) {
        this.comTime = comTime;
    }

    public Byte getReconsider() {
        return reconsider;
    }

    public void setReconsider(Byte reconsider) {
        this.reconsider = reconsider;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}