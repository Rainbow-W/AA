package com.yunkai.browser.utils;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

public class NetBroadcastReceiver extends BroadcastReceiver {

	private NetEvent netEvent;

	@Override
	public void onReceive(Context context, Intent intent) {

		if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
			//检查网络状态的类型
			int netWrokState = NetUtil.getNetWorkState(context);
			if (netEvent != null)
				// 接口回传网络状态的类型
				netEvent.onNetChange(netWrokState);
		}
	}

	public void setNetEvent(NetEvent netEvent) {
		this.netEvent = netEvent;
	}

}