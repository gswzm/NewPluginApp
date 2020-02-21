package com.gsww.pluginapk;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.gsww.surveysdk.ui.SurveyMainActivity;
/**
 * 描述：插件apk首页
 * 类名： PluginActivity
 * 创作者： wangzm
 * 时间：2020/2/21
 */

public class PluginActivity extends AppCompatActivity {

    private String strType;
    private int intType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin);
        strType=getIntent().getStringExtra("str");
        intType=getIntent().getIntExtra("int",0);
        Log.d("PluginActivity", "onCreate: strType="+strType+" intType="+intType);
        SurveyMainActivity.actionStart(this,"1315dfa4422b40959fe5fbed8a54120c");

    }
}
