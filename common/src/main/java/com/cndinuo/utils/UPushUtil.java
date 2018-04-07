package com.cndinuo.utils;

import com.cndinuo.common.Const;


/**
 * 友盟推送工具类
 */
public class UPushUtil {

  /*  private static PushClient client = new PushClient();

    public static void sendAndroidUnicast(String deviceToken,String title,String ticker,String text,boolean productionMode) {

        try {
            AndroidUnicast unicast = new AndroidUnicast(Const.UMENG_APP_KEY,Const.UMENG_APP_MASTER_SECRET);
            unicast.setDeviceToken(deviceToken);
            unicast.setTitle(title);
            unicast.setTicker(ticker);
            unicast.setText(text);
            unicast.goAppAfterOpen();
            unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
            if (productionMode == true) {
                unicast.setProductionMode();
            }else{
                unicast.setTestMode();
            }
            client.send(unicast);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendAndroidGroupcast(String title, String ticker, String text, boolean productionMode, JSONObject filter) {
        AndroidGroupcast groupcast = null;
        try {
            groupcast = new AndroidGroupcast(Const.UMENG_APP_KEY,Const.UMENG_APP_MASTER_SECRET);
            groupcast.setFilter(filter);
            groupcast.setTicker(ticker);
            groupcast.setTitle(title);
            groupcast.setText(text);
            groupcast.goAppAfterOpen();
            groupcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
            if (productionMode == true) {
                groupcast.setProductionMode();
            }else{
                groupcast.setTestMode();
            }
            client.send(groupcast);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void sendIOSUnicast(String deviceToken,String alert,String data,boolean productionMode){

        try {
            IOSUnicast unicast = new IOSUnicast(Const.UMENG_APP_KEY,Const.UMENG_APP_MASTER_SECRET);
            unicast.setDeviceToken(deviceToken);
            unicast.setAlert(alert);
            unicast.setBadge(0);
            unicast.setSound("default");
            if (productionMode == true) {
                unicast.setProductionMode();
            } else {
                unicast.setTestMode();
            }
            unicast.setCustomizedField("data", data);
            client.send(unicast);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        UPushUtil.sendAndroidUnicast("Av98ZXy9J_sT29xqKqy3El_ch7c5FqYq3YIySoRQ5M1K","测试一下","测试","测试text",false);
    }*/
}
