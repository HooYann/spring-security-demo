package cn.beautybase.authorization.utils;

import java.util.regex.Pattern;

public class EmailUtils {

    private static Pattern pattern = Pattern.compile("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+");

    public static boolean match(String email) {
        return pattern.matcher(email).matches();
    }

}
