package com.yunkai.browser.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.taicd.browserIP.R;
import com.yunkai.browser.utils.GetInfor;

import java.io.File;

/**
 * Created by Administrator on 2017/9/22.
 */

public class WebViewActivity extends Activity implements SurfaceHolder.Callback {
    final private int REQUEST_CODE_ASK_PERMISSIONS = 122;

    //录音
    private MediaRecorder Recorder; //获取系统媒体录音
    private String sPath;//进行录音时用于存储录音文件路径

    //录像
    private SurfaceView mSurfaceview;//可以预览，本地摄像
    private boolean mStartedFlg = false;//是否正在录像
    private MediaRecorder mRecorder;
    private SurfaceHolder mSurfaceHolder;
    private Camera camera;
    private String path;
    private GetInfor info;

    WebView webview;
    private Context context;

    private String index_url = "http://www.taobao.com/"; //应用主页地址

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏(需要继承Activity 而不是AppCompatActivity)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        context = this;
        setContentView(R.layout.activity_main);

        getPermission();


        webview = findViewById(R.id.web);

        //设置WebView属性，能够执行Javascript脚本
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        //加载需要显示的网页
        webview.loadUrl(index_url);
        //设置webView视图
        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边, 返回false会跳转到浏览器访问。
                //view.loadUrl(url);
                // return true;
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        webview.addJavascriptInterface(new JsInteration(), "$java");

        //摄像预览
        mSurfaceview = (SurfaceView) findViewById(R.id.surfaceview);
        info = new GetInfor();
        SurfaceHolder holder = mSurfaceview.getHolder();
        holder.addCallback(this);
        // setType必须设置，要不出错.
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    }


    //js调用此方法
    public class JsInteration {
        //加上此注释才能被h5调用到
        @JavascriptInterface
        public String j2JgetSoundPath(int motionEvent) {
            String data = "";
            if (motionEvent == 0) {
                try {
                    Recorder = new MediaRecorder();
                    Recorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置音源为micphone

                    Recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);// 设置封装格式(THREE_GPP)
                    Recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);// 设置编码格式
                    // 定义存储文件名称
                    sPath = context.getExternalCacheDir().getPath() + File.separator + "voice_cache.amr";

                    Recorder.setOutputFile(sPath);
                    Recorder.prepare();
                    Recorder.start();

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    System.out.println(e.toString());
                }


            } else if (motionEvent == 1) {
                try {
                    Recorder.stop();
                    Recorder.release();
                    Recorder = null;
                    data = new GetInfor().encodeBase64File(sPath);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e.toString());
                }

            }

            return data;
        }

        @JavascriptInterface
        public void getToast() {//测试是否真的可以调用到
            Toast.makeText(getBaseContext(), "调用到了！", Toast.LENGTH_LONG).show();
        }

        @JavascriptInterface
        public String j2JstartVideo() {//获取1秒的录像,保存并返回Base64
            Toast.makeText(getBaseContext(), "打开录像", Toast.LENGTH_LONG).show();
            // if (mSurfaceview.getVisibility() == View.GONE) {//当预览隐藏的时候显示
            //  mSurfaceview.setVisibility(View.VISIBLE);
            //  }

            if (mRecorder == null) {
                mRecorder = new MediaRecorder();
            }

            try {
                // if(camera==null){
                camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
                //camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
                //  }

            } catch (Exception e) {
                System.out.println("----eee ----" + e.toString());
            }

            if (camera != null) {
                camera.setDisplayOrientation(90); //添加此句，会变成竖屏(若注释此句，还有另外的地方要修改，否则播放的时候角度不对)
                camera.unlock();
                mRecorder.setCamera(camera);
            }


            try {
                // 这两项需要放在setOutputFormat之前
                mRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
                mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

                // Set output file format
                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

                // 这两项需要放在setOutputFormat之后
                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);

                mRecorder.setVideoSize(640, 480);//设置视频的分辨率，在手机上看不出什么区别，可能在大屏幕上投影或者电脑上观看的时候就有差距了
                mRecorder.setVideoFrameRate(30);//设置视频录制的帧率，即1秒钟30帧
                mRecorder.setVideoEncodingBitRate(5 * 1024 * 1024);//直接影响到视频录制的大小，这个设置的越大，视频越清晰
                mRecorder.setOrientationHint(90);
                //设置记录会话的最大持续时间（毫秒）
                // mRecorder.setMaxDuration(30 * 1000);
                mRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());

                path = info.getSDPath();
                if (path != null) {
                    File dir = new File(path + "/recordtest");
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                    path = dir + "/" + info.getDate() + ".mp4";
                    mRecorder.setOutputFile(path);
                    mRecorder.prepare();
                    mRecorder.start();
                    mStartedFlg = true;

                }

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //execute the task
                        try {

                            mRecorder.stop();//这时已经可以生成可播放的文件
                            mRecorder.reset();
                            mRecorder.release();
                            mRecorder = null;

                            // if (camera != null) {
                            //  camera.release();
                            //  camera = null;
                            // }
                            path = info.encodeBase64File(path);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mStartedFlg = false;
                    }
                }, 1000);//1S

            } catch (Exception e) {
                e.printStackTrace();
            }

            return path;
        }

        @JavascriptInterface
        public void j2JcloseVideo() {//关闭录像
            //if(mSurfaceview.getVisibility()==View.VISIBLE){//当预览显示的时候隐藏
            // mSurfaceview.setVisibility(View.GONE);
            //}
            //该关闭的都关闭
            if (camera != null) {
                camera.release();
                camera = null;
            }

            //删除文件夹
            info.deleteDir(info.getSDPath() + "/recordtest");

        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mSurfaceHolder = surfaceHolder;
        new JsInteration().j2JstartVideo();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        // 将holder，这个holder为开始在onCreate里面取得的holder，将它赋给mSurfaceHolder
        mSurfaceHolder = surfaceHolder;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mSurfaceview = null;
        mSurfaceHolder = null;
        if (mRecorder != null) {
            mRecorder.release(); // Now the object cannot be reused
            mRecorder = null;
            //Log.d(TAG, "surfaceDestroyed release mRecorder");
        }
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }


    public void getPermission() {
        //isGetPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        //   &&isGetPermission(Manifest.permission.RECORD_AUDIO)
        //    &&isGetPermission(Manifest.permission.CAMERA)){
        getNeedPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        getNeedPermission(Manifest.permission.RECORD_AUDIO);
        getNeedPermission(Manifest.permission.CAMERA);
    }

    public boolean isGetPermission(String permission) {
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(WebViewActivity.this, permission)) {
            //has permission, do operation directly
            return true;
        } else {//do not have permission
            return false;
        }
    }

    public void getNeedPermission(String permission) {
        if (isGetPermission(permission)) {
            //获取了所需权限
        } else {//do not have permission
            //Log.i(DEBUG_TAG, "user do not have this permission!");
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(WebViewActivity.this,
                    permission)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                //Log.i(DEBUG_TAG, "we should explain why we need this permission!");
            } else {

                // No explanation needed, we can request the permission.
                // Log.i(DEBUG_TAG, "==request the permission==");

                ActivityCompat.requestPermissions(WebViewActivity.this,
                        new String[]{permission},
                        REQUEST_CODE_ASK_PERMISSIONS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    System.out.println("----------获取到权限");
                    //Log.i(DEBUG_TAG, "user granted the permission!");

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    //Log.i(DEBUG_TAG, "user denied the permission!");
                }
                return;
            }
        }
    }


}
