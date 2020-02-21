package com.wzm.newpluginapp.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.gsww.baselibs.net.download.DownloadRetrofitHelper;
import com.gsww.baselibs.net.netlistener.FileCallBackLis;
import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.model.PluginInfo;

import java.io.File;

public class MMCPlugin {

    private static MMCPlugin instance = null;

    private MMCPlugin() {
    }

    public static MMCPlugin getInstance() {
        if (instance == null) {
            instance = new MMCPlugin();
        }
        return instance;
    }

    /**
     * 打开插件
     *
     * @param context
     * @param pluginName
     * @param activityName
     * @param installListener
     */
    public void openPlugin(Context context, String pluginName, String activityName, InstallListener installListener) {
        this.installListener = installListener;
        if (RePlugin.isPluginInstalled(pluginName)) {//判断是否已经安装，安装了的话，就打开Activity，并且检查插件版本，需要更新的话就下载插件
            RePlugin.startActivity(context, RePlugin.createIntent(pluginName, activityName));
            if (installListener != null) {
                installListener.onSuccess();
            }
            PluginInfo info = RePlugin.getPluginInfo(pluginName);
            if (info.getVersion() < 2) {
                //版本号由你们接口获得，然后进行对比，插件版本低于接口的版本就下载更新
                //http://36.40.85.189:8081/authApi/feign/download
                downPlugin(context, "https://ali-fir-pro-binary.fir.im/fa819d2b28faa326794161d24e1a3b30e9ebf12e.apk?auth_key=1582198033-0-0-e8923b8d70ab526ab4953802a4c5543e", pluginName, activityName, true);
            }
        } else {
            //https://fir.im/y3nt
            downPlugin(context, "https://ali-fir-pro-binary.fir.im/fa819d2b28faa326794161d24e1a3b30e9ebf12e.apk?auth_key=1582198033-0-0-e8923b8d70ab526ab4953802a4c5543e", pluginName, activityName, false);
        }
    }

    /**
     * 安装插件
     *
     * @param context
     * @param pluginName
     * @param activityName
     */
    public void installPlugin(final Context context, final String pluginName, final String activityName, boolean isUpdate) {
        String pluginFilePath = context.getFilesDir().getAbsolutePath() + File.separator + pluginName + ".apk";
        final PluginInfo info = RePlugin.install(pluginFilePath);
        if (info != null) {
            if (isUpdate) {//判断，是否为更新，如果是更新就预加载，下次打开就是最新的插件，不是更新就开始安装
                RePlugin.preload(info);
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        RePlugin.startActivity(context, RePlugin.createIntent(info.getName(), activityName));
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (installListener != null) {
                                    installListener.onSuccess();
                                }
                            }
                        });
                    }
                }).start();
            }
        } else {
            if (installListener != null) {
                installListener.onFail("安装失败");
            }
        }
    }

    /**
     * 下载插件
     *
     * @param context
     * @param fileUrl
     * @param pluginName
     * @param activityName
     */
    public void downPlugin(final Context context, String fileUrl, final String pluginName, final String activityName, final boolean isUpdate) {
        //获取文件存储权限
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        //下载插件，里面的下载方法可以换成你们自己的，例如okhttp，xutils3等等下载都行，然后在回调中处理那几个方法就行
        String pluginFilePath = context.getFilesDir().getAbsolutePath() + File.separator + pluginName + ".apk";
        DownloadRetrofitHelper.getInstance().downloadFile(fileUrl, pluginFilePath, new FileCallBackLis() {
            @Override
            public void onSuccess(String info) {
                installPlugin(context, pluginName, activityName, isUpdate);
            }

            @Override
            public void onFailure(String error) {
                if (installListener != null) {
                    installListener.onFail("下载失败");
                }
            }

            @Override
            public void onProgress(int progress) {
                if (installListener != null) {
                    installListener.onInstalling(progress);
                }
            }
        });
    }

    /**
     * 打开插件的Activity
     *
     * @param context
     * @param pluginName
     * @param activityName
     */
    public void openActivity(Context context, String pluginName, String activityName) {
        RePlugin.startActivity(context, RePlugin.createIntent(pluginName, activityName));
    }

    /**
     * 打开插件的Activity 可带参数传递
     *
     * @param context
     * @param intent
     * @param pluginName
     * @param activityName
     */
    public void openActivity(Context context, Intent intent, String pluginName, String activityName) {
        intent.setComponent(new ComponentName(pluginName, activityName));
        RePlugin.startActivity(context, intent);
    }

    /**
     * 打开插件的Activity 带回调
     *
     * @param activity
     * @param intent
     * @param pluginName
     * @param activityName
     * @param requestCode
     */
    public void openActivityForResult(Activity activity, Intent intent, String pluginName, String activityName, int requestCode) {
        intent.setComponent(new ComponentName(pluginName, activityName));
        RePlugin.startActivityForResult(activity, intent, requestCode, null);
    }

    private InstallListener installListener;

    public interface InstallListener {
        void onInstalling(int progress);

        void onFail(String msg);

        void onSuccess();
    }
}