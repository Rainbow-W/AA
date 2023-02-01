package com.yunkai.browser.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.yunkai.browser.R;

/**
 * Created by Administrator on 2017/11/3.
 */

public class MyDialogLoading extends AlertDialog {
    public MyDialogLoading(Context context, int theme) {
        super(context, theme);
    }

    public MyDialogLoading(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_dialog);
    }
}
