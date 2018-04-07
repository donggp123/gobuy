package com.cndinuo.eunm;

/**
 * 订单跟踪状态
 */
public enum OrderTrackEnum {

    ORDER_CONFIRM(1,"订单已提交"),
    ORDER_PAY_SUCCESS(2,"支付成功"),
    ORDER_MERCHANT_ORDERS(3,"商家已接单"),
    ORDER_RIDER_TO_MERCHANT(4,"骑手赶往商家"),
    ORDER_GOODS_SEND_OUT(5,"商品已送出"),
    ORDER_GOODS_ARRIVED(6,"商品已送达");

    private OrderTrackEnum(Integer code, String name) {
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
        for (OrderTrackEnum o : OrderTrackEnum.values()) {
            if (o.getCode() == code) {
                return o.getName();
            }
        }
        return null;
    }
}
