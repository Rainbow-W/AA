package com.yunkai.browser.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.smartdevice.aidl.ICallBack;
import com.taicd.browserIP.R;
import com.yunkai.browser.adapter.SearchTicketAdapter;
import com.yunkai.browser.scan.BaseActivity;
import com.yunkai.browser.scan.MessageType;
import com.yunkai.browser.utils.SearchTicketInfor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import taobe.tec.jcc.JChineseConvertor;

/**
 * Created by Administrator on 2017/11/3.
 */
//implements IZKCService //不用的
public class SearchInfoActivity extends BaseActivity {

    ImageView ivBack;
    String info;
    ListView lvSearch;
    Button btnBack,btnPrint;
    List<SearchTicketInfor> list=new ArrayList<>();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏(需要继承Activity 而不是AppCompatActivity)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search_info);

        try {
            info = getIntent().getStringExtra("searchinfo");//


            // requestReadExternalPermission();

            initView();

            initListenter();

            initData(info);

        }catch(Exception e){
            System.out.println("-ee----"+e.toString());
        }

    }

    private void initView() {
        ivBack=(ImageView) findViewById(R.id.iv_back);
        lvSearch=(ListView) findViewById(R.id.lv_search);
        btnBack = (Button)findViewById(R.id.btn_back);
        btnPrint = (Button)findViewById(R.id.btn_print);
    }

    //繁体转成简体
    public String change1(String changeText) {
        try {
            JChineseConvertor jChineseConvertor = JChineseConvertor
                    .getInstance();
            changeText = jChineseConvertor.t2s(changeText);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return changeText;
    }


    private void initListenter() {
        ivBack.setOnClickListener(view -> finish());
        btnBack.setOnClickListener(view -> finish());
        btnPrint.setOnClickListener(view -> {
            //进行打印  调用打印接口
                try {
                    mIzkcService.printerInit();//打印机初始化
                   // mIzkcService.printerSelfChecking();//打印机自检，打印机会打印自检页   可以执行，打印纸有打印。
                    System.out.println("----状态------"+mIzkcService.getPrinterStatus());

                    StringBuilder str= new StringBuilder();
                    for(int i=0;i<list.size();i++){
                           str.append("\n").append(getResources().getString(R.string.search_ticket_name)).append(list.get(i).getTicketName()).append("\n").append(getResources().getString(R.string.search_class)).append(list.get(i).getTicketClass()).append("\n").append(getResources().getString(R.string.search_time)).append(list.get(i).getTime()).append("\n");
                    }


                    //打印时，将繁体字转换为简体。
                    mIzkcService.printTextAlgin(change1(str.toString())+"\n"+"---------------------------------------"+"\n",Typeface.BOLD,1,0);



                }catch(Exception e){
                    System.out.println("-------println  1111----"+e.toString());
                }

        });
    }

    private void initData(String info){
        System.out.println("info---"+info);
        //解析json数组并将数据显示
        try {
            JSONArray jsonArray = new JSONArray(info);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectnew = jsonArray.getJSONObject(i);
                String time = jsonObjectnew.getString("time");//检票时间
                String searchClass = jsonObjectnew.getString("fromtype");//检票渠道
                String searchTicketName = jsonObjectnew.getString("title");//票名
                list.add(new SearchTicketInfor(time,searchClass,searchTicketName));
            }

            SearchTicketAdapter myAdapter = new SearchTicketAdapter(this,list);
            lvSearch.setAdapter(myAdapter);
        }catch(Exception e){
            System.out.println("search=---"+e.toString());
        }


    }



    //以下为打印部分
    @Override
    protected void handleStateMessage(final Message message) {
        super.handleStateMessage(message);
        switch (message.what){
            //服务绑定成功 service bind success
            case MessageType.BaiscMessage.SEVICE_BIND_SUCCESS:
                //Toast.makeText(this, "服务绑定成功aaaaa", Toast.LENGTH_SHORT).show();
                System.out.println("服务绑定成功aaaaa");
                registerCallbackAndInitPrint();
                break;
            //服务绑定失败 service bind fail
            case MessageType.BaiscMessage.SEVICE_BIND_FAIL:
                Toast.makeText(this, getResources().getString(R.string.scan_service_error), Toast.LENGTH_SHORT).show();
                break;
            case MessageType.BaiscMessage.DETECT_PRINTER_SUCCESS:
                //type=getIntent().getStringExtra("type");
                System.out.println("-------printer   obj-------"+(String) message.obj);


                break;
        }
    }

    ICallBack.Stub mCallback = new ICallBack.Stub() {

        @Override
        public void onReturnValue(byte[] buffer, int size) {
            String codeStr = new String(buffer, 0, size);
            System.out.println("----printer----"+codeStr);

            //text += codeStr+"\n";
            sendMessage(MessageType.BaiscMessage.DETECT_PRINTER_SUCCESS, codeStr.trim());
        }
    };

    public void registerCallbackAndInitPrint(){
        // 注册回调接口
        try {
            mIzkcService.registerCallBack("printer", mCallback);
            mIzkcService.printerInit();//打印机初始化
            //判断打印机是否空闲
//            if(mIzkcService.checkPrinterAvailable()){
//                Toast.makeText(SearchInfoActivity.this,"打印机空闲000",Toast.LENGTH_SHORT).show();
//            }else{
//                Toast.makeText(SearchInfoActivity.this,"忙!!!!!!!!",Toast.LENGTH_SHORT).show();
//            }

            //System.out.println("打印机固件版本----------"+mIzkcService.getServiceVersion()+"-----------"+mIzkcService.getDeviceModel());


        } catch (RemoteException e) {
            System.out.println("--00-----"+e.toString());
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        try {
            mIzkcService.unregisterCallBack("printer", mCallback);
        } catch (RemoteException e) {
            System.out.println("111---"+e.toString());
            e.printStackTrace();
        }
//		unregisterReceiver(screenStatusReceiver);
        super.onDestroy();
    }


}
