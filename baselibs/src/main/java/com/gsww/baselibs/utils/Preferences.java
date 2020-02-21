package com.gsww.baselibs.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.gsww.baselibs.BaseApplication;
import com.gsww.baselibs.utils.aes.AESUtils;

public class Preferences {

    public static void saveString(String key, String value) {
        //加密
        value = AESUtils.encrypt(value, AESUtils.AUTHCODE);
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(String key) {
        String string = getSharedPreferences().getString(key, "");
        if("".equals(string)){
            return "";
        }
        return AESUtils.decrypt(string, AESUtils.AUTHCODE);
    }

    static SharedPreferences getSharedPreferences() {
        return BaseApplication.getInstance().getSharedPreferences(Constant.APP_CACHE, Context.MODE_PRIVATE);
    }

    public static void setBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static Boolean getBoolean(String key) {
        return getSharedPreferences().getBoolean(key, true);
    }

}
