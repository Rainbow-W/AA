package com.yunkai.browser.scan;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class ClientConfig {

    private static final String PREFERENCES_NAME = "com.aide.ticket";

    private static Context mContext;

    private static SharedPreferences mSharedPreferences;

    private static Map<String, Object> configs = new HashMap<>();

    public static final String OPEN_SCAN = "open_scan";
    public static final String DATA_APPEND_ENTER = "data_append_enter";
    public static final String APPEND_RINGTONE = "append_ringtone";
    public static final String APPEND_VIBRATE = "append_vibate";
    public static final String CONTINUE_SCAN = "continue_scan";
    public static final String SCAN_REPEAT = "scan_repeat";
    public static final String RESET = "reset";


    private static void initDefaultValue() {

    }

    /**
     * 获取分享参数
     *
     * @return
     */
    private static synchronized SharedPreferences getSharedPreferences() {
        if (mSharedPreferences == null) {
            if (mContext != null) {
                mSharedPreferences = mContext.getSharedPreferences(
                        PREFERENCES_NAME, Context.MODE_PRIVATE);
            } else {
                Log.e("SharedPreferencesNUll", "getSharedPreferences error:mContext is null");
            }
        }
        return mSharedPreferences;
    }


    public static String getString(String key) {
        return getString(key, "");
    }

    public static String getString(String key, String defaultValue) {
        String msg = defaultValue;
        try {
            Object obj = configs.get(key);
            msg = obj == null ? "" : obj.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }


    public static int getInt(String key, int defaultValue) {
        int msg = defaultValue;
        try {
            Object obj = configs.get(key);
            if (obj != null) {
                if (obj instanceof Integer) {
                    msg = (Integer) obj;
                } else {
                    msg = Integer.valueOf(obj.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }

    public static long getLong(String key, long defaultValue) {
        long msg = defaultValue;
        try {
            Object obj = configs.get(key);
            if (obj != null) {
                if (obj instanceof Long) {
                    msg = (Long) obj;
                } else {
                    msg = Long.valueOf(obj.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }

    public static float getFloat(String key, float defaultValue) {
        float msg = defaultValue;
        try {
            Object obj = configs.get(key);
            if (obj != null) {
                if (obj instanceof Float) {
                    msg = (Float) obj;
                } else {
                    msg = Float.valueOf(obj.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }


    public static Double getDouble(String key, double defaultValue) {
        double msg = defaultValue;
        try {
            Object obj = configs.get(key);
            if (obj != null) {
                if (obj instanceof Double) {
                    msg = (Double) obj;
                } else {
                    msg = Double.valueOf(obj.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }


    public static Boolean getBoolean(String key, boolean defaultValue) {
        boolean msg = defaultValue;
        try {
            Object obj = configs.get(key);
            if (obj != null) {
                msg = Boolean.valueOf(obj.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }

    public static Boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

}
