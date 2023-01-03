package com.yunkai.browser.scan;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.taicd.browserIP.R;
import com.yunkai.browser.utils.JsonHelp;


import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ScannerActivity extends AppCompatActivity implements OnClickListener, ScanDataImp {

    private ScanBroadcastReceiver scanBroadcastReceiver = null;
    private Intent intentBroadcast = null;
    private static final String TAG = "ScannerActivity.class";

    ImageView ivBack;

    private RelativeLayout rlScan;
    public String text = "";
    MediaPlayer player;
    MediaPlayer playerSuc, playerFai;
    Vibrator vibrator;
    TextView tv_send;


    String type;
    String scanUrl = "";


    // disposable 是订阅事件，可以用来取消订阅。防止在 activity 或者 fragment 销毁后仍然占用着内存，无法释放。
    private final CompositeDisposable mDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_two);
        initView();

        initHandler();
        player = MediaPlayer.create(getApplicationContext(), R.raw.scan);

        playerSuc = MediaPlayer.create(getApplicationContext(), R.raw.chenggong);//声音初始化
        playerFai = MediaPlayer.create(getApplicationContext(), R.raw.shibai);//声音初始化

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        registerCallbackAndInitScan();
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

    //弹窗列表选择项目
    // 信息列表提示框
    private AlertDialog alertDialog1;

    public void showListAlertDialog(List<String> listName) {

        final List<String> listNameOnly = new ArrayList<>();
        final List<String> listNameId = new ArrayList<>();
        for (int i = 0; i < listName.size(); i++) {
            listNameOnly.add(listName.get(i).substring(listName.get(i).indexOf("*") + 1));
        }
        for (int i = 0; i < listName.size(); i++) {
            listNameId.add(listName.get(i).substring(0, listName.get(i).indexOf("*")));
        }

        final String[] items = listNameOnly.toArray(new String[0]);
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle(getResources().getString(R.string.scan_choose));
        alertBuilder.setItems(items, (arg0, index) -> {
            try {
                new Thread(new JsonHelp(ScannerActivity.this, scanUrl, 0, listNameId.get(index)).postThreadCheckPac).start();//post   PDA二维码检票
            } catch (Exception e) {
                Log.e(TAG, "getScanData: " + e);
                mDisposable.add(Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
                                    playerFai.start();//播放声音
                                    emitter.onNext(1);
                                }).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(state -> {
                                    Log.e(TAG, "handleMessage:state = " + state);
                                    showCircleDialog(getResources().getString(R.string.scan_check_error)
                                            + getResources().getString(R.string.scan_error_error), 1);
                                })
                );
            }

            alertDialog1.dismiss();
        });
        alertDialog1 = alertBuilder.create();
        alertDialog1.show();
    }

    public static Handler handler;
    List<String> listName = new ArrayList<>();

    @SuppressLint("HandlerLeak")
    public void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Object toast = msg.obj;
                if (toast.toString().startsWith("[")) {//说明传值类型是 list，需要弹窗 列表给用户选择项目
                    listName = (List<String>) msg.obj;
                    showListAlertDialog(listName);

                } else {//说明传值类型是 string
                    Log.e(TAG, "handleMessage: msg.obj" + toast);
                    final String data;
                    if (!toast.equals("")) {
                        data = toast.toString();
                    } else {
                        data = getResources().getString(R.string.scan_error_noinfo);
                    }

                    mDisposable.add(Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
                                        boolean is = data.contains(getResources().getString(R.string.scan_suc_suc));
                                        if (is) {
                                            playerSuc.start();
                                        } else {
                                            playerFai.start();
                                        }
                                        emitter.onNext(is);
                                    }).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(state -> {
                                        Log.e(TAG, "handleMessage:state = " + state);
                                        showCircleDialog(data, state ? 2 : 1);
                                    })
                    );
                }
            }
        };
    }

    private void showCircleDialog(String message, int type) {
        //以下是再次启动扫描的代码
        new SweetAlertDialog(this, type)
                .setTitleText(getResources().getString(R.string.scan_check_title))
                .setContentText(message)
                .setCancelText(getResources().getString(R.string.scan_check_quit))
                .setConfirmText(getResources().getString(R.string.scan_check_continue))
                .showCancelButton(true)
                .setCancelClickListener(sDialog -> {
                    sDialog.dismiss();
                    finish();
                })
                .setConfirmClickListener(Dialog::dismiss)
                .show();

    }

    private void initEvent() {
        rlScan.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        rlScan = findViewById(R.id.rl_scan);
        tv_send = findViewById(R.id.tv_send);
        ivBack = findViewById(R.id.iv_back);


        initEvent();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_scan:
                this.sendBroadcast(intentBroadcast);
                break;
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (scanBroadcastReceiver != null) {
            scanBroadcastReceiver.clearAbortBroadcast();
            scanBroadcastReceiver = null;
        }

        if (intentBroadcast != null) {
            intentBroadcast.clone();
            intentBroadcast = null;
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

    @Override
    public void getScanData(String text) {
        Log.e(TAG, "onReceive: type =" + type);
        type = getIntent().getStringExtra("type");
        try {
            switch (type) {
                case "check": //检测票是否有效
                    try {
                        scanUrl = text;
                        new Thread(new JsonHelp(ScannerActivity.this, scanUrl, 0).postThreadCheck).start();//post   PDA二维码检票
                    } catch (Exception e) {
                        Log.e(TAG, "getScanData: " + e);
                        mDisposable.add(Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
                                            scanUrl = "";
                                            playerFai.start();//播放声音
                                            emitter.onNext(1);
                                        }).subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(state -> {
                                            Log.e(TAG, "handleMessage:state = " + state);
                                            showCircleDialog(getResources().getString(R.string.scan_check_error)
                                                    + getResources().getString(R.string.scan_error_error), 1);
                                        })
                        );
                    }

                    break;
                case "account": //获取会员信息
                    //将获取到的id用来获取ic卡信息
                    new Thread(new JsonHelp(ScannerActivity.this, text).postThreadIC).start();//post 获取IC卡会员信息

                    break;
                case "search":
                    String searchTicket = text;
                    try {
                        new Thread(new JsonHelp(searchTicket, ScannerActivity.this).postThreadSearch).start();//post 查询票信息
                    } catch (Exception e) {
                        Log.e(TAG, "onReceive: searchTicket = " + searchTicket + "--" + e);
                    }
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "onReceive: type:" + type + e);
        }
    }


}
