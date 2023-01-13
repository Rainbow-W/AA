package com.yunkai.browser.scan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

/**
 * 创建时间：2022/11/26
 *
 * @version 1.0
 * @auther ZhanHaoYuan
 * <p>
 * 扫描结果广播
 */


public class ScanBroadcastReceiver extends BroadcastReceiver {
    public static ScanBroadcastReceiver scanBroadcastReceiver;
    public static ScanDataImp scanData = null;
    public static Context context;

    public void setScanData(ScanDataImp scanData) {
        this.scanData = scanData;
    }

    public void cloneImp() {
        this.scanData = null;
    }

    public static ScanBroadcastReceiver getInstance(Context temp) {
        if (scanBroadcastReceiver == null) {
            synchronized (ScanBroadcastReceiver.class) {
                if (scanBroadcastReceiver == null) {
                    context = temp;
                    scanBroadcastReceiver = new ScanBroadcastReceiver();
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("com.scancode.resault");
                    context.registerReceiver(scanBroadcastReceiver, intentFilter);
                }

            }
        }
        return scanBroadcastReceiver;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        String text = intent.getExtras().getString("code").trim();
        Log.e("ScanBroadcastReceiver", "ScanBroadcastReceiver code:" + text);
        if (scanData != null) {
            scanData.getScanData(text);
        }
    }
}
