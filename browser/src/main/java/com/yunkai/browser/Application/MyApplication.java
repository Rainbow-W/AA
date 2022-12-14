package com.yunkai.browser.Application;


import android.app.Application;



/**
 * Created by Administrator on 2017/10/9.
 */

public class MyApplication extends Application {

    public int func=0;  //0 代表红外， 1代表相机扫码。 控制checkFragment和TicketFragment中方法调用

    @Override
    public void onCreate() {
        super.onCreate();

    }

}
