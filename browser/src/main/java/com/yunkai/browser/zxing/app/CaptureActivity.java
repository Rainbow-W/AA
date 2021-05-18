/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yunkai.browser.zxing.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.taicd.browserIP.R;
import com.yunkai.browser.utils.JsonHelp;
import com.yunkai.browser.utils.TimeUtil;
import com.yunkai.browser.zxing.camera.CameraManager;
import com.yunkai.browser.zxing.decoding.CaptureActivityHandler;
import com.yunkai.browser.zxing.decoding.InactivityTimer;
import com.yunkai.browser.zxing.util.Utils;
import com.yunkai.browser.zxing.view.ViewfinderView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * The barcode reader activity itself. This is loosely based on the
 * CameraPreview example included in the Android SDK.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 * @author Sean Owen
 */
public class CaptureActivity extends AppCompatActivity implements SurfaceHolder.Callback,
        OnClickListener {

    String url;
    private CaptureActivityHandler handler;// 消息中心
    private ViewfinderView viewfinderView;// 绘制扫描区域
    private boolean hasSurface;// 控制调用相机属性
    private Vector<BarcodeFormat> decodeFormats;// 存储二维格式的数组
    private String characterSet;// 字符集
    private InactivityTimer inactivityTimer;// 相机扫描刷新timer
    private MediaPlayer mediaPlayer;// 播放器
    private boolean playBeep;// 声音布尔
    private static final float BEEP_VOLUME = 0.10f;// 声音大小
    private boolean vibrate;// 振动布尔

    // 闪光灯
    private Button flash_btn;
    private boolean isTorchOn = true;

    private String type;//time   or  check

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_capture_layout);

        CameraManager.init(this);

        type=getIntent().getStringExtra("type");

        initHandler();

        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);

        // 返回
        Button mButtonBack = (Button) findViewById(R.id.button_back);
        mButtonBack.setOnClickListener(this);

        flash_btn = (Button) findViewById(R.id.flash_btn);
        flash_btn.setOnClickListener(this);

        // 相册选择
        Button photo_btn = (Button) findViewById(R.id.photo_btn);
        photo_btn.setOnClickListener(this);


        inactivityTimer = new InactivityTimer(this);
    }
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;

    @Override
    protected void onResume() {
        super.onResume();
        // 初始化相机画布
        surfaceView= (SurfaceView) findViewById(R.id.preview_view);
        surfaceHolder= surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;
        // 声音
        playBeep = true;
        // 初始化音频管理器
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        // 振动
        vibrate = true;

        preferences = getSharedPreferences("18bangTicketCheck", Context.MODE_PRIVATE);
        editor = preferences.edit();


    }
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onPause() {

        // 停止相机 关闭闪光灯
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {

        // 停止相机扫描刷新timer
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**
     * 处理扫描结果
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(final Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        System.out.println("type-----------"+type);
        if (resultString.equals("")) {
            Toast.makeText(this, "扫描失败!", Toast.LENGTH_SHORT)
                    .show();
        } else {

            switch (type) {
                case "time":
                    //获取当前扫描时间
                    TimeUtil timeUtil = new TimeUtil();
                    String now = timeUtil.getNowTime();//年月日时分秒


                    String startTime = preferences.getString(result.toString(), "");  //扫描的网址 上次记录的时间

                    if (!startTime.equals("")) {
                        String diff = timeUtil.getTimeDifference(startTime, now);//时分秒
                        System.out.println("时间差： " + diff);
                        Toast.makeText(CaptureActivity.this, "此二维码与上次时间差 " + diff, Toast.LENGTH_LONG).show();
                        editor.putString(result.toString(), now);//扫描的网址作为key
                        editor.commit();
                    } else {
                        //保存当前时间
                        editor.putString(result.toString(), now);//扫描的网址作为key
                        editor.commit();
                        System.out.println("当前时间： " + now);
                        Toast.makeText(CaptureActivity.this, "第一次扫描此二维码 " + now, Toast.LENGTH_LONG).show();
                    }
                    break;
                case "check": //扫码检票
                    url = result.toString();
                    try {
                        new Thread(new JsonHelp(CaptureActivity.this, (String) result.toString(), 0).postThreadCheck).start();//post   PDA二维码检票
                    } catch (Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showCircleDialog("检票失败：" + "失败了!!");
                            }
                        });
                    }


                    break;
                case "account": //获取会员卡信息，扫描得到会员卡信息，然后跳转到会员卡信息界面
                    String cardId = result.toString();
                    //通过cardId 获取到会员信息
                    try {
                        new Thread(new JsonHelp(CaptureActivity.this, cardId).postThreadIC).start();//post 获取IC卡会员信息
                    } catch (Exception e) {
                        System.out.println(cardId + "--" + e.toString());
                    }
                    //关闭Activity
                    //finish();
                    break;
                case "search":
                    String searchTicket = result.toString();
                    System.out.println("search----------");
                    try {
                        new Thread(new JsonHelp(searchTicket, CaptureActivity.this).postThreadSearch).start();//post 查询票信息
                    } catch (Exception e) {
                        System.out.println(searchTicket + "--" + e.toString());
                    }
                    break;
            }


        }
    }

    private void showCircleDialog(String message){

        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("檢票結果")
                .setContentText(message)
                .setCancelText("退出掃描")
                .setConfirmText("繼續掃描")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                        finish();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        //以下是再次启动扫描的代码
                        if (hasSurface) {
                            initCamera(surfaceHolder);
                        } else {
                            surfaceHolder.addCallback(CaptureActivity.this);
                            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
                        }
                        if (handler != null) {
                            handler.restartPreviewAndDecode();
                        }
                        sDialog.dismiss();


                    }
                })
                .show();

    }
    private void showNormalDialog(String message){

        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(CaptureActivity.this);
        //normalDialog.setIcon(R.drawable.icon_dialog);
        //normalDialog.setTitle(title);
        normalDialog.setMessage(message);
        normalDialog.setPositiveButton("退出扫描",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do 关闭扫描页
                        finish();
                    }
                });
        normalDialog.setNegativeButton("继续扫描",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do 再次初始化扫描
                        //以下是再次启动扫描的代码
                        if (hasSurface) {
                            initCamera(surfaceHolder);
                        } else {
                            surfaceHolder.addCallback(CaptureActivity.this);
                            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
                        }
                        if (handler != null) {
                            handler.restartPreviewAndDecode();
                        }
                    }
                });
        // 显示
        normalDialog.setCancelable(false);
        normalDialog.show();
    }

    /**
     * 相册选择图片
     */
    private void selectPhoto() {
/*
        Intent innerIntent = new Intent(); // "android.intent.action.GET_CONTENT"
        if (Build.VERSION.SDK_INT < 19) {
            innerIntent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            innerIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        }*/
        Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT); // "android.intent.action.GET_CONTENT"

        innerIntent.setType("image/*");

        Intent wrapperIntent = Intent.createChooser(innerIntent, "选择二维码图片");

        startActivityForResult(wrapperIntent,
                REQUEST_CODE);
    }

    /**
     * 初始化相机
     */
    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    /**
     * 声音设置
     */
    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    /**
     * 结束后的声音
     */
    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    private static final int REQUEST_CODE = 234;// 相册选择code
    private String photo_path;// 选择图片的路径

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case REQUEST_CODE:
                    String[] proj = { MediaStore.Images.Media.DATA };
                    // 获取选中图片的路径
                    Cursor cursor = getContentResolver().query(data.getData(),
                            proj, null, null, null);

                    if (cursor.moveToFirst()) {

                        int column_index = cursor
                                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        photo_path = cursor.getString(column_index);
                        if (photo_path == null) {
                            photo_path = Utils.getPath(getApplicationContext(),
                                    data.getData());
                        }

                    }

                    cursor.close();

                    releaseImgThread();

                    break;

            }

        }


    }

    /**
     * 解析图片Thread
     */
    private void releaseImgThread() {
        new Thread(new Runnable() {

            @Override
            public void run() {

                Result result = Utils.scanningImage(photo_path);
                if (result == null) {
                    msgHandler.sendEmptyMessage(SHOW_TOAST_MSG);

                } else {
                    // 数据返回
                    String recode = Utils.recode(result.toString());
                    Intent data = new Intent();
                    data.putExtra("LOCAL_PHOTO_RESULT", recode);
                    setResult(300, data);
                    finish();

                }
            }
        }).start();
    }

    //检票信息结果返回
    //弹窗列表选择项目
// 信息列表提示框
    private AlertDialog alertDialog1;
    public void showListAlertDialog(List<String> listName){

        final List<String> listNameOnly=new ArrayList<>();
        final List<String> listNameId=new ArrayList<>();
        for(int i=0;i<listName.size();i++){
            listNameOnly.add(listName.get(i).substring(listName.get(i).indexOf("*")+1,listName.get(i).length()));
        }
        for(int i=0;i<listName.size();i++){
            listNameId.add(listName.get(i).substring(0,listName.get(i).indexOf("*")));
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
                try{
                    new Thread(new JsonHelp(CaptureActivity.this, url,0,listNameId.get(index)).postThreadCheckPac).start();//post   PDA二维码检票
                }catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showCircleDialog(getResources().getString(R.string.scan_check_error)
                                    + getResources().getString(R.string.scan_error_error));
                        }
                    });
                }

                alertDialog1.dismiss();
            }
        });
        alertDialog1 = alertBuilder.create();
        alertDialog1.show();
    }

    public static Handler handlerMy;
    List<String> listName=new ArrayList<String>();
    @SuppressLint("HandlerLeak")
    public void initHandler() {
        handlerMy = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Object toast = (Object) msg.obj;
                if(toast.toString().startsWith("[")){//说明传值类型是 list，需要弹窗 列表给用户选择项目
                    listName=(List<String>)msg.obj;
                    showListAlertDialog(listName);

                }else {//说明传值类型是 string

                    System.out.println("toast--------" + toast.toString());
                    final String aa;
                    if (toast != null && !toast.equals("")) {
                        aa = toast.toString();
                    } else {
                        //aa="检票失败\n"+"获取信息失败";
                        aa = getResources().getString(R.string.scan_error_noinfo);
                    }

                    runOnUiThread(new
                                          Runnable() {
                                              @Override
                                              public void run() {
                                                  //showNormalDialog(aa);
                                                  showCircleDialog(aa);

                                              }
                                          });
                }
            }
        };
    }






    private static final int SHOW_TOAST_MSG = 0;
    Handler msgHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case SHOW_TOAST_MSG:

                    Toast.makeText(getApplicationContext(), "未发现二维码图片", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }
        };
    };

    /*
     * onClick
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.flash_btn:// 闪光灯开关
                if (isTorchOn) {
                    isTorchOn = false;
                    flash_btn.setText("关灯");
                    CameraManager.start();
                } else {
                    isTorchOn = true;
                    flash_btn.setText("开灯");
                    CameraManager.stop();
                }
                break;
            case R.id.photo_btn: {// 选择相册
                selectPhoto();
            }
            break;
            case R.id.button_back: {// 返回
                finish();
            }
            break;

            default:
                break;
        }

    }

}