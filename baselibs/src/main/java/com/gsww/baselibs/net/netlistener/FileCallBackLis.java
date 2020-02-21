package com.gsww.baselibs.net.netlistener;

public interface FileCallBackLis {

    void onSuccess(String info);

    void onFailure(String error);

    void onProgress(int progress);
}
