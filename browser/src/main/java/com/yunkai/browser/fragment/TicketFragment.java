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

import java.util.Objects;

import static com.yunkai.browser.activity.PhoneHomeAppActivity.mySharePreferences;
import static com.yunkai.browser.fragment.CheckFragment.REQUEST_QRCODE;
import static com.yunkai.browser.fragment.CheckFragment.REQUEST_QRCODE_NFC;

public class TicketFragment extends Fragment implements NetEvent {
    public static String MODULE_FLAG = "module_flag";
    View root;
    Button btnScan;
    Button btnIc, btnScanCard;
    RelativeLayout rlICButton, rlScanButton;

    TextView tip;
    RelativeLayout rlIC;

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
        root = inflater.inflate(R.layout.fragment_ticket, container, false);

        initView();
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
                loginOrNot();

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
                        loginOrNot();
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
                return true;//有网
            }
        }
        return false;//没有网
    }

    public void loginOrNot() {//也要判断网络
        if (isNetworkConnected(getContext())) {
            if (mySharePreferences.getBoolean("isLogin", false)) {//登录了，则隐藏提示
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
        tip = (TextView) root.findViewById(R.id.tv_login_ticket);
        rlIC = (RelativeLayout) root.findViewById(R.id.rl_ic);

        loginOrNot();

        btnScan = (Button) root.findViewById(R.id.btn_scan);

        btnScan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    // ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},1);
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
                } else {

                    Intent i = new Intent(getActivity(), CaptureActivity.class);
                    i.putExtra("type", "time");
                    startActivityForResult(i, REQUEST_QRCODE);
                }
            }
        });
    }

    //检测会员卡相关
    public void initICTeck() {
        rlICButton = (RelativeLayout) root.findViewById(R.id.rl_ic_button);
        rlScanButton = (RelativeLayout) root.findViewById(R.id.rl_red_button);
        btnIc = (Button) root.findViewById(R.id.btn_ic);
        btnScanCard = (Button) root.findViewById(R.id.btn_scan_card);

        rlICButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), getResources().getString(R.string.check_IC_card), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), NFCActivity.class);
                startActivityForResult(intent, REQUEST_QRCODE);
            }
        });
        rlScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   Toast.makeText(getActivity(),"扫描会员卡",Toast.LENGTH_SHORT).show();

                if (((MyApplication) getActivity().getApplication()).func == 0) {
                    //扫描会员卡   红外方法
                    Intent intent = new Intent(getActivity(),
                            ScannerActivity.class);
                    intent.putExtra(MODULE_FLAG, 4);
                    intent.putExtra("type", "account");
                    startActivityForResult(intent, REQUEST_QRCODE);
                } else if (((MyApplication) getActivity().getApplication()).func == 1) {
                    //扫描会员卡   摄像头方法
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        //申请WRITE_EXTERNAL_STORAGE权限
                        // ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},1);
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
                    } else {
                        Intent i = new Intent(getActivity(), CaptureActivity.class);
                        i.putExtra("type", "account");
                        startActivityForResult(i, REQUEST_QRCODE);
                    }
                }

            }
        });
    }

    //跳转会员卡信息
    @Override
    public void onActivityResult(int requestCode, int resultCode,  Intent data) {
        if(data == null)return;
        try {
            if (requestCode == REQUEST_QRCODE) {
                String result = Objects.requireNonNull(data.getExtras()).getString("result");//得到新Activity 关闭后返回的数据
                if (result.equals(getResources().getString(R.string.check_account_error))) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.check_account_error), Toast.LENGTH_LONG).show();
                } else if (result.contains("{")) {//说明是json
                    //  System.out.println("-"+result);//
                    Intent ii = new Intent(getActivity(), AccountInfoActivity.class);
                    ii.putExtra("accountinfo", result);

                    startActivity(ii);
                }

            } else if (requestCode == REQUEST_QRCODE_NFC) {
                String result = Objects.requireNonNull(data.getExtras()).getString("result");//得到新Activity 关闭后返回的数据
                if (result.equals(getResources().getString(R.string.check_account_error))) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.check_account_error), Toast.LENGTH_LONG).show();
                } else if (result.contains("{")) {//说明是json
                    //  System.out.println("-"+result);//
                    Intent ii = new Intent(getActivity(), AccountInfoActivity.class);
                    ii.putExtra("accountinfo", result);
                    ii.putExtra("accountinfotag", "NFC");
                    startActivity(ii);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //二维码相关 相机权限申请
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                Intent i = new Intent(getActivity(), CaptureActivity.class);
                i.putExtra("type", "time");
                startActivityForResult(i, REQUEST_QRCODE);
            } else {

                Toast.makeText(getActivity(), getResources().getString(R.string.check_open_camera), Toast.LENGTH_LONG).show();

            }
        }

    }


}
