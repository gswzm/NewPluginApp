package com.gsww.baselibs.net.download;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface DownloadApi {

    /**
     * 下载文件
     *
     * @param url 完整的url
     * @return
     */
    @Streaming
    @GET
    @Headers("Accept-Encoding:identity")
    Call<ResponseBody> downloadWithUrl(@Url String url);
}
