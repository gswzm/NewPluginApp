package com.gsww.baselibs.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gsww.baselibs.R;

public class NavigationBar extends RelativeLayout {

    private FTextView mTvTitle;
    private AlphaTextView mTvLeft;
    public AlphaTextView mTvRight;

    //左边按钮图片
    private Drawable leftBtnDrawable;
    //右边按钮图片
    private Drawable rightBtnDrawable;
    //导航栏标题
    private String title;
    //标题颜色
    private int titleColor;
    //标题大小
    private float titleSize;

    //按钮文字属性
    private String leftBtnText;
    private String rightBtnText;
    private int leftBtnTextColor;
    private int rightBtnTextColor;
    private float leftBtnTextSize;
    private float rightBtnTextSize;

    public NavigationBar(Context context) {
        this(context, null);
    }

    public NavigationBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NavigationBar);
        if (typedArray != null) {
            title = typedArray.getString(R.styleable.NavigationBar_title);
            titleColor = typedArray.getColor(R.styleable.NavigationBar_title_color, getResources().getColor(android.R.color.white));
            titleSize = typedArray.getDimension(R.styleable.NavigationBar_title_size, context.getResources().getDimension(R.dimen.text_size_big));
            leftBtnDrawable = typedArray.getDrawable(R.styleable.NavigationBar_left_btn_drawable);
            rightBtnDrawable = typedArray.getDrawable(R.styleable.NavigationBar_right_btn_drawable);

            leftBtnText = typedArray.getString(R.styleable.NavigationBar_left_btn_text);
            rightBtnText = typedArray.getString(R.styleable.NavigationBar_right_btn_text);
            leftBtnTextColor = typedArray.getColor(R.styleable.NavigationBar_left_btn_text_color, getResources().getColor(android.R.color.white));
            rightBtnTextColor = typedArray.getColor(R.styleable.NavigationBar_right_btn_text_color, getResources().getColor(android.R.color.white));
            leftBtnTextSize = typedArray.getDimension(R.styleable.NavigationBar_left_btn_text_size, context.getResources().getDimension(R.dimen.text_size_small));
            rightBtnTextSize = typedArray.getDimension(R.styleable.NavigationBar_right_btn_text_size, context.getResources().getDimension(R.dimen.text_size_small));
            typedArray.recycle();
        }
        initView(context);
    }

    private void initView(Context context) {
        View view = inflate(context, R.layout.navigation_bar, this);
        mTvTitle = view.findViewById(R.id.tv_title);
        mTvLeft = (AlphaTextView) view.findViewById(R.id.tv_back);
        mTvRight = (AlphaTextView) view.findViewById(R.id.tv_right);

        //设置图片
        setTextViewDrawable(mTvLeft, Gravity.LEFT, leftBtnDrawable);
        setTextViewDrawable(mTvRight, Gravity.RIGHT, rightBtnDrawable);
        if (!TextUtils.isEmpty(leftBtnText)) {
            mTvLeft.setText(leftBtnText);
            mTvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftBtnTextSize);
            mTvLeft.setTextColor(leftBtnTextColor);
            setTouchDelegate(mTvLeft,100);
        }
        if (!TextUtils.isEmpty(rightBtnText)) {
            mTvRight.setText(rightBtnText);
            mTvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightBtnTextSize);
            mTvRight.setTextColor(rightBtnTextColor);
        }

        //设置文字属性
        mTvTitle.setText(title);
        mTvTitle.setTextColor(titleColor);
        mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
    }

    public void setOnLeftBtnClickListener(OnClickListener leftBtnClickListener) {
        mTvLeft.setOnClickListener(leftBtnClickListener);
    }

    public void setOnRightBtnClickListener(OnClickListener rightBtnClickListener) {
        mTvRight.setOnClickListener(rightBtnClickListener);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
        mTvTitle.setText(title);
    }

    /**
     * 设置标题字体颜色
     *
     * @param titleColor
     */
    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
        mTvTitle.setTextColor(titleColor);
    }

    /**
     * 设置标题字体大小
     *
     * @param titleSize
     */
    public void setTitleSize(float titleSize) {
        this.titleSize = titleSize;
        mTvTitle.setTextSize(titleSize);
    }

    /**
     * 设置左边按钮图标
     *
     * @param leftBtnDrawable
     */
    public void setLeftBtnDrawable(Drawable leftBtnDrawable) {
        this.leftBtnDrawable = leftBtnDrawable;
        setTextViewDrawable(mTvRight, Gravity.LEFT, leftBtnDrawable);
    }

    /**
     * 设置右边按钮图标
     *
     * @param rightBtnDrawable
     */
    public void setRightBtnDrawable(Drawable rightBtnDrawable) {
        this.rightBtnDrawable = rightBtnDrawable;
        setTextViewDrawable(mTvRight, Gravity.RIGHT, rightBtnDrawable);
    }

    public void setLeftBtnText(String leftBtnText) {
        this.leftBtnText = leftBtnText;
        mTvLeft.setText(leftBtnText);
    }

    public void setRightBtnText(String rightBtnText) {
        this.rightBtnText = rightBtnText;
        mTvRight.setText(rightBtnText);
    }

    public AlphaTextView getRightBtn(){
        return mTvRight;
    }

    public void setLeftBtnTextColor(int leftBtnTextColor) {
        this.leftBtnTextColor = leftBtnTextColor;
        mTvLeft.setTextColor(leftBtnTextColor);
    }

    public void setRightBtnTextColor(int rightBtnTextColor) {
        this.rightBtnTextColor = rightBtnTextColor;
        mTvRight.setTextColor(rightBtnTextColor);
    }

    public void setLeftBtnTextSize(float leftBtnTextSize) {
        this.leftBtnTextSize = leftBtnTextSize;
        mTvLeft.setTextSize(leftBtnTextSize);
    }

    public void setRightBtnTextSize(float rightBtnTextSize) {
        this.rightBtnTextSize = rightBtnTextSize;
        mTvRight.setTextSize(rightBtnTextSize);
    }

    private void setTextViewDrawable(TextView textView, int gravity, Drawable drawable) {
        if (gravity == Gravity.LEFT) {
            textView.setCompoundDrawablesWithIntrinsicBounds(drawable,
                    null, null, null);
        } else if (gravity == Gravity.RIGHT) {
            textView.setCompoundDrawablesWithIntrinsicBounds(null,
                    null, drawable, null);
        }
        //设置文字和图片的间距
        textView.setCompoundDrawablePadding(5);
    }

    public void clearLeftButtonDrawable(){
        mTvLeft.setCompoundDrawables(null,null,null,null);
    }

    public static void setTouchDelegate(final View view, final int expandTouchWidth) {
        final View parentView = (View) view.getParent();
        parentView.post(new Runnable() {
            @Override
            public void run() {
                final Rect rect = new Rect();
                view.getHitRect(rect); // view构建完成后才能获取，所以放在post中执行
                // 4个方向增加矩形区域
                rect.top -= expandTouchWidth;
                rect.bottom += expandTouchWidth;
                rect.left -= expandTouchWidth;
                rect.right += expandTouchWidth;

                parentView.setTouchDelegate(new TouchDelegate(rect, view));
            }
        });
    }
}
