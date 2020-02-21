/**
 * Copyright 中国电信甘肃万维公司 All rights reserved.
 * 中国电信甘肃万维公司 专有/保密源代码,未经许可禁止任何人通过任何* 渠道使用、修改源代码.
 */
package com.gsww.baselibs.utils;

import android.content.Context;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ToastUtil {

    private Toast toast = null;
    private Context context;
    private Handler handler = null;
    private String content = "";
    private Runnable toastThread = new Runnable() {
        public void run() {
            if (!TextUtils.isEmpty(content)) {
                toast.setText(Html.fromHtml(content));
                toast.show();
                // 3.3秒后再度重启，设为4s的话将会看到Toast是断断续续地显示着的。
                handler.postDelayed(toastThread, 3300);
            }
        }
    };

    public ToastUtil(Context context) {
        this.context = context;
        handler = new Handler(this.context.getMainLooper());
        toast = Toast.makeText(this.context, "", Toast.LENGTH_LONG);
        LinearLayout out = (LinearLayout) toast.getView();
        //设置Toast文字字体大小  R.id.message  android源码/framework/base目录里
        TextView tv = (TextView) out.findViewById(android.R.id.message);
        tv.setTextSize(16);
    }

    public void setText(String text) {
        content = text;
    }

    public void showToast(final long length) {
        handler.post(toastThread);
        Thread timeThread = new Thread() {
            public void run() {
                try {
                    Thread.sleep(length);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ToastUtil.this.stopToast();
            }
        };
        timeThread.start();
    }

    public void stopToast() {
        // 删除Handler队列中的仍处理等待的消息元素删除
        handler.removeCallbacks(toastThread);
        toast.cancel();
    }
    public static void showToast(Context context,String text){
        ToastUtil toastUtil=new ToastUtil(context);
        toastUtil.setText(text);
        toastUtil.showToast(1000);
    }
}
