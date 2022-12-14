package com.yunkai.browser.scan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

/**
 * 创建时间：2022/11/26
 *
 * @version 1.0
 * @auther ZhanHaoYuan
 * <p>
 * 扫描结果广播
 */


public class ScanBroadcastReceiver extends BroadcastReceiver {
    private static ScanBroadcastReceiver scanBroadcastReceiver;
    private static ScanDataImp scanData = null;

    public static ScanBroadcastReceiver getInstance(ScanDataImp scanDataImp, Context context) {
        if (scanBroadcastReceiver == null) {
            synchronized (ScanBroadcastReceiver.class) {
                if (scanBroadcastReceiver == null) {
                    scanBroadcastReceiver = new ScanBroadcastReceiver();
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("com.scancode.resault");
                    context.registerReceiver(scanBroadcastReceiver, intentFilter);
                    scanData = scanDataImp;
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
        scanData.getScanData(text);
    }
}
