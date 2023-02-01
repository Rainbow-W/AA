package com.yunkai.browser.fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;

import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yunkai.browser.R;
import com.yunkai.browser.activity.AccountInfoActivity;
import com.yunkai.browser.nfc.NFCActivity;
import com.yunkai.browser.scan.ScannerActivity;
import com.yunkai.browser.utils.ConfigUtil;
import com.yunkai.browser.utils.NetBroadcastReceiver;
import com.yunkai.browser.utils.NetEvent;


import java.util.Objects;

public class TicketFragment extends Fragment implements NetEvent {

    private View root;

    private RelativeLayout rlICButton, rlScanButton;

    private TextView tip;
    private RelativeLayout rlIC;

    public static final int REQUEST_QRCODE_NFC = 0x6;


    /**
     * 监控网络的广播
     */
    private NetBroadcastReceiver netBroadcastReceiver;
    private String TAG = "TicketFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_ticket, container, false);
        initView();
        loginOrNot();
        initICTeck();
        initHandler();

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        //注册广播
        if (netBroadcastReceiver == null) {
            netBroadcastReceiver = new NetBroadcastReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            getActivity().registerReceiver(netBroadcastReceiver, filter);
            /**
             * 设置监听
             */
            netBroadcastReceiver.setNetEvent(this);
        }

        if (rlICButton != null) rlICButton.setClickable(true);

        if (rlScanButton != null) rlScanButton.setClickable(true);
    }

    @Override
    public void onNetChange(int netMobile) {
        loginOrNot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (netBroadcastReceiver != null) {
            //注销广播
            getActivity().unregisterReceiver(netBroadcastReceiver);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    public static Handler handler;

    /**
     * 登录结果处理
     */
    @SuppressLint("HandlerLeak")
    public void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                loginOrNot();
            }
        };
    }

    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return true;//有网
            }
        }
        return false;//没有网
    }

    public void loginOrNot() {//也要判断网络
        if (isNetworkConnected(getContext())) {
            if (ConfigUtil.getUserInfo(getContext()) != null) {//登录了，则隐藏提示
                tip.setVisibility(View.GONE);
                rlIC.setVisibility(View.VISIBLE);
            } else {
                tip.setVisibility(View.VISIBLE);
                tip.setText(getResources().getString(R.string.check_login));
                rlIC.setVisibility(View.GONE);
            }
        } else {//没有连接网络
            tip.setVisibility(View.VISIBLE);
            tip.setText(getResources().getString(R.string.check_net));
            rlIC.setVisibility(View.GONE);
        }
    }


    private void initView() {
        tip = root.findViewById(R.id.tv_login_ticket);
        rlIC = root.findViewById(R.id.rl_ic);


    }

    //检测会员卡相关
    public void initICTeck() {
        rlICButton = root.findViewById(R.id.rl_ic_button);
        rlScanButton = root.findViewById(R.id.rl_red_button);
        rlICButton.setFocusable(false);
        rlScanButton.setFocusable(false);

        rlICButton.setOnClickListener(view -> {
            rlICButton.setClickable(false);
            Toast.makeText(getActivity(), getResources().getString(R.string.check_IC_card), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), NFCActivity.class);
            startActivityForResult(intent, REQUEST_QRCODE_NFC);
        });

        rlScanButton.setOnClickListener(view -> {
            rlScanButton.setClickable(false);
            //扫描会员卡   红外方法
            Intent intent = new Intent(getActivity(), ScannerActivity.class);
            intent.putExtra("type", "account");
            startActivity(intent);

        });
    }


    //跳转会员卡信息
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "onActivityResult: requestCode = " + requestCode);
        Log.e(TAG, "onActivityResult: resultCode = " + resultCode);
        if (data == null) return;
        if (requestCode == REQUEST_QRCODE_NFC) {
            int tpye = data.getExtras().getInt("tpye");
            String result = data.getExtras().getString("result");//得到新Activity 关闭后返回的数据
            switch (tpye) {
                case 1:
                    Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    Intent ii = new Intent(getActivity(), AccountInfoActivity.class);
                    ii.putExtra("member", result);
                    startActivity(ii);
                    break;
            }
        }

    }
}
