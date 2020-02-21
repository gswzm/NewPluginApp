package com.wzm.pluginapk;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.gsww.surveysdk.ui.SurveyMainActivity;

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
        SurveyMainActivity.actionStart(this,"ae70bc6559974d97b25d77b90b556bf5");

    }
}
