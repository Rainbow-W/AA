package com.yunkai.browser.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.taicd.browserIP.R;
import com.yunkai.browser.fragment.TicketFragment;
import com.yunkai.browser.fragment.MeFragment;
import com.yunkai.browser.fragment.CheckFragment;


import java.util.ArrayList;
import java.util.List;

import eu.long1.spacetablayout.SpaceTabLayout;

/**
 * Created by Administrator on 2017/11/1.
 */

public class PhoneHomeAppActivity extends AppCompatActivity {

    protected static Context mContext;
    protected static Activity mActivity;

    public ViewPager viewPager;
    public static FragmentPagerAdapter pagerAdapter;

    public static SharedPreferences mySharePreferences;

    // String fragUrl = "";

    SpaceTabLayout tabLayout;

    SharedPreferences.Editor editor;
    boolean isLogin;


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

        getIMEI(this);
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
        // mAdView.startImageCycle();
    }

    @Override
    public void onPause() {
        super.onPause();
        // mAdView.pushImageCycle();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // mAdView.pushImageCycle();
    }


    private long exitTime = 0;

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


    /**
     * 获取CAMERA权限
     */
    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getIMEI(Context context) {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            //申请CAMERA权限
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_PHONE_STATE}, 2);
        } else {
            TelephonyManager mTelephony = (TelephonyManager) mActivity.getSystemService(Context.TELEPHONY_SERVICE);
            assert mTelephony != null;
            if (mTelephony.getDeviceId() != null) {
                return mTelephony.getDeviceId();
            } else {
                return Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
            }

        }
        return null;
    }
}
