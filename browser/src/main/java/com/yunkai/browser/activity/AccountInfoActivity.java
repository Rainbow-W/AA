package com.yunkai.browser.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;

import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.taicd.browserIP.R;
import com.yunkai.browser.utils.JsonHelp;
import com.yunkai.browser.view.PullDownMenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.widget.ImageView;
import android.widget.Toast;
import android.os.Handler;

/**
 * Created by Administrator on 2017/11/3.
 */

public class AccountInfoActivity extends Activity {

    ImageView ivBack;
    private PullDownMenu pullDownMenu;
    String info, infoTag;
    private TextView tvNum, tvYuE, tvJiFen, tvTFee, tvicclientname, tvicclientphone, tviccardtype, tviccardstate, tvicendtime;
    Button btnFEE;
    String chooseId, cardnum;
    List<String> listName = new ArrayList<>();
    ImageView tvicclientimg;
    MediaPlayer playerSuc, playerFai;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏(需要继承Activity 而不是AppCompatActivity)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_account_info);

        info = getIntent().getStringExtra("accountinfo");//cardnum
        infoTag = getIntent().getStringExtra("accountinfotag");

        // requestReadExternalPermission();

        initView();
        initData();
        initListenter();

        playerSuc = MediaPlayer.create(getApplicationContext(), R.raw.chenggong);//声音初始化
        playerFai = MediaPlayer.create(getApplicationContext(), R.raw.shibai);//声音初始化

    }

    private void initPullDownMenu(List<String> list) {

        List<String> stringList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            stringList.add(list.get(i).substring(list.get(i).indexOf("*") + 1));
        }
        pullDownMenu.setData(list.get(0).substring(list.get(0).indexOf("*") + 1), stringList, false);


    }


    private void initView() {
        pullDownMenu = findViewById(R.id.tvPullDownMenu);
        ivBack = findViewById(R.id.iv_back);
        tvNum = findViewById(R.id.tv_ic_num);
        tvYuE = findViewById(R.id.tv_yu_e);
        tvJiFen = findViewById(R.id.tv_ji_fen);
        btnFEE = findViewById(R.id.btn_fee);
        tvTFee = findViewById(R.id.tv_count_fee);

        tvicclientname = findViewById(R.id.tv_ic_clientname);
        tvicclientphone = findViewById(R.id.tv_ic_clientphone);
        tviccardtype = findViewById(R.id.tv_ic_cardtype);
        tvicclientimg = findViewById(R.id.tv_ic_clientimg);
        tviccardstate = findViewById(R.id.tv_ic_cardstate);
        tvicendtime = findViewById(R.id.tv_ic_endtime);


    }


    private void initData() {//获取门票列表
        initHandler();
        new Thread(() -> {
            try {
                JsonHelp.getTicketList();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();


        if (!info.equals("")) {
            try {
                JSONObject reader = new JSONObject(info);//使用JSONObject解析

                System.out.println("=====================================" + reader);
                cardnum = reader.getString("cardnum");
                System.out.println(cardnum + "--------" + cardnum.length());
                //cardnum 不够10位的话，要补全为10位数，只有NFC的才需要补全
                if (infoTag != null && infoTag.equals("NFC")) {
                    for (int i = 0; i < 9; i++) {
                        if (cardnum.length() < 10) {
                            cardnum = "0" + cardnum;

                        } else {
                            break;
                        }
                    }
                }
                String credit1 = reader.getString("credit1");//积分
                String credit2 = reader.getString("credit2");//余额
                String realname = reader.getString("realname");//姓名
                String mobile = reader.getString("mobile");//电话
                String type = getString(R.string.account_7); //reader.getString("type");//卡类型
                String photo = reader.getString("photo");//头像
                String state = reader.getString("status");//卡状态
                String endtime = getString(R.string.account_6);//reader.getString("endtime");//有效期

                tvNum.setText(getResources().getString(R.string.account_1, reader.getString("cardnum")));
                tvJiFen.setText(getResources().getString(R.string.account_2, credit1));
                tvYuE.setText(getResources().getString(R.string.account_3, credit2));
                tvicclientname.setText(getResources().getString(R.string.account_4, realname));
                tvicclientphone.setText(getResources().getString(R.string.account_5, mobile));
                tviccardtype.setText(type);
                System.out.println("===========photo=========" + photo);

                Glide.with(this)
                        .load(photo)
                        .placeholder(R.drawable.loading)
                        .into(new SimpleTarget<GlideDrawable>() {
                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                tvicclientimg.setImageDrawable(resource);
                            }
                        });

                switch (state) {
                    case "0":
                        tviccardstate.setText("禁用");
                        break;
                    case "1":
                        tviccardstate.setText("可用");
                        break;
                }

                tvicendtime.setText(endtime);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private void initListenter() {
        ivBack.setOnClickListener(view -> finish());
        btnFEE.setOnClickListener(view -> {
            new Thread(new JsonHelp(AccountInfoActivity.this, cardnum, chooseId).postThreadICPay).start();//post IC卡支付

        });
    }

    public static Handler handler, handlerTicket, handlerPay;

    @SuppressLint("HandlerLeak")
    public void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                listName = (List<String>) msg.obj;
                initPullDownMenu(listName);

                //设置默认总金额
                chooseId = listName.get(0).substring(0, listName.get(0).indexOf("*"));//获取对应的id
                String fee01 = listName.get(0);
                tvTFee.setText("Total Amount：" + fee01.substring(fee01.indexOf("  "), fee01.length() - 1) + "元");

            }
        };
        handlerTicket = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String data = (String) msg.obj;
                String data2 = (String) msg.obj;//为了获取position 然后获取对应id
                int i = Integer.parseInt(data2.substring(0, data2.indexOf("*")));
                chooseId = listName.get(i).substring(0, listName.get(i).indexOf("*"));
                String fee = data.substring(data.indexOf("*") + 1);

                tvTFee.setText("Total Amount:" + fee + "$");
            }
        };
        handlerPay = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String data = (String) msg.obj;
                //  Toast.makeText(AccountInfoActivity.this, data, Toast.LENGTH_SHORT).show();
                if (data.equals("支付成功")) {
                    playerSuc.start();//播放声音
                    Toast.makeText(AccountInfoActivity.this, "payment success!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {//支付失败
                    playerFai.start();//播放声音
                    Toast.makeText(AccountInfoActivity.this, "payment failure!", Toast.LENGTH_SHORT).show();

                }

            }
        };

    }


}
