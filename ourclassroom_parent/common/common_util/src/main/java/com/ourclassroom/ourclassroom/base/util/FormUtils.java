package com.ourclassroom.ourclassroom.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormUtils {

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^([A-Za-z0-9_\\-\\.])+@([A-Za-z0-9_\\-\\.])+\\.\\w+$");
        Matcher m = pattern.matcher(email);
        return m.matches();
    }

}
