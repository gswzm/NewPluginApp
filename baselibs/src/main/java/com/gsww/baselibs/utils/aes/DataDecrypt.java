package com.gsww.baselibs.utils.aes;

/**
 * 数据解密接口
 * @author wuxie
 * @date 2019-1-16
 */
public interface DataDecrypt {

    /**
     * 解密字符串
     * @param encryptStr
     * @param secretKey
     * @return
     */
     String decrypt(String encryptStr, String secretKey);

    /**
     * 解密字节数组
     * @param encryptBytes
     * @param secretKey
     * @return
     */
     byte[] decrypt(byte[] encryptBytes, byte[] secretKey);
}
