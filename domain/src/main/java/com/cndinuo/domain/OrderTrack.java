package com.cndinuo.domain;

import java.io.Serializable;
import java.util.Date;

public class OrderTrack implements Serializable {
    private Integer id;

    private String orderNo;

    private Byte trackStatus;

    private Date trackTime;

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

    public Byte getTrackStatus() {
        return trackStatus;
    }

    public void setTrackStatus(Byte trackStatus) {
        this.trackStatus = trackStatus;
    }

    public Date getTrackTime() {
        return trackTime;
    }

    public void setTrackTime(Date trackTime) {
        this.trackTime = trackTime;
    }
}