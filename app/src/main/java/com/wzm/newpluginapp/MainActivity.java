package com.wzm.newpluginapp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gsww.baselibs.net.download.DownloadRetrofitHelper;
import com.gsww.baselibs.net.netlistener.FileCallBackLis;
import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.model.PluginInfo;
import com.wzm.newpluginapp.utils.FileUtils;
import com.wzm.newpluginapp.utils.MMCPlugin;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
//                final ProgressDialog pd = ProgressDialog.show(MainActivity.this, "正在加载插件", "请稍后...", true, true);
//                // FIXME: 仅用于安装流程演示 2017/7/24
//                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        simulateInstallPlugin();
//                        pd.dismiss();
//                    }
//                }, 1000);
                btnSkip.setClickable(false);
                MMCPlugin.getInstance().openPlugin(this, "survey", "com.wzm.pluginapk.PluginActivity", new MMCPlugin.InstallListener() {
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
                break;
        }
    }

    /**
     * 暂时在assets文件下存放apk 并加载
     */
    private void simulateInstallPlugin(){
        String apkName= "survey.apk";
        String dapkPath = "external" + File.separator + apkName;

        // 文件是否已经存在？直接删除重来
        String pluginFilePath = getFilesDir().getAbsolutePath() + File.separator + apkName;
        File pluginFile = new File(pluginFilePath);

        if (pluginFile.exists()) {
            pluginFile.delete();
        }
        // 开始复制
        FileUtils.copyAssetsFileToAppFiles(this,dapkPath, apkName);
        PluginInfo info = null;
        if (pluginFile.exists()) {
            info = RePlugin.install(pluginFilePath);
        }

        if (info != null) {
            Intent intent=RePlugin.createIntent(info.getAlias(), info.getPackageName()+".PluginActivity");
            intent.putExtra("str","传一个string类型的参数");
            intent.putExtra("int",9527);
            boolean isPlugin=RePlugin.startActivity(MainActivity.this, intent);
            if (!isPlugin){
                Toast.makeText(MainActivity.this, "启动失败", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "install external plugin failed", Toast.LENGTH_SHORT).show();
        }
    }
}
