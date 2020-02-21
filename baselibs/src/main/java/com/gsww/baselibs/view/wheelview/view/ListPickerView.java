package com.gsww.baselibs.view.wheelview.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.gsww.baselibs.R;

import java.util.ArrayList;

/**
 * 列表选择
 * Created by Sai on 15/11/22.
 */
public class ListPickerView extends BasePickerView implements View.OnClickListener {
    WheelList wheelList;
    private View btnSubmit, btnCancel;
    private TextView tvTitle;
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";
    private OnListSelectListener listSelectListener;

    public ListPickerView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.base_pickerview_list, contentContainer);
        // -----确定和取消按钮
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setTag(TAG_SUBMIT);
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setTag(TAG_CANCEL);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        // 顶部标题
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        // ----时间转轮
        final View sexpickerview = findViewById(R.id.sexpicker1);
        wheelList = new WheelList(sexpickerview);
        wheelList.setPicker(0);
        wheelList.setCyclic(false);

    }

    public void setList(ArrayList<String> list) {
        wheelList.setList(list);
    }

    /**
     * 设置选中第几个
     * <p>
     * aram date
     */
    public void setIndex(Integer index) {
        wheelList.setPicker(index);
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wheelList.setCyclic(cyclic);
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_CANCEL)) {
            dismiss();
            return;
        } else {
            if (listSelectListener != null) {
                listSelectListener.onListSelect(wheelList.getValue(),wheelList.getCurrentItem());
            }
            dismiss();
            return;
        }
    }

    public interface OnListSelectListener {
        public void onListSelect(String value, int currentItem);
    }

    public void setOnListSelectListener(OnListSelectListener listSelectListener) {
        this.listSelectListener = listSelectListener;
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }
}
