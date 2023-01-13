package com.yunkai.browser.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ConfigUtil {
    private static final String APP_NAME = "carnie";
    private static final String APP_IMEI = "imei";//设备IMEI
    private static final String LOGIN_USER = "userinfo"; //登陆的用户信息
    private static final String APP_isUrl = "isUrl";//服务器路径状态
    private static final String APP_Url = "url"; //路径


    public static boolean setAppIMEI(Context context, String data) {
        if (context == null) {
            return false;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.edit()
                .putString(APP_IMEI, data)
                .commit();
    }

    public static String getAppIMEI(Context context) {
        if (context == null) {
            return null;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(APP_IMEI, null);
    }

    public static boolean setUserInfo(Context context, String data) {
        if (context == null) {
            return false;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.edit()
                .putString(LOGIN_USER, data)
                .commit();
    }

    public static String getUserInfo(Context context) {
        if (context == null) {
            return null;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(LOGIN_USER, null);
    }


    public static boolean setUrl(Context context, String url) {
        if (context == null) {
            return false;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.edit()
                .putString(APP_Url, url)
                .commit();
    }

    public static String getUrl(Context context) {
        if (context == null) {
            return null;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(APP_Url, null);
    }


    public static boolean setaBoolean(Context context, boolean aBoolean) {
        if (context == null) {
            return false;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.edit()
                .putBoolean(APP_isUrl, aBoolean)
                .commit();
    }

    public static boolean isaBoolean(Context context) {
        if (context == null) {
            return false;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(APP_isUrl, false);
    }


}
