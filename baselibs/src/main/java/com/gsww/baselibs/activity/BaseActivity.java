package com.gsww.baselibs.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.gsww.baselibs.R;
import com.gsww.baselibs.manager.ActivityStackManager;
import com.gsww.baselibs.net.ActivityLifeCycleEvent;
import com.gsww.baselibs.utils.Logger;
import com.gsww.baselibs.utils.ScreenUtil;
import com.gsww.baselibs.utils.StatusBarUtil;
import com.gsww.baselibs.utils.ToastUtil;
import com.gsww.baselibs.view.CustomProgressDialog;
import com.gsww.baselibs.widget.NavigationBar;
import com.gsww.baselibs.widget.StatusBarCompat;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.subjects.PublishSubject;

public abstract class BaseActivity extends AppCompatActivity {
    //退出activity 取消请求
    public final PublishSubject<ActivityLifeCycleEvent> lifeCycleSubject=PublishSubject.create();

    public Context mContext;
    public NavigationBar navigationBar;
    public FrameLayout contentView;
    public LinearLayout mViewNeedOffset;
    public ImageView iv_top_bg;
    protected Dialog progressDialog;
    public Activity activity;
    public Typeface customFont;
    Unbinder unbinder;

    /**
     * Screen information
     */
    protected int mScreenWidth = 0;
    protected int mScreenHeight = 0;
    protected float mScreenDensity = 0.0f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeZDWW);
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            getBundleExtras(extras);
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        mScreenDensity = displayMetrics.density;
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;

        doBeforeSetcontentView();
        setContentView(R.layout.activity_base);
        //设置沉浸式标题栏
        mViewNeedOffset=findViewById(R.id.view_need_offset);
        //顶部的图片，也是titlebar的背景
        iv_top_bg=findViewById(R.id.iv_top_bg);
        //标题栏
        navigationBar = findViewById(R.id.navigation_bar);


        mContext = this;
        activity = this;
        customFont = Typeface.createFromAsset(getAssets(), "heavy.ttf");
        initData();
        initViews();

        if(isNeedSetStatus()){
            calculateStatusBar();
        }else{
            iv_top_bg.setVisibility(View.GONE);
            navigationBar.setVisibility(View.GONE);
        }
    }


    public boolean isNeedSetStatus(){
        return true;
    }

    //计算statusbar
    public void calculateStatusBar(){
        RelativeLayout.LayoutParams ivParams= (RelativeLayout.LayoutParams) iv_top_bg.getLayoutParams();
        LinearLayout.LayoutParams titleBarParams= (LinearLayout.LayoutParams) navigationBar.getLayoutParams();
        int titleBarHeight=titleBarParams.height;
        //顶部背景图的高度应该是titlebar高度+状态栏高度
        ivParams.height=titleBarHeight+StatusBarCompat.getStatusBarHeight(this);
        iv_top_bg.setLayoutParams(ivParams);
        setStatusBarAlpha(0);
    }

    public void setStatusBarAlpha(int alpha){
        StatusBarUtil.setTranslucentForImageView(this, alpha, mViewNeedOffset);

    }



    public void initViews() {
        //子类布局
        contentView = (FrameLayout) findViewById(R.id.fl_content_view);
        //添加子类布局
        LayoutInflater.from(this).inflate(getContentView(), contentView);
        unbinder=ButterKnife.bind(this);
        customFont = Typeface.createFromAsset(getAssets(), "heavy.ttf");
        navigationBar.setOnLeftBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListenerTopLeft != null) {
                    clickListenerTopLeft.onClick();
                    return;
                }
                finish();

            }
        });
        navigationBar.setOnRightBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListenerTopRight != null) {
                    clickListenerTopRight.onClick();
                }
            }
        });
        //标题抽象方法
        initTitle();
    }

    protected void initData() { //初始化一些数据，例如intent传过来的值

    }

    /**
     * 设置layout前配置
     */
    public void doBeforeSetcontentView() {
//        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//        }else{
//
//        }

    }
    /**
     * 布局设置
     *
     * @return
     */
    protected abstract int getContentView();
    /**
     * 接收上个界面传递的参数
     * @param extras
     */
    protected abstract void getBundleExtras(Bundle extras);


    /**
     * 标题设置
     */
    protected abstract void initTitle();

    public void setTitle(String title) {
        navigationBar.setTitle(title);
    }
    /**
     * 是否显示标题栏左边按钮
     *
     * @param isShow true 显示 false 隐藏
     */
    public void showLeftButton(boolean isShow){
        if (!isShow) {
            navigationBar.clearLeftButtonDrawable();
            navigationBar.setOnLeftBtnClickListener(null);
        }
    }
    /**
     * 是否显示标题栏左边按钮
     *
     * @param isShow true 显示 false 隐藏
     */
    public void showRightButton(boolean isShow){
        if (isShow) {
            navigationBar.mTvRight.setVisibility(View.VISIBLE);
        }else{
            navigationBar.mTvRight.setVisibility(View.INVISIBLE);
        }
    }

    public void setTitleBarBg(int resId){
        iv_top_bg.setImageResource(resId);
    }



    /**
     * 是否显示标题栏
     *
     * @param isShow true 显示 false 隐藏
     */
    public void showTitleBar(boolean isShow) {
        if (!isShow) {
            navigationBar.setVisibility(View.GONE);
        }
    }

    /**
     * 左右按钮点击
     */
    private OnClickListener clickListenerTopLeft;
    private OnClickListener clickListenerTopRight;

    public interface OnClickListener {
        void onClick();
    }

    /**
     * 默认图标左边点击事件
     *
     * @param onClickListener
     */
    protected void setTopLeftButton(OnClickListener onClickListener) {
        this.clickListenerTopLeft = onClickListener;
    }

    /**
     * 自定义图标左边点击事件
     *
     * @param iconResId       图标id
     * @param onClickListener
     */
    protected void setTopLeftButton(int iconResId, OnClickListener onClickListener) {
        navigationBar.setLeftBtnDrawable(getResources().getDrawable(iconResId));
        this.clickListenerTopLeft = onClickListener;
    }
    protected void setTopRightButton(String str) {
        navigationBar.setRightBtnText(str);
    }
    /**
     * 文字类型右边点击事件
     *
     * @param str             菜单文字
     * @param onClickListener
     */
    protected void setTopRightButton(String str, OnClickListener onClickListener) {
        navigationBar.setRightBtnText(str);
        this.clickListenerTopRight = onClickListener;
    }

    /**
     * 图标类型右边点击事件
     *
     * @param iconResId       图标id
     * @param onClickListener
     */

    protected void setTopRightButton(int iconResId, OnClickListener onClickListener) {
        navigationBar.setRightBtnDrawable(getResources().getDrawable(iconResId));
        this.clickListenerTopRight = onClickListener;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unbinder!=null){
            unbinder.unbind();
        }
        lifeCycleSubject.onNext(ActivityLifeCycleEvent.DESTROY);
        ActivityStackManager.getAppManager().finishActivity(this);
    }



    /**
     * 吐司
     *
     * @param text
     */
    public void showToast(String text) {
        ToastUtil toastUtil = new ToastUtil(mContext);
        toastUtil.setText(text);
        toastUtil.showToast(2000);
    }

    /**
     * 网络加载遮罩层
     *
     * @param msg
     */
    public void showProgressDialog(String msg) {
        progressDialog = CustomProgressDialog.show(mContext, "", msg, true);
    }


    public void showProgressDialog() {
        progressDialog = CustomProgressDialog.show(mContext, "", "数据加载中，请稍后...", true);
    }


    public void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * 防止控件被重复点击
     */
    private long lastClickTime;

    public boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 2000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
