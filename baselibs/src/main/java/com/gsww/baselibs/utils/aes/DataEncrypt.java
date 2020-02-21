package com.gsww.baselibs.utils.aes;

/**
 * 数据解密接口
 * @author wuxie
 * @date 2019-1-16
 */
public interface DataEncrypt {

    /**
     * 加密字符串
     * @param sourceStr
     * @param secretKey
     * @return
     */
     String encrypt(String sourceStr, String secretKey);

    /**
     * 加密字符数组
     * @param sourceBytes
     * @param secretKey
     * @return
     */
     byte[] encrypt(byte[] sourceBytes, byte[] secretKey);
}
