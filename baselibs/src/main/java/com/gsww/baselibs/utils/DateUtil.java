package com.gsww.baselibs.utils;

import android.text.TextUtils;

/**
 * @Description:
 * @Author: yaochangliang
 * @CreateDate: 2019-08-15 10:56
 * @UpdateUser: 更新者
 * @UpdateDate: 2019-08-15 10:56
 * @UpdateRemark: 更新说明
 */

public class DateUtil {

    public static String getYearMonthDayStr(String createTime){
        if(TextUtils.isEmpty(createTime)){
            return "";
        }else{
            //2019-08-15
            if(createTime.length()>=10){
                return createTime.substring(0,10);
            }else{
                return createTime;
            }
        }
    }
}
