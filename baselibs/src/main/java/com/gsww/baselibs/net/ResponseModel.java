package com.gsww.baselibs.net;

import java.io.Serializable;

public class ResponseModel<T> implements Serializable {

    public static final int SUCCESS = 000;

    private int resCode;
    private String resMsg;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getResCode() {
        return resCode;
    }

    public void setResCode(int resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }


}
