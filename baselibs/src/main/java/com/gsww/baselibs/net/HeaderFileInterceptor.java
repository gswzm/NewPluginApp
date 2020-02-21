/**
 * Copyright (C), 2015-2018, 甘肃万维有限公司
 * FileName: BaseInterceptor
 * Author:   Administrator
 * Date:     2018/11/6 9:52
 * Description: 拦截器
 */
package com.gsww.baselibs.net;

import com.gsww.baselibs.utils.Constant;
import com.gsww.baselibs.utils.StringHelper;
import com.gsww.baselibs.utils.aes.AESUtils;

import java.io.IOException;
import java.net.URLDecoder;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 〈拦截器〉
 *
 * @author kangjh
 * @create 2019/07/16
 */
public class HeaderFileInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request().newBuilder()
                .addHeader("Accept-Encoding", "identity")
                .build();
        Response response = chain.proceed(request);
        return response;
    }


}
