package com.gsww.baselibs.net.netlistener;

/**
 * 网络请求回调监听
 */
public interface CallBackLis<T> {

    void onSuccess(String method, T content);

    void onFailure(String method, String error);

    void onComplete();

}
