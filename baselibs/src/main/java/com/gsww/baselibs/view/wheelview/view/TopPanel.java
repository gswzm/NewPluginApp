package com.gsww.baselibs.view.wheelview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gsww.baselibs.R;

/**
 * kangjh
 */
public class TopPanel extends RelativeLayout {
    private static final int PANEL_IMAGE = 0; //图片
    private static final int PANEL_TEXT = 1; //文字
    public TextView titleText;
    public ImageView menuBtn;
    public ImageView searchBtn;
    public TextView searchText;
    private Context context;
    private RelativeLayout topPanel;
    private int type;

    public TopPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public TopPanel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //取出attrs中我们为View设置的相关值
        TypedArray tArray = context.obtainStyledAttributes(attrs, R.styleable.toppanel);
        type = tArray.getInt(R.styleable.toppanel_type, PANEL_IMAGE);
        tArray.recycle();
        this.context = context;
        initView();
    }

    public void initView() {
        if (type == PANEL_TEXT) {
            topPanel = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.base_top_panel_text, null);
            titleText = (TextView) topPanel.findViewById(R.id.tvTitle);
            menuBtn = (ImageView) topPanel.findViewById(R.id.tv_back);
            searchText = (TextView) topPanel.findViewById(R.id.search);
        } else {
            topPanel = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.base_top_panel, null);
            titleText = (TextView) topPanel.findViewById(R.id.tvTitle);
            menuBtn = (ImageView) topPanel.findViewById(R.id.tv_back);
            searchBtn = (ImageView) topPanel.findViewById(R.id.search);
        }
        this.addView(topPanel, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
    }

}
