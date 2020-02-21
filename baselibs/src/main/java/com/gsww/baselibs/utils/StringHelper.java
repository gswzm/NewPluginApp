package com.gsww.baselibs.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 字符串帮助类
 * </p>
 *
 * @author ht
 * @version V2.0.0
 * @company 中国电信甘肃万维公司
 * @project nma-c-android
 * @date 2012-8-13 上午10:08:57
 * @class com.gsww.util.StringHelper
 */
public class StringHelper {
    /**
     * 过滤HTML标签
     *
     * @param content
     * @return
     */
    public static String dealHtml(String content) {
        Pattern pt = Pattern.compile("<[^>]*>");
        content = content.replaceAll(pt.pattern(), "");
        if (content.indexOf("&ldquo;") > -1) {
            content = content.replaceAll("&ldquo;", "“");
        }
        if (content.indexOf("&rdquo;") > -1) {
            content = content.replaceAll("&rdquo;", "”");
        }

        if (content.indexOf("&lsquo;") > -1) {
            content = content.replaceAll("&lsquo;", "’");
        }
        if (content.indexOf("&rsquo;") > -1) {
            content = content.replaceAll("&rsquo;", "‘");
        }

        if (content.indexOf("&sbquo;") > -1) {
            content = content.replaceAll("&sbquo;", "，");
        }

        if (content.indexOf("&quot;") > -1) {
            content = content.replaceAll("&quot;", "\"");
        }
        if (content.indexOf("&amp;") > -1) {
            content = content.replaceAll("&amp;", "&");
        }
        if (content.indexOf("&lt;") > -1) {
            content = content.replaceAll("&lt;", "<");
        }
        if (content.indexOf("&gt;") > -1) {
            content = content.replaceAll("&gt;", ">");
        }
        if (content.indexOf("&nbsp;") > -1) {
            content = content.replaceAll("&nbsp;", "");
        }
        return content;
    }

    /**
     * 处理正文中的特殊字符
     *
     * @param str
     * @return
     */
    public static String dealSpecial(String str) {
        char[] char_arr = str.toCharArray();
        int[] pos = new int[10000];
        for (int i = 0; i < pos.length; i++)
            pos[i] = -1;
        int j = 0;
        for (int i = 0; i < char_arr.length; i++) {
            if ((int) char_arr[i] == 10 || (int) char_arr[i] == 13) {
                pos[j++] = i;
            }
        }
        StringBuffer sb = new StringBuffer(str);
        for (int i = 0; i < pos.length; i++) {
            if (pos[i] != -1) {
                sb.insert(pos[i] + i * (5 - 1), "<br/>");
                sb.replace(pos[i] + i * (5 - 1) + 5, pos[i] + i * (5 - 1) + 5
                        + 1, "");
            }
        }
        return sb.toString();
    }

    /**
     * 根据传入的正则表达式数组里的内容，判断传入的字符串是否符合正则表达式的要求
     *
     * @param checkedStr -- 传入的要被检查的字符串
     * @param regStr     -- 要匹配的正则表达式数组
     * @return -- 如果符合正则表达式的要求，返回true;如果不符合正则表达式的要求，返回false
     */
    public static Boolean ifMeetRequire(final String checkedStr,
                                        final String[] regStr) {
        Boolean res = false;
        for (String s : regStr) {
            if (checkedStr.matches(s)) {
                res = true;
                break;
            }
        }
        return res;
    }

    /**
     * 字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        if (str == null || str.trim().equals(""))
            return true;
        else
            return false;
    }

    /**
     * 字符串是否不为空
     *
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * object 转 String
     *
     * @param obj
     * @return
     */
    public static String convertToString(Object obj) {
        if (obj == null)
            return "";
        String str = obj.toString().trim();
        if (str.equals("null") || str.equals("NULL"))
            return "";
        return str;
    }

    /**
     * 阿拉伯数字转中文
     *
     * @param string
     * @return
     */
    public static String numToChinese(String string) {
        String[] s1 = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String[] s2 = {"十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千"};

        String result = "";

        int n = string.length();
        for (int i = 0; i < n; i++) {

            int num = string.charAt(i) - '0';

            if (i != n - 1 && num != 0) {
                result += s1[num] + s2[n - 2 - i];
            } else {
                result += s1[num];
            }

        }
        return result;

    }

    /**
     * 半角转换为全角
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 获取百分比
     *
     * @param p1
     * @param p2
     * @param decimals
     * @return
     */
    public static String getPercent(double p1, double p2, int decimals) {
        if (p2 <= p1) {
            return "100%";
        } else {
            double p3 = p1 / p2;
            NumberFormat nf = NumberFormat.getPercentInstance();
            if (decimals > 0)
                nf.setMinimumFractionDigits(decimals);
            return nf.format(p3);
        }
    }

    /**
     * 获取百分比
     *
     * @param p1
     * @param p2
     * @param decimals
     * @return
     */
    public static double getPercent2(double p1, double p2, int decimals) {
        double p3 = p1 / p2;
		/*if (p3 <1) {
			p3 = p3 *100;
		}*/
        // 设置位数
        int roundingMode = 4;// 表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
        BigDecimal bd = new BigDecimal(p3 * 100);

        bd = bd.setScale(decimals, roundingMode);
        return bd.doubleValue();
    }

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }

    /**
     * * 提示输入内容超过规定长度 * @param context * @param editText * @param max_length * @param
     * err_msg
     */

    public static void lengthFilter(final Context context, EditText editText,
                                    final int max_length, final String err_msg) {

        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(max_length) {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                // TODO Auto-generated method stub //获取字符个数(一个中文算2个字符)
                int destLen = getCharacterNum(dest.toString());
                int sourceLen = getCharacterNum(source.toString());
                if (destLen + sourceLen > max_length) {
                    Toast.makeText(context, err_msg, Toast.LENGTH_SHORT).show();
                    return "";
                }
                return source;
            }
        };
        editText.setFilters(filters);
    }

    /**
     * * @param content * @return
     */
    public static int getCharacterNum(String content) {
        if (content.equals("") || null == content) {
            return 0;
        } else {
            return content.length() + getChineseNum(content);
        }
    }

    /**
     * 计算字符串的中文长度 * @param s * @return
     */
    public static int getChineseNum(String s) {
        int num = 0;
        char[] myChar = s.toCharArray();
        for (int i = 0; i < myChar.length; i++) {
            if ((char) (byte) myChar[i] != myChar[i]) {
                num++;
            }
        }
        return num;
    }

    /**
     * 方法描述 : 电信手机号码校验
     *
     * @param phone
     * @return
     */
    public static boolean phoneCheck(String phone) {
        String regxpForHtml = "^((1[35]3)|(18[019])|(177))\\d{8}$"; // 电信手机校验
        Pattern pattern = Pattern.compile(regxpForHtml);
        Matcher matcher = pattern.matcher(phone);
        boolean result1 = matcher.find();
        if (result1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检查是否为有效的身份证号码
     *
     * @param str
     * @return
     */
    public static boolean checkIDCard(String str) {

        class IDCard {
            final int[] wi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8,
                    4, 2, 1};

            final int[] vi = {1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2};

            private int[] ai = new int[18];

            public IDCard() {
            }

            public boolean Verify(String idcard) {
                if (idcard.length() == 15) {
                    idcard = uptoeighteen(idcard);
                }
                if (idcard.length() != 18) {
                    return false;
                }
                String verify = idcard.substring(17, 18);
                if (verify.equals(getVerify(idcard))) {
                    return true;
                }
                return false;
            }

            public String getVerify(String eightcardid) {
                int remaining = 0;
                if (eightcardid.length() == 18) {
                    eightcardid = eightcardid.substring(0, 17);
                }
                if (eightcardid.length() == 17) {
                    int sum = 0;
                    for (int i = 0; i < 17; i++) {
                        String k = eightcardid.substring(i, i + 1);
                        ai[i] = Integer.parseInt(k);
                    }
                    for (int i = 0; i < 17; i++) {
                        sum = sum + wi[i] * ai[i];
                    }
                    remaining = sum % 11;
                }
                return remaining == 2 ? "X" : String.valueOf(vi[remaining]);
            }

            public String uptoeighteen(String fifteencardid) {
                String eightcardid = fifteencardid.substring(0, 6);
                eightcardid = eightcardid + "19";
                eightcardid = eightcardid + fifteencardid.substring(6, 15);
                eightcardid = eightcardid + getVerify(eightcardid);
                return eightcardid;
            }

        }

        IDCard idCard = new IDCard();
        return idCard.Verify(str);
    }

    /**
     * 检查是否为有效的电话号码
     *
     * @param str
     * @return
     */
    public static boolean checkPhone(String str) {
        Pattern p = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
        Matcher m = p.matcher(str);
        return m.matches();
    }


    /**
     * 检查是否为有效的电话号码
     *
     * @param str
     * @return
     */
    public static boolean isTel(String str) {
        Pattern p = Pattern.compile("^(((13[0-9]{1})|(15[0-9]{1})|(17[013678])|(18[0-3,5-9]{1}))+\\d{8})$|^(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 数据为空,转化为零
     **/
    public static String convertNullToString(Object obj) {
        if (obj == null) return "0";
        String str = obj.toString().trim();
        if (str.equals("null") || str.equals("NULL")) return "0";
        return str;
    }

    //是否非负整数
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("^\\d+$"); // 非负整数
        Matcher match = pattern.matcher(str);
        if (match.matches() == false) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * string类型的值转换为double
     *
     * @param str
     * @return
     */
    public static Double getDoubleValue(String str) {
        Double dou = null;
        if (isNotBlank(str)) {
            dou = Double.valueOf(str);
        }
        return dou;
    }

    /**
     * 获取实际的value值
     *
     * @param key
     * @param maps
     * @return
     */
    public static String getRealValue(String key, Map<String, String> maps) {
        return maps.get(key);
    }


    /**
     * 返回必填字段
     *
     * @param text 要变的文字
     * @return
     */
    public static String getMajorTitle(String text) {

        return "<font color='#007FEB'> " + text + "</font>";
    }

    /**
     * 通过身份证号算年龄
     *
     * @param IdNO
     * @return
     */
    public static int IdNOToAge(String IdNO) {
        int leh = IdNO.length();
        String dates = "";
        if (leh >= 10) {
            dates = IdNO.substring(6, 10);
            SimpleDateFormat df = new SimpleDateFormat("yyyy");
            String year = df.format(new Date());
            int u = Integer.parseInt(year) - Integer.parseInt(dates);
            return u;
        } else {
            return 0;
        }
    }

    /**
     * 得到手机分辨率的宽度
     *
     * @return
     */
    public static int getEqumentWidth(Activity activity) {
        /* 必须引用android.util.DisplayMetrics */
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 得到手机分辨率的高度
     *
     * @return
     */
    public static int getEqumentHeight(Activity activity) {
        /* 必须引用android.util.DisplayMetrics */
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public static void getTwoSpan(Context context, TextView textTV, String key, float relativeSize, String color) {
        String text = textTV.getText().toString();
        int end = text.indexOf(key);
        SpannableString ss = new SpannableString(text);
        ss.setSpan(new RelativeSizeSpan(relativeSize), end, end + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new StyleSpan(Typeface.BOLD), 0, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(Color.parseColor(color)), end, end + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textTV.setText(ss);
    }

    public static Map<String,Object> getJsonToMap(String jsonData) throws Exception{
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.readValue(jsonData, Map.class);
    }

    public static List<Map<String,Object>> getJsonToList(String jsonData) throws Exception{
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.readValue(jsonData, List.class);
    }
}
