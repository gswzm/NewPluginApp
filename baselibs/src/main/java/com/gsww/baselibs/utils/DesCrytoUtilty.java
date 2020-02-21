package com.gsww.baselibs.utils;


import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/***
 * des加解密码工具类
 * @author WYY 2015-9-25
 */
public class DesCrytoUtilty {

    public static final String AUTHCODE="efa8032d713beab2c262a040a47b2a40";
    /**
     * 加密
     *
     * @param text 待加密内容
     * @param key  公钥 长度32字符
     * @return
     */
    public static String DESEncrypt(String text, String key) {
        try {
            // 进行3-DES加密后的内容的字节
            DESedeKeySpec dks = new DESedeKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            SecretKey skey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            byte[] encryptedData = cipher.doFinal(text.getBytes("utf-8"));
            // 进行3-DES加密后的内容进行BASE64编码
//			Base64 base64en = new Base64();
            return Base64.encodeToString(encryptedData, Base64.NO_WRAP);
        } catch (Exception e) {
            return text;
        }
    }

    /**
     * 解密
     *
     * @param text 待解密内容
     * @param key  公钥
     * @return
     */
    public static String DESDecrypt(String text, String key) {
        try {
            // 进行3-DES加密后的内容进行BASE64解码
//			BASE64Decoder base64Decode = new BASE64Decoder();
            byte[] base64DValue = Base64.decode(text, Base64.NO_WRAP);
            // 进行3-DES解密后的内容的字节
            DESedeKeySpec dks = new DESedeKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            SecretKey skey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.DECRYPT_MODE, skey);
            byte[] encryptedData = cipher.doFinal(base64DValue);
            return new String(encryptedData, "utf-8");
        } catch (Exception e) {
            return text;
        }
    }


}
