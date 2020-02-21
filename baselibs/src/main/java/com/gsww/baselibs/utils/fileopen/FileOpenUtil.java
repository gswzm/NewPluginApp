package com.gsww.baselibs.utils.fileopen;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.gsww.baselibs.BaseApplication;
import com.gsww.baselibs.activity.image.FileDisplayActivity;
import com.gsww.baselibs.activity.image.ImageLargeDisplayActivity;

/**
 * @Description:
 * @Author: yaochangliang
 * @CreateDate: 2019-05-17 11:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2019-05-17 11:47
 * @UpdateRemark: 更新说明
 */

public class FileOpenUtil {

    public static void openFile(Context context,String url,String fileName){
        if (isImageFile(url, fileName)) {
            //跳转到图片的界面
            Intent intent = new Intent(context, ImageLargeDisplayActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("path", url);
            bundle.putString("datumName", fileName);
            intent.putExtras(bundle);
            context.startActivity(intent);
        } else {
            if (Build.VERSION.SDK_INT > 29) {
                FileHelper.openDocThirdParty(context,url, fileName);
            } else {
                //如果有x5内核 则打开，否则调取第三方
                if (BaseApplication.getInstance().isX5Init) {
                    FileDisplayActivity.show(context, url, fileName);
                } else {
                    FileHelper.openDocThirdParty(context,url, fileName);
                }
            }

        }
    }
    public static boolean isImageFile(String url, String fileName) {
        if (url.contains("?rid=")) {
            if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") ||
                    fileName.endsWith(".JPG") || fileName.endsWith(".JPEG")) {
                return true;
            } else {
                return false;
            }
        } else {
            if (url.endsWith(".jpg") || url.endsWith(".jpeg") || url.endsWith(".png") ||
                    url.endsWith(".JPG") || url.endsWith(".JPEG")) {
                return true;
            } else {
                return false;
            }
        }
    }
}
