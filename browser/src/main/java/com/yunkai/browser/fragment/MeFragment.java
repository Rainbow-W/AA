package com.yunkai.browser.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.taicd.browserIP.R;
import com.yunkai.browser.okhttp.HttpServer;
import com.yunkai.browser.okhttp.UserBean;
import com.yunkai.browser.utils.ConfigUtil;


public class MeFragment extends Fragment implements View.OnClickListener {

    private View root;
    private Context context;
    private Button btnLogin;
    private EditText mEditAccount, mEditPw;
    private TextView tvAccount;
    private RelativeLayout rlPw;
    private UserBean userBean;
    private boolean isLogin = false;
    private String account;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_me, container, false);
        context = root.getContext();
        initData();
        initView();
        initHandler();


        return root;
    }


    private AlertDialog alertDialog;

    public void UrlInputbox() {
        final EditText inputServer = new EditText(getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("请输入URL").setView(inputServer)
                .setNegativeButton("取消", null);
        builder.setPositiveButton("确定", (dialog, which) -> {
            String string = inputServer.getText().toString();
            Log.d("TAG", "onClick: --------------------" + string);

            if (!string.isEmpty()) {
                ConfigUtil.setaBoolean(this.getContext(), true);
                ConfigUtil.setUrl(this.getContext(), string);
            }
            alertDialog.dismiss();
        });
        alertDialog = builder.create();
        alertDialog.show();

    }


    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            @SuppressLint("MissingPermission")
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            //mNetworkInfo.isAvailable();
            return mNetworkInfo != null;//有网
        }
        return false;//没有网
    }

    //退出登录成功，显示登录布局
    public void changeLoginShow() {
        mEditAccount.setVisibility(View.VISIBLE);
        tvAccount.setVisibility(View.GONE);
        rlPw.setVisibility(View.VISIBLE);
        mEditPw.setText("");
        btnLogin.setText(getResources().getString(R.string.me_login));
        ConfigUtil.setUserInfo(getContext(), "");
        isLogin = false;
        //退出登录后，对界面等有哪些影响，比如点击其他tab，会怎样

    }

    //登录成功，将登录布局隐藏，换为退出登录
    public void changeLoginHide() {
        tvAccount.setText(account);
        mEditAccount.setText(account);
        mEditAccount.setVisibility(View.GONE);
        tvAccount.setVisibility(View.VISIBLE);
        rlPw.setVisibility(View.GONE);
        btnLogin.setText(getResources().getString(R.string.me_quit));
    }

    public Handler handler;

    /**
     * 登录结果处理
     */
    @SuppressLint("HandlerLeak")
    public void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 2:
                        //登录成功
                        changeLoginHide();
                        Message msg1 = new Message();
                        msg1.what = 0;
                        CheckFragment.handler.sendMessage(msg1);
                        Message msg2 = new Message();
                        msg2.what = 0;
                        TicketFragment.handler.sendMessage(msg2);
                        userBean = (UserBean) msg.obj;
                        Log.e("TAG", "handleMessage: userBean = " + userBean.toString());
                        ConfigUtil.setUserInfo(getContext(), new Gson().toJson(userBean));

                        break;
                    case 1:
                    case 3:
                        Toast.makeText(getActivity(), (String) msg.obj, Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };
    }

    private void initData() {
        String temp = ConfigUtil.getUserInfo(getContext());
        if (temp != null && !temp.equals("")) {
            userBean = new Gson().fromJson(temp, UserBean.class);
            isLogin = true;
        }

    }

    private void initView() {
        btnLogin = root.findViewById(R.id.btn_login);
        mEditAccount = root.findViewById(R.id.edt_login_name_value);
        tvAccount = root.findViewById(R.id.tv_login_name_value);
        mEditPw = root.findViewById(R.id.edt_login_ps_value);
        rlPw = root.findViewById(R.id.rl_pw);

        btnLogin.setOnClickListener(this);

        //判断是否登录 isLogin
        if (isLogin) {//已经登录过
            changeLoginHide();
            tvAccount.setText(userBean.getName());
        }

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            if (isNetworkConnected(getContext())) {
                if (!isLogin) {
                    account = mEditAccount.getText().toString().trim();
                    if (TextUtils.isEmpty(account)) {
                        Toast.makeText(getContext(), "账号不能为空~", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String pw = mEditPw.getText().toString().trim();
                    if (TextUtils.isEmpty(pw)) {
                        Toast.makeText(getContext(), "密码不能为空~", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!ConfigUtil.isaBoolean(context)) {
                        UrlInputbox();
                        return;
                    }


                    HttpServer.getInstance(context).login(ConfigUtil.getAppIMEI(context), account, pw, handler);

                } else {
                    if (isLogin) {//点击退出登录
                        changeLoginShow();
                    }
                }
            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.check_net), Toast.LENGTH_LONG).show();
            }


        }
    }
}
