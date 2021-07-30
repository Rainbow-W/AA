package com.yunkai.browser.scan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.smartdevice.aidl.ICallBack;
import com.taicd.browserIP.R;
import com.yunkai.browser.utils.JsonHelp;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ScannerActivity extends BaseActivity implements OnClickListener {

    ImageView ivBack;

    private RelativeLayout rlScan;
    private EditText et_code;
    private Button btn_scan, btn_clear, btn_instruction, btn_set;
    public String text = "";
    MediaPlayer player;
    MediaPlayer playerSuc, playerFai;
    Vibrator vibrator;
    private String firstCodeStr = "";
    TextView tv_send, tv_receiver;
    int send = 0, receiver = 0;

    String type;
    String scanUrl = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_two);
        initView();

        initHandler();
        btn_scan.setEnabled(false);//false
        rlScan.setEnabled(false);//false
        player = MediaPlayer.create(getApplicationContext(), R.raw.scan);

        playerSuc = MediaPlayer.create(getApplicationContext(), R.raw.chenggong);//声音初始化
        playerFai = MediaPlayer.create(getApplicationContext(), R.raw.shibai);//声音初始化

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    private void registerCallbackAndInitScan() {
        // 注册回调接口
        try {
            mIzkcService.registerCallBack("Scanner", mCallback);
            mIzkcService.openScan(ClientConfig.getBoolean(ClientConfig.OPEN_SCAN));
            mIzkcService.dataAppendEnter(ClientConfig.getBoolean(ClientConfig.DATA_APPEND_ENTER));
            btn_scan.setEnabled(true);
            rlScan.setEnabled(true);
        } catch (RemoteException e) {
            System.out.println("--00-----" + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    protected void handleStateMessage(final Message message) {
        super.handleStateMessage(message);
        System.out.println("======================handleStateMessage=============================");
        switch (message.what) {
            //服务绑定成功 service bind success
            case MessageType.BaiscMessage.SEVICE_BIND_SUCCESS:
                //Toast.makeText(this, "服务绑定成功", Toast.LENGTH_SHORT).show();
                registerCallbackAndInitScan();
                break;
            //服务绑定失败 service bind fail
            case MessageType.BaiscMessage.SEVICE_BIND_FAIL:
                Toast.makeText(this, getResources().getString(R.string.scan_service_error), Toast.LENGTH_SHORT).show();
                break;
            case MessageType.BaiscMessage.SCAN_RESULT_GET_SUCCESS:
                type = getIntent().getStringExtra("type");
                try {
                    if (type.equals("check")) {//检测票是否有效
                        try {
                            scanUrl = (String) message.obj;
                            new Thread(new JsonHelp(ScannerActivity.this, (String) message.obj, 0).postThreadCheck).start();//post   PDA二维码检票
                        } catch (Exception e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    scanUrl = "";
                                    showCircleDialog(getResources().getString(R.string.scan_check_error)
                                            + getResources().getString(R.string.scan_error_error));
                                    playerFai.start();//播放声音
                                }
                            });
                        }

                    } else if (type.equals("account")) {//获取会员信息
                        //将获取到的id用来获取ic卡信息
                        new Thread(new JsonHelp(ScannerActivity.this, (String) message.obj).postThreadIC).start();//post 获取IC卡会员信息
                    } else if (type.equals("search")) {
                        String searchTicket = (String) message.obj;
                        try {
                            new Thread(new JsonHelp(searchTicket, ScannerActivity.this).postThreadSearch).start();//post 查询票信息
                        } catch (Exception e) {
                            System.out.println(searchTicket + "--" + e.toString());
                        }
                    }
                } catch (Exception e) {
                    System.out.println("type:" + type + e.toString());
                }

                et_code.setText((String) message.obj);
                tv_receiver.setText("R:" + et_code.getText().length());
                break;
        }
    }

    //弹窗列表选择项目
    // 信息列表提示框
    private AlertDialog alertDialog1;

    public void showListAlertDialog(List<String> listName) {

        final List<String> listNameOnly = new ArrayList<>();
        final List<String> listNameId = new ArrayList<>();
        for (int i = 0; i < listName.size(); i++) {
            listNameOnly.add(listName.get(i).substring(listName.get(i).indexOf("*") + 1, listName.get(i).length()));
        }
        for (int i = 0; i < listName.size(); i++) {
            listNameId.add(listName.get(i).substring(0, listName.get(i).indexOf("*")));
        }

        final String[] items = (String[]) listNameOnly.toArray(new String[0]);
        //final String[] items = {"Struts2","Spring","Hibernate","Mybatis","Spring MVC"};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle(getResources().getString(R.string.scan_choose));
        alertBuilder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int index) {
                //Toast.makeText(ScannerActivity.this, listNameId.get(index), Toast.LENGTH_SHORT).show();
                //将选择的项目id进行提交
                try {
                    new Thread(new JsonHelp(ScannerActivity.this, scanUrl, 0, listNameId.get(index)).postThreadCheckPac).start();//post   PDA二维码检票
                } catch (Exception e) {
                    runOnUiThread(() -> {
                        showCircleDialog(getResources().getString(R.string.scan_check_error)
                                + getResources().getString(R.string.scan_error_error));
                        playerFai.start();//播放声音
                    });
                }

                alertDialog1.dismiss();
            }
        });
        alertDialog1 = alertBuilder.create();
        alertDialog1.show();
    }

    public static Handler handler;
    List<String> listName = new ArrayList<>();

    public void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Object toast = (Object) msg.obj;
                if (toast.toString().startsWith("[")) {//说明传值类型是 list，需要弹窗 列表给用户选择项目
                    listName = (List<String>) msg.obj;
                    showListAlertDialog(listName);

                } else {//说明传值类型是 string

                    System.out.println("toast--------" + toast.toString());
                    final String aa;
                    if (toast != null && !toast.equals("")) {
                        aa = toast.toString();
                    } else {
                        //aa="检票失败\n"+"获取信息失败";
                        aa = getResources().getString(R.string.scan_error_noinfo);
                    }

                    runOnUiThread(() -> {
                        //showNormalDialog(aa);
                        showCircleDialog(aa);
                        if (aa.contains(getResources().getString(R.string.scan_suc_suc))) {
                            playerSuc.start();//播放声音
                        } else {
                            playerFai.start();//播放声音
                        }

                    });
                }
            }
        };
    }

    private void showCircleDialog(String message) {

        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getResources().getString(R.string.scan_check_title))
                .setContentText(message)
                .setCancelText(getResources().getString(R.string.scan_check_quit))
                .setConfirmText(getResources().getString(R.string.scan_check_continue))
                .showCancelButton(true)
                .setCancelClickListener(sDialog -> {
                    sDialog.dismiss();
                    finish();
                })
                .setConfirmClickListener(sDialog -> {
                    //以下是再次启动扫描的代码
                    sDialog.dismiss();
                })
                .show();

    }

    private void initEvent() {
        btn_scan.setOnClickListener(this);
        rlScan.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
        btn_instruction.setOnClickListener(this);
        btn_set.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        et_code = findViewById(R.id.et_code);
        btn_scan = findViewById(R.id.btn_scan);
        rlScan = findViewById(R.id.rl_scan);

        btn_clear = findViewById(R.id.btn_clear);
        btn_instruction = findViewById(R.id.btn_instruction);
        btn_set = (Button) findViewById(R.id.btn_set);
        tv_receiver = (TextView) findViewById(R.id.tv_receiver);
        tv_send = (TextView) findViewById(R.id.tv_send);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initEvent();
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.rl_scan:
            case R.id.btn_scan:
                try {
                    mIzkcService.openScan(true);
                    mIzkcService.scan();
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    System.out.println("scan--" + e.toString());
                    e.printStackTrace();
                }
                break;
            case R.id.btn_clear:
                et_code.setText("");
                text = "";
                tv_receiver.setText("R:" + "  ");
                tv_send.setText("S:" + "  ");
                send = 0;
                receiver = 0;
                break;
            case R.id.btn_instruction:
                //intent = new Intent(this, ScanInstructionActivity.class);
                break;
            case R.id.btn_set:
                System.out.println("dayindayin---");

                //	intent = new Intent(this, ScanSetActivity.class);
                //	intent.putExtra(BaseActivity.MODULE_FLAG, 4);
                break;

            default:
                break;
        }

        if (intent != null)
            startActivity(intent);

    }

    ICallBack.Stub mCallback = new ICallBack.Stub() {
        @Override
        public void onReturnValue(byte[] buffer, int size) throws RemoteException {
            String codeStr = new String(buffer, 0, size);
            System.out.println("----scan----" + codeStr);
            if (ClientConfig.getBoolean(ClientConfig.SCAN_REPEAT)) {
                if (firstCodeStr.equals(codeStr)) {
                    vibrator.vibrate(100);
                }
            }

            if (ClientConfig.getBoolean(ClientConfig.APPEND_RINGTONE)) {
                //player.start();
            }
            if (ClientConfig.getBoolean(ClientConfig.APPEND_VIBRATE)) {
                vibrator.vibrate(100);
            }
            firstCodeStr = codeStr;
            //text += codeStr+"\n";
            sendMessage(MessageType.BaiscMessage.SCAN_RESULT_GET_SUCCESS, codeStr.toString().trim());
        }
    };


    @Override
    protected void onDestroy() {
        try {
            mIzkcService.unregisterCallBack("Scanner", mCallback);
        } catch (RemoteException e) {
            System.out.println("111---" + e.toString());
            e.printStackTrace();
        }
//		unregisterReceiver(screenStatusReceiver);
        super.onDestroy();
    }
}
