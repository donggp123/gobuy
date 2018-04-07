package com.cndinuo.domain;

import com.cndinuo.annotation.Name;

import java.io.Serializable;

public class MrhtStock implements Serializable{

    @Name("ID")
    private Integer id;

    @Name("商户ID")
    private Integer mrhtId;

    @Name("库存量")
    private Integer stockLimit;

    @Name("采购数量")
    private Integer purNum;

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

    public Integer getStockLimit() {
        return stockLimit;
    }

    public void setStockLimit(Integer stockLimit) {
        this.stockLimit = stockLimit;
    }

    public Integer getPurNum() {
        return purNum;
    }

    public void setPurNum(Integer purNum) {
        this.purNum = purNum;
    }
}