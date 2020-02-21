package com.gsww.newpluginapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MainInfoConfig {
    public static final String MAIN_LIST="main_version";
    private static final String MAIN_INFO="main_info";
    private static SharedPreferences sp;
    /**
     * 保存初始化数据
     */
    public static void saveMainInfo(Context context, int verisonId) {
        if(sp==null){
            sp=context.getSharedPreferences(MAIN_INFO, Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor=sp.edit();
        editor.putInt(MAIN_LIST,verisonId);
        editor.commit();
    }

    public static int getVersion(Context context){
        if(sp==null){
            sp=context.getSharedPreferences(MAIN_INFO, Context.MODE_PRIVATE);
        }
        return sp.getInt(MAIN_LIST,0);
    }
}
