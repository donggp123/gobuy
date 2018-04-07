package com.cndinuo.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class MrhtAccount implements Serializable {
    private Integer mrhtId;

    private BigDecimal amount;

    public Integer getMrhtId() {
        return mrhtId;
    }

    public void setMrhtId(Integer mrhtId) {
        this.mrhtId = mrhtId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}