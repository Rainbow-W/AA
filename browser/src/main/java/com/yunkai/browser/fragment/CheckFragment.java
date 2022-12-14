package com.yunkai.browser.fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.taicd.browserIP.R;
import com.yunkai.browser.Application.MyApplication;
import com.yunkai.browser.activity.AccountInfoActivity;
import com.yunkai.browser.nfc.NFCActivity;
import com.yunkai.browser.scan.ScannerActivity;
import com.yunkai.browser.utils.NetBroadcastReceiver;
import com.yunkai.browser.utils.NetEvent;
import com.yunkai.browser.zxing.app.CaptureActivity;

import static com.yunkai.browser.activity.PhoneHomeAppActivity.mySharePreferences;

public class CheckFragment extends Fragment implements NetEvent {

    private View root;
    private Context context;
    public static final int REQUEST_QRCODE = 0x5;
    public static final int REQUEST_QRCODE_NFC = 0x6;
    public static final int REQUEST_QRCODE_SEARCH = 0x7;
    public static String MODULE_FLAG = "module_flag";

    Button btnIc, btnScanCard, btnQrcode;
    TextView tip;

    RelativeLayout rlScan, rlSearch, rl_scan_button;

    /**
     * 网络状态
     */
    private int netMobile;

    /**
     * 监控网络的广播
     */
    private NetBroadcastReceiver netBroadcastReceiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_check, container, false);
        context = getActivity();

        initView();
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
    }

    @Override
    public void onNetChange(int netMobile) {
        this.netMobile = netMobile;
        isNetConnect();
    }

    private void isNetConnect() {
        switch (netMobile) {
            case 1://wifi
            case 0://移动数据
            case -1://没有网络
                //mtvNet.append("\n当前无网络");
                //mtvNet.append("\n当前网络类型:移动数据");
                //mtvNet.append("\n当前网络类型:wifi");
                loginOrNo();

                break;

        }
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
                switch (msg.what) {
                    case 0:
                    case 1:
                        //取消登录成功
                        //登录成功
                        loginOrNo();
                        break;
                }
            }
        };
    }

    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                //mNetworkInfo.isAvailable();
                return true;//有网
            }
        }
        return false;//没有网
    }

    public void loginOrNo() {//也要判断网络
        if (isNetworkConnected(getContext())) {
            if (mySharePreferences.getBoolean("isLogin", false)) {//登录了，则隐藏提示
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
        tip = (TextView) root.findViewById(R.id.tv_login_check);
        btnQrcode = (Button) root.findViewById(R.id.btn_qrcode);
        btnQrcode.setClickable(false);
        rlScan = (RelativeLayout) root.findViewById(R.id.rl_scan);
        rlSearch = (RelativeLayout) root.findViewById(R.id.rl_search);
        rl_scan_button = (RelativeLayout) root.findViewById(R.id.rl_scan_check);


        loginOrNo();

        rlSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (((MyApplication) getActivity().getApplication()).func == 0) {
                    //查询调用红外方法
                    Intent intent = new Intent(getActivity(),
                            ScannerActivity.class);
                    intent.putExtra(MODULE_FLAG, 4);
                    intent.putExtra("type", "search");
                    startActivityForResult(intent, REQUEST_QRCODE_SEARCH);
                } else if (((MyApplication) getActivity().getApplication()).func == 1) {
                    //查询调用摄像头方法
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
                    } else {
                        Intent i = new Intent(getActivity(), CaptureActivity.class);
                        i.putExtra("type", "search");
                        startActivityForResult(i, REQUEST_QRCODE_SEARCH);
                    }
                }

            }
        });

        rl_scan_button.setOnClickListener(view -> {
            //Toast.makeText(context,"正在开启扫描",Toast.LENGTH_SHORT).show();
            if (((MyApplication) getActivity().getApplication()).func == 0) {
                //检票调用红外方法
                Intent intent = new Intent(getActivity(),
                        ScannerActivity.class);
                intent.putExtra(MODULE_FLAG, 4);
                intent.putExtra("type", "check");
                startActivityForResult(intent, REQUEST_QRCODE);
            } else if (((MyApplication) getActivity().getApplication()).func == 1) {
                //检票调用摄像头方法
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    Intent i = new Intent(getActivity(), CaptureActivity.class);
                    i.putExtra("type", "check");
                    startActivityForResult(i, REQUEST_QRCODE);
                }
            }
        });


    }


    //二维码相关 相机权限申请
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent i = new Intent(getActivity(), CaptureActivity.class);
                i.putExtra("type", "check");
                startActivityForResult(i, REQUEST_QRCODE);
            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.check_open_camera), Toast.LENGTH_LONG).show();
            }
        }

    }

    //跳转会员卡信息
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("result-1111--check了");
        if (data == null) return;
        try {
            if (requestCode == REQUEST_QRCODE) {
                String result = data.getExtras().getString("result");//得到新Activity 关闭后返回的数据
                System.out.println("");
                if (result.equals(getResources().getString(R.string.check_account_error))) {
                    Toast.makeText(context, getResources().getString(R.string.check_account_error), Toast.LENGTH_LONG).show();
                } else if (result.contains("{")) {//说明是json
                    Intent ii = new Intent(getActivity(), AccountInfoActivity.class);
                    ii.putExtra("accountinfo", result);
                    startActivity(ii);
                }

            }
        } catch (Exception e) {
            System.out.println("err-----" + e.toString());
            e.printStackTrace();
        }
    }

    //检测会员卡相关，需要隐藏
    public void needHide() {
        btnIc = (Button) root.findViewById(R.id.btn_ic);
        btnScanCard = (Button) root.findViewById(R.id.btn_scan_card);
        btnIc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, getResources().getString(R.string.check_IC_card), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), NFCActivity.class);
                startActivityForResult(intent, REQUEST_QRCODE);
            }
        });


        btnScanCard.setOnClickListener(view -> {

            Intent intent = new Intent(getActivity(),
                    ScannerActivity.class);
            intent.putExtra(MODULE_FLAG, 4);
            intent.putExtra("type", "check");
            startActivityForResult(intent, REQUEST_QRCODE);
        });
    }


}
