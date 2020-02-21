package com.gsww.baselibs.net.download;


import android.os.Handler;
import android.os.Looper;


import com.gsww.baselibs.manager.ThreadPoolManager;
import com.gsww.baselibs.net.HeaderFileInterceptor;
import com.gsww.baselibs.net.netlistener.FileCallBackLis;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 下载文件帮助类 单例
 */
public class DownloadRetrofitHelper {

    private static volatile DownloadRetrofitHelper instance;
    private Retrofit retrofit;

    private DownloadRetrofitHelper() {

    }

    /**
     * DCL单例
     *
     * @return
     */
    public static DownloadRetrofitHelper getInstance() {
        if (instance == null) {
            synchronized (DownloadRetrofitHelper.class) {
                if (instance == null) {
                    instance = new DownloadRetrofitHelper();
                }
            }
        }
        return instance;
    }

    /**
     * 构建Retrofit实例
     *
     * @param fileCallBackLis
     * @return
     */
    private Retrofit getRetrofit(FileCallBackLis fileCallBackLis) {
        if (retrofit == null) {
            DownloadInterceptor interceptor = new DownloadInterceptor(fileCallBackLis);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .addInterceptor(new HeaderFileInterceptor())
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .writeTimeout(120, TimeUnit.SECONDS)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://www.baidu.com/")
                    .client(okHttpClient)
                    .build();
        }
        //构建并返回retrofit对象
        return retrofit;
    }

    /**
     * 下载文件
     *  @param url             完整url
     * @param dstPath         文件保存位置（绝对路径）
     * @param fileCallBackLis
     */
    public Call downloadFile(String url, final String dstPath, final FileCallBackLis fileCallBackLis) {
        final Call<ResponseBody> call = getRetrofit(fileCallBackLis).create(DownloadApi.class).downloadWithUrl(url);
        //线程池开启新线程
        ThreadPoolManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<ResponseBody> execute = call.execute();
                    if (execute.isSuccessful()) {
                        InputStream inputStream = execute.body().byteStream();
                        final File file = DownloadRetrofitHelper.this.writeFile(inputStream, dstPath);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                fileCallBackLis.onSuccess(file.getAbsolutePath());
                            }
                        });
                    } else {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                fileCallBackLis.onFailure("网络异常！");
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            fileCallBackLis.onFailure("文件下载失败！");
                        }
                    });
                }
            }
        });
        return call;
    }

    /**
     * 写文件到储存
     *
     * @param inputStream
     * @param dstPath
     * @return
     */
    private File writeFile(InputStream inputStream, String dstPath) throws IOException {
        File file = new File(dstPath);
        //如果父目录不存在，先创建目录
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(file);
        byte[] bytes = new byte[1024];
        int len;
        while ((len = inputStream.read(bytes)) != -1) {
            fos.write(bytes, 0, len);
        }
        fos.flush();
        inputStream.close();
        fos.close();
        return file;
    }
}
