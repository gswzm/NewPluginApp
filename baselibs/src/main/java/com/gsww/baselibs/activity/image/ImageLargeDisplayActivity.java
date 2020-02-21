package com.gsww.baselibs.activity.image;

import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;


import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.gsww.baselibs.R;
import com.gsww.baselibs.R2;
import com.gsww.baselibs.activity.BaseActivity;
import com.gsww.baselibs.utils.Logger;
import com.gsww.baselibs.utils.MD5;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Description:显示附件中的大图
 * @Author: yaochangliang
 * @CreateDate: 2019/4/18 12:07
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/4/18 12:07
 * @UpdateRemark: 更新说明
 */

public class ImageLargeDisplayActivity extends BaseActivity {
    public static final String TAG = "ImageLargeDisplayActivity";
    String path;
    String datumName;

    @BindView(R2.id.imageView)
    SubsamplingScaleImageView imageView;


    @Override
    protected void initTitle() {
        setTitle(datumName);
        setTopLeftButton(new OnClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        init();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        datumName = extras.getString("datumName");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_large_image;
    }

    OkHttpClient okHttpClient;

    private void init() {
        path = getIntent().getExtras().getString("path");
        downLoadFromNet(path);

    }

    private void downLoadFromNet(final String url) {

        if(!TextUtils.isEmpty(url)&&!url.contains("http")){
            File file=new File(url);
            setImage(file);
            return;
        }
        //1.网络下载、存储路径、
        File cacheFile = getDownCacheFile(url);
        if (cacheFile.exists()) {
            if (cacheFile.length() <= 0) {
                Logger.d(TAG, "删除空文件！！");
                cacheFile.delete();
                return;
            } else {
                //本地存在 直接打开
                setImage(cacheFile);
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
        Request request = new Request.Builder().url(url).build();
        showProgressDialog("数据加载中,请稍候...");
        okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败
                Log.e("test", "onFailure:" + e.toString());
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
                            setImage(file);
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
        File cacheFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/haidong/"
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

    private void setImage(File file) {
//判断是否是AndroidN以及更高的版本
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            Uri contentUri = FileProvider.getUriForFile(context, "com.gsww.party.fileprovider", file);
//            imageView.setImage(ImageSource.uri(contentUri));
//        } else {
//           imageView.setImage(ImageSource.uri(file.getAbsolutePath()));
//        }
        imageView.setImage(ImageSource.uri(file.getAbsolutePath()));
    }

}
