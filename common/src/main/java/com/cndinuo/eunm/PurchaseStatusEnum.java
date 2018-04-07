package com.cndinuo.eunm;

/**
 * 采购单状态
 */
public enum PurchaseStatusEnum {


    STAY_QUOTE(1,"待报价"),
    QUOTED_PRICE(2,"已报价"),
    STAY_DELIVER(3,"待送货"),
    STAY_RECEIPT(4,"待收货"),
    STAY_RECEIVABLES(5,"待收款"),
    PUR_CANCEL(6,"取消采购"),
    PUR_COMPLETE(7,"采购完成"),

    /**
     * 退货单状态
     */
    STAY_AGREE(20,"待同意"),
    REJECT_RETURN(21,"拒绝退货"),
    RET_COMPLETE(22,"退货完成");

    private PurchaseStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    private Integer code;
    private String name;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getName(Integer code) {
        for (PurchaseStatusEnum s : PurchaseStatusEnum.values()) {
            if (s.getCode() == code) {
                return s.getName();
            }
        }
        return null;
    }
}
