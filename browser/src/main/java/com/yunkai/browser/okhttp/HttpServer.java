package com.yunkai.browser.okhttp;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.google.gson.Gson;
import com.yunkai.browser.utils.ConfigUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 创建时间：2021/1/20
 *
 * @version 1.0
 * @auther ZhanHaoYuan
 */
public class HttpServer {
    public static final String TAG = "HttpServer";
    private static String HOST1 = "";
    private static String HOST2 = "";

    private static HttpServer httpServer;


    private static OkHttpClient okHttpClient;


    public static HttpServer getInstance(Context context) {
        if (httpServer == null) {
            synchronized (HttpServer.class) {
                if (httpServer == null) {
                    httpServer = new HttpServer();
                    okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(30, TimeUnit.SECONDS)//设置连接超时时间
                            .readTimeout(15, TimeUnit.SECONDS)//设置读取超时时间
                            .build();
                    HOST1 = "http://" + ConfigUtil.getUrl(context) + "ad.18bang.cn/";
                    HOST2 = "http://" + ConfigUtil.getUrl(context) + "qt.18bang.cn";
                }
            }
        }
        return httpServer;
    }

    /**
     * PDA登录
     * http://ticketqt.18bang.cn/
     *
     * @param imei_num 机器IMEI码
     * @param username 登录用户名
     * @param password 登录密码
     */
    public void login(String imei_num, String username, String password, Handler handler) {

        String strUrl = HOST2 + "/Api/PdaDevice/loginPdaDevice";
        Log.e(TAG, "login: strUrl=" + strUrl);
        Log.e(TAG, "imei_num=" + imei_num);
        Log.e(TAG, "username=" + username);
        Log.e(TAG, "password=" + password);

        FormBody formBody = new FormBody.Builder()
                .add("imei_num", imei_num)
                .add("username", username)
                .add("password", password)
                .build();

        final Request request = new Request.Builder().url(strUrl).post(formBody).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: " + e);
                Message msg = new Message();
                msg.what = 3;
                msg.obj = "网络异常!";
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                System.out.println(str);
                Gson gson = new Gson();
                LoginErrBean loginErr = gson.fromJson(str, LoginErrBean.class);
                if (handler != null) {
                    Message msg;
                    if (loginErr.getErrcode() != 0) {
                        msg = handler.obtainMessage(1);
                        msg.obj = loginErr.getErrmsg();
                    } else {
                        msg = handler.obtainMessage(2);
                        msg.obj = loginErr.getData();
                    }
                    handler.sendMessage(msg);
                }
            }
        });
    }


    /**
     * 获取IC卡会员信息
     *
     * @param imei_num
     * @param cardnum
     */
    public void getMemberInfo(String imei_num, String cardnum, final Handler handler) {

        String strUrl = HOST2 + "/Api/PdaDevice/getMemberInfo";

        System.out.println("imei_num" + imei_num);
        System.out.println("cardnum" + cardnum);

        FormBody formBody = new FormBody.Builder()
                .add("imei_num", imei_num)
                .add("cardnum", cardnum)
                .build();

        final Request request = new Request.Builder().url(strUrl).post(formBody).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: " + e);
                Message msg = new Message();
                msg.what = 3;
                msg.obj = "网络异常!";
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                Log.e(TAG, "onResponse: " + str);
                MemberListDean memberListDean = new Gson().fromJson(str, MemberListDean.class);
                Message msg;
                if (memberListDean.getErrcode() != 0) {
                    msg = handler.obtainMessage(2);
                    msg.obj = memberListDean.getErrmsg();
                } else {
                    msg = handler.obtainMessage(1);
                    msg.obj = memberListDean.getData();
                }
                handler.sendMessage(msg);

            }
        });
    }


    /**
     * @param imei_num 机器IMEI码 pdaout
     * @param link_url 二维码内容链接
     * @param clerk_id 员工id
     */
    public void inspectTicket(String imei_num, String link_url, String clerk_id, Handler handler) {

        String strUrl = HOST2 + "/Api/PdaDevice/inspectTicket";

        FormBody formBody = new FormBody.Builder()
                .add("imei_num", imei_num)
                .add("link_url", link_url)
                .add("clerk_id", clerk_id)
                .build();

        final Request request = new Request.Builder().url(strUrl).post(formBody).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: " + e);
                Message msg = new Message();
                msg.what = 3;
                msg.obj = "网络异常!";
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                Log.e(TAG, "onResponse: " + str);
                Message msg = new Message();
                msg.obj = str;
                msg.what = 1;
                handler.sendMessage(msg);
            }
        });
    }


    /**
     * @param imei_num 机器IMEI码
     * @param link_url 二维码内容链接
     * @param item_id  单项票活动id
     * @param clerk_id 员工id
     */
    public void inspectTicket(String imei_num, String link_url, String item_id, String clerk_id, Handler handler) {

        String strUrl = HOST2 + "/Api/PdaDevice/inspectTicket";

        FormBody formBody = new FormBody.Builder()
                .add("imei_num", imei_num)
                .add("link_url", link_url)
                .add("item_id", item_id)
                .add("clerk_id", clerk_id)
                .build();

        final Request request = new Request.Builder().url(strUrl).post(formBody).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: " + e);
                Message msg = new Message();
                msg.what = 3;
                msg.obj = "网络异常!";
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                Log.e(TAG, "onResponse: " + str);
                Message msg = new Message();
                msg.obj = str;
                msg.what = 2;
                handler.sendMessage(msg);
            }
        });
    }


    /**
     * 获取门票列表
     *
     * @param type    机器类型
     * @param handler 通信
     */
    public void getTicketsList(final Handler handler, String type) {

        String strUrl = HOST2 + "/Api/PdaDevice/getTicketsList";
        FormBody formBody = new FormBody.Builder()
                .add("type", type)
                .build();

        final Request request = new Request.Builder().url(strUrl).post(formBody).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: " + e);
                Message msg = new Message();
                msg.what = 3;
                msg.obj = "网络异常!";
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                Gson gson = new Gson();
                TicketsList ticketsList = gson.fromJson(str, TicketsList.class);
                Log.e(TAG, "onResponse: " + ticketsList);
                Message msg;
                if (ticketsList.getErrcode() != 0) {
                    msg = handler.obtainMessage(1);
                    msg.obj = ticketsList.getErrmsg();
                } else {
                    msg = handler.obtainMessage(2);
                    msg.obj = ticketsList.getData();
                }
                handler.sendMessage(msg);

            }
        });
    }


    /**
     * 结算
     */
    public void Settlement(String cardnum, String ticketid, String userid, String imei_num, Handler handler) {
        String strUrl = HOST2 + "/Api/PdaDevice/payByIcCard";

        FormBody formBody = new FormBody.Builder()
                .add("cardnum", cardnum)
                .add("ticketid", ticketid)
                .add("userid", userid)
                .add("imei_num", imei_num)
                .build();

        Request request = new Request.Builder()
                .url(strUrl)
                .post(formBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("TAG", "onFailure: " + e);
                Log.e(TAG, "onFailure: " + e);
                Message msg = new Message();
                msg.what = 3;
                msg.obj = "网络异常!";
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                Log.e(TAG, "onResponse: " + str);
                Message message = new Message();
                message.what = 1;
                message.obj = str;
                handler.sendMessage(message);
            }
        });
    }


}
