package com.gsww.baselibs.utils.fileopen;

import android.app.Dialog;
import android.content.Context;
import android.os.Environment;


import com.gsww.baselibs.view.CustomProgressDialog;

import java.io.File;

public class FileShowUtil {

    private Context context;
    private UploadFileState fileState;

    public FileShowUtil(Context context) {
        this.context = context;
    }

    public void clickFileView(final String url, String fileName) {
        final Dialog progressDialog = CustomProgressDialog.show(context, "", "数据获取中,请稍候...", true);
        File rootFile = new File(Environment.getExternalStorageDirectory(), "/jzfp_henan/files/");
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        //文件存储
        final File localFile = new File(Environment.getExternalStorageDirectory() + "/jzfp_henan/files/", OpenFileUtil.getFileName(fileName));
        new Thread() {
            public void run() {
                File file = new File(localFile.getAbsolutePath());
                //判断是否有此文件
                if (file.exists()) {
                    //有缓存文件,拿到路径 直接打开
                    progressDialog.dismiss();
                    if(fileState!=null){
                        fileState.fileUploadSuccess(file);
                    }
                    return;
                }
                //本地没有此文件 则从网上下载打开
                File downloadfile = OpenFileUtil.downLoad(url, localFile.getAbsolutePath(), progressDialog);
                progressDialog.dismiss();
                if (downloadfile != null) {
                    if(fileState!=null){
                        fileState.fileUploadSuccess(downloadfile);
                    }
                } else {
                    if(fileState!=null){
                        fileState.fileUploadError();
                    }
                }
            }

        }.start();
    }

    public void setFileState(UploadFileState fileState) {
        this.fileState = fileState;
    }

    public interface UploadFileState {
        void fileUploadSuccess(File file);

        void fileUploadError();
    }
}
