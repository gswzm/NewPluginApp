package com.gsww.baselibs.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

/**
 * @author kangjh
 * @description:
 * @date :2019/8/1 11:19
 */
public class SpanUtils {

    /**
     * 设置字体相对大小设置前景色
     *
     * @param context
     * @param str          目标字符串
     * @param start        开始位置
     * @param end          结束位置
     * @param relativeSize 相对大小 如：0.5f，2.0f
     * @param color        颜色值 如：#CCCCCC
     * @return
     */
    public static SpannableString getSpan(String str, int start, int end, float relativeSize, String color) {
        SpannableString ss = new SpannableString(str);
        ss.setSpan(new RelativeSizeSpan(relativeSize), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(Color.parseColor(color)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }
}
