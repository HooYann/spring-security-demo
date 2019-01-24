package cn.beautybase.authorization.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author zhuangjiewei
 * @date 2018-06-06
 */
public class PhoneNumberUtils {

    private static Pattern china = Pattern.compile("^1\\d{10}$");
    private static Pattern hk = Pattern.compile("^(5|6|8|9)\\d{7}$");

    /**
     * 大陆号码或香港号码均可
     */
    public static boolean match(String phoneNumber)throws PatternSyntaxException {
        return matchChina(phoneNumber) || matchHK(phoneNumber);
    }

    /**
     * 大陆手机号码11位数
     */
    public static boolean matchChina(String phoneNumber) throws PatternSyntaxException {
        return china.matcher(phoneNumber).matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean matchHK(String phoneNumber)throws PatternSyntaxException {
        return hk.matcher(phoneNumber).matches();
    }
}
