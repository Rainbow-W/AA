package com.yunkai.browser.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
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

import com.taicd.browserIP.R;
import com.yunkai.browser.utils.JsonHelp;

import static com.yunkai.browser.activity.PhoneHomeAppActivity.mySharePreferences;


public class MeFragment extends Fragment {

    View root;

    Button btnLogin;
    EditText mEditAccount, mEditPw;
    TextView tvAccount;
    RelativeLayout rlPw;


    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_me, container, false);


        //实例化SharedPreferences.Editor对象
        editor = mySharePreferences.edit();


        initView();
        initHandler();

        btnLogin.setOnClickListener(view -> {
            //判断是否登录 isLogin
            boolean isLogin = mySharePreferences.getBoolean("isLogin", false);

            boolean isUrl = mySharePreferences.getBoolean("isUrl", false);

            if (!isUrl) {
                UrlInputbox();
                return;
            }

            if (isNetworkConnected(getContext())) {
                if (!isLogin && !mEditAccount.getText().toString().trim().equals("") && !mEditPw.getText().toString().trim().equals("")) {


                    new Thread(new JsonHelp(getActivity(), mEditAccount.getText().toString().trim(),
                            mEditPw.getText().toString().trim(), 0).postThreadLogin).start();//post PDA登录  "李小明", "12345678",

                } else {
                    if (isLogin) {//点击退出登录
                        changeLoginShow();

                        Message msg1 = new Message();
                        msg1.what = 1;
                        CheckFragment.handler.sendMessage(msg1);
                        Message msg2 = new Message();
                        msg2.what = 1;
                        TicketFragment.handler.sendMessage(msg2);
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.me_input_tip), Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.check_net), Toast.LENGTH_LONG).show();
            }
        });


        return root;
    }

    private AlertDialog alertDialog1;

    public void UrlInputbox() {
        final EditText inputServer = new EditText(getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("请输入URL").setView(inputServer)
                .setNegativeButton("Cancel", null);
        builder.setPositiveButton("Ok", (dialog, which) -> {
            String string = inputServer.getText().toString();
            Log.d("TAG", "onClick: --------------------" + string);

            if (!string.isEmpty()) {
                editor.putBoolean("isUrl", true);
                editor.putString("Url", "http://" + string);
                editor.commit();
            }
            alertDialog1.dismiss();
        });
        alertDialog1 = builder.create();
        alertDialog1.show();

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
        editor.putBoolean("isLogin", false);//退出登录，将状态设置为FALSE
        editor.commit();
        //退出登录后，对界面等有哪些影响，比如点击其他tab，会怎样

    }

    //登录成功，将登录布局隐藏，换为退出登录
    public void changeLoginHide() {
        String name = mySharePreferences.getString("userName", "");
        tvAccount.setText(name);
        mEditAccount.setText(name);
        mEditAccount.setVisibility(View.GONE);
        tvAccount.setVisibility(View.VISIBLE);
        rlPw.setVisibility(View.GONE);
        btnLogin.setText(getResources().getString(R.string.me_quit));
    }

    public static Handler handler;

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
                    case 0:
                        //登录成功
                        changeLoginHide();
                        Message msg1 = new Message();
                        msg1.what = 0;
                        CheckFragment.handler.sendMessage(msg1);
                        Message msg2 = new Message();
                        msg2.what = 0;
                        TicketFragment.handler.sendMessage(msg2);

                        break;
                    case 1:
                        Toast.makeText(getActivity(), getResources().getString(R.string.me_input_check), Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };
    }


    private void initView() {
        btnLogin = (Button) root.findViewById(R.id.btn_login);
        mEditAccount = (EditText) root.findViewById(R.id.edt_login_name_value);
        tvAccount = (TextView) root.findViewById(R.id.tv_login_name_value);
        mEditPw = (EditText) root.findViewById(R.id.edt_login_ps_value);

        rlPw = (RelativeLayout) root.findViewById(R.id.rl_pw);

        //判断是否登录 isLogin
        boolean isLogin = mySharePreferences.getBoolean("isLogin", false);
        if (isLogin) {//已经登录过
            changeLoginHide();
        }

    }


}
