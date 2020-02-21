package com.gsww.baselibs.utils.fileopen;

import android.os.Environment;

import com.gsww.baselibs.utils.Logger;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

/**
 * 客户端配置
 *
 * @author ht
 * @date 2012-5-2
 */
public class Configuration {
    private static Properties defaultProperty;

    static {
        init();
    }

    static void init() {
        defaultProperty = new Properties();
        InputStream stream =
                Configuration.class.getResourceAsStream("/assets/config.properties");
        try {
            defaultProperty.load(stream);
        } catch (Exception e) {

        }
        defaultProperty.putAll(System.getProperties());
    }

    /**
     * 获取应用Logo
     *
     * @return
     */
    public static String getSMSNum() {
        return String.valueOf(Configuration.defaultProperty.get("sms.num"));
    }

    /**
     * 获取应用下载地址
     *
     * @return
     */
    public static String getAppDownloadUrl() {
        return String.valueOf(Configuration.defaultProperty.get("app.download.url"));
    }

    /**
     * 获取dubug标识，1.打印debug日志，0：不打印
     *
     * @return
     */
    public static String getDebugLogSign() {
        return String.valueOf(Configuration.defaultProperty.get("debugLog.sign"));
    }

    /**
     * 获取连接超时设置
     *
     * @return
     */
    public static int getTimeout() {
        int timeout = 5000;
        try {
            timeout = Integer.parseInt((String) (Configuration.defaultProperty.get("client.timeout")));
        } catch (Exception e) {
            Logger.info(e);
        }
        return timeout;
    }

    /**
     * 获取socket超时设置
     *
     * @return
     */
    public static int getSockeout() {
        int timeout = 10000;
        try {
            timeout = Integer.parseInt((String) (Configuration.defaultProperty.get("client.socketout")));
        } catch (Exception e) {
            Logger.info(e);
        }
        return timeout;
    }

    /**
     * 获取列表每页显示数据量
     *
     * @return
     */
    public static int getPageSize() {
        int timeout = 10;
        try {
            timeout = Integer.parseInt((String) (Configuration.defaultProperty.get("list.pagesize")));
        } catch (Exception e) {
            Logger.info(e);
        }
        return timeout;
    }

    /**
     * 获取本地文件存储地址
     *
     * @return
     */
    public static String getBaseFilePath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
            return sdDir.toString() + String.valueOf(Configuration.defaultProperty.get("client.filePath"));
        }
        return String.valueOf(Configuration.defaultProperty.get("client.filePath"));
    }

    /**
     * 获取本地文件存储临时地址
     *
     * @return
     */
    public static String getTempPath() {
        return String.valueOf(Configuration.defaultProperty.get("client.tempfilepath"));
    }
}
