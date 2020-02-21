package com.gsww.baselibs.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.subjects.PublishSubject;

import com.gsww.baselibs.R;
import com.gsww.baselibs.net.ActivityLifeCycleEvent;
import com.gsww.baselibs.utils.ToastUtil;
import com.gsww.baselibs.view.CustomProgressDialog;

public abstract class BaseFragment extends Fragment {
    //退出activity 取消请求
    public final PublishSubject<ActivityLifeCycleEvent> lifeCycleSubject=PublishSubject.create();

    public FrameLayout flContentView;

    public View mContentView;//base布局view
    public Context mContext;
    public Dialog progressDialog;
    Unbinder unbinder;
    public Typeface customFont;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_base, container, false);
        mContext = getContext();
        flContentView = mContentView.findViewById(R.id.fl_content_view);//子类布局存放位置

        LayoutInflater.from(mContext).inflate(setLayoutResourceID(), flContentView);//添加子类布局


        unbinder = ButterKnife.bind(this, mContentView);

        customFont = Typeface.createFromAsset(getActivity().getAssets(), "heavy.ttf");
        init();
        setUpView();//设置布局
        setUpData();//设置数据

        return mContentView;
    }


    /**
     * 此方法用于返回Fragment设置flContentView的布局文件资源ID * * @return 布局文件资源ID
     */
    protected abstract int setLayoutResourceID();

    /**
     * 一些View的相关操作
     */
    protected abstract void setUpView();


    /**
     * 一些Data的相关操作
     */
    protected abstract void setUpData();

    /**
     * 此方法用于初始化成员变量及获取Intent传递过来的数据 * 注意：这个方法中不能调用所有的View，因为View还没有被初始化，要使用View在initView方法中调用
     */
    protected void init() {

    }


    /**
     * /左右按钮点击
     */

    private OnClickListener clickListenerTopLeft;
    private OnClickListener clickListenerTopRight;

    public interface OnClickListener {
        void onClick();
    }


    public void showToast(String msg) {
        ToastUtil toastUtil = new ToastUtil(mContext);
        toastUtil.setText(msg);
        toastUtil.showToast(2000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        lifeCycleSubject.onNext(ActivityLifeCycleEvent.DESTROY);
    }

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
}
