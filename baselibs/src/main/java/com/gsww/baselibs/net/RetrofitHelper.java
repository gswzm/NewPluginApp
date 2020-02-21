package com.gsww.baselibs.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitHelper {
    private static volatile RetrofitHelper instance;
    private Retrofit retrofit;
    private Retrofit newRetrofit;
    public static String baiduUrl = "http://api.map.baidu.com/";
    public static String baseUrl;
    public static String vBaseUrl;
    private Retrofit bRetrofit;

    public static RetrofitHelper getInstance() {
        if (instance == null) {
            synchronized (RetrofitHelper.class) {
                if (instance == null) {
                    instance = new RetrofitHelper();
                }
            }
        }
        return instance;
    }
    private RetrofitHelper(){
        //日志拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new HeaderInterceptor())
                .retryOnConnectionFailure(true)//连接失败后是否重新连接
                .connectTimeout(30, TimeUnit.SECONDS)//超时时间15S
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(IGsonFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        newRetrofit = new Retrofit.Builder()
                .baseUrl(vBaseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(IGsonFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        bRetrofit = new Retrofit.Builder()
                .baseUrl(baiduUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(IGsonFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public Retrofit getNewRetrofit() {
        return newRetrofit;
    }

    public Retrofit getbRetrofit() {
        return bRetrofit;
    }
}

