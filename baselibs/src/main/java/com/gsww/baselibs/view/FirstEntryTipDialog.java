package com.gsww.baselibs.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gsww.baselibs.R;
import com.gsww.baselibs.activity.PublicWebviewActivity;


/**
 * 文件描述：申请权限的dialog
 * Created by WangXW on 2019/3/15.
 */

public class FirstEntryTipDialog extends Dialog {


    private Context mContext;

    private Activity mActivity;
    private clickListener mListener;
    private String mYsUrl,mZcUrl;

    public FirstEntryTipDialog(@NonNull Context context, Activity mActivity,String mYsUrl,String mZcUrl) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.mActivity = mActivity;
        this.mYsUrl=mYsUrl;
        this.mZcUrl=mZcUrl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_entry_tip_dialog);
        initView();
    }

    private void initView() {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        Window window = getWindow();

        if (window != null) {
            WindowManager m = mActivity.getWindowManager();
            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
            WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
            p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.8
            window.setAttributes(p);
        }
        final TextView yes = findViewById(R.id.tv_yes);
        TextView no = findViewById(R.id.tv_no);
        TextView contentText = findViewById(R.id.text);
        LinearLayout llCheck = findViewById(R.id.ll_check);
        llCheck.setVisibility(View.GONE);
        CheckBox agreement_bt = findViewById(R.id.agreement_bt);
        ImageView tip_close_tv = findViewById(R.id.tip_close_tv);
        TextView tip_user_agreement_tv = findViewById(R.id.tip_user_agreement_tv);
        yes.setText("我同意");
        no.setText("取消");
        yes.setEnabled(true);
        yes.setBackgroundResource(R.drawable.shape_rounded_rectangle_bot_right);
        contentText.setText("\u3000\u3000" + "尊敬的用户，为保证本应用的正常使用，需要您为本应用授权本机存储权限，如不授权将无法使用本应用。");
        tip_user_agreement_tv.setHighlightColor(mContext.getResources().getColor(R.color.transparent));

        final SpannableStringBuilder style = new SpannableStringBuilder();

        //设置文字
        style.append("我已阅读并同意服务协议和隐私政策");

        //设置服务协议点击事件
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                //方法重新设置文字背景为透明色。
                ((TextView) widget).setHighlightColor(mContext.getResources().getColor(R.color.transparent));
                Intent intent1 = new Intent(mContext, PublicWebviewActivity.class);
                intent1.putExtra("url", mYsUrl);
                intent1.putExtra("topName", mContext.getResources().getString(R.string.service_agreement));
                mContext.startActivity(intent1);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false); //取消下划线
            }
        };
        style.setSpan(clickableSpan, 7, 11, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tip_user_agreement_tv.setText(style);


        //设置隐私政策点击事件
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                //方法重新设置文字背景为透明色。
                ((TextView) widget).setHighlightColor(mContext.getResources().getColor(R.color.transparent));

                Intent intent2 = new Intent(mContext, PublicWebviewActivity.class);
                intent2.putExtra("url", mZcUrl);
                intent2.putExtra("topName", mContext.getResources().getString(R.string.information_protection_policy));
                mContext.startActivity(intent2);
            }

            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false); //取消下划线
            }
        };
        style.setSpan(clickableSpan1, 12, 16, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tip_user_agreement_tv.setText(style);

        //设置部分文字颜色
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#46B53D"));
        style.setSpan(foregroundColorSpan, 7, 11, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        //设置部分文字颜色
        ForegroundColorSpan foregroundColorSpan1 = new ForegroundColorSpan(Color.parseColor("#46B53D"));
        style.setSpan(foregroundColorSpan1, 12, 16, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        //配置给TextView
        tip_user_agreement_tv.setMovementMethod(LinkMovementMethod.getInstance());
        tip_user_agreement_tv.setText(style);

        agreement_bt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    yes.setEnabled(true);
                    yes.setBackgroundResource(R.drawable.shape_rounded_rectangle_bot_right);
                } else {
                    yes.setEnabled(false);
                    yes.setBackgroundResource(R.drawable.shape_rounded_rectangle_bot_right_gray);
                }
            }
        });

        tip_close_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
                if (mListener != null) {
                    mListener.cancelListener();
                }
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
                if (mListener != null) {
                    mListener.sureListener();
                }
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
                if (mListener != null) {
                    mListener.cancelListener();
                }
            }
        });
    }

    public void setListener(clickListener mListener) {
        this.mListener = mListener;
    }

    public interface clickListener {

        void cancelListener();

        void sureListener();
    }


}
