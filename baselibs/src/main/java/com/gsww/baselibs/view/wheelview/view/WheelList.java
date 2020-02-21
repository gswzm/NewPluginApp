package com.gsww.baselibs.view.wheelview.view;

import android.view.View;


import com.gsww.baselibs.R;
import com.gsww.baselibs.view.wheelview.adapter.ArrayWheelAdapter;
import com.gsww.baselibs.view.wheelview.lib.WheelView;

import java.util.ArrayList;


public class WheelList {
    private View view;
    private WheelView wv_list;
    ArrayList<String> data_list = new ArrayList<>();

    public WheelList(View view) {
        super();
        this.view = view;
        setView(view);
    }

    public void setList(ArrayList<String> list) {
        data_list.clear();
        for (String s : list) {
            data_list.add(s);
        }
    }

    /**
     * @Description: 弹出列表选择器
     */
    public void setPicker(int index) {

        wv_list = (WheelView) view.findViewById(R.id.list_wheel_base);
        wv_list.setAdapter(new ArrayWheelAdapter(data_list));// 设置"性别"的显示数据
        wv_list.setCurrentItem(index);// 初始化时显示的数据
        // 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
        int textSize = 18;
        wv_list.setTextSize(textSize);
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wv_list.setCyclic(cyclic);
    }

    public String getValue() {
        return data_list.get(wv_list.getCurrentItem());
    }
    public int getCurrentItem() {
        return wv_list.getCurrentItem();
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

}
