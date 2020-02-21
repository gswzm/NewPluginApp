//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gsww.baselibs.utils;

import android.util.Log;
/**
 * 描述：
 * 类名： Logger
 * 创作者： wangzm
 * 时间：2019/2/29
 */

public class Logger {
    private static boolean SHOW_FLAG = false;

    public Logger() {
    }
    public static void info(Exception e){
        if(SHOW_FLAG)
        Log.i("utils.Logger",e.toString());
    }

    public static void info(Throwable e){
        if(SHOW_FLAG)
        Log.i("utils.Logger",e.toString());
    }
    public static void d(String d){
        if(SHOW_FLAG){
            Log.d("utils.Logger",d);
        }

    }
    public static void e(String e){
        if(SHOW_FLAG){
            Log.e("utils.Logger",e);
        }

    }
    public static void e(String tag, String msg) {
        if (SHOW_FLAG) {
            Log.e(tag, msg);
        }

    }

    public static void d(String tag, String msg) {
        if (SHOW_FLAG) {
            Log.d(tag, msg);
        }

    }

    public static void i(String tag, String msg) {
        if (SHOW_FLAG) {
            Log.i(tag, msg);
        }

    }

    public static void v(String tag, String msg) {
        if (SHOW_FLAG) {
            Log.v(tag, msg);
        }

    }

    public static void w(String tag, String msg) {
        if (SHOW_FLAG) {
            Log.w(tag, msg);
        }

    }

    public static void e(String tag, String msg, Throwable e) {
        if (SHOW_FLAG) {
            Log.e(tag, msg, e);
        }

    }

    public static void d(String tag, String msg, Throwable e) {
        if (SHOW_FLAG) {
            Log.d(tag, msg, e);
        }

    }

    public static void i(String tag, String msg, Throwable e) {
        if (SHOW_FLAG) {
            Log.d(tag, msg, e);
        }

    }

    public static void v(String tag, String msg, Throwable e) {
        if (SHOW_FLAG) {
            Log.v(tag, msg, e);
        }

    }

    public static void w(String tag, String msg, Throwable e) {
        if (SHOW_FLAG) {
            Log.w(tag, msg, e);
        }

    }

    public static void setDebug(boolean debug) {
        SHOW_FLAG = debug;
    }

    public static boolean getDebug() {
        return SHOW_FLAG;
    }
}
