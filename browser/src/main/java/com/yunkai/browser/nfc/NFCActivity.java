package com.yunkai.browser.nfc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.taicd.browserIP.R;

import com.yunkai.browser.fragment.CheckFragment;
import com.yunkai.browser.fragment.TicketFragment;
import com.yunkai.browser.okhttp.HttpServer;
import com.yunkai.browser.okhttp.MemberListDean;
import com.yunkai.browser.utils.ConfigUtil;

public class NFCActivity extends Activity {

    ImageView ivBack;
    private NfcAdapter nfcAdapter;
    private TextView promt;
    private byte password[] = {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
            (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
            (byte) 0xff, (byte) 0xff, (byte) 0xff};
    public List<Nfc> list;
    private ListView listView;
    private Intent intents;

    private boolean isnews = true;
    private PendingIntent pendingIntent;
    private IntentFilter[] mFilters;
    private String[][] mTechLists;
    private Nfc mynfc;
    private String dataString;

    private static final String TAG = "NFCActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfc);
        listView = (ListView) findViewById(R.id.listView1);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        promt = (TextView) findViewById(R.id.promt);
        // 获取默认的NFC控制器
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (nfcAdapter == null) {
            promt.setText("Device can not support NFC！");
            finish();
            return;
        }
        if (!nfcAdapter.isEnabled()) {
            promt.setText("Please open NFC in system setting！");
            finish();
            return;
        }

        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        ndef.addCategory("*/*");
        mFilters = new IntentFilter[]{ndef};// 过滤器
        mTechLists = new String[][]{
                new String[]{MifareClassic.class.getName()},
                new String[]{NfcA.class.getName()}};// 允许扫描的标签类型

    }

    @Override
    protected void onResume() {
        super.onResume();
        // 得到是否检测到ACTION_TECH_DISCOVERED触发
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, mFilters,
                mTechLists);
        if (isnews) {
            if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent()
                    .getAction())) {
                // 处理该intent
                processIntent(getIntent());
                intents = getIntent();
                isnews = false;
            }
        }
    }

    // 字符序列转换为16进制字符串
    private String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);

            stringBuilder.append(buffer);

        }
        return stringBuilder.toString();
    }

    //16进制转换为10进制
    private String SixteenToTen(String src) {
        int i = Integer.parseInt("13EC", 16);
        int i2 = Integer.parseInt("8C", 16);
        return i + "";
    }

    public String toD(String a, int b) {//---------------------------a为16进制，b=16；三
        int r = 0;
        for (int i = 0; i < a.length(); i++) {
            r = (int) (r + formatting(a.substring(i, i + 1))
                    * Math.pow(b, a.length() - i - 1));
        }
        return String.valueOf(r);
    }

    // 将十六进制中的字母转为对应的数字
    public int formatting(String a) {
        int i = 0;
        for (int u = 0; u < 10; u++) {
            if (a.equals(String.valueOf(u))) {
                i = u;
            }
        }
        if (a.equals("a")) {
            i = 10;
        }
        if (a.equals("b")) {
            i = 11;
        }
        if (a.equals("c")) {
            i = 12;
        }
        if (a.equals("d")) {
            i = 13;
        }
        if (a.equals("e")) {
            i = 14;
        }
        if (a.equals("f")) {
            i = 15;
        }
        return i;
    }


    private String bytesToHexString2(byte[] src) {

        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);

            stringBuilder.append(buffer);
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        // 得到是否检测到ACTION_TECH_DISCOVERED触发
        // nfcAdapter.enableForegroundDispatch(this, pendingIntent, mFilters,
        // mTechLists);
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
            // 处理该intent
            processIntent(intent);
            intents = intent;
        }

    }

    class ListLongClick implements OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view,
                                       int position, long id) {
            return false;
        }

    }

    //把十六进制翻转字符 并转换为十六进制
    //https://blog.csdn.net/Jhear/article/details/52381803?locationNum=10
    public static String hex2Decimal(String hex) {
        StringBuilder builder = new StringBuilder();
        if (hex.length() == 8) {
            for (int i = 0; i < 4; i++) {
                String str = hex.substring(hex.length() - 2 * (i + 1), hex.length() - 2 * i);
                builder.append(str);
            }
        }
        StringBuilder decimal = new StringBuilder(String.valueOf(Long.parseLong(builder.toString(), 16)));
        while (decimal.length() < 10) {
            decimal.insert(0, "0");
        }

        return decimal.toString();
    }

    @SuppressLint("HandlerLeak")
    public Handler handlers = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            Log.e(TAG, "handleMessage: " + msg.what);
            Log.e(TAG, "handleMessage: " + msg.obj.toString());
            //数据是使用Intent返回
            Intent intent = new Intent();
            switch (msg.what) {
                case 1:
                    MemberListDean.MemberDean data = (MemberListDean.MemberDean) msg.obj;
                    //把返回数据存入Intent
                    intent.putExtra("tpye", 2);
                    intent.putExtra("result", new Gson().toJson(data));
                    //设置返回数据,返回给的是CaptureActivity
                    setResult(TicketFragment.REQUEST_QRCODE_NFC, intent);
                    finish();
                    break;
                case 2:
                    //把返回数据存入Intent
                    intent.putExtra("tpye", 1);
                    intent.putExtra("result", (String) msg.obj);
                    //设置返回数据,返回给的是CaptureActivity
                    //context.setResult(CheckFragment.REQUEST_QRCODE, intent);
                    setResult(TicketFragment.REQUEST_QRCODE_NFC, intent);
                    finish();
                    break;
                case 3:
                    Toast.makeText(NFCActivity.this, (String) msg.obj, Toast.LENGTH_LONG).show();
                    break;
            }

            super.handleMessage(msg);
        }
    };

    /**
     * 读取NFC信息数据
     */
    private void processIntent(Intent intent) {
        boolean auth = false;
        String cardStr = "";
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        cardStr = "ID：" + bytesToHexString(tag.getId());

        //通过cardId 获取到会员信息
        try {
            String ss = bytesToHexString(tag.getId());
            System.out.println(ss + "--------ss");

            String cardnum = hex2Decimal(ss);

            HttpServer.getInstance(this).getMemberInfo(ConfigUtil.getAppIMEI(this), cardnum, handlers);

        } catch (Exception e) {
            System.out.println("-nfc-" + e.toString());
        }

        String[] techList = tag.getTechList();
        boolean haveMifareUltralight = false;
        cardStr += "\r\nTECH：";
        for (String tech : techList) {
            cardStr += tech + ",";
            if (tech.indexOf("MifareClassic") >= 0) {
                haveMifareUltralight = true;
                break;
            }
        }
        if (!haveMifareUltralight) {
            //promt.setText(cardStr);
            Toast.makeText(this, "this card type is not MifareClassic", Toast.LENGTH_LONG).show();
            return;
        }

        MifareClassic mfc = MifareClassic.get(tag);

        try {
            String metaInfo = "";
            // Enable I/O operations to the tag from this TagTechnology object.
            mfc.connect();
            int type = mfc.getType();// 获取TAG的类型
            int sectorCount = mfc.getSectorCount();// 获取TAG中包含的扇区数
            String typeS = "";
            switch (type) {
                case MifareClassic.TYPE_CLASSIC:
                    typeS = "TYPE_CLASSIC";
                    break;
                case MifareClassic.TYPE_PLUS:
                    typeS = "TYPE_PLUS";
                    break;
                case MifareClassic.TYPE_PRO:
                    typeS = "TYPE_PRO";
                    break;
                case MifareClassic.TYPE_UNKNOWN:
                    typeS = "TYPE_UNKNOWN";
                    break;
            }
            byte[] myNFCID = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);

            int before = (int) Long.parseLong(bytesToHexString(myNFCID), 16);

            int r24 = before >> 24 & 0x000000FF;
            int r8 = before >> 8 & 0x0000FF00;
            int l8 = before << 8 & 0x00FF0000;
            int l24 = before << 24 & 0xFF000000;

            metaInfo += "ID(dec):"
                    + Long.parseLong(
                    Integer.toHexString((r24 | r8 | l8 | l24)), 16)
                    + "\nID(hex):" + bytesToHexString2(myNFCID) + "\nType："
                    + typeS + "\nSector：" + sectorCount + "\n Block："
                    + mfc.getBlockCount() + "\nSize： " + mfc.getSize() + "B";
            if (list == null) {
                list = new ArrayList<Nfc>();
            } else {
                list.clear();
            }

            for (int j = 0; j < sectorCount; j++) {
                auth = mfc.authenticateSectorWithKeyA(j, password);

                int bCount;
                int bIndex;
                if (auth) {
                    list.add(new Nfc(j, 0, "Sector " + j + ": Authentication ok", j));

                    // 读取扇区中的块
                    bCount = mfc.getBlockCountInSector(j);
                    bIndex = mfc.sectorToBlock(j);
                    for (int i = 0; i < bCount; i++) {

                        System.out.println("bIndex : " + bIndex);
                        byte[] data = mfc.readBlock(bIndex);

                        list.add(new Nfc(bIndex, 1, "Block " + bIndex + " : "
                                + bytesToHexString(data), j));
                        bIndex++;
                    }
                } else {

                    list.add(new Nfc(j, 0, "Sector " + j + ": Authentication Failed", j));
                }
            }


            NfcsAdapter adapter = new NfcsAdapter(list, getApplicationContext());

            listView.setAdapter(adapter);
            listView.setOnItemLongClickListener(new ListLongClick());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (mfc != null) {
                    mfc.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
