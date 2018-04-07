package com.cndinuo.eunm;

/**
 * 订单状态
 */
public enum OrderStatusEnum {

    /**
     * 订单表中的状态
     */
    STAY_MERCHANT_ORDERS(1,"待商家接单"),
    MERCHANT_RECEIVED_ORDER(2,"商家已接单"),
    RIDER_HURRY_TO_MERCHANT(3,"骑手赶往商家"),
    GOODS_SENT_OUT(4,"商品已送出"),
    ORDER_COMPLETE(5,"订单完成"),
    /**
     * 退款单表中的状态
     */
    RETURN_APPROVE_ORDER(6,"退款审核中"),
    RETURN_REJECT_ORDER(7,"拒绝退款"),
    RETURN_COMPLETE(8,"退款完成"),
    MERCHANT_REJECT_ORDER(9,"商家未接单"),

    /**
     * 订单临时表状态
     */
    ORDER_TEMP_UNPAID(21,"未支付"),
    ORDER_TEMP_COMPLETE(22,"支付完成"),
    ORDER_TEMP_FAIL(23,"支付完成");

    private OrderStatusEnum(Integer code, String name) {
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
        for (OrderStatusEnum o : OrderStatusEnum.values()) {
            if (o.getCode() == code) {
                return o.getName();
            }
        }
        return null;
    }
}
