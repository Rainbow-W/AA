package com.yunkai.browser.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yunkai.browser.R;
import com.yunkai.browser.scan.ScannerActivity;
import com.yunkai.browser.utils.ConfigUtil;
import com.yunkai.browser.utils.NetBroadcastReceiver;
import com.yunkai.browser.utils.NetEvent;


public class CheckFragment extends Fragment implements NetEvent {

    private View root;
    private Context context;

    TextView tip;

    RelativeLayout rlScan, rlSearch, rl_scan_button;

    /**
     * 监控网络的广播
     */
    private NetBroadcastReceiver netBroadcastReceiver;
    private String TAG = "CheckFragment";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_check, container, false);
        context = getActivity();


        initView();
        loginOrNo();
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

        if (rlSearch != null) rlSearch.setClickable(true);
        if (rl_scan_button != null) rl_scan_button.setClickable(true);
    }

    @Override
    public void onNetChange(int netMobile) {
        loginOrNo();
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
                loginOrNo();
            }
        };
    }

    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            //mNetworkInfo.isAvailable();
            return mNetworkInfo != null;//有网
        }
        return false;//没有网
    }

    public void loginOrNo() {//也要判断网络
        if (isNetworkConnected(getContext())) {
            if (ConfigUtil.getUserInfo(getContext()) != null) {//登录了，则隐藏提示
                tip.setVisibility(View.GONE);
                rlScan.setVisibility(View.VISIBLE);
            } else {
                tip.setVisibility(View.VISIBLE);
                tip.setText(getResources().getString(R.string.check_login));
                rlScan.setVisibility(View.GONE);
            }
        } else {//没有连接网络
            tip.setVisibility(View.VISIBLE);
            tip.setText(getResources().getString(R.string.check_net));
            rlScan.setVisibility(View.GONE);
        }
    }


    public void initView() {
        tip = root.findViewById(R.id.tv_login_check);
        rlScan = root.findViewById(R.id.rl_scan);

        rlSearch = root.findViewById(R.id.rl_search);
        rl_scan_button = root.findViewById(R.id.rl_scan_check);

        rlSearch.setOnClickListener(view -> {
            Log.e(TAG, "initView: rlSearch 触发点击");
            rlSearch.setClickable(false);
            Intent intent = new Intent(getActivity(), ScannerActivity.class);
            intent.putExtra("type", "search");
            startActivity(intent);
        });

        rl_scan_button.setFocusable(false);
        rl_scan_button.setOnClickListener(view -> {
            Log.e(TAG, "initView: rl_scan_button 触发点击");
            rl_scan_button.setClickable(false);
            Intent intent = new Intent(getActivity(), ScannerActivity.class);
            intent.putExtra("type", "check");
            startActivity(intent);
        });


    }


}
