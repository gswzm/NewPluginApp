package com.gsww.baselibs.activity.image;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;


import com.gsww.baselibs.R;
import com.gsww.baselibs.activity.BaseActivity;
import com.gsww.baselibs.utils.Logger;
import com.gsww.baselibs.utils.MD5;
import com.gsww.baselibs.widget.SuperFileView2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class FileDisplayActivity extends BaseActivity {


    private String TAG = "FileDisplayActivity";
    SuperFileView2 mSuperFileView;

    String filePath;
    String datumName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView();
        init();
    }

    @Override
    protected void initTitle() {
        datumName = getIntent().getExtras().getString("datumName");
        setTitle(datumName);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_file_display;
    }


    public void init() {
        mSuperFileView = (SuperFileView2) findViewById(R.id.mSuperFileView);

        mSuperFileView.setOnGetFilePathListener(new SuperFileView2.OnGetFilePathListener() {
            @Override
            public void onGetFilePath(SuperFileView2 mSuperFileView2) {
                getFilePathAndShowFile(mSuperFileView2);
            }
        });

        Intent intent = this.getIntent();
        String path = intent.getExtras().getString("path");
        if (!TextUtils.isEmpty(path)) {
            Logger.d(TAG, "文件path:" + path);
            setFilePath(path);
        }
        mSuperFileView.show();

    }


    private void getFilePathAndShowFile(SuperFileView2 mSuperFileView2) {
        File cacheFile = getDownCacheFile(getFilePath());
        if (cacheFile.exists()) {
            if (cacheFile.length() <= 0) {
                Logger.d(TAG, "删除空文件！！");
                cacheFile.delete();
                return;
            }
        }

        if (getFilePath().contains("http")) {//网络地址要先下载

            downLoadFromNet(getFilePath(), mSuperFileView2);

        } else {
            mSuperFileView2.displayFile(new File(getFilePath()));
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d("FileDisplayActivity-->onDestroy");
        if (mSuperFileView != null) {
            mSuperFileView.onStopDisplay();
        }
    }

    /**
     * @param context
     * @param url       文件下载地址
     * @param datumName 文件的名称，如果url是".doc"这种结尾的，则datumExtension传空，
     */
    public static void show(Context context, String url, String datumName) {
        if (!TextUtils.isEmpty(url)) {
            Intent intent = new Intent(context, FileDisplayActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("path", url);
            bundle.putString("datumName", datumName);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }


    }

    public void setFilePath(String fileUrl) {
        this.filePath = fileUrl;
    }

    private String getFilePath() {
        return filePath;
    }


    OkHttpClient okHttpClient;

    private void downLoadFromNet(final String url, final SuperFileView2 mSuperFileView2) {

        //1.网络下载、存储路径、
        File cacheFile = getDownCacheFile(url);
        if (cacheFile.exists()) {
            if (cacheFile.length() <= 0) {
                Logger.d(TAG, "删除空文件！！");
                cacheFile.delete();
                return;
            } else {
                //本地存在 直接打开
                mSuperFileView2.displayFile(cacheFile);
                return;
            }
        }


        File file1 = getCacheDir(url);
        if (!file1.exists()) {
            file1.mkdirs();
            Logger.d(TAG, "创建缓存目录： " + file1.toString());
        }

        //fileN : /storage/emulated/0/pdf/kauibao20170821040512.pdf
        File fileN = getDownCacheFile(url);//new File(getCacheDir(url), getFileName(url))

        if (!fileN.exists()) {
            try {
                boolean mkdir = fileN.createNewFile();
                Logger.d(TAG, "创建缓存文件： " + fileN.toString() + " mkdir:" + mkdir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.e("test","url:"+url);
        Log.e("test","fileN.getAbsolutePath():"+fileN.getAbsolutePath());

        Request request = new Request.Builder().url(url).build();
        showProgressDialog("数据加载中,请稍候...");
        okHttpClient=new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败
                Log.e("test","onFailure:"+e.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast("文件下载失败");
                        File file = getDownCacheFile(url);
                        if (file.exists()) {
                            Logger.d(TAG, "删除下载失败文件");
                            file.delete();
                        }
                    }
                });
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    final File file = new File(getCacheDir(url), getFileName(url, datumName));
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        // 下载中
                       // listener.onDownloading(progress);
                    }
                    fos.flush();
                    // 下载完成
                   // listener.onDownloadSuccess();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dismissDialog();
                            mSuperFileView2.displayFile(file);
                        }
                    });
                } catch (Exception e) {
                   // listener.onDownloadFailed();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("文件下载失败");
                            File file = getDownCacheFile(url);
                            if (file.exists()) {
                                Logger.d(TAG, "删除下载失败文件");
                                file.delete();
                            }
                        }
                    });
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });

    }

    /***
     * 获取缓存目录
     *
     * @param url
     * @return
     */
    private File getCacheDir(String url) {

        return new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/jzfp_henan/");

    }

    /***
     * 绝对路径获取缓存文件
     *
     * @param url
     * @return
     */
    private File getDownCacheFile(String url) {
        File cacheFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/jzfp_henan/"
                + getFileName(url, datumName));
        Logger.d(TAG, "缓存文件 = " + cacheFile.toString());
        return cacheFile;
    }

    /***
     * 根据链接获取文件名（带类型的），具有唯一性
     *
     * @param url
     * @return
     */
    private String getFileName(String url, String datumName) {
        String fileName = "";
        if (url.contains("?rid=")) {//这种下载链接不包含文件名
            fileName = MD5.hashKey(url) + "." + getFileType(datumName);
        } else {
            fileName = MD5.hashKey(url) + "." + getFileType(url);
        }
        return fileName;
    }

    /***
     * 获取文件类型
     *
     * @param paramString
     * @return
     */
    private String getFileType(String paramString) {
        String str = "";

        if (TextUtils.isEmpty(paramString)) {
            Logger.d(TAG, "paramString---->null");
            return str;
        }
        Logger.d(TAG, "paramString:" + paramString);
        int i = paramString.lastIndexOf('.');
        if (i <= -1) {
            Logger.d(TAG, "i <= -1");
            return str;
        }


        str = paramString.substring(i + 1);
        Logger.d(TAG, "paramString.substring(i + 1)------>" + str);
        return str;
    }


}
