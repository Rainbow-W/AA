package com.yunkai.browser.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.taicd.browserIP.R;
import com.yunkai.browser.fragment.TicketFragment;
import com.yunkai.browser.fragment.MeFragment;
import com.yunkai.browser.fragment.CheckFragment;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import eu.long1.spacetablayout.SpaceTabLayout;

/**
 * Created by Administrator on 2017/11/1.
 */

public class PhoneHomeAppActivity extends AppCompatActivity {
    private String TAG = "PhoneHomeAppActivity";
    protected static Context mContext;
    protected static Activity mActivity;

    public ViewPager viewPager;
    public static FragmentPagerAdapter pagerAdapter;

    public static SharedPreferences mySharePreferences;

    SpaceTabLayout tabLayout;

    SharedPreferences.Editor editor;
    boolean isLogin;

    private long exitTime = 0;

    private final int MY_REQUEST_CODE = 1000;
    /**
     * 读取日历
     * 相机
     * 读取手机联系人
     */
    private String[] permissions = new String[]{
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,

            Manifest.permission.NFC,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.CHANGE_NETWORK_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.CAMERA
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏(需要继承Activity 而不是AppCompatActivity)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_homeapp_phone);
        mContext = this;
        mActivity = this;
        //实例化SharedPreferences对象,参数1是存储文件的名称，参数2是文件的打开方式，当文件不存在时，直接创建，如果存在，则直接使用
        mySharePreferences = getSharedPreferences("aideGroupTicket", Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象
        editor = mySharePreferences.edit();
        isLogin = mySharePreferences.getBoolean("isLogin", false);

        viewPager = (ViewPager) findViewById(R.id.vp);

        //add the fragments you want to display in a List
        final List<Fragment> fragmentList = new ArrayList<>();
        TicketFragment ticketFragment = new TicketFragment();
        CheckFragment checkFragment = new CheckFragment();
        MeFragment meFragment = new MeFragment();
        fragmentList.add(ticketFragment);
        fragmentList.add(checkFragment);
        fragmentList.add(meFragment);

        //设置加载三页面，防止间隔点击的时候轮播图空白一下
        viewPager.setOffscreenPageLimit(3);

        //获取ViewPager
        //创建一个FragmentPagerAdapter对象，该对象负责为ViewPager提供多个Fragment
        pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            //获取第position位置的Fragment
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            //该方法的返回值i表明该Adapter总共包括多少个Fragment
            @Override
            public int getCount() {
                return fragmentList.size();
            }

        };
        //为ViewPager组件设置FragmentPagerAdapter
        viewPager.setAdapter(pagerAdapter);


        tabLayout = findViewById(R.id.spaceTabLayout);

        //we need the savedInstanceState to get the position
        tabLayout.initialize(viewPager, getSupportFragmentManager(), fragmentList, savedInstanceState);
        tabLayout.setTabOneText(getResources().getString(R.string.home_account));
        tabLayout.setTabTwoText(getResources().getString(R.string.home_check));
        tabLayout.setTabThreeText(getResources().getString(R.string.home_set));

        getSerialNum(this);
        init();
    }


    //we need the outState to save the position
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        tabLayout.saveState(outState);
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public void init() {
        PackageManager packageManager = this.getPackageManager();

        PermissionInfo permissionInfo = null;

        for (int i = 0; i < permissions.length; i++) {
            try {
                permissionInfo = packageManager.getPermissionInfo(permissions[i], 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            CharSequence permissionName = permissionInfo.loadLabel(packageManager);
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                // 未获取权限
                Log.i(TAG, "您未获得【" + permissionName + "】的权限 ===>");
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                    Log.i(TAG, "您勾选了不再提示【" + permissionName + "】权限的申请");
                } else {
                    ActivityCompat.requestPermissions(this, permissions, MY_REQUEST_CODE);
                }
            } else {
                Log.i(TAG, "您已获得了【" + permissionName + "】的权限");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PackageManager packageManager = this.getPackageManager();
        PermissionInfo permissionInfo = null;
        for (int i = 0; i < permissions.length; i++) {
            try {
                permissionInfo = packageManager.getPermissionInfo(permissions[i], 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            CharSequence permissionName = permissionInfo.loadLabel(packageManager);
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "您同意了【" + permissionName + "】权限");
            } else {
                Log.i(TAG, "您拒绝了【" + permissionName + "】权限");
            }
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.home_quit), Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static String getSerialNum(Context context) {
        String serial = "";
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {//9.0+
                serial = Build.getSerial();
            } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {//8.0+
                serial = Build.SERIAL;
            } else {//8.0-
                Class<?> c = Class.forName("android.os.SystemProperties");
                Method get = c.getMethod("get", String.class);
                serial = (String) get.invoke(c, "ro.serialno");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serial;
    }

}
