package com.cndinuo.domain;

import java.io.Serializable;
import java.util.Date;

public class MberFavorite implements Serializable {

    private Integer id;

    private Integer mberId;

    private Integer mrhtId;

    private Date favTime;

    private String mrhtName;
    private String storeImage;
    private String officeTime;
    private Integer deliveryFee;
    private Double distance;
    private Integer time;
    private Byte office;


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

    public Date getFavTime() {
        return favTime;
    }

    public void setFavTime(Date favTime) {
        this.favTime = favTime;
    }

    public String getMrhtName() {
        return mrhtName;
    }

    public void setMrhtName(String mrhtName) {
        this.mrhtName = mrhtName;
    }

    public String getStoreImage() {
        return storeImage;
    }

    public void setStoreImage(String storeImage) {
        this.storeImage = storeImage;
    }

    public String getOfficeTime() {
        return officeTime;
    }

    public void setOfficeTime(String officeTime) {
        this.officeTime = officeTime;
    }

    public Integer getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(Integer deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Byte getOffice() {
        return office;
    }

    public void setOffice(Byte office) {
        this.office = office;
    }
}