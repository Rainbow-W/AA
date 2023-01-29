package com.yunkai.browser.scan;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.taicd.browserIP.R;
import com.yunkai.browser.activity.AccountInfoActivity;
import com.yunkai.browser.okhttp.HttpServer;
import com.yunkai.browser.okhttp.MemberListDean;
import com.yunkai.browser.okhttp.ScanErrBean;
import com.yunkai.browser.okhttp.TicketBean;
import com.yunkai.browser.okhttp.UserBean;
import com.yunkai.browser.utils.ConfigUtil;


import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ScannerActivity extends AppCompatActivity implements ScanDataImp {

    private ScanBroadcastReceiver scanBroadcastReceiver = null;
    private Intent intentBroadcast = null;
    private static final String TAG = "ScannerActivity.class";

    private ImageView ivBack;

    private RelativeLayout rlScan;
    public String text = "";
    private MediaPlayer playerSuc, playerFai;

    private String type;
    private String scanUrl = "";

    private UserBean userBean;


    // disposable 是订阅事件，可以用来取消订阅。防止在 activity 或者 fragment 销毁后仍然占用着内存，无法释放。
    private final CompositeDisposable mDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        initData();
        initView();

        playerSuc = MediaPlayer.create(getApplicationContext(), R.raw.chenggong);//声音初始化
        playerFai = MediaPlayer.create(getApplicationContext(), R.raw.shibai);//声音初始化


        registerCallbackAndInitScan();
    }

    private void initData() {
        userBean = new Gson().fromJson(ConfigUtil.getUserInfo(this), UserBean.class);
    }

    private long time = 0;

    private void initView() {
        rlScan = findViewById(R.id.rl_scan);
        ivBack = findViewById(R.id.iv_back);
        rlScan.setFocusable(false);
        rlScan.setOnClickListener(v -> {
            if (System.currentTimeMillis() - time > 3000) {
                sendBroadcast(intentBroadcast);
                time = System.currentTimeMillis();
            }
        });

        ivBack.setFocusable(false);
        ivBack.setOnClickListener(v -> {
            Log.e(TAG, "onClick: ivBack 触发点击");
            finish();
        });
    }


    private void registerCallbackAndInitScan() {
        if (scanBroadcastReceiver == null) {
            scanBroadcastReceiver = ScanBroadcastReceiver.getInstance(this);
            scanBroadcastReceiver.setScanData(this);
        }

        if (intentBroadcast == null) {
            intentBroadcast = new Intent();
            intentBroadcast.setAction("com.zkc.keycode");
            intentBroadcast.putExtra("keydown", 136);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (scanBroadcastReceiver != null) {
            scanBroadcastReceiver.cloneImp();
            scanBroadcastReceiver.clearAbortBroadcast();
        }

        if (intentBroadcast != null) {
            intentBroadcast.clone();
        }

        if (playerFai != null) {
            playerFai.release();
            playerFai = null;
        }

        if (playerSuc != null) {
            playerSuc.release();
            playerSuc = null;
        }

        mDisposable.clear();


    }

    //弹窗列表选择项目
    // 信息列表提示框
    private AlertDialog alertDialog1;

    public void showListAlertDialog(List<TicketBean> listName) {

        final List<String> listNameOnly = new ArrayList<>();
        final List<String> listNameId = new ArrayList<>();
        for (int i = 0; i < listName.size(); i++) {
            listNameOnly.add(listName.get(i).getName());
        }
        for (int i = 0; i < listName.size(); i++) {
            listNameId.add(listName.get(i).getId());
        }

        final String[] items = listNameOnly.toArray(new String[0]);
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle(getResources().getString(R.string.scan_choose));
        alertBuilder.setItems(items, (arg0, index) -> {
            HttpServer.getInstance(this).inspectTicket(ConfigUtil.getAppIMEI(this),
                    scanUrl, listNameId.get(index), userBean.getClerkid(), handler);
            alertDialog1.dismiss();
        });
        alertDialog1 = alertBuilder.create();
        alertDialog1.show();
    }

    ScanErrBean scanErr;
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String toast = (String) msg.obj;
            Log.e(TAG, "handleMessage:msg.obj = " + toast);
            int tpye = msg.what;
            Log.e(TAG, "handleMessage:msg.what = " + tpye);
            mDisposable.add(Observable
                    .create((ObservableOnSubscribe<Integer>) emitter -> {
                        switch (tpye) {
                            case 1:
                                scanErr = new Gson().fromJson(toast, ScanErrBean.class);
                                Log.e(TAG, "handleMessage:ScanErrBean = " + scanErr.toString());
                                if (scanErr.getErrcode() == 0) {
                                    if (scanErr.getData() != null) {
                                        emitter.onNext(1);
                                    } else if (scanErr.getInfo() != null) {
                                        if (playerSuc != null)
                                            playerSuc.start();//播放声音
                                        emitter.onNext(3);
                                    }
                                } else if (scanErr.getErrcode() == 1) {
                                    if (playerFai != null) {
                                        playerFai.start();
                                    }
                                    emitter.onNext(2);
                                }
                                break;
                            case 2:
                                scanErr = new Gson().fromJson(toast, ScanErrBean.class);
                                Log.e(TAG, "handleMessage:ScanErrBean = " + scanErr.toString());
                                if (scanErr.getErrcode() == 0) {
                                    if (playerSuc != null)
                                        playerSuc.start();//播放声音
                                    emitter.onNext(3);
                                } else if (scanErr.getErrcode() == 1) {
                                    if (playerFai != null)
                                        playerFai.start();//播放声音
                                    emitter.onNext(4);
                                }
                                break;
                            case 3:
                                emitter.onNext(5);
                                break;
                        }
                    }).onErrorResumeNext(observer -> {
                        Log.e(TAG, "handleMessage: 异常 ");
                        observer.onNext(3);
                    }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(state -> {
                        Log.e(TAG, "handleMessage:state =  " + state);
                        switch (state) {
                            case 1:
                                showListAlertDialog(scanErr.getData());
                                break;
                            case 2:
                                showCircleDialog(scanErr.getErrmsg(), 1);
                                break;
                            case 3:
                                showCircleDialog(scanErr.getErrmsg(), 2);
                                break;
                            case 4:
                                showCircleDialog(scanErr.getErrmsg(), 1);
                                break;
                            case 5:
                                showCircleDialog(toast, 3);
                                break;
                        }
                    })
            );
        }
    };


    @SuppressLint("HandlerLeak")
    public Handler handlers = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    MemberListDean.MemberDean data = (MemberListDean.MemberDean) msg.obj;
                    Intent intent = new Intent(ScannerActivity.this, AccountInfoActivity.class);
                    // 设置你要传的参数
                    Bundle bundle = new Bundle();
                    bundle.putString("member", new Gson().toJson(data));
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                    break;
                case 2:
                case 3:
                    Toast.makeText(ScannerActivity.this, (String) msg.obj, Toast.LENGTH_LONG).show();
                    break;

            }

        }
    };


    private void showCircleDialog(String message, int type) {
        //以下是再次启动扫描的代码
        new SweetAlertDialog(this, type)
                .setContentText(message)
                .setTitleText(getResources().getString(R.string.scan_check_title))
                .setCancelText(getResources().getString(R.string.scan_check_quit))
                .setConfirmText(getResources().getString(R.string.scan_check_continue))
                .showCancelButton(true)
                .setCancelClickListener(sDialog -> {
                    Log.e(TAG, "setCancelClickListener: ");
                    sDialog.dismiss();
                    finish();
                })
                .setConfirmClickListener(sDialog -> {
                    Log.e(TAG, "setConfirmClickListener: ");
                    sDialog.dismiss();
                    time = 0;
                })
                .show();
    }


    @Override
    public void getScanData(String text) {
        type = getIntent().getStringExtra("type");
        Log.e(TAG, "onReceive: type =" + type);
        switch (type) {
            case "check": //检测票是否有效
                scanUrl = text;
                HttpServer.getInstance(this).inspectTicket(ConfigUtil.getAppIMEI(this), scanUrl, userBean.getClerkid(), handler);

                break;
            case "account": //获取会员信息
                scanUrl = text;
                //将获取到的id用来获取ic卡信息
                HttpServer.getInstance(this).getMemberInfo(ConfigUtil.getAppIMEI(this), scanUrl, handlers);
                break;
            case "search":
                scanUrl = text;
                Log.e(TAG, "onReceive: searchTicket = " + scanUrl);
                break;
        }

    }


}
