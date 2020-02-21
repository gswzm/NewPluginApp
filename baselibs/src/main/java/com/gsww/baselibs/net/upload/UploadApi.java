package com.gsww.baselibs.net.upload;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface UploadApi {

    /**
     * 单个文件上传
     *
     * @param url
     * @param file
     * @return
     */
    @Multipart
    @POST
    Call<ResponseBody> uploadFile(@Url String url, @Part MultipartBody.Part file);

    /**
     * 单个文件带信息上传
     *
     * @param url
     * @param body
     * @return
     */
    @POST
    Call<ResponseBody> uploadFileWithInfo(@Url String url, @Body MultipartBody body);

    /**
     * 多文件上传
     *
     * @param url
     * @param body
     * @return
     */
    @POST
    Call<ResponseBody> uploadFiles(@Url String url, @Body MultipartBody body);

    /**
     * 多文件带信息上传
     *
     * @param url
     * @param body
     * @return
     */
    @POST
    Call<ResponseBody> uploadFilesWithInfo(@Url String url, @Body MultipartBody body);

}
