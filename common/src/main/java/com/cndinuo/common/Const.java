package com.cndinuo.common;

public class Const {

    public static final String INIT_PASSWORD = "123456";

    public static final String ERROR_MSG = "网络异常，请检查网络是否正常";

    //消息模板
    public static String MSG_TEMPLATE_QOUTE = "%s向您的采购单号【%s】发起了报价，请您查收";
    public static String MSG_TEMPLATE_CONFIRM = "%s的采购单号【%s】采纳了您的报价，请尽快发货";
    public static String MSG_TEMPLATE_CHANCEL = "%s的采购单号【%s】拒绝您的报价，请您查收";
    public static String MSG_TEMPLATE_DELIVER = "%s的采购单号【%s】已发货，请您查收";
    public static String MSG_TEMPLATE_RETURN = "%s向您发起退货申请，退货单号为【%s】，请您查收";
    public static String MSG_TEMPLATE_RECEIVE = "您发的商品%s已收到，单号为【%s】，请您查收";
    public static String MSG_TEMPLATE_REJECT_RETURN = "%s拒绝了您发起退货申请，退货单号为【%s】，请您查收";
    public static String MSG_TEMPLATE_RECEIVABLES = "%s的采购单号【%s】已收款，请您查收";
    public static String MSG_TEMPLATE_RETUTN_COMPLETE = "%s同意了您发起退货申请，退货单号为【%s】，请您查收";

    //短信模板
    public static String SMS_VERIF_CODE_TEMPLATE = "您请求的验证码是:%s【GOBUY】";
    public static String SMS_SPPROVE_RIDER_ADOPT = "尊敬的%s，您提交的骑手认证信息已经审核通过了，赶快去接单吧【GOBUY】";
    public static String SMS_SPPROVE_RIDER_REFUSE = "尊敬的%s，您提交的骑手认证信息被拒绝，拒绝原因（%s）【GOBUY】";
    public static String SMS_REJECT_RETURN_RODER = "尊敬的用户%s,您申请的退款订单：%s，商家没有审核通过，请联系商家。【GOBUY】";

    //微信支付
    public static String WX_PAY_UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";   //微信统一下单接口地址
    public static String WX_PAY_REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";         //退款申请
    public static String WX_PAY_ORDER_QUERY = "https://api.mch.weixin.qq.com/pay/orderquery";           //查询支付是否成功
    public static String WX_TRADE_TYPE = "APP";              //JSAPI--公众号支付,NATIVE--原生扫码支付,APP--app支付
    public static String WX_REQUEST_METHOD_GET = "GET";
    public static String WX_REQUEST_METHOD_POST = "POST";
    public static String WX_PAY_APPID = "wx30aa9756014a843d";//appid
    public static String WX_PAY_MCH_ID = "1279718701";       //商户号
    public static String WX_PAY_MCH_KEY = "yes6sxma7455yxdeda6zys7d3ivvbjma";

    //支付宝支付
    public static String ALIPAY_URL = "https://openapi.alipay.com/gateway.do";
    public static String ALIPAY_APP_ID = "2017102409493073";
    public static String ALIPAY_APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCEifMEKw4YTZFmPIfAK1zaxddiEzMpW9TSyp8jE+iHbJ/KbFPEo0g6OxsmgvB2/lXOoFlioyXPHvovMK6jnU0tn80Xqcrrv7XxRv9W2/6Zo94BhFhewFO/KviyN1s6FT1RkRUU4oeRIG7bTA6pD0H49S1nCAZHTlqXu0gvF00KiQeA45AF/9mO7Zkwmawh7Lv6NJoV0NGQBz0k09YUsPUpmCuKuz8yte3xZ+UN9OIomyHN4DU0O9VfdCpKNdha8l21ulgVAZt2D689/Qkp7Lk63I5KspFWDzu+LB1cft6NYuaOVMnMUWFcpm9TzPh59DLFrSSgQ6r16Dt4xXVow4pnAgMBAAECggEAf0dasK4IIx6aeuu2Eb2StiU36V82yoj9oFAk8rKzoxmVefb9muCTBBiuwhUMh7lG/d7O+AJk1LqS166Qh18QNrg3ZHoR/MNmHgZoPymVHkZXdG17z8IyqmrB2aN/YA/1itcgv6WrOptC2iX4DjIw0LvilgcGwINmTD/12gMuYOZAeIomqgpAfPtmC85wepDEY1gjAxmDDR+YHvzhkW5uO9yLj1xaTHkDbszEY3EhmYm+Bqh88rHBOnIckw++wn2/MFkPmISRiB+OS/jO25QdlX8vXGRz5FaA9kJyVFQytXgQ9qx7EQFT5JzQKzWjhEraa19sqmeSMXL4Kr42ZNYagQKBgQDYjxn7uYzGearpJOl29FoanuWp7QeNgVhNmDWR253ypCu0nNDwS87gI9xie9p/V1iEAku2beqcb9lgtA44DHyM8bun2PU73t2P0IKB4f1jvFteQYlx/y5RcAEnhk+sSxnDxNQp5+/hOXm+57qKsoIMfdGHX1tJ3ucv5aFvOJLMSQKBgQCcrXkkJTOLUzw7dFq81lublIeQ7mOl2T2CUJ/01GzKtD0m/idQ2d9oPC77p6fBkOFrD+wUtewBA3DpQqto6aFAOewj6U/rkBjxRSn6XFN1Nw7JShB82+V7YuHvmAEnnHpgdRn7s1LepRNJqFjJomu+g8Iib7eMj3t9iuoYuWXBLwKBgQCJMceFSRzdVJEpWQArI0y+FrJq/8ZMKIh3yBkQCbE72jPyHXLk+THbn/xrzAk1qXFjWEz9Cw+aAeYWNJvZeFejtzVqdbGPN3boOHGltTUhbV/5C832kXKXlzyz/IwXHBD7jIPY9J5s8kul3Qq5+bMYFNGcUJX166hNGM44NwAROQKBgGONbk3jPW8nwjvc4NLgOXEP3VvfvX1iHc0xwZFlcIMUJ08haoXbCjNHGmtYyiUaE8Fdapp2RbEXKKVucU/GM2mF5F+lHd0FwHN9iQDFrVnrU//eib9sUdImsHz2zrUGnlOv1IS0GtHRfgHtcA0eoOt6+N28spojiUnp/HkdRGSNAoGAYr4rAH51+l6lsJATZnYi79kt98pUdyzVTz74xEOuobgpCwrZa5icRCsXUEzKPmf5MFMjrkTOYfsPD4nAedgWXlmcA2EVpJSrSzAg0I6HfJF8FtdtAjRx1rYIczIvmS9CsP5lbu4M6ayXRBK6cQM3L4+IvXvGrWCwbymJ1sAmhvw=";
    public static String SIGN_TYPE = "RSA2";
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhInzBCsOGE2RZjyHwCtc2sXXYhMzKVvU0sqfIxPoh2yfymxTxKNIOjsbJoLwdv5VzqBZYqMlzx76LzCuo51NLZ/NF6nK67+18Ub/Vtv+maPeAYRYXsBTvyr4sjdbOhU9UZEVFOKHkSBu20wOqQ9B+PUtZwgGR05al7tILxdNCokHgOOQBf/Zju2ZMJmsIey7+jSaFdDRkAc9JNPWFLD1KZgrirs/MrXt8WflDfTiKJshzeA1NDvVX3QqSjXYWvJdtbpYFQGbdg+vPf0JKey5OtyOSrKRVg87viwdXH7ejWLmjlTJzFFhXKZvU8z4efQyxa0koEOq9eg7eMV1aMOKZwIDAQAB";
    public static String INPUT_CHARSET = "utf-8";
    public static String DATA_TYPE = "json";


    //短信接口
    public static String SMS_URL = "https://api.smsbao.com/sms?u=%s&p=%s&m=%s&c=%s";
    public static String SMS_USER = "943164151";
    public static String SMS_PWD = "395e1ab8b182fed26481b521a2bb8ebd";


    //友盟推送接口
    public static String UMENG_APP_KEY = "599f9360677baa021b00058a";
    public static String UMENG_APP_MASTER_SECRET = "ttkgqd5a4fkmkbpykjmsmvqsqq5h1gd3";



}
