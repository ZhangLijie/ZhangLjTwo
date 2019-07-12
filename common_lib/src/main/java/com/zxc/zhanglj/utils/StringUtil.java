package com.zxc.zhanglj.utils;

import android.text.TextUtils;
import android.util.Log;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具类
 *
 * @author like1
 */
public class StringUtil {

    /**
     * 拼装网络请求地址
     *
     * @param url       网络请求地址
     * @param paramsMap 待拼接的参数集合
     * @return 拼接后的请求地址
     */
    public static String getUrlParamsByMap(String url, Map paramsMap) {
        if (url != null) {
            if (paramsMap != null) {
                String args = "";
                if (!"".equals(paramsMap) && paramsMap.size() > 0) {
                    Set<String> key = paramsMap.keySet();
                    for (Iterator it = key.iterator(); it.hasNext(); ) {
                        String s = (String) it.next();
                        args = args + s + "=" + paramsMap.get(s) + "&";
                    }
                }

                if (args != null && args.length() > 0) {
                    args = args.substring(0, args.length() - 1);
                    url = url + "?" + args;
                }
            }

            return url;
        }

        return null;
    }

    /**
     * get 请求组合 "?"+参数
     *
     * @param paramsMap
     * @return
     */
    public static String organizeParams(Map paramsMap) {
        String args = "";
        if (paramsMap != null) {
            if (!"".equals(paramsMap) && paramsMap.size() > 0) {
                Set<String> key = paramsMap.keySet();
                for (Iterator it = key.iterator(); it.hasNext(); ) {
                    String s = (String) it.next();
                    args = args + s + "=" + paramsMap.get(s) + "&";
                }
            }

            if (args != null && args.length() > 0) {
                args = args.substring(0, args.length() - 1);
                args = "?" + args;
            }
        }

        return args;
    }

    /**
     * map 转String
     *
     * @param map
     * @return
     */
    public static String transMapToString(Map map) {
        if (map == null) {
            return null;
        }
        java.util.Map.Entry entry;
        StringBuffer sb = new StringBuffer();
        for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
            entry = (java.util.Map.Entry) iterator.next();
            sb.append(entry.getKey().toString()).append("=").append(null == entry.getValue() ? "" :
                    entry.getValue().toString()).append(iterator.hasNext() ? ";" : "");
        }
        return sb.toString();
    }

    /**
     * 过滤特殊字符
     *
     * @param str
     * @return
     */
    public static String fiterSpecialCharacter(String str) {
        if (str != null) {
            return str.replaceAll("[^a-zA-Z0-9\\u4E00-\\u9FA5]", "");
        }

        return null;
    }

    /**
     * 过滤所有空格，制表符等
     *
     * @param str
     * @return
     */
    public static String fiterEmptyCharacter(String str) {
        if (str != null) {
            return str.replaceAll("[\\s*|\t|\r|\n]", "");
        }

        return null;
    }

    /**
     * 是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 数组转逗号分隔的字符串
     *
     * @param ig
     * @return
     */
    public static String converToString(String[] ig) {
        String str = "";
        if (ig != null && ig.length > 0) {
            for (int i = 0; i < ig.length; i++) {
                str += ig[i] + ",";
            }
        }
        if (!TextUtils.isEmpty(str)) {
            str = str.substring(0, str.length() - 1);
        }

        return str;
    }

    /**
     * ArrayList转逗号分隔的字符串
     *
     * @param
     * @return
     */
    public static String converToString(List list) {
        String str = "";
        try {
            int size = list.size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    str += list.get(i) + ",";
                }
            }
            if (!TextUtils.isEmpty(str)) {
                str = str.substring(0, str.length() - 1);
            }
        } catch (Exception e) {
            Log.i("", "converToString " + e.toString());
        }

        return str;
    }

    /**
     * 保证source中字母大小写不变，忽略newString的字符的大小写
     *
     * @param source    源字符串
     * @param oldString 需要被替换的字符串
     * @param newString 待替换的字符串
     * @return
     */
//    public static String replaceWithSourceText(String source, String oldString, String newString) {

//        StringBuffer buffer = new StringBuffer();
//        Matcher matcher = Pattern.compile(oldString).matcher(source);
//
//        while (matcher.find()) {
//            matcher.appendReplacement(buffer, "<font color='red'>" + matcher.group() + "</font>");//这样保证了原文的大小写没有发生变化
//        }
//        matcher.appendTail(buffer);
//        nickname = buffer.toString();
//        return null;
//    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
        /*
        移动：139、138、137、136、135、134、
		    159、158、157、150、151、152、
		    147(数据卡)、
		    188、187、182、183、184、
		    178
		联通：130、131、132、156、155、186、185、145(数据卡)、176
		电信：133、153、180、189、（1349卫通）、181、177、173(待放)
		总结起来就是第一位必定为1，第二位必定为3、4或5或7、8，其他位置的可以为0-9
		*/
        String telRegex = "[1][2345789]\\d{9}";//"[1]"代表第1位为数字1，"[2345789]"代表第二位可以为里面中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    public static String getTelnum(String sParam) {

        if (TextUtils.isEmpty(sParam))
            return null;
        Pattern pattern = Pattern.compile("[1][2345789]\\d{9}");
        Matcher matcher = pattern.matcher(sParam);
        StringBuffer bf = new StringBuffer();
        while (matcher != null && matcher.find()) {
            bf.append(matcher.group()).append(",");
        }
        int len = bf.length();
        if (len > 0) {
            bf.deleteCharAt(len - 1);
        }
        return bf.toString();
    }

    /**
     * @param str
     * @return
     */
    public static String trim(String str) {
        return str == null ? "" : str.trim();
    }

    /**
     * Helper function for making null strings safe for comparisons, etc.
     *
     * @return (s == null) ? "" : s;
     */
    public static String makeSafe(String s) {
        return (s == null) ? "" : s;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0 || str.equalsIgnoreCase("null");
    }
}
