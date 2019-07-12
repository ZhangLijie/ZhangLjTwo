package com.zxc.zhanglj.utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: 正则表达式验证工具类
 * author: ZLJ
 * date: 16/12/12 下午2:18
 */
public class RegexUtil {

    private static String TAG = "RegexUtil";

    /**
     * a标签验证表达式
     */
    private static String aLabelRegex = "<a.*?href=[\"']?((https?://)?/?[^\"']+)[\"']?.*?>(.+)</a>";

    private static String expressionRegex = "\\[(([\\u4E00-\\u9FA5])|([A-Z])){1,3}\\]";

    private static String urlRegex = "((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?";

    private static String H5Regex = "<([^>]*)>";

    public static boolean regexALabel(String str) {
        Pattern pattern = Pattern.compile(aLabelRegex);
        Matcher matcher = pattern.matcher(str);

        if (matcher.find()) {
            return true;
        }
        return false;
    }

    /**
     * 是否有H5标签
     *
     * @param str
     * @return
     */
    public static boolean regexH5Label(String str) {
        Pattern pattern = Pattern.compile(H5Regex);
        Matcher matcher = pattern.matcher(str);

        if (matcher.find()) {
            return true;
        }
        return false;
    }

    public static boolean regexExpression(String str) {
        Pattern pattern = Pattern.compile(expressionRegex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    /**
     * 获取过滤标签后的字符串
     *
     * @param str
     * @return
     */
    public static String replaceLabel(String str) {
//        str = str.replaceFirst(aLabelRegex, "Hello world");

        Pattern pattern = Pattern.compile(aLabelRegex);
        Matcher matcher = pattern.matcher(str);

        if (matcher.find()) {
            String title = matcher.group(3).trim();
            str = str.replaceFirst(aLabelRegex, title);
        }

        return str;
    }

    public static String replaceH5Label(String str) {

        Pattern pattern = Pattern.compile(H5Regex);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        boolean result1 = matcher.find();
        while (result1) {
            matcher.appendReplacement(sb, "");
            result1 = matcher.find();
        }
        matcher.appendTail(sb);
        return sb.toString();
//        Pattern pattern = Pattern.compile(H5Regex);
//        Matcher matcher = pattern.matcher(str);
//        return str.replaceAll(H5Regex, str);
    }

    public static String getValueOfLabel(String string) {

        Pattern p = Pattern.compile("<a.+?href=\"(.+?)\".*>(.+)</a>");
        Matcher matcher = p.matcher(string);
        while (matcher.find()) {
            String str = matcher.group();
            LogUtil.i(TAG, "str  :  " + str);
        }
        return "";
    }

    /**
     * 在字符串中获取url集合
     *
     * @param string
     * @return
     */
    public static ArrayList<String> getUrlList(String string) {
        Pattern p = Pattern.compile(urlRegex);
        Matcher matcher = p.matcher(string);
        boolean result = matcher.find();
        if (!result) {
            return null;
        }
        ArrayList<String> resultList = new ArrayList<>();
        while (result) {
            resultList.add(matcher.group());
            result = matcher.find();
        }
        return resultList;
    }
}
