package com.cndinuo.domain;

import java.io.Serializable;
import java.util.Date;

public class MberFabulous implements Serializable {
    private Integer id;

    private Integer mberId;

    private Integer mrhtId;

    private Integer goodsId;

    private Date fabTime;

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

    public Date getFabTime() {
        return fabTime;
    }

    public void setFabTime(Date fabTime) {
        this.fabTime = fabTime;
    }
}