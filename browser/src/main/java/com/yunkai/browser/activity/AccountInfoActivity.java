package com.yunkai.browser.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;

import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.taicd.browserIP.R;
import com.yunkai.browser.nfc.NFCActivity;
import com.yunkai.browser.okhttp.HttpServer;
import com.yunkai.browser.okhttp.IcCardPayErr;
import com.yunkai.browser.okhttp.MemberListDean;


import com.yunkai.browser.okhttp.TicketsList;
import com.yunkai.browser.okhttp.UserBean;
import com.yunkai.browser.utils.ConfigUtil;
import com.yunkai.browser.view.PullDownMenu;


import java.util.ArrayList;
import java.util.List;

import android.widget.ImageView;
import android.widget.Toast;
import android.os.Handler;

/**
 * Created by Administrator on 2017/11/3.
 */

public class AccountInfoActivity extends Activity {

    private ImageView ivBack;
    private PullDownMenu pullDownMenu;
    private MemberListDean.MemberDean data;
    private UserBean userBean;
    private TextView tvNum, tvYuE, tvJiFen, tvTFee, tvicclientname, tvicclientphone, tviccardtype, tviccardstate, tvicendtime;
    private Button btnFEE;
    private String chooseId, cardnum;
    private List<TicketsList.Tickets> tickets;
    private List<String> listName = new ArrayList<>();
    private ImageView tvicclientimg;
    private MediaPlayer playerSuc, playerFai;
    private String TAG = "AccountInfoActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏(需要继承Activity 而不是AppCompatActivity)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_account_info);


    }

    private void initPullDownMenu(List<String> list) {

        Log.e(TAG, "initPullDownMenu:list " + list);

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


    private void initData() {

        String name = getIntent().getStringExtra("member");
        data = new Gson().fromJson(name, MemberListDean.MemberDean.class);
        Log.e(TAG, "initData: data = " + data);
        String user = ConfigUtil.getUserInfo(this);
        userBean = new Gson().fromJson(user, UserBean.class);

        playerSuc = MediaPlayer.create(getApplicationContext(), R.raw.chenggong);//声音初始化
        playerFai = MediaPlayer.create(getApplicationContext(), R.raw.shibai);//声音初始化

        HttpServer.getInstance(this).getTicketsList(handler, userBean.getClerkid());

        cardnum = data.getCardnum();
        for (int i = 0; i < 9; i++) {
            if (cardnum.length() < 10) {
                cardnum = "0" + cardnum;

            } else {
                break;
            }
        }

        tvNum.setText(getResources().getString(R.string.account_1, cardnum));
        tvJiFen.setText(getResources().getString(R.string.account_2, data.getCredit1()));
        tvYuE.setText(getResources().getString(R.string.account_3, data.getCredit2()));
        tvicclientname.setText(getResources().getString(R.string.account_4, data.getRealname()));
        tvicclientphone.setText(getResources().getString(R.string.account_5, data.getMobile()));
        tviccardtype.setText(data.getCardtype());
        Log.e(TAG, "initData: photo = " + data.getPhoto());
        if (data.getPhoto() != null) {
            Glide.with(this)
                    .load(data.getPhoto())
                    .placeholder(R.drawable.loading)
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            tvicclientimg.setImageDrawable(resource);
                        }
                    });
        }


        switch (data.getStatus()) {
            case "0":
                tviccardstate.setText("禁用");
                break;
            case "1":
                tviccardstate.setText("可用");
                break;
        }

        tvicendtime.setText("");


    }


    private void initListenter() {
        ivBack.setOnClickListener(view -> finish());
        btnFEE.setOnClickListener(view -> {
            btnFEE.setEnabled(false);
            HttpServer.getInstance(this).Settlement(cardnum, chooseId, userBean.getUserid(), ConfigUtil.getAppIMEI(this), handlerPay);
        });
    }

    public static Handler handler, handlerTicket, handlerPay;

    @SuppressLint("HandlerLeak")
    public void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                    case 3:
                        Toast.makeText(AccountInfoActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        tickets = (List<TicketsList.Tickets>) msg.obj;
                        Log.e(TAG, "handleMessage: listName" + tickets.toString());
                        Log.e(TAG, "handleMessage: listName.size" + tickets.size());
                        listName.clear();
                        for (TicketsList.Tickets tickets : tickets) {
                            listName.add(tickets.getName());
                        }
                        initPullDownMenu(listName);
                        chooseId = tickets.get(0).getId();//获取对应的id
                        String fee01 = tickets.get(0).getFee();
                        tvTFee.setText("总金额：" + fee01 + "元");
                        break;
                }
            }
        };
        handlerTicket = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int data = (Integer) msg.obj;
                Log.e(TAG, "handleMessage: " + tickets.get(data).toString());
                chooseId = tickets.get(data).getId();
                String fee = tickets.get(data).getFee();

                tvTFee.setText("总金额:" + fee + "元");
            }
        };
        handlerPay = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                btnFEE.setEnabled(true);
                switch (msg.what) {
                    case 1:
                        IcCardPayErr icCardPayErr = new Gson().fromJson((String) msg.obj, IcCardPayErr.class);
                        Log.e(TAG, "onResponse: IcCardPayErr = " + icCardPayErr.toString());
                        if (icCardPayErr.getErrcode() == 0) {
                            playerSuc.start();//播放声音
                            Toast.makeText(AccountInfoActivity.this, icCardPayErr.getErrmsg(), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {//支付失败
                            playerFai.start();//播放声音
                            Toast.makeText(AccountInfoActivity.this, icCardPayErr.getErrmsg(), Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 3:
                        Toast.makeText(AccountInfoActivity.this, (String) msg.obj, Toast.LENGTH_LONG).show();
                        break;

                }


            }
        };

    }


    @Override
    protected void onStart() {
        super.onStart();
        initHandler();
        initView();
        initData();
        initListenter();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (playerFai != null) {
            playerFai.release();
            playerFai = null;
        }

        if (playerSuc != null) {
            playerSuc.release();
            playerSuc = null;
        }

    }
}
