package com.gsww.pluginapk;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.gsww.surveysdk.utils.SurveyHelper;

/**
 * 描述：
 * 创建者： wangzm
 * 时间：2020/2/19
 * 修改者：
 * 时间：
 */
public class PluginApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SurveyHelper.getInstance().initUrl(BuildConfig.surveyUrl, BuildConfig.APPLICATION_ID, BuildConfig.DEBUG, SurveyHelper.TO_APP, BuildConfig.calculateUrl);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
