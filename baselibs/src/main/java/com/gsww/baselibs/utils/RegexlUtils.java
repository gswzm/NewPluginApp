package com.gsww.baselibs.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具
 *
 * @author ht
 *         2012-7-26
 */
public class RegexlUtils {

    /**
     * isCDMAMobile:判断是否电信手机号. <br/>
     *
     * @param mobile -	要判断的手机号码
     * @author wchhuangya
     * @return            -	true-是电信手机号码；false-不是电信手机号码 ；
     */
    public static boolean isCDMAMobile(String mobile) {
        Pattern p = Pattern.compile("^((189|133|153|180|181|177|170){1}[0-9]{8})$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * 判断是否手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobile(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[^4,\\D])|(17[^4,\\D]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 判断是否电话号码 手机和座机一起验证
     *
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone) {
        Pattern p = Pattern.compile("((^(13|15|18|17)[0-9]{9}$)|(^([0-9]{3}-?[0-9]{7,8})|([0-9]{4}-?[0-9]{7,8})$))");
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    /**
     * 判断是否Email
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        Pattern p = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 验证是否Ip
     *
     * @param ip
     * @return
     */
    public static boolean isIp(String ip) {
        Pattern p = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
        Matcher m = p.matcher(ip);
        return m.matches();
    }

    /**
     * 验证是否日期
     *
     * @param date
     * @return
     */
    public static boolean isDate(String date) {
        Pattern p = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = p.matcher(date);
        return m.matches();
    }

    /**
     * 验证密码是否有效
     *
     * @param password
     * @return
     */
    public static boolean isValidPassword(String password) {
        if (!password.matches(".{6,14}")) {
            return false;
        }

        int level = 0;
        Matcher m = Pattern.compile("[a-z]+").matcher(password);
        if (m.find()) {
            level++;
        }
        Matcher m2 = Pattern.compile("[A-Z]+").matcher(password);
        if (m2.find()) {
            level++;
        }
        Matcher m3 = Pattern.compile("[0-9]+").matcher(password);
        if (m3.find()) {
            level++;
        }
        Matcher m4 = Pattern.compile("[^a-zA-Z0-9]+").matcher(password);
        if (m4.find()) {
            level++;
        }
        if (level < 2) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 电话号码短号码验证
     */
    public static boolean phoneValidate(String phone) {
        Pattern p = Pattern.compile("^([0-9]{1,100})$");
        Matcher m = p.matcher(phone);
        return m.matches();
    }


    /**
     * 弱口令校验规则
     *
     * @param str
     * @return
     */
    public static boolean checkInput(String str) {
        int num = 0;
        num = Pattern.compile("\\d").matcher(str).find() ? num + 1 : num;
        num = Pattern.compile("[a-z]").matcher(str).find() ? num + 1 : num;
        num = Pattern.compile("[A-Z]").matcher(str).find() ? num + 1 : num;
        num = Pattern.compile("[-~_.!@#$%^&*()+?><]").matcher(str).find() ? num + 1 : num;
        return num >= 3;
    }

    /**
     * 获取URL指定值
     * @param url
     * @param name
     * @return
     */
    public static String getParamByUrl(String url, String name) {
        url += "&";
        String pattern = "(\\?|&){1}#{0,1}" + name + "=[a-zA-Z0-9]*(&{1})";

        Pattern r = Pattern.compile(pattern);

        Matcher m = r.matcher(url);
        if (m.find( )) {
            System.out.println(m.group(0));
            return m.group(0).split("=")[1].replace("&", "");
        } else {
            return null;
        }
    }
}
