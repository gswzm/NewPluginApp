package com.gsww.newpluginapp;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gsww.newpluginapp.utils.MPluginHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 描述：主程序主界面
 * 类名： MainActivity
 * 创作者： wangzm
 * 时间：2020/2/21
 */

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btnSkip)
    Button btnSkip;
    @BindView(R.id.btnOpen)
    Button btnOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnSkip, R.id.btnOpen})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSkip://外置
                btnSkip.setClickable(false);
                MPluginHelper.getInstance().openPlugin(this, "survey", "com.gsww.pluginapk.PluginActivity", new MPluginHelper.InstallListener() {
                    @Override
                    public void onInstalling(int progress) {
                        btnSkip.setText("正在下载插件：" + progress + "%");
                        if (progress == 100) {
                            btnSkip.setText("正在安装插件...");
                        }
                    }

                    @Override
                    public void onFail(String msg) {
                        btnSkip.setClickable(true);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess() {
                        btnSkip.setClickable(true);
                        btnSkip.setText("打开插件");
                    }
                });
                break;
            case R.id.btnOpen://内置
                MPluginHelper.getInstance().openActivity(this,"survey","com.gsww.pluginapk.PluginActivity");
                break;
        }
    }

}
