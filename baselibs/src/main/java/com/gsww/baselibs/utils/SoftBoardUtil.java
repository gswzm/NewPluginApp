package com.gsww.baselibs.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * @Description: 软键盘的工具类
 * @Author: yaochangliang
 * @CreateDate: 2019-08-13 09:22
 * @UpdateUser: 更新者
 * @UpdateDate: 2019-08-13 09:22
 * @UpdateRemark: 更新说明
 */

public class SoftBoardUtil {
    /**
     * 关闭软键盘
     *

     */
    public static void closeKeybord(EditText mEditText, Context mContext)
    {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }
}
