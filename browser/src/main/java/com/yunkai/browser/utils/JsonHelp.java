package com.yunkai.browser.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Message;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.taicd.browserIP.R;
import com.yunkai.browser.activity.AccountInfoActivity;
import com.yunkai.browser.fragment.CheckFragment;
import com.yunkai.browser.fragment.MeFragment;
import com.yunkai.browser.scan.ScannerActivity;
import com.yunkai.browser.zxing.app.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.yunkai.browser.activity.PhoneHomeAppActivity.getIMEI;
import static com.yunkai.browser.activity.PhoneHomeAppActivity.mySharePreferences;

/**
 * json相关辅助类
 */
public class JsonHelp {

    static Activity context;
    String cardnum, ticketid;
    String username, password;
    String linkUrl;

    SharedPreferences.Editor editor;

    /**
     * 从指定的URL中获取数组
     *
     * @param urlPath
     * @return
     * @throws Exception
     */
    public static String readParse(String urlPath) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        InputStream inStream = conn.getInputStream();
        while ((len = inStream.read(data)) != -1) {
            outStream.write(data, 0, len);
        }
        inStream.close();
        String str = new String(outStream.toByteArray());
        return new String(str.getBytes("UTF-8"), "UTF-8");//通过out.Stream.toByteArray获取到写的数据    new String(str.getBytes("UTF-8"), "UTF-8");
    }

    //解析
    public static String Analysis(String jsonStr) throws JSONException {
        // 初始化
        //Map<String, Object> map = new Map<String, Object>();
        JSONObject jsonObject = new JSONObject(jsonStr);
        String ss = jsonObject.getString("errcode");
        //jsonArray = new JSONArray(jsonStr);
        //map.put("errcode", jsonObject.getString("errcode"));//	errcode：检票状态，0为检票成功，1为检票失败；
        //map.put("msg", jsonObject.getString("msg"));//	msg：检票结果信息；
//			map.put("info", jsonObject.getString("info"));
//			map.put("title", jsonObject.getString("title"));
//			map.put("fee", jsonObject.getString("fee"));

        return ss;
    }


    //参数说明
//	errcode：检票状态，0为检票成功，1为检票失败；
//	msg：检票结果信息；
//	info：票的信息；
//	title：票名；
//	fee：票价格；


    //post 注册激活IMEI码
    public JsonHelp(Activity context, String user, String pw, String login) {
        this.context = context;
        this.username = user;
        this.password = pw;
    }

    public Thread postThread = new Thread() {
        public void run() {
            post(context, username, password);
        }
    };

    public void post(Activity context, String user, String pw) {
        String strUrl = mySharePreferences.getString("Url", "") + context.getResources().getString(R.string.url_post);
        URL url = null;
        try {
            url = new URL(strUrl);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection(); // 创建一个HTTP连接
            urlConn.setRequestMethod("POST"); // 指定使用POST请求方式
            urlConn.setDoInput(true); // 向连接中写入数据
            urlConn.setDoOutput(true); // 从连接中读取数据
            urlConn.setUseCaches(false); // 禁止缓存
            urlConn.setInstanceFollowRedirects(true); //自动执行HTTP重定向
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // 设置内容类型
            DataOutputStream out = new DataOutputStream(urlConn.getOutputStream()); // 获取输出流
            String param = "imei_num=" + URLEncoder.encode(getIMEI(context), "UTF-8"); //连接要提交的数 //"869949029152723",
            out.writeBytes(param);//将要传递的数据写入数据输出流
            out.flush();  //输出缓存
            out.close(); //关闭数据输出流

            if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) { // 判断是否响应成功
                InputStreamReader in = new InputStreamReader(urlConn.getInputStream(), "utf-8"); // 获得读取的内容，utf-8获取内容的编码
                BufferedReader buffer = new BufferedReader(in); // 获取输入流对象
                String inputLine = null;
                while ((inputLine = buffer.readLine()) != null) {
                    try {
                        JSONObject reader = new JSONObject(inputLine);//使用JSONObject解析
                        System.out.println("---" + reader.toString());
                        String errcode = reader.getString("errcode");

                        //实例化SharedPreferences.Editor对象
                        editor = mySharePreferences.edit();

                        if (errcode.equals("0")) {//激活成功
                            editor.putBoolean("isRegister", true);
                            editor.commit();
                            new Thread(new JsonHelp(context, user, pw, 0).postThreadLogin).start();//post PDA登录
                        } else if (errcode.equals("1")) {//失败
                            String errmsg = reader.getString("errmsg");
                            //System.out.println("000"+errmsg);
                            if (errmsg.trim().contains("已被激活")) {
                                editor.putBoolean("isRegister", true);
                                editor.commit();
                                new Thread(new JsonHelp(context, user, pw, 0).postThreadLogin).start();//post PDA登录
                            } else {
                                Toast.makeText(context, "设备激活失败", Toast.LENGTH_LONG).show();
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                in.close(); //关闭字符输入流
            }
            urlConn.disconnect();  //断开连接

        } catch (Exception e) {
            e.printStackTrace();

        }

    }


    //post PDA登录
    public JsonHelp(Activity context, String username, String password, int i) {
        this.context = context;
        this.username = username;
        this.password = password;
        //实例化SharedPreferences对象,参数1是存储文件的名称，参数2是文件的打开方式，当文件不存在时，直接创建，如果存在，则直接使用
        mySharePreferences = context.getSharedPreferences("aideGroupTicket", Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象
        editor = mySharePreferences.edit();
    }

    public Thread postThreadLogin = new Thread() {
        public void run() {
            postLogin(context, username, password);
        }
    };

    public void postLogin(Context context, String username, String password) {

        String Url = mySharePreferences.getString("Url", "");
        String strUrl = Url + "/Api/PdaDevice/loginPdaDevice";

        Log.d("TAG", "postLogin: ================" + strUrl);

        URL url = null;
        try {
            url = new URL(strUrl);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection(); // 创建一个HTTP连接
            urlConn.setRequestMethod("POST"); // 指定使用POST请求方式
            urlConn.setDoInput(true); // 向连接中写入数据
            urlConn.setDoOutput(true); // 从连接中读取数据
            urlConn.setUseCaches(false); // 禁止缓存
            urlConn.setInstanceFollowRedirects(true); //自动执行HTTP重定向
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // 设置内容类型
            DataOutputStream out = new DataOutputStream(urlConn.getOutputStream()); // 获取输出流
            String param = "imei_num=" + URLEncoder.encode(getIMEI(context), "UTF-8")
                    + "&username="
                    + URLEncoder.encode(username, "UTF-8") + "&password="
                    + URLEncoder.encode(password, "UTF-8") + "&username="
                    + URLEncoder.encode(username, "UTF-8"); //连接要提交的数 //"869949029152723",
            out.writeBytes(param);//将要传递的数据写入数据输出流
            out.flush();  //输出缓存
            out.close(); //关闭数据输出流


            //实例化SharedPreferences对象,参数1是存储文件的名称，参数2是文件的打开方式，当文件不存在时，直接创建，如果存在，则直接使用
            mySharePreferences = context.getSharedPreferences("aideGroupTicket", Activity.MODE_PRIVATE);
            //实例化SharedPreferences.Editor对象
            editor = mySharePreferences.edit();
            if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) { // 判断是否响应成功
                InputStreamReader in = new InputStreamReader(urlConn.getInputStream(), "utf-8"); // 获得读取的内容，utf-8获取内容的编码
                BufferedReader buffer = new BufferedReader(in); // 获取输入流对象
                String inputLine = null;
                while ((inputLine = buffer.readLine()) != null) {
                    try {
                        JSONObject reader = new JSONObject(inputLine);//使用JSONObject解析
                        System.out.println("-PDA登录--" + reader.toString());
                        String errcode = reader.getString("errcode");
                        if (errcode.equals("0")) {//登录成功,需要通知meFragment 登录按钮换成退出登录
                            String data = reader.getString("data");
                            JSONObject readerString = new JSONObject(data);//使用JSONObject解析
                            String userid = readerString.getString("userid");
                            editor.putBoolean("isLogin", true);
                            editor.putString("userName", username);
                            editor.putString("userid", userid);
                            editor.commit();

                            Message msg = new Message();
                            msg.what = 0;
                            MeFragment.handler.sendMessage(msg);
                        } else if (errcode.equals("1")) {//登录失败
                            editor.putBoolean("isLogin", false);
                            editor.commit();
                            Message msg = new Message();
                            msg.what = 1;
                            MeFragment.handler.sendMessage(msg);

                        }
                    } catch (JSONException e) {
                        System.out.println("---pda-00--" + e.toString());
                        e.printStackTrace();
                    }
                }
                in.close(); //关闭字符输入流
            }
            urlConn.disconnect();  //断开连接

        } catch (Exception e) {
            System.out.println("---pda---" + e.toString());
            e.printStackTrace();
        }

    }


    //post 获取IC卡会员信息（扫码）
    public JsonHelp(Activity context, String cardnum) {
        this.context = context;
        this.cardnum = cardnum;
    }

    public Thread postThreadIC = new Thread() {
        public void run() {
            postIC(context, cardnum);
        }
    };

    public void postIC(Activity context, String cardnum) {
        String strUrl = mySharePreferences.getString("Url", "") + context.getResources().getString(R.string.url_post_ic);
        URL url = null;
        try {
            url = new URL(strUrl);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection(); // 创建一个HTTP连接
            urlConn.setRequestMethod("POST"); // 指定使用POST请求方式
            urlConn.setDoInput(true); // 向连接中写入数据
            urlConn.setDoOutput(true); // 从连接中读取数据
            urlConn.setUseCaches(false); // 禁止缓存
            urlConn.setInstanceFollowRedirects(true); //自动执行HTTP重定向
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // 设置内容类型
            DataOutputStream out = new DataOutputStream(urlConn.getOutputStream()); // 获取输出流
            String param = "imei_num=" + URLEncoder.encode(getIMEI(context), "UTF-8")
                    + "&cardnum="
                    + URLEncoder.encode(cardnum, "UTF-8") + "&username="
                    + URLEncoder.encode(mySharePreferences.getString("userName", ""), "UTF-8"); //连接要提交的数
            out.writeBytes(param);//将要传递的数据写入数据输出流
            out.flush();  //输出缓存
            out.close(); //关闭数据输出流

            if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) { // 判断是否响应成功
                InputStreamReader in = new InputStreamReader(urlConn.getInputStream(), "utf-8"); // 获得读取的内容，utf-8获取内容的编码
                BufferedReader buffer = new BufferedReader(in); // 获取输入流对象
                String inputLine = null;
                while ((inputLine = buffer.readLine()) != null) {
                    try {
                        JSONObject reader = new JSONObject(inputLine);//使用JSONObject解析
                        System.out.println(cardnum + "-获取IC卡会员信息--" + reader.toString());
                        String errcode = reader.getString("errcode");
                        if (errcode.equals("0")) {//请求成功
                            String data = reader.getString("data");
                            //数据是使用Intent返回
                            Intent intent = new Intent();
                            //把返回数据存入Intent
                            intent.putExtra("result", data);
                            //设置返回数据,返回给的是CheckFragment
                            context.setResult(CheckFragment.REQUEST_QRCODE, intent);
                            context.finish();


                            //	context.setResult(TicketFragment.REQUEST_QRCODE, intent);

                        } else if (errcode.equals("1")) {//失败
                            //数据是使用Intent返回
                            Intent intent = new Intent();
                            //把返回数据存入Intent
                            intent.putExtra("result", context.getResources().getString(R.string.check_account_error));
                            //设置返回数据,返回给的是CheckFragment
                            //context.setResult(CheckFragment.REQUEST_QRCODE, intent);
                            context.setResult(CheckFragment.REQUEST_QRCODE, intent);
                            context.finish();
                        }
                    } catch (JSONException e) {
                        //数据是使用Intent返回
                        Intent intent = new Intent();
                        //把返回数据存入Intent
                        intent.putExtra("result", context.getResources().getString(R.string.check_account_error));
                        //设置返回数据,返回给的是CaptureActivity
                        //context.setResult(CheckFragment.REQUEST_QRCODE, intent);
                        context.setResult(CheckFragment.REQUEST_QRCODE, intent);
                        context.finish();
                        e.printStackTrace();
                    }
                }
                in.close(); //关闭字符输入流
            }
            urlConn.disconnect();  //断开连接

        } catch (Exception e) {
            //数据是使用Intent返回
            Intent intent = new Intent();
            //把返回数据存入Intent
            intent.putExtra("result", context.getResources().getString(R.string.check_account_error));
            //设置返回数据,返回给的是CaptureActivity
            context.setResult(CheckFragment.REQUEST_QRCODE, intent);
            context.finish();
            e.printStackTrace();
        }

    }

    //post 获取IC卡会员信息 （NFC）
    public Thread postThreadICNFC = new Thread() {
        public void run() {
            postICNFC(context, cardnum);
        }
    };

    public void postICNFC(Activity context, String cardnum) {

        String strUrl = mySharePreferences.getString("Url", "") + context.getResources().getString(R.string.url_post_ic_nfc);
        URL url = null;
        try {
            url = new URL(strUrl);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection(); // 创建一个HTTP连接
            urlConn.setRequestMethod("POST"); // 指定使用POST请求方式
            urlConn.setDoInput(true); // 向连接中写入数据
            urlConn.setDoOutput(true); // 从连接中读取数据
            urlConn.setUseCaches(false); // 禁止缓存
            urlConn.setInstanceFollowRedirects(true); //自动执行HTTP重定向
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // 设置内容类型
            DataOutputStream out = new DataOutputStream(urlConn.getOutputStream()); // 获取输出流
            String param = "imei_num=" + URLEncoder.encode(getIMEI(context), "UTF-8")
                    + "&cardnum="
                    + URLEncoder.encode(cardnum, "UTF-8") + "&username="
                    + URLEncoder.encode(mySharePreferences.getString("userName", ""), "UTF-8"); //连接要提交的数
            out.writeBytes(param);//将要传递的数据写入数据输出流
            out.flush();  //输出缓存
            out.close(); //关闭数据输出流

            if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) { // 判断是否响应成功
                InputStreamReader in = new InputStreamReader(urlConn.getInputStream(), "utf-8"); // 获得读取的内容，utf-8获取内容的编码
                BufferedReader buffer = new BufferedReader(in); // 获取输入流对象
                String inputLine = null;
                while ((inputLine = buffer.readLine()) != null) {
                    try {
                        JSONObject reader = new JSONObject(inputLine);//使用JSONObject解析
                        System.out.println(cardnum + "-获取IC卡会员信息--" + reader.toString());
                        String errcode = reader.getString("errcode");
                        if (errcode.equals("0")) {//请求成功
                            String data = reader.getString("data");
                            //数据是使用Intent返回
                            Intent intent = new Intent();
                            //把返回数据存入Intent
                            intent.putExtra("result", data);
                            //设置返回数据,返回给的是CaptureActivity
                            context.setResult(CheckFragment.REQUEST_QRCODE_NFC, intent);
                            context.finish();


                            //	context.setResult(TicketFragment.REQUEST_QRCODE, intent);

                        } else if (errcode.equals("1")) {//失败
                            //数据是使用Intent返回
                            Intent intent = new Intent();
                            //把返回数据存入Intent
                            intent.putExtra("result", context.getResources().getString(R.string.check_account_error));
                            //设置返回数据,返回给的是CaptureActivity
                            //context.setResult(CheckFragment.REQUEST_QRCODE, intent);
                            context.setResult(CheckFragment.REQUEST_QRCODE_NFC, intent);
                            context.finish();
                        }
                    } catch (JSONException e) {
                        //数据是使用Intent返回
                        Intent intent = new Intent();
                        //把返回数据存入Intent
                        intent.putExtra("result", context.getResources().getString(R.string.check_account_error));
                        //设置返回数据,返回给的是CaptureActivity
                        //context.setResult(CheckFragment.REQUEST_QRCODE, intent);
                        context.setResult(CheckFragment.REQUEST_QRCODE_NFC, intent);
                        context.finish();
                        e.printStackTrace();
                    }
                }
                in.close(); //关闭字符输入流
            }
            urlConn.disconnect();  //断开连接

        } catch (Exception e) {
            //数据是使用Intent返回
            Intent intent = new Intent();
            //把返回数据存入Intent
            intent.putExtra("result", context.getResources().getString(R.string.check_account_error));
            //设置返回数据,返回给的是CaptureActivity
            context.setResult(CheckFragment.REQUEST_QRCODE_NFC, intent);
            context.finish();
            e.printStackTrace();
        }

    }

    //post    IC卡支付
    public JsonHelp(Activity context, String cardnum, String ticketid) {
        this.context = context;
        this.cardnum = cardnum;
        this.ticketid = ticketid;
    }

    public Thread postThreadICPay = new Thread() {
        public void run() {
            postICPay(context, cardnum, ticketid);
        }
    };

    public void postICPay(Activity context, String cardnum, String ticketid) {

        String strUrl = mySharePreferences.getString("Url", "") + context.getResources().getString(R.string.url_post_ic_nfc);
        URL url = null;
        try {
            url = new URL(strUrl);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection(); // 创建一个HTTP连接
            urlConn.setRequestMethod("POST"); // 指定使用POST请求方式
            urlConn.setDoInput(true); // 向连接中写入数据
            urlConn.setDoOutput(true); // 从连接中读取数据
            urlConn.setUseCaches(false); // 禁止缓存
            urlConn.setInstanceFollowRedirects(true); //自动执行HTTP重定向
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // 设置内容类型
            DataOutputStream out = new DataOutputStream(urlConn.getOutputStream()); // 获取输出流
            String param = "imei_num=" + URLEncoder.encode(getIMEI(context).trim(), "UTF-8")
                    + "&cardnum="
                    + URLEncoder.encode(cardnum, "UTF-8") + "&ticketid="
                    + URLEncoder.encode(ticketid, "UTF-8") + "&userid="
                    + URLEncoder.encode(mySharePreferences.getString("userid", ""), "UTF-8"); //连接要提交的数
            out.writeBytes(param);//将要传递的数据写入数据输出流
            out.flush();  //输出缓存
            out.close(); //关闭数据输出流

            if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) { // 判断是否响应成功
                InputStreamReader in = new InputStreamReader(urlConn.getInputStream(), "utf-8"); // 获得读取的内容，utf-8获取内容的编码
                BufferedReader buffer = new BufferedReader(in); // 获取输入流对象
                String inputLine = null;
                while ((inputLine = buffer.readLine()) != null) {
                    try {
                        JSONObject reader = new JSONObject(inputLine);//使用JSONObject解析
                        System.out.println("IC卡支付---" + reader.toString());
                        String errcode = reader.getString("errcode");
                        String output = "";
                        if (errcode.equals("0")) {//请求成功
                            output = "支付成功";
                        } else if (errcode.equals("1")) {//失败
                            output = "支付失败";
                        }
                        Message msg = new Message();
                        msg.obj = output;
                        AccountInfoActivity.handlerPay.sendMessage(msg);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                in.close(); //关闭字符输入流
            }
            urlConn.disconnect();  //断开连接

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // GET  获取门票列表
    public static void getTicketList() throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;

        URL url = new URL(mySharePreferences.getString("Url", "") + context.getResources().getString(R.string.url_ticket_list));


        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        InputStream inStream = conn.getInputStream();
        while ((len = inStream.read(data)) != -1) {
            outStream.write(data, 0, len);
        }
        inStream.close();
        String str = new String(outStream.toByteArray());
        JSONObject jsonObject = new JSONObject(new String(str.getBytes("UTF-8"), "UTF-8"));
        System.out.println("-list-" + jsonObject.toString());

        String errcode = jsonObject.getString("errcode");
        String dataInfo = jsonObject.getString("data");
        List<String> listName = new ArrayList<String>();
        if (errcode.equals("0")) {
            JSONArray jsonArray = new JSONArray(dataInfo);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectnew = jsonArray.getJSONObject(i);
                String id = jsonObjectnew.getString("id");
                String name = jsonObjectnew.getString("name");
                String fee = jsonObjectnew.getString("fee");
                listName.add(id + "*" + name + "  " + fee + "元");
            }
            Message msg = new Message();
            msg.obj = listName;
            AccountInfoActivity.handler.sendMessage(msg);

        } else if (errcode.equals("1")) {
        }

        //return  new String(str.getBytes("UTF-8"), "UTF-8");//通过out.Stream.toByteArray获取到写的数据    new String(str.getBytes("UTF-8"), "UTF-8");
    }


    //post   PDA二维码检票
    public JsonHelp(Activity context, String linkUrl, int qr) {
        this.context = context;
        this.linkUrl = linkUrl;
    }

    public Thread postThreadCheck = new Thread() {
        public void run() {
            checkQR(context, linkUrl);
        }
    };

    public void checkQR(Activity context, String linkUrl) {
        // String strUrl =context.getResources().getString(R.string.url_es)+"/Api/PdaDevice/inspectTicket";
        String strUrl = mySharePreferences.getString("Url", "") + context.getResources().getString(R.string.url_check_qr);

        URL url = null;
        try {
            url = new URL(strUrl);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection(); // 创建一个HTTP连接
            urlConn.setRequestMethod("POST"); // 指定使用POST请求方式
            urlConn.setDoInput(true); // 向连接中写入数据
            urlConn.setDoOutput(true); // 从连接中读取数据
            urlConn.setUseCaches(false); // 禁止缓存
            urlConn.setInstanceFollowRedirects(true); //自动执行HTTP重定向
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // 设置内容类型
            DataOutputStream out = new DataOutputStream(urlConn.getOutputStream()); // 获取输出流
            String param = "link_url=" + URLEncoder.encode(linkUrl.trim(), "UTF-8")
                    + "&imei_num=" + URLEncoder.encode(getIMEI(context).trim(), "UTF-8")
                    + "&userid=" + URLEncoder.encode(mySharePreferences.getString("userid", ""), "UTF-8"); //连接要提交的数
            out.writeBytes(param);//将要传递的数据写入数据输出流
            out.flush();  //输出缓存
            out.close(); //关闭数据输出流

            if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) { // 判断是否响应成功
                InputStreamReader in = new InputStreamReader(urlConn.getInputStream(), "utf-8"); // 获得读取的内容，utf-8获取内容的编码
                BufferedReader buffer = new BufferedReader(in); // 获取输入流对象
                String inputLine = null;
                while ((inputLine = buffer.readLine()) != null) {
                    try {
                        JSONObject reader = new JSONObject(inputLine);//使用JSONObject解析
                        System.out.println("inputLine--------" + reader.toString());
                        String errcode = reader.getString("errcode");
                        String output = "";
                        List<String> listName = new ArrayList<String>();
                        if (errcode.equals("0")) {//请求成功
                            String msg = reader.getString("errmsg");
                            if (msg.equals("")) {//inputLine--------{"errcode":0,"errmsg":"","data":[{"id":"5","name":"景区红峡谷漂流门票-演示版"},{"id":"6","name":"儿童乐园-演示版"}]}
                                //需要弹窗列表展示出套票的名称选项，然后根据选择的项目把ID进行回调
                                String dataInfo = reader.getString("data");
                                JSONArray jsonArray = new JSONArray(dataInfo);
                                for (int i = 0; i < jsonArray.length(); i++) {//套票信息
                                    JSONObject jsonObjectnew = jsonArray.getJSONObject(i);
                                    String id = jsonObjectnew.getString("id");
                                    String name = jsonObjectnew.getString("name");
                                    listName.add(id + "*" + name);
                                }

                            } else {
                                String info = reader.getString("info");

                                JSONObject jsonObjectInfo = new JSONObject(info);
                                String title = jsonObjectInfo.getString("title");
                                String fee = jsonObjectInfo.getString("fee");
                                output = msg + "\n" + title + "!";
                            }
                        } else if (errcode.equals("1")) {//失败
                            String msg = reader.getString("errmsg");
                            output = msg;
                        }
                        System.out.println("output--------" + output);
                        Message msg = new Message();
                        if (output.equals("")) {
                            msg.obj = listName;
                        } else {
                            msg.obj = output;
                        }

                        if (context.getLocalClassName().contains("ScannerActivity")) {
                            ScannerActivity.handler.sendMessage(msg);
                        } else if (context.getLocalClassName().contains("CaptureActivity")) {
                            CaptureActivity.handlerMy.sendMessage(msg);
                        }


                    } catch (JSONException e) {
                        System.out.println("e1--------" + e);
                        e.printStackTrace();
                    }
                }
                in.close(); //关闭字符输入流
            }
            urlConn.disconnect();  //断开连接

        } catch (Exception e) {
            System.out.println("e2--------" + e);
            e.printStackTrace();
        }
    }


    //post   PDA二维码检票  套票检票
    String idTicket;

    public JsonHelp(Activity context, String linkUrl, int qr, String idTicket) {
        this.context = context;
        this.linkUrl = linkUrl;
        this.idTicket = idTicket;
    }

    public Thread postThreadCheckPac = new Thread() {
        public void run() {
            checkQRPac(context, linkUrl, idTicket);
        }
    };

    public void checkQRPac(Activity context, String linkUrl, String idTicket) {
        String strUrl = mySharePreferences.getString("Url", "") + context.getResources().getString(R.string.url_check_qrpac);
        URL url = null;
        try {
            url = new URL(strUrl);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection(); // 创建一个HTTP连接
            urlConn.setRequestMethod("POST"); // 指定使用POST请求方式
            urlConn.setDoInput(true); // 向连接中写入数据
            urlConn.setDoOutput(true); // 从连接中读取数据
            urlConn.setUseCaches(false); // 禁止缓存g
            urlConn.setInstanceFollowRedirects(true); //自动执行HTTP重定向
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // 设置内容类型
            DataOutputStream out = new DataOutputStream(urlConn.getOutputStream()); // 获取输出流
            String param = "link_url=" + URLEncoder.encode(linkUrl.trim(), "UTF-8")
                    + "&imei_num=" + URLEncoder.encode(getIMEI(context).trim(), "UTF-8")
                    + "&item_id=" + URLEncoder.encode(idTicket.trim(), "UTF-8")
                    + "&userid=" + URLEncoder.encode(mySharePreferences.getString("userid", ""), "UTF-8"); //连接要提交的数
            out.writeBytes(param);//将要传递的数据写入数据输出流
            out.flush();  //输出缓存
            out.close(); //关闭数据输出流

            if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) { // 判断是否响应成功
                InputStreamReader in = new InputStreamReader(urlConn.getInputStream(), "utf-8"); // 获得读取的内容，utf-8获取内容的编码
                BufferedReader buffer = new BufferedReader(in); // 获取输入流对象
                String inputLine = null;
                while ((inputLine = buffer.readLine()) != null) {
                    try {
                        JSONObject reader = new JSONObject(inputLine);//使用JSONObject解析
                        System.out.println("inputLine--------" + reader.toString());
                        String errcode = reader.getString("errcode");
                        String output = "";
                        List<String> listName = new ArrayList<String>();
                        if (errcode.equals("0")) {//请求成功
                            String msg = reader.getString("errmsg");
                            if (msg.equals("")) {//inputLine--------{"errcode":0,"errmsg":"","data":[{"id":"5","name":"景区红峡谷漂流门票-演示版"},{"id":"6","name":"儿童乐园-演示版"}]}
                                //需要弹窗列表展示出套票的名称选项，然后根据选择的项目把ID进行回调
                                String dataInfo = reader.getString("data");
                                JSONArray jsonArray = new JSONArray(dataInfo);
                                for (int i = 0; i < jsonArray.length(); i++) {//套票信息
                                    JSONObject jsonObjectnew = jsonArray.getJSONObject(i);
                                    String id = jsonObjectnew.getString("id");
                                    String name = jsonObjectnew.getString("name");
                                    listName.add(id + "*" + name);
                                }

                            } else {
                                String info = reader.getString("info");

                                JSONObject jsonObjectInfo = new JSONObject(info);
                                String title = jsonObjectInfo.getString("title");
                                String fee = jsonObjectInfo.getString("fee");
                                //output = msg + "：\n" + title + "， 票价：" + fee + "元";
                                output = msg + "\n" + title + "!";
                            }
                        } else if (errcode.equals("1")) {//失败
                            String msg = reader.getString("errmsg");
                            output = msg;
                        }
                        System.out.println("output--------" + output);
                        Message msg = new Message();
                        if (output.equals("")) {
                            msg.obj = listName;
                        } else {
                            msg.obj = output;
                        }
                        if (context.getLocalClassName().contains("ScannerActivity")) { //红外的方法
                            ScannerActivity.handler.sendMessage(msg);
                        } else if (context.getLocalClassName().contains("CaptureActivity")) {//相机扫码的方法
                            CaptureActivity.handlerMy.sendMessage(msg);
                        }

                    } catch (JSONException e) {
                        System.out.println("e1--------" + e);
                        e.printStackTrace();
                    }
                }
                in.close(); //关闭字符输入流
            }
            urlConn.disconnect();  //断开连接

        } catch (Exception e) {
            System.out.println("e2--------" + e);
            e.printStackTrace();
        }
    }


    // post 查询接口
    public JsonHelp(String linkUrl, Activity context) {
        this.context = context;
        this.linkUrl = linkUrl;
    }

    public Thread postThreadSearch = new Thread() {
        public void run() {
            search(context, linkUrl);
        }
    };

    public void search(Activity context, String linkUrl) {
        //System.out.println("linkUrl---"+linkUrl);
        String strUrl = mySharePreferences.getString("Url", "") + context.getResources().getString(R.string.url_search);
        URL url = null;
        try {
            url = new URL(strUrl);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection(); // 创建一个HTTP连接
            urlConn.setRequestMethod("POST"); // 指定使用POST请求方式
            urlConn.setDoInput(true); // 向连接中写入数据
            urlConn.setDoOutput(true); // 从连接中读取数据
            urlConn.setUseCaches(false); // 禁止缓存g
            urlConn.setInstanceFollowRedirects(true); //自动执行HTTP重定向
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // 设置内容类型
            DataOutputStream out = new DataOutputStream(urlConn.getOutputStream()); // 获取输出流
            String param = "link_url=" + URLEncoder.encode(linkUrl.trim(), "UTF-8"); //连接要提交的数
            out.writeBytes(param);//将要传递的数据写入数据输出流
            out.flush();  //输出缓存
            out.close(); //关闭数据输出流

            if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) { // 判断是否响应成功
                InputStreamReader in = new InputStreamReader(urlConn.getInputStream(), "utf-8"); // 获得读取的内容，utf-8获取内容的编码
                BufferedReader buffer = new BufferedReader(in); // 获取输入流对象
                String inputLine = null;
                while ((inputLine = buffer.readLine()) != null) {
                    try {
                        JSONObject reader = new JSONObject(inputLine);//使用JSONObject解析
                        System.out.println("inputLine--------" + reader.toString());
                        String errcode = reader.getString("errcode");
                        String output = "";
                        List<String> listName = new ArrayList<String>();
                        if (errcode.equals("0")) {//请求成功

                            String dataInfo = reader.getString("data");
                            System.out.println("errcode--------" + dataInfo);
                            //数据是使用Intent返回
                            Intent intent = new Intent();
                            //把返回数据存入Intent
                            intent.putExtra("result", dataInfo);
                            //设置返回数据,返回给的是CheckFragment
                            context.setResult(CheckFragment.REQUEST_QRCODE_SEARCH, intent);
                            context.finish();

                        } else if (errcode.equals("1")) {//失败
                            String msg = reader.getString("errmsg");
                            //output = msg;
                            //数据是使用Intent返回
                            Intent intent = new Intent();
                            //把返回数据存入Intent
                            intent.putExtra("result", msg);
                            //设置返回数据,返回给的是CheckFragment
                            context.setResult(CheckFragment.REQUEST_QRCODE_SEARCH, intent);
                            context.finish();


                        }
//						System.out.println("output--------"+output);
//						Message msg = new Message();
//						if(output.equals("")){
//							msg.obj = listName;
//						}else{
//							msg.obj = output;
//						}
//						ScannerActivity.handler.sendMessage(msg);

                    } catch (JSONException e) {
                        System.out.println("e1--------" + e.toString());
                        e.printStackTrace();
                    }
                }
                in.close(); //关闭字符输入流
            }
            urlConn.disconnect();  //断开连接

        } catch (Exception e) {
            System.out.println("e2--------" + e);
            e.printStackTrace();
        }
    }


}
